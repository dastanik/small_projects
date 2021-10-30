//  Nonsequential and parallel programming
//
//  Created by Dastan Kasmamytov


// Inspired by https://rosettacode.org/wiki/N-body_problem#C
// https://en.wikipedia.org/wiki/N-body_simulation
// http://www-inf.telecom-sudparis.eu/COURS/CSC5001/new_site/Supports/Projet/NBody/sujet.php
// https://github.com/ROCm-Developer-Tools/HIP-Examples/tree/master/mini-nbody

  
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
struct timeval timerStart;

//Struct that defines a single body with its coordinates
typedef struct { float x, y, z, vx, vy, vz; } Body;

//randomize initial positions of all n bodies
void randomizeBodies(float *data, int n) {
  for (int i = 0; i < n; i++) {
    data[i] = 2.0f * (rand() / (float)RAND_MAX) - 1.0f;
  }
}

//compute the forces between the bodies
void calculateBodyForce(Body *p, float timeStep, int n) {
  for (int i = 0; i < n; i++) {
    float Fx = 0.0f; float Fy = 0.0f; float Fz = 0.0f;

    for (int j = 0; j < n; j++) {
      float dx = p[j].x - p[i].x;
      float dy = p[j].y - p[i].y;
      float dz = p[j].z - p[i].z;
      float distanceSquared = dx*dx + dy*dy + dz*dz;
      float invariantDistance = 1.0f / sqrtf(distanceSquared);
      float invariantDistance3 = invariantDistance * invariantDistance * invariantDistance;

      Fx += dx * invariantDistance3; Fy += dy * invariantDistance3; Fz += dz * invariantDistance3;
    }

    p[i].vx += timeStep*Fx; p[i].vy += timeStep*Fy; p[i].vz += timeStep*Fz;
  }
}

int main(const int argc, const char** argv) {
  
  int nTotalBodies = 20000; //total number of bodies
  
  const int numOfIterations = 3;  // total number of simulation iterations
  
  const float timeStep = 0.01f; // time step

  int totalSizeOfAllBodies = nTotalBodies*sizeof(Body);
  
  float *bufferSize = (float*)malloc(totalSizeOfAllBodies); //dynamic allocation of the memory
  Body *p = (Body*)bufferSize;

  randomizeBodies(bufferSize, 6*nTotalBodies); // Initialize positions and velocities

  double totalTime = 0.0;

  for (int iteration = 1; iteration <= numOfIterations; iteration++) {
    clock_t begin = clock();

    calculateBodyForce(p, timeStep, nTotalBodies); // compute interbody forces

    for (int i = 0 ; i < nTotalBodies; i++) { // integrate position
      p[i].x += p[i].vx*timeStep;
      p[i].y += p[i].vy*timeStep;
      p[i].z += p[i].vz*timeStep;
    }
    
    clock_t end = clock();
    
    const double timeElapsed = (double)(end - begin) / CLOCKS_PER_SEC; //// time elapsed in ms
    if (iteration > 1) { // First iteration is warm up
      totalTime += timeElapsed;
    }

    printf("Iteration %d: %.3f seconds\n", iteration, timeElapsed);

  }
  double avgTime = totalTime / (double)(numOfIterations-1);

  printf("Total %d bodies: in average %0.3f billion interactions per each second\n", nTotalBodies, 1e-9 * nTotalBodies * nTotalBodies / avgTime);
  free(bufferSize); //free the memory
}
