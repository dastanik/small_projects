package alpv.mwp;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Scanner;
import java.util.Random;

public class Client {
	protected Server _server;

	public Client(String host, int port) throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("MasterWorker"));
		System.out.println("Client started");
	}

	public void execute() throws RemoteException {
		
		Random rand = new Random();
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many numbers to round up?");
		double[] array = new double[scanner.nextInt()];
		for(int i =0; i < array.length; i++) {
            array[i] = rand.nextDouble();
        }
		
		Job job = new JobExample(array);

		RemoteFuture remoteFuture = _server.doJob(job);
		System.out.println(remoteFuture.get());
	}
}