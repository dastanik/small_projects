
//  monitor.h
//  6.1.
//
//  Created by Dastan Kasmamytov
//

#ifndef monitor_h
#define monitor_h

void  MonitorInit(int  n);       // initialize monitor
int   IncreaseCounter(void);     // increase the counter
int   DecreaseCounter(void);     // decrease the counter
void  SetCounter(int  n);        // reset the counter
int   GetCounter(void);          // get the current counter

#endif /* monitor_h */
