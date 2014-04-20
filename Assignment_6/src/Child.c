#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>

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
	if (argc>1) {
		childID = atoi(argv[1]);
	} else {
		exit(1);
	}


	ts start;
	ts end;
	ts now;


	clock_gettime(CLOCK_MONOTONIC,&start);
	end.tv_sec = start.tv_sec +30 ;
	now=start;
	ts *temp;
	while (now.tv_sec<end.tv_sec){
		r= rand()%3;
		temp = diff(&start,&now);
		printf(" 0:%06.3f : Child %d Message %d\n",timespec_to_double(temp),childID,count);//fflush(stdout);
		free(temp);
		count++;
		sleep(r);

		clock_gettime(CLOCK_MONOTONIC,&now);
	}
	exit(0);
}
