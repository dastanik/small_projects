// Used resources:
// https://www.javatpoint.com/creating-thread

package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public abstract class WorkerClass implements Worker, Runnable{
    
    private Master master;
	private boolean runningCheck = true;
    
    //register work at a server using MRI
    public void registerWorker (String host, int port){
		System.out.println("Register worker at the server with a host " + host + " and a port " + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		master = (Master) (registry.lookup("MasterWorker"));
		master.registerWorker(this);
	}
	
	public void doJob() {
		System.out.print("Worker is now running");

		BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
		while (runningCheck) {
			String line = buffRead.readLine();
			if (line != null) {
				master.unregisterWorker(this);
				runningCheck = false;
				buffRead.close();
				System.out.println("Worker has finished its job");
			}
		}
	}
			
	public <Argument, Result> void start(Task<Argument, Result> t, Pool<Argument> argpool, Pool<Result> respool) throws RemoteException{
		Thread neuWorkerThread = new Thread(new WorkersThread<Argument, Result>(t,	argpool, respool));
		neuWorkerThread.start();
	}
	
	private class WorkersThread<Argument, Result> implements Runnable {
		private Task<Argument, Result> _task;
		private Pool<Argument> _argumentPool;
		private Pool<Result> _resultPool;

		public WorkersThread(Task<Argument, Result> task, Pool<Argument> argumentPool, Pool<Result> resultPool) {
			_task = task;
			_argumentPool = argumentPool;
			_resultPool = resultPool;
		}

		//runs and sends the results to result pool as soon as they are available
		
		public void run() {
		    while (runningCheck && _argumentPool.size() != -1) {
		        Argument argument = _argumentPool.get();
		        Result result = _task.exec(argument);
		        _resultPool.put(result);
				}
		}
	}
	
	
}