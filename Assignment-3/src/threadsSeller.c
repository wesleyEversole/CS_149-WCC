#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <assert.h>
#include <string.h>

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
    Boolean open;
	int id;
	int pid; // next person id
	Person *que; //que->prev points to seller.que when at head (Tricky)
	Person *tail;
	int lastRow;
	int lastColumn;
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

pthread_mutex_t outputLock;

Concert hall;

Seller *garbage;

Seller *allSellers[NUM_SELLERS];

int minutes; // simulated system time

char buffer[512];
char *getTime() {
	sprintf(buffer,"0:%d",minutes);
	return buffer;
}

// Constructors
Seller *createSeller(Price p, int id) {
	Seller *s;
	s = malloc(sizeof(Seller));
	s->lock = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t));
	pthread_mutex_init(s->lock,NULL);
	s->que = NULL;
	s->tail = NULL;
	s->pid = 1;
	s->id = id;
	s->price = p;
	s->lastColumn = 1;
    s->open = TRUE;
	switch (p) {
	case HIGH:
		s->lastRow = 1;
		break;
	case MEDIUM:
		s->lastRow = 5;
		break;
	case LOW:
		s->lastRow = 10;
		s->lastColumn = 10;
		break;
	default:
		printf("Error - we've been hacked Price is %d", p);
		exit(-1);
	}
	return s;
}
/**
 * createPerson()
 *   create a person and assign to the seller passed as an argument
 */
Person *createPerson(Seller *s) {
	Person *p;
	p = malloc(sizeof(Person));
	p->inQ = FALSE;
	p->seller = s;
	p->arrival = rand() % 60;
	p = mkSelfReferential(p);
	return p;
}
// general utility methods

Boolean isRowFull(Concert *hall, int row) {
	int i;
	for (i = 1; i <= COLUMNS; i++) {
		if (hall->seats[row][i]==NULL)
			return FALSE;
	}
	return TRUE;
}

Boolean isSeatEmpty(Concert *hall, int row, int column){
	return (hall->seats[row][column] == NULL);
}

Boolean isSoldOut(Concert *hall){
	int r;
	int c;
	for (r = 1; r <= ROWS; r++) {
		for(c = 1; c <= COLUMNS; c++){
			if (hall->seats[r][c]==NULL)
				return FALSE;
		}
	}
	return TRUE;
}
// Action methods

void queAdd(Seller *s, Person *person) {
	// add
	if(s->id != 11){
		person->id = s->pid++;
	}

	if (s->que == NULL) {
		person = mkSelfReferential(person); // should already be true
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

Person *queRemove(Seller *s, Person *que) {
	Person *p;
	if (que == NULL) {
		return NULL;
	} else {
		p = que;
		if (p->next == que && p->prev == que) {
			// only one person on queue
			// que is now empty
			s->que = NULL;
		} else if (p->next == que) {
			// tail removal
			que->prev->next = que->prev;
		} else {
			// middle form?
			que->next->prev = que->prev;
			if (p->prev == que) {
				// head removal (normal case)
				s->que = p->next;
				p->next->prev = p->next;
			} else {
				// remove from the middle
				que->prev->next = que->next;
				que->next->prev =  que->prev;
			}
		}
	}
	p = mkSelfReferential(p);
	p->inQ = FALSE;
	return p;
}
void *addPerson(void *param) {
	Person *person = (Person *) param;
	sleep(person->arrival);
	LOCK(person->seller->lock);
	person->inQ = TRUE;
	queAdd(person->seller, person);
	UNLOCK(person->seller->lock);
	// can explicitly kill thread if we want
	return person;
}

Person *removePerson(Person *person) {
	if (person==NULL) return NULL;
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

void output() {
	//printing the seats layout
	printf("Time:%s	FRONT	\n",getTime());
	printf("------------------------------\n");
	int row;
	int col;
	for (row = 1; row <= ROWS; row++){
		printf("| ");
		for (col = 0; col <= COLUMNS; col++)
		{
			Person *p = hall.seats[row][col];
			if(hall.seats[row][col] == NULL)
			{
				printf("  @  |");
			} else {
				switch (p->seller->price) {
					case HIGH:
						printf("H");
						break;
					case MEDIUM:
						printf("M");
						break;
					case LOW:
						printf("L");
						break;
					default:
						break;
				}
				printf("%d", p->seller->id);
				if (p->id < 10)
				{
					printf("0");
				}
				printf("%d |", p->id);
			}
		}
		printf("\n");
	}
	printf("------------------------------\n");
	printf("	BACK	\n");
}

void *timer (void *parm) {
	Boolean done = false;
	minutes =0;
	while (!done) {
		sleep(1);
		minutes++;
		if (minutes>240) {
			done = TRUE; // stop after 4 hours maximum
		}
	}
	return NULL;
}

void getHighSeat(Concert *hall, Person *p) {

	int row = p->seller->lastRow;
	int column = p->seller->lastColumn;
	// fill from row 1 to row 10
	while (row > 0 && row <= ROWS && !isSeatEmpty(hall, row, column))
	{
		if(column == 10){
			column = 1;
			row = row + 1;
		}
		else {
			column = column + 1;
		}
	}


	// fill from column 1 to column 10
	hall->seats[row][column] = p;
	p->seller->lastRow = row;
	p->seller->lastColumn = column;
	//printf("Assign high value seat row %d\n",p->seller->lastRow);
}

void getLowSeat(Concert *hall, Person *p) {
	// fill from row 10 to row 1
	int row = p->seller->lastRow;
	int column = p->seller->lastColumn;

	while (row > 0 && row <= ROWS && !isSeatEmpty(hall, row, column))
	{
		if(column == 1){
			column = 10;
			row = row - 1;
		}
		else {
			column = column - 1;
		}
	}
	// fill from column 10 to column 1
	hall->seats[row][column] = p;
	p->seller->lastRow = row;
	p->seller->lastColumn = column;
	//printf("Assign low value seat row %d\n",p->seller->lastRow);
}

void getMediumSeat(Concert *hall, Person *p) {
	// act like high or low depending on set point
	int row = p->seller->lastRow;

	while (row > 0 && row <= ROWS && isRowFull(hall, row)) {
		switch (row) {
		case 5:
			row = 6;
			break;
		case 6:
			row = 4;
			break;
		case 4:
			row = 7;
			break;
		case 7:
			row = 3;
			break;
		case 3:
			row = 8;
			break;
		case 8:
			row = 2;
			break;
		case 2:
			row = 9;
			break;
		case 9:
			row = 1;
			break;
		case 1:
			row = 10;
			break;
		default: // something wrong
			row = 0;
			break;
		}
	}

	if (row > 0 && row <= ROWS) {
		// legal row to check for seat
		p->seller->lastRow = row;
		if (row <= 5) {
			getHighSeat(hall, p);
		} else {
			getLowSeat(hall, p);
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

int getRandomTime(int low, int high) {
	return (rand() % (high - low)) + low;
}

void *sellTickets(void *param) {
	// threaded method for seller
	if(isSoldOut(&hall)){
		return NULL;
	}
	Person *p;
	int tl, th;
	Seller *s = (Seller *) param;
	while (!(hall.hasStarted || hall.isSoldOut)) {
		if (s->que != NULL) {
			p = removePerson(s->que);
			getSeat(&hall, p, s->price);
			switch (s->price) {
			case HIGH:
				tl = 1;
				th = 2;
				break;
			case MEDIUM:
				tl = 2;
				th = 4;
				break;
			case LOW:
				tl = 4;
				th = 7;
				break;
			default: // broken code
				printf("Error, we've been hacked Price is %d", s->price);
				exit(-1);
			}
			sleep(getRandomTime(tl, th)); // random time based on price
			// this code needed to handle frustrated customers safely
			LOCK(&outputLock);
			output();
			UNLOCK(&outputLock);
			LOCK(garbage->lock);
			queAdd(garbage, p);
			UNLOCK(garbage->lock);
		}
	}
	s->open = FALSE;
	return NULL;
	// thread finished
}

void closeDoorsIn60Minutes() {
	sleep(60);
	LOCK(hall.lock);
	hall.hasStarted = TRUE;
	printf("\nThe concert hall is closed\n\n");
	UNLOCK(hall.lock);
}
void waitForSellersToFinish(Seller *seller[],int numSellers) {
	Boolean notDone=TRUE;
	int sid;
	while(notDone) {
		notDone = FALSE;
		for(sid=0;sid<numSellers;sid++) {
			if (seller[sid]->open) {
				notDone = TRUE;
				break;
			}
		}
		sleep(1);
	}
}
Boolean checkN(int value, int expect, char * msg) {
	if (expect != value) {
		printf("%s: Found %d, Expected %d\n", msg, value, expect);
	}
	return expect != value;
}
Boolean checkT(int expr, char * msg) {
	if (!expr) {
		printf("%s: returned FALSE %d\n", msg, expr);
	}
	return expr;
}
void mkTest() {

	printf(" Seller\n");
	srand(0);
	Seller *s;
	s = createSeller(HIGH, 1);
	assert(checkT(s->price==HIGH,"Seller is HIGH"));
	assert(checkT(s->que==NULL,"Seller starts with empty que"));
	assert(checkN(s->lastRow,1,"HIGH seller starts in row 1"));
	free(s);
	s = createSeller(MEDIUM, 1);
	assert(checkN(s->lastRow,5,"MEDIUM seller starts in row 5"));
	free(s);
	s = createSeller(LOW, 1);
	assert(checkN(s->lastRow,10,"LOW seller starts in row 10"));

	printf(" Person\n");
	Person *p = createPerson(s);
	assert(checkN(p->arrival,43,"p->arrival"));
	assert(checkT(s==p->seller,"checkT"));
	assert(checkT((p->next==p->prev) && (p->prev==p),"p is self referential"));

	// add person to seller
	//   we need 4 people to test que management
	//   for our testing set arrival to 0 and do not thread
	printf(" Add Person\n");
	int i;
	for(i=0; i<4;i++) {
		p=createPerson(s);
		p->arrival = 0;
		addPerson(p);
	}
	assert(checkN(s->que->id,1,"First customer id"));
	assert(checkN(s->que->next->id,2,"Second customer id"));
	assert(checkN(s->que->next->next->id,3,"Third customer id"));
	assert(checkN(s->que->next->next->next->id,4,"Fourth/Last customer id"));
	assert(checkN(s->que->next->next->next->next->id,4,"Fifth customer id (tail points to self"));

	assert(checkT(s->que->inQ==TRUE,"First customer id"));
	assert(checkT(s->que->next->inQ==TRUE,"Second customer id"));
	assert(checkT(s->que->next->next->inQ==TRUE,"Third customer id"));
	assert(checkT(s->que->next->next->next->inQ==TRUE,"Fourth/Last customer id"));

	printf(" Remove Person\n");
	// test removals
	p = removePerson(s->que);// remove person 1
	assert(checkN(p->id,1,"First person on que removed"));
	assert(checkN(s->que->id,2,"First person on que now number 2"));

	p = removePerson(s->que->next); // remove person 3
	assert(checkN(p->id,3,"Second(middle) person on que removed"));
	assert(checkN(s->que->id,2,"First person on que now number 2"));
	assert(checkN(s->que->next->id,4,"Second person should now be #4"));

	p = removePerson(s->que->next); // remove person 4
	assert(checkN(p->id,4,"Second(tail) person on que removed"));
	assert(checkN(s->que->id,2,"First person on que now number 2"));
	assert(checkN(s->que->id,s->que->next->id,"Only one person left on que #2"));
	assert(checkT((p->next==p->prev) && (p->prev==p),"p(2) is self referential"));

	p = removePerson(s->que);// remove person 2 (single person left)
	assert(checkN(p->id,2,"Last person on que removed"));
	assert(checkT(s->que==NULL,"Seller que now empty"));

	p = removePerson(s->que);// remove person from empty que
	assert(checkT(p==NULL,"NULL pointer expected"));

	//
	printf(" getSeat()\n");
	p=createPerson(s); // current low price seller
	p->id = 42;
	// check if row full algorithm even works
	assert(checkT(!isRowFull(&hall,8),"Is row 8 full NO"));

	printf("   LOW\n");
	getSeat(&hall,p,LOW);
	// first call to low assigns 10,10
	Person *cp = hall.seats[10][10];
	assert(checkT(cp!=NULL,"Seat 10,10 occupied"));
	assert(checkN(cp->id,42,"Customer 42 has the seat"));

	printf("   HIGH\n");
	p->seller->lastRow=1;
	getSeat(&hall,p,HIGH);
	// first call to low assigns 1,1
	cp = hall.seats[1][1];
	assert(checkT(cp!=NULL,"Seat 1,1 occupied"));
	assert(checkN(cp->id,42,"Customer 42 has the seat"));

	printf("   MEDIUM\n");
	p->seller->lastRow=5;
	getSeat(&hall,p,MEDIUM);
	// first call to low assigns 5,10
	cp = hall.seats[5][10];
	assert(checkT(cp!=NULL,"Seat 5,10 occupied"));
	assert(checkN(cp->id,42,"Customer 42 has the seat"));

	p->seller->lastRow=3;
	getSeat(&hall,p,MEDIUM);
	// first call to low assigns 3,10
	cp = hall.seats[3][10];
	assert(checkT(cp!=NULL,"Seat 3,10 occupied"));
	assert(checkN(cp->id,42,"Customer 42 has the seat"));

	p->seller->lastRow=6;
	getSeat(&hall,p,MEDIUM);
	// first call to low assigns 6,1
	cp = hall.seats[6][1];
	assert(checkT(cp!=NULL,"Seat 6,1 occupied"));
	assert(checkN(cp->id,42,"Customer 42 has the seat"));

	// need non-threaded test for sellTickets method


	// non-threaded test for frustratedPerson()

	printf(" frustratedPerson()\n");
	s = createSeller(HIGH,103);
	p=createPerson(s);
	p->arrival=0;
	addPerson(p);
	frustratedPerson(p);
	assert(checkT(s->que==NULL,"Person has left the building"));
}

int main(int argc, char *argv[]) {

	printf("Threaded Ticket Seller - Project  3\n");
	// Garbage seller is used to hold persons that
	//   have been processed by the other sellers
	//   we have to wait on possible frustrated users
	//   threads have cleared or they may crash when their
	//   data is freed
	garbage = createSeller(HIGH, 11);
	// finish creation of the concert hall
	hall.isSoldOut = FALSE;
	hall.hasStarted = FALSE;
	hall.lock = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t));
    pthread_mutex_init(hall.lock,NULL);
    pthread_mutex_init(&outputLock,NULL);

	if (argc == 2) {
		if (argv[1][0] == 'T') {
			// do self test
			printf("Self Test\n");
			mkTest();
			printf("Self test succeeded\n");
			exit(0);
		}
		int N = atoi(argv[1]);
		printf("Run with %d customers per ticket seller\n", N);

		//Create Sellers
		Seller *allSellers[NUM_SELLERS];

		allSellers[0] = createSeller(HIGH, 0);

		int i;
		for (i = 1; i < 4; i++) {
				allSellers[i] = createSeller(MEDIUM, i);
		}

		for(i = 1; i < 7; i++){
			allSellers[i+3] = createSeller(LOW, i + 3);
		}

		//declare person pointer
		Person *p;

		int sellerNum;
		
		//create thread when adding person to the Seller que
		pthread_t personThreadId[NUM_SELLERS*N];

		//create thread for putting people into concert hall
		pthread_t sellerThreadId[NUM_SELLERS];

		pthread_t timerTID;
		pthread_create(&timerTID,NULL,&timer,NULL);

		for (sellerNum = 0; sellerNum < NUM_SELLERS; sellerNum++)
		{
			pthread_create(&sellerThreadId[sellerNum], NULL,
				       &sellTickets, (void *) allSellers[sellerNum]);
			for(i = 0; i < N; i++)
			{
				p = createPerson(allSellers[sellerNum]);
				p->arrival = 0;
				pthread_create(&personThreadId[sellerNum*N+i], NULL,
					       &addPerson, (void *) p);
			}
		}

		closeDoorsIn60Minutes();
		waitForSellersToFinish(allSellers,NUM_SELLERS);

	} else {
		printf("Usage:\n");
		printf("threadSeller [T|number]\n");
		printf(" T - run self test without any threading\n");
		printf(" number - number of customers per ticket seller\n");
		printf("             full threaded simulation\n");
	}

	printf("Exiting threadSeller\n");
	return 0;
}

