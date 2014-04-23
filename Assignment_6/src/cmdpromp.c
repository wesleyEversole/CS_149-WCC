#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <time.h>
#include <limits.h>

typedef struct timespec ts;

double timespec_to_double(ts * ts1) {
	return (double) (ts1->tv_sec + ((double) ts1->tv_nsec) * 1.0E-9);
}

ts *diff(ts * t1, ts * t2) {
	ts *t3;
	t3 = (ts *) malloc(sizeof(ts));
// if (t2->tv_nsec < t1->tv_nsec) {
// t3->tv_sec = t2->tv_sec-t1->tv_sec-1;
// t3->tv_nsec = 1000000000 + t2->tv_nsec-t1->tv_nsec;
// } else {
	t3->tv_sec = t2->tv_sec - t1->tv_sec;
	t3->tv_nsec = t2->tv_nsec - t1->tv_nsec;
// }
	return t3;
}

int main(int argc, char *argv[]) {
	FILE *pipe_fd[6], *fp;
	fp = fopen("output.txt", "w");
	fd_set set;
	struct timeval timeout;

	timeout.tv_sec = 30;
	timeout.tv_usec = 0;

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
	if ((pipe_fd[4] = popen("./child 5 prompt", "r")) == NULL) {
		perror("popen child 5 failed");
		exit(1);
	}

	int fds = INT_MIN;
	int fn = 0;

	ts start;
	ts now;
	clock_gettime(CLOCK_MONOTONIC, &start);
	now = start;
	ts *temp;

	int count = 40;
	int nfd;			// = 0;
	char buff[2048];
	char *rv;
// count<40 debug stop
	while (count > 0) {
// put all the file descriptors into a set
		FD_ZERO(&set);
		for (i = 0; i < 5; i++) {
			fn = fileno(pipe_fd[i]);
			FD_SET(fn, &set);
			fds = (fds < fn) ? fn : fds;
		}
		//printf("fds = %d max FD is %d\n", fds, FD_SETSIZE);

// printf("in the while loop\n");
		timeout.tv_sec = 1;
		timeout.tv_usec = 0;
		clock_gettime(CLOCK_MONOTONIC, &now);
		temp = diff(&start, &now);
		//printf("before select %f \n", timespec_to_double(temp));
		nfd = select(fds + 1, &set, NULL, NULL, &timeout);
		clock_gettime(CLOCK_MONOTONIC, &now);
		temp = diff(&start, &now);
		//printf("after select %f \n", timespec_to_double(temp));
		//printf("ndf = %d\n", nfd);
		free(temp);
		if (nfd < 0) {
			perror("select()");
			exit(1);
		}

		if (nfd == 0) {
			count--;
			continue;
		}
		for (i = 0; i < 5; i++) {
			clock_gettime(CLOCK_MONOTONIC, &now);
			temp = diff(&start, &now);
			//printf("%d got into the loop %f \n", i, timespec_to_double(temp));
			if (FD_ISSET(fileno(pipe_fd[i]), &set)) {
				//printf("%d got to the fget\n", i);
				rv = fgets(buff, sizeof(buff), pipe_fd[i]);
				if (rv == NULL) {
					FD_CLR(fileno(pipe_fd[i]), &set);
				} else {
					fprintf(fp," 0:%06.3f : %s\n", timespec_to_double(temp), buff);
				}
			}
			free(temp);
		}
		count--;
	}
	exit(0);
}
