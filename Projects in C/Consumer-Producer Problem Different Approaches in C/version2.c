//  Nonsequential and parallel programming
//
//  Created by Dastan Kasmamytov and Björn Benedict Heyder



/*We made the consumers more aggressive/active,
 so producers do not have enough time to fill in the buffer fully:
 producer function is much slower than consumer fucntion because of sleep(), also
 consumer threads are created before the producer threads as well as
we limited the number of actions per thread and equaled it to the size of the buffer*/
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>

//global variables shared by all threads:
int numActions; //total number of actions
int numProducers; //total number of producers
int numConsumers; //total number of consumers
int *buffer; //ring buffer used by producers and consumers
int *startOfBuffer; //pointer to the buffer's beginning
size_t bufferSize; //size of the ring buffer
size_t bufferElementCount = 0; //total number of elements currently in the buffer

pthread_t threadsConsumers[100];
pthread_t threadsProducers[100];

bool cateringClosed = false; //check to ensure that consumers will stop to consume when producers stop to produce

//mutex variables
pthread_mutex_t buffermutex;

//producer inserts an element order into the buffer
void insert(int order) {
    size_t tmp = (startOfBuffer - buffer) + bufferElementCount % bufferSize; //calculating the element's place in the buffer
    buffer[tmp] = order; //inserting the element into the specific position in the buffer
    bufferElementCount++; //incrementing the total number of elements in the buffer
    printf("Inserting %d into the buffer's position #: %zu\n", order, tmp%bufferSize);
    /*printf("The current content of the buffer: [");
     for(int i = 0; i < bufferElementCount; i++){         //print out the content of the buffer
     printf("%d, ", buffer[(startOfBuffer - buffer) + i % bufferSize]);
     }
     printf(" end of buffer]\n");*/
}

// Simulation of a producer as a thread:
void *producer (void* id) {
    int tid = (int) id;         //saving the producer's id into tid
    sleep(1);
    printf("The producer %d is simulated\n", tid+1);
    
    for (int i = 0; i < numActions; i++){     //producing up until the number of actions required
        
        int order = rand()%10; //order is produced, data that needs to be saved is generated randomly
        
        while(true){
            
            pthread_mutex_lock(&buffermutex);
            if (bufferSize == bufferElementCount){    //if buffer is full, unlock the mutex, wait and start over again
                pthread_mutex_unlock(&buffermutex);
                sleep(1);              //wait
                printf("Attempt to add into the buffer has failed, because the buffer is full\n");
                continue;
            }
            insert(order);             //insert order into the buffer
            sleep(4);
            pthread_mutex_unlock(&buffermutex);     //unlock mutex
            break;
        }
    }
    
    printf("The producer %d stopped to produce (to save data into the buffer)\n", tid+1);
    pthread_exit(0);   //exit the thread
}


// Simulation of a consumer as a thread similar to the thread of a producer above
void *consumer (void* id) {
    int tid = (int) id;
    printf("The consumer %d is simulated\n", tid+1);
    
    for (int i = 0; i < numActions; i++){
        
        int order;
        while(true){
            
            pthread_mutex_lock(&buffermutex);
            if (bufferElementCount == 0 && !cateringClosed){ //if the buffer is empty and the producers have not finished producing, then unlock the mutex and wait
                pthread_mutex_unlock(&buffermutex);
                printf("Attempt to consume an element in the buffer has failed, because the buffer is empty\n");
                continue;
            }else if (cateringClosed){ //if the producers stopped producing (saving any data into the buffer), then unlock the mutex and exit the thread
                pthread_mutex_unlock(&buffermutex);
                printf("The consumer %d stopped to consume\n)", tid+1);
                pthread_exit(0);
            }
            order = *startOfBuffer; //get the content of the element in the buffer
            bufferElementCount --; //decrement the total number of elements in the buffer
            startOfBuffer ++; //increment the pointer
            
            pthread_mutex_unlock(&buffermutex);
            
            printf("The following data was consumed: %d \n", order);
            break;
        }
    }
    
    printf("The consumer %d stopped to consume\n", tid+1);
    pthread_exit(0);
}

int main(int argc, const char * argv[]) {
    
    printf("Please enter a number of consumers: \n");
    scanf ("%d", &numConsumers);
    
    //if a wrong number of consumers was entered
    while (numConsumers <=1 || numConsumers > 100) {
        printf("Please enter a correct number of consumers (>= 2, <100): \n");
        scanf ("%d", &numConsumers);
    }
    
    printf("Please enter a number of producers: \n");
    scanf ("%d", &numProducers);
    
    //if a wrong number of producers was entered
    while (numProducers <=1 || numProducers > 100 || numProducers > numConsumers) {
        printf("Please enter a correct number of producers (>= 2, <100 and <= %d): \n",numConsumers);
        scanf ("%d", &numProducers);
    }
    
    printf("What is the size of the buffer? \n");
    scanf ("%zu", &bufferSize);
    buffer = malloc(sizeof(int)*bufferSize); //dynamic allocation of buffer's size
    startOfBuffer = buffer; //pointer to the start of the ring buffer
    
    numActions = (int) bufferSize;
    
    printf("The initial buffer is empty: [] \n");
    
    //mutual exclusion init
    pthread_mutex_init(&buffermutex, NULL);
    
    //creating threads of consumers
    for(int i=0; i<numConsumers; i++){
        pthread_create(&threadsConsumers[i], NULL, consumer, (void *) i);
    }
    
    //creating threads of producers
    for(int i=0; i<numProducers; i++){
        pthread_create(&threadsProducers[i], NULL, producer, (void *) i);
    }
    
    //wait until all producer threads are done:
    for(int i=0; i<numProducers; i++){
        pthread_join(threadsProducers[i], NULL);
    }
    
    printf("All producers stopped producing (saving data into the buffer)!\n");
    cateringClosed = true; //set to true after all producers are done with their jobs
    
    for(int i=0; i<numConsumers; i++){
        pthread_join(threadsConsumers[i], NULL);
    }
    
    free(buffer);
    
    printf("All consumers and producers have finished their work\n");
    
    pthread_mutex_destroy(&buffermutex);
    
    return 0;
}

