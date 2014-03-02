#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define LOCK(l) pthread_mutex_lock((l))
#define UNLOCK(l) pthread_mutex_unlock((l))
#define ROWS 10
#define COLUMNS 10
#define SEATS ROWS*COLUMNS
#define NUM_SELLERS 10

// forward declare my objects and methods
typedef struct person Person;
typedef struct seller Seller;
typedef struct concert Concert;

typedef enum boolean {
	FALSE = 0, TRUE = 1, false = 0, true = 1, False = 0, True = 1, F = 0, T = 1
} Boolean;

Person *mkSelfReferential(Person* p);

//
typedef enum price {
	HIGH, MEDIUM, LOW
} Price;

struct seller {
	pthread_mutex_t *lock;
	Price price;
	int id;
	int pid; // next person id
	Person *que; //que->prev points to seller.que when at head (Tricky)
	Person *tail;
	int lastRow;
};

struct person {
	Person *prev;
	Person *next;
	Seller *seller;
	int row;
	int seat;
	int id;
	float arrival;
	Boolean inQ;
};

struct concert {
	pthread_mutex_t *lock;
	Person * seats[ROWS + 1][COLUMNS + 1];
	Boolean isSoldOut;
	Boolean hasStarted;
};

Concert hall;

Seller *garbage;

Seller *allSellers[NUM_SELLERS];

// Constructors
Seller *createSeller(Price p, int id) {
	Seller *s;
	s = malloc(sizeof(Seller));
	s->lock = (pthread_mutex_t *)malloc(sizeof(pthread_mutex_t));
	s->id = id;
	s->price = p;
	s->que = NULL;
	s->tail = NULL;
	s->pid = 1;
	switch (p) {
	case HIGH:
		s->lastRow = 1;
		break;
	case MEDIUM:
		s->lastRow = 5;
		break;
	case LOW:
		s->lastRow = 10;
		break;
	default:
		printf("Error - we've been hacked Price is %d", p);
		exit(-1);
	}
	return s;
}

Person *createPerson(int sellerNum) {
	Person *p;
	p = malloc(sizeof(Person));
	p = mkSelfReferential(p);
	p->inQ = FALSE;
	// assign to a seller???
	return p;
}
// general utility methods

Boolean isRowFull(Concert *hall, int row) {
	// return 1 if row has no null pointers in columns 1 to 10
	// return 0 if any column has a null
	return TRUE;
}

// Action methods

void queAdd(Seller *s, Person *person) {
	// add
	person->id = s->pid++;

	if (s->que == NULL) {
		person=mkSelfReferential(person) ;// should already be true
		s->que = person;
		s->tail = person;
	} else {
		person->prev = s->tail;
		s->tail->next = person;
		s->tail = person;
	}
}

Person *mkSelfReferential(Person* p) {
	p->next = p;
	p->prev = p;
	return p;
}

Person *queRemove(Seller *s,Person *que) {
	Person *p;
	if (que == NULL) {
		return NULL;
	} else {
		p = que;
		if (p->next == que && p->prev==que) {
			// only one person on queue
			// que is now empty
			s->que = NULL;
		} else if (p->next == que) {
			// tail removal
			que->prev->next = que->prev;
		} else {
			que->next->prev = que->prev;
			if (p->prev == que) {
				// head removal (normal case)
				s->que = p->next;
			} else {
				que->prev = que->next;
			}
		}
	}
	p = mkSelfReferential(p);
	p->inQ = FALSE;
	return p;
}
void *addPerson(Person *person) {
	sleep(person->arrival);
	LOCK(person->seller->lock);
	person->inQ = TRUE;
	queAdd(person->seller, person);
	UNLOCK(person->seller->lock);
	// can explicitly kill thread if we want
	return NULL;
}

Person *removePerson(Person *person) {
	LOCK(person->seller->lock);
	person = queRemove(person->seller, person);
	UNLOCK(person->seller->lock);
	return person;
}

void frustratedPerson(Person *person) {
	// this will be threaded... does it need a return ?
	if (person->inQ) {
		person = removePerson(person);
		free(person);
	}
	// can explicitly kill thread if we want
}
void getHighSeat(Concert *hall, Person *p) {
	// fill from row 1 to row 10
	// fill from column 1 to column 10
}

void getLowSeat(Concert *hall, Person *p) {
	// fill from row 10 to row 1
	// fill from column 10 to column 1
}

void getMediumSeat(Concert *hall, Person *p) {
   // act like high or low depending on set point
	int row = p->seller->lastRow;
    while (row>0 && row<=ROWS && isRowFull(hall,row)) {
	   switch (row) {
	   case 5: row = 6; break;
	   case 6: row = 4;  break;
	   case 4:row = 7;break;
	   case 7: row=3; break;
	   case 3: row=8; break;
	   case 8: row=2;  break;
	   case 2: row=9; break;
	   case 9: row=1; break;
	   case 1: row=10; break;
	   default: // something wrong
		   row = 0;
		   break;
	   }
   }
   if  (row >0 && row <= ROWS) {
	   // legal row to check for seat
	   p->seller->lastRow = row;
	   if (row <=5) {
		   getLowSeat(hall,p);
	   } else {
		   getHighSeat(hall,p);
	   }
   }
}


void getSeat(Concert *hall, Person *person, Price p) {
	LOCK(hall->lock);
	switch (p) {
	case HIGH:
		getHighSeat(hall, person);
		break;
	case MEDIUM:
		getMediumSeat(hall, person);
		break;
	case LOW:
		getLowSeat(hall, person);
		break;
	default:
		// error condition
		printf("FATAL error - we've been hacked. Code %d requested", p);
		exit(-1);
	}
	UNLOCK(hall->lock);
}

int getRandomTime(int low,int high) {
	// create random number in range
	//   (rnd()*(high-low))+low
	return high;
}
void sellTickets(Seller *s) {
	// threaded method for seller
    Person *p;
    int tl,th;

	while (!(hall.hasStarted || hall.isSoldOut)) {
		if (s->que != NULL) {
			p=removePerson(s->que);
			getSeat(&hall,p,s->price);
			switch (s->price) {
			case HIGH: tl=1;th=2;  break;
			case MEDIUM: tl=2;th=4; break;
			case LOW: tl=4;th=7; break;
			default: // broken code
				printf("Error, we've been hacked Price is %d",s->price);
				exit(-1);
			}
			sleep(getRandomTime(tl,th));// random time based on price
			// this code needed to handle frustrated customers safely
			LOCK(garbage->lock);
			queAdd(garbage, p);
			UNLOCK(garbage->lock);
		}
	}
	// thread finished
}

void doorsCloseIn60Minutes() {
	sleep(60);
}
void mkTest() {
	printf("Self Test\n");
}

main(int argc, char *argv[]) {
	printf("Threaded Ticket Seller - Project  3\n");
	// Garbage seller is used to hold persons that
	//   have been processed by the other sellers
	//   we have to wait on possible frustrated users
	//   threads have cleared or they may crash when their
	//   data is freed
	garbage = createSeller(HIGH,0);
	// finish creation of the concert hall
	hall.isSoldOut=FALSE;
	hall.hasStarted=FALSE;
	hall.lock = (pthread_mutex_t *)malloc(sizeof(pthread_mutex_t));

	if (argc == 2) {
		if (argv[1][0]=='T') {
			// do self test
			mkTest();
			exit(0);
		}
		int N = atoi(argv[1]);
		printf("Run with %d customers per ticket seller\n",N);
	// create sellers
	Seller *allSellers[NUM_SELLERS];

	// create attendees

	Person *p;
	p = (Person *) malloc(sizeof(Person));

	//pthread_create(pthread_t *thread_id, const pthread_attr_t *attributes,
	//        void *(*thread_function)(void *), void *arguments);
	} else {
		printf("Usage:\n");
		printf("threadSeller [T|number]\n");
		printf(" T - run self test without any threading\n");
		printf(" number - number of customers per ticket seller\n");
		printf("             full threaded simulation\n");
	}
}

void output() {

}

