#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

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

int main(int argc, char *argv[]){
	int childID;
	int r;
	int count=1;
	int prompt = 0;
	char buff[2048];

	if (argc>1) {
		childID = atoi(argv[1]);
	} else {
		exit(1);
	}
	if (argc==3) {
	   prompt=1;
	}

	ts start;
	ts end;
	ts now;

	clock_gettime(CLOCK_MONOTONIC,&start);
	end.tv_sec = start.tv_sec + 30 ;
	now=start;
	ts *temp;
	while (now.tv_sec<end.tv_sec){
		if (prompt) {
		   fprintf(stderr,"Enter a message:\n");
		   fgets(buff,sizeof(buff),stdin);
		} else {
		  sprintf(buff,"Message %d",count);
		}
		clock_gettime(CLOCK_MONOTONIC,&now);
		temp = diff(&start,&now);
		fprintf(stdout," 0:%06.3f : Child %d %s\n",timespec_to_double(temp),childID,buff);
		fflush(stdout);
		free(temp);
		count++;
		r= rand()%3;
		sleep(r);
	}
	fprintf(stderr,"Child %d exiting\n",childID);
	exit(0);
}
