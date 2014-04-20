#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <time.h>

char* getInput() {
	char buf[128];
	printf("Enter a Message: ");
	char *input = fgets(buf, 128, stdin);
	getchar();
	return input;
}

typedef struct timespec ts;

double timespec_to_double(ts *ts1) {
	return (double)(ts1->tv_sec + ((double)ts1->tv_nsec)*1.0E-9);
}

ts *diff(ts *t1, ts *t2) {
	ts *t3;
	t3 = (ts *)malloc(sizeof(ts));
//    if (t2->tv_nsec < t1->tv_nsec) {
//    	t3->tv_sec = t2->tv_sec-t1->tv_sec-1;
//    	t3->tv_nsec = 1000000000 + t2->tv_nsec-t1->tv_nsec;
//    } else {
    	t3->tv_sec = t2->tv_sec-t1->tv_sec;
    	t3->tv_nsec = t2->tv_nsec-t1->tv_nsec;
//    }
    return t3;
}

int main(int argc, char *argv[]) {
	FILE *pipe_fd[6];
	fd_set set;
	struct timeval timeout;
	timeout.tv_sec=30;
	timeout.tv_usec=0;

	int i = 0;
	// bad form but its a lot easier then a loop
	if ((pipe_fd[0] = popen("./child 1", "w")) == NULL) {
		perror("popen child 1 failed");
		exit(1);
	}
	if ((pipe_fd[1] = popen("./child 2", "w")) == NULL) {
		perror("popen child 2 failed");
		exit(1);
	}
	if ((pipe_fd[2] = popen("./child 3", "w")) == NULL) {
		perror("popen child 3 failed");
		exit(1);
	}
	if ((pipe_fd[3] = popen("./child 4", "w")) == NULL) {
		perror("popen child 4 failed");
		exit(1);
	}
	//the call to build child five should go here
	//child five will have to use the long format for piping.

	// put all the file descriptors into a set
	FD_ZERO(&set);

	for (i=0; i<4;i++) {
		FD_SET(fileno(pipe_fd[i]),&set);
	}


	ts start;
	ts now;
	clock_gettime(CLOCK_MONOTONIC,&start);
	now=start;
	ts *temp;
	int count = 0;
    int nfd = 0;
    int fds = 4;
    char *buff[2048];
    char *rv;
    // count<40 debug stop
	while (count<40) {
        nfd = select(fds, &set, NULL, NULL,  &timeout);
		for (i=0; i<4;i++) {
			if (FD_ISSET(fileno(pipe_fd[i]),&set)) {
				rv = fgets(*buff,2048,pipe_fd[i]);
				if (rv==NULL) {
					FD_CLR(fileno(pipe_fd[i]),&set);
					fds--;//??????
				} else {
					printf(" 0:%06.3f : %s\n",timespec_to_double(temp),*buff);
				}
			}
		}
		temp = diff(&start,&now);
		clock_gettime(CLOCK_MONOTONIC,&now);

        count++;
	}
	exit(0);
}

