//  Nonsequential and parallel programming
//
//  Created by Dastan Kasmamytov and Philipp Wellner


/*
 
 We simplified the Newton's Law on calculating the force by assuming that
 the gravitational constant equals 1.
 
 https://en.wikipedia.org/wiki/N-body_problem
 
 F = m1*m2*(v1 - v2)/d12
 
 Since no input format was given, we decided to create all values randomly:
 m = mass, v = vectors, d = distance between the bodies
 
 */

#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <math.h>
#include <pthread.h>
#include <stdbool.h>

//global variables shared by all threads:
int numBodies; //total number of bodies
int numCycles; //total number of cycles
pthread_t threadsBodies[100];
int m[100]; //array of masses of all bodies
typedef struct{
    int x,y; //the position of the body on the (x,y) coordinate field
}vector;
vector bodyVector[100]; //array of vectors of each body


//Filling in the array of masses with random values
void simulateAllMasses (){
    for (int i=0; i<numBodies; i++){
        m[i] = 0;
        while(m[i] == 0) // we do not want bodies with mass equal zero
        {
            srand((unsigned int)time(NULL));
            m[i] = rand() % 20; //random int between 1 and 100
        }
    }
    for (int i=0; i<numBodies; i++){
        printf("All masses are simulated:\n");
        printf("Body \n");
    }
}

//Filling all vectors with random values
void simulateAllVectors (){
    for (int i=0; i<numBodies; i++){
        bodyVector[i].x = 0;
        bodyVector[i].y = 0;
        while(bodyVector[i].x == 0 || bodyVector[i].y == 0)
        {
            srand((unsigned int)time(NULL));
            bodyVector[i].x = rand() % 20;
            srand((unsigned int)time(NULL));
            bodyVector[i].y = rand() % 20;
        }
    } 
}

// Simulation of a body as a thread
void *bodySimulation (void* id) {
    int tid = (int) id;
    
    
    while(m[i] == 0)
    {
        srand((unsigned int)time(NULL));
        m = rand() % 20; //random int between 1 and 100
        bodyVector a = {a.x+b.x,a.y+b.y,a.z+b.z};
    }


int main(int argc, const char * argv[]) {
    
    printf("Please enter a number of bodies: \n");
    scanf ("%d", &numBodies);
    
    //if a wrong number of consumers was entered
    while (numBodies <=1 || numBodies > 100) {
        printf("Please enter a correct number of bodies (>= 2, <100): \n");
        scanf ("%d", &numBodies);
    }
    
    printf("Please enter a number of cycles: \n");
    scanf ("%d", &numCycles);
    
    //if a wrong number of consumers was entered
    while (numCycles <1 || numCycles > 100) {
        printf("Please enter a correct number of bodies (>= 1, <100): \n");
        scanf ("%d", &numCycles);
    }
    
    printf("The vector coordinates, masses and distances between the bodies will be selected randomly \n");
    
    simulateAllMasses();
    
    //creating threads of bodies
    for(int i=0; i<numBodies; i++){
        pthread_create(&threadsBodies[i], NULL, bodySimulation, (void *) i);
    }
    
    //wait until all threads are done:
    for(int i=0; i<numBodies; i++){
        pthread_join(threadsBodies[i], NULL);
    }
    
    printf("All cycles have finished\n");
    
    return 0;
}

