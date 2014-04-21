#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <time.h>
#include <limits.h>

char* getInput() {
	char buf[128];
	printf("Enter a Message: ");
	char *input = fgets(buf, 128, stdin);
	getchar();
	return input;
}

typedef struct timespec ts;

double timespec_to_double(ts *ts1) {
	return (double) (ts1->tv_sec + ((double) ts1->tv_nsec) * 1.0E-9);
}

ts *diff(ts *t1, ts *t2) {
	ts *t3;
	t3 = (ts *) malloc(sizeof(ts));
//    if (t2->tv_nsec < t1->tv_nsec) {
//    	t3->tv_sec = t2->tv_sec-t1->tv_sec-1;
//    	t3->tv_nsec = 1000000000 + t2->tv_nsec-t1->tv_nsec;
//    } else {
	t3->tv_sec = t2->tv_sec - t1->tv_sec;
	t3->tv_nsec = t2->tv_nsec - t1->tv_nsec;
//    }
	return t3;
}

int main(int argc, char *argv[]) {
	FILE *pipe_fd[6], *fp;
	fp = fopen("output.txt", "w");
	fd_set set;
	struct timeval timeout;
	timeout.tv_sec = 0;
	timeout.tv_usec = 100;

	int i = 0;
	// bad form but its a lot easier then a loop
	if ((pipe_fd[0] = popen("./child 1", "r")) == NULL) {
		perror("popen child 1 failed");
		exit(1);
	}
	if ((pipe_fd[1] = popen("./child 2", "r")) == NULL) {
		perror("popen child 2 failed");
		exit(1);
	}
	if ((pipe_fd[2] = popen("./child 3", "r")) == NULL) {
		perror("popen child 3 failed");
		exit(1);
	}
	if ((pipe_fd[3] = popen("./child 4", "r")) == NULL) {
		perror("popen child 4 failed");
		exit(1);
	}
	//pclose(pipe_fd[3]);
	//the call to build child five should go here
	//child five will have to use the long format for piping.

	// put all the file descriptors into a set
	FD_ZERO(&set);
	int fds = INT_MIN;
	int fn = 0;
	for (i = 0; i < 4; i++) {
		fn = fileno(pipe_fd[i]);
		printf("File %d for i=%d\n", fn, i);
		FD_SET(fn, &set);
		fds = (fds < fn) ? fn : fds;
	}
	printf("fds = %d\n", fds);

	ts start;
	ts now;
	clock_gettime(CLOCK_MONOTONIC, &start);
	now = start;
	ts *temp;
	int count = 40;
	int nfd; // = 0;
	char *buff[2048];
	char *rv;
	// count<40 debug stop
	while (count > 0) {
		//printf("in the while loop\n");
		timeout.tv_sec = 1;
		timeout.tv_usec = 0;
		clock_gettime(CLOCK_MONOTONIC, &now);
		temp = diff(&start, &now);
		printf("before select %f \n", timespec_to_double(temp));
		nfd = select(fds + 1, &set, NULL, NULL, &timeout);
		clock_gettime(CLOCK_MONOTONIC, &now);
		temp = diff(&start, &now);
		printf("after select %f \n", timespec_to_double(temp));
		printf("ndf = %d\n", nfd);
		if (nfd) {

			for (i = 0; i < 4; i++) {
				clock_gettime(CLOCK_MONOTONIC, &now);
				temp = diff(&start, &now);
				printf("got into the loop %f \n", timespec_to_double(temp));
				if (FD_ISSET(fileno(pipe_fd[i]),&set)) {
					printf("get to the fget\n");
					rv = fgets(*buff, 2048, pipe_fd[i]);
					if (rv == NULL) {
						FD_CLR(fileno(pipe_fd[i]), &set);

					} else {
						printf(" 0:%06.3f : %s\n", timespec_to_double(temp),
								*buff);
					}
				}
			}
			count--;
		} else if (nfd < 0) {
			// error condition
			perror("select()");
		}
	}
	exit(0);
}

