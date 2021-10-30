//  monitor.c
//  6.1.
//
//  Created by Dastan Kasmamytov
//
//  I took some parts of the code here: http://www.csl.mtu.edu/cs4411.ck/www/NOTES/threads/sim-mon.html

#include  <pthread.h>
#include <stdlib.h>
#include "monitor.h"

//The static counter und mutex are necessary for ensuring that these two variables
//are available only in this file and not in the main.c file

static  int  counter;           //the static counter
static  pthread_mutex_t  monitorLock; // the static mutex lock

void  MonitorInit(int  n)
{
    counter = n;
    
    pthread_mutex_init(&monitorLock, (void *)  NULL);
}

int  IncreaseCounter(void)
{
    int  value; //to avoid overwritten counter and in order to return a correct counter value
    
    pthread_mutex_lock(&monitorLock);     //lock the monitor
    value = (++counter);       // increase and save counter
    pthread_mutex_unlock(&monitorLock); // release the monitor
    return  value;                // return the new value
}

int  DecreaseCounter(void)
{
    int  value;
    
    pthread_mutex_lock(&monitorLock);
    value = (--counter);
    pthread_mutex_unlock(&monitorLock);
    return  value;
}

void  SetCounter(int  n)
{
    pthread_mutex_lock(&monitorLock);
    counter = n;
    pthread_mutex_unlock(&monitorLock);
}

int  GetCounter(void)
{
    int  value;
    
    pthread_mutex_lock(&monitorLock);
    value = counter;
    pthread_mutex_unlock(&monitorLock);
    return  value;
}
