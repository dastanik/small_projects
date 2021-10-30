//  Nonsequential and parallel programming
//
//  Created by Dastan Kasmamytov and Philipp Wellner on 25.04.19.

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>

//global variables shared by all threads:
int numPhil; //total number of philosophers, processes and resources
int allocated = 0; //maximum allocated resources
int numMeals; //total number of meals per philosopher

pthread_t threads[100];

//mutex variables
pthread_mutex_t forks[100];

//function declarations
void think (int);
void eat (int);
bool checkIfSafe(void);


// Simulation of a philosopher as a thread:
void *philosopher_simulation (void* id) {
    int tid = (int) id;
    printf("The philosopher %d is simulated\n", tid+1);
    
    for (int i = 0; i < numMeals; i++) {
        think(tid);     //thinking
        eat(tid);       //eating
    }
    
    printf ("The philosopher %d has finished eating\n", tid+1);
    
    pthread_exit(0);
}

//Simulation of thinking
void think(int tid){
    printf("The philosopher %d is thinking\n", tid+1);
    usleep(6);
}

//Simulation of eating
void eat(int tid){
    pthread_mutex_lock((tid +1) %numPhil == 0 && checkIfSafe()==1 ? &forks[0] :  &forks[tid]);
    allocated++;
    pthread_mutex_lock((tid +1) %numPhil == 0 && checkIfSafe()==1 ? &forks[tid] : &forks[(tid+1)%numPhil] );
    allocated++;
    printf("The philosopher %d is locked\n", tid+1);
    printf("The philosopher %d is eating\n", tid+1);
    usleep(6);
    pthread_mutex_unlock(&forks[tid]);
    allocated--;
    pthread_mutex_unlock(&forks[(tid+1)%numPhil]);
    allocated--;
    printf("The philosopher %d is unlocked\n", tid+1);
}

//banker's simplified
bool checkIfSafe() {
    if(allocated <= numPhil) {
        return true;
    }
    else {
        return false;
    }

}

int main(int argc, const char * argv[]) {
    
    printf("Please enter a number of philosophers: \n");
    scanf ("%d", &numPhil);
    
    //if a wrong number of philosophers was entered
    if (numPhil <=1 || numPhil > 100) {
        printf("Please enter a correct number of philosophers (>= 2, <100): \n");
        scanf ("%d", &numPhil);
    }
    
    //mutual exclusion init
    for(int i=0; i<numPhil; i++){
        pthread_mutex_init(&forks[i], NULL);
    }
    
    printf("How many times should every philosopher have a meal? \n");
    scanf ("%d", &numMeals);
    
    //if a wrong number of meals was entered
    if (numPhil <=0) {
        printf("Please enter a correct number of meals (>= 1): \n");
        scanf ("%d", &numMeals);
    }
    
    //creating threads
    for(int i=0; i<numPhil; i++){
        pthread_create(&threads[i], NULL, philosopher_simulation, (void *) i);
    }
    
    //wait until all other threads are done:
    for(int i=0; i<numPhil; i++){
        pthread_join(threads[i], NULL);
    }
    
    printf("All phisophers finished eating\n");
    
    for(int i=0; i<numPhil; i++){
        pthread_mutex_destroy(&forks[i]);
    }
    
    return 0;
}
