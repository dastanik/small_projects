package alpv.mwp;

import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.server.UnicastRemoteObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.InetAddress;


public class MasterClass extends UnicastRemoteObject implements Master, Server{

	private final String lookUpName = "MasterWorker";

	private Registry _registry;
	private List<Worker> _workers;

	public void Master(int port) throws RemoteException {
		System.out.println("Server is initializing");
		_registry = LocateRegistry.createRegistry(port);
		_registry.rebind(lookUpName, this);

		_workers = new ArrayList<Worker>();
		String address = (InetAddress.getLocalHost()).toString();
		System.out.println("Server running at: " + address + " with the port " + port);
		run();
	}

	private void run() {
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("The Master is running");
		boolean check = true;
		while (check) {
			String inputLine = buffReader.readLine();
			if (inputLine != null) {
					if (inputLine.equals("size")) {
						System.out.println("There are " + _workers.size()	+ " workers.");
					}
			}
			else{
			    check = false;
			}
			
		}
		buffReader.close();
		System.exit(0);
	}

	public void registerWorker(Worker w) throws RemoteException {
		System.out.println("A new worker is registered");
		_workers.add(w);
		System.out.println("There are" + _workers.size() + " workers.");
	}

	public void unregisterWorker(Worker w) throws RemoteException {
		System.out.println("A worker is deregistered");
		_workers.remove(w);
		System.out.println("There are" + _workers.size() + " workers.");
	}

	public <Argument, Result, ReturnObject> RemoteFuture<ReturnObject> doJob(Job<Argument, Result, ReturnObject> job) throws RemoteException {
		Pool<Argument> argumentPool = new PoolClass<Argument>(_workers.size()); job.split(argumentPool, _workers.size());
		Pool<Result> resultPool = new PoolClass<Result>();
		System.out.println("The job is started");
		for (Worker w : _workers) {
			w.start(job.getTask(), argumentPool, resultPool);
		}

		Thread mergeThreads = new Thread(new Runnable() {
			public void run() {
				while (argumentPool.size() != -1) {
					Thread.sleep(100);

				}
				
				job.merge(resultPool);
	
			}
		});
		
		mergeThreads.start();
		
		System.out.println("The job is merged");

		return job.getFuture();
	}
}
