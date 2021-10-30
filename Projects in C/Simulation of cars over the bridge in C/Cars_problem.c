//  Nonsequential and parallel programming
//
//  Created by Dastan Kasmamytov and Philipp Wellner on 25.04.19.

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>

#define NUM_CARS 10

//global variables shared by all threads:
bool bridgeFree; //flag to show if the bridge is free
int numCrossings = 100000; //number of crossings over the bridge for each car
int sumOfCrashes; //total number of crashes
int _error = 0;
pthread_t threads[NUM_CARS];
pthread_mutex_t lock;

//function declarations
void crossbridge (void);


// Simulation of a car as a thread:
void *car_simulation (void* id) {
    int tid = (int) id;
    printf("The car %d is simulated\n",tid+1);
    
    for (int i = 0; i < numCrossings; i++) {
        usleep(6+tid);      //driving outside of the bridge
        printf("The car %d driving\n", tid+1);
        _error = pthread_mutex_lock(&lock); //locking
        crossbridge();
        _error = pthread_mutex_unlock(&lock); //unlocking
    }
    
    pthread_exit(0);
}

void crossbridge () {
    if(!bridgeFree){
        sumOfCrashes++;
    }
    bridgeFree = 0;
    usleep(3);              //crossing the bridge
    bridgeFree = 1;
    return;
}

int main(int argc, const char * argv[]) {
    
    bridgeFree = 1; //the bridge is free
    sumOfCrashes = 0; //initialising the total number of crashes
    
    //mutual exclusion init
    pthread_mutex_init(&lock, NULL);
    
    //creating threads
    for(int i=0; i<NUM_CARS; i++){
        pthread_create(&threads[i], NULL, car_simulation, (void *) i);
    }
    
    
    //wait until all other threads are done:
    for(int i=0;i<NUM_CARS;i++){
        pthread_join(threads[i], NULL);
    }
    
    printf("Number of crashes: %d\n",sumOfCrashes);
    
    return 0;
}
