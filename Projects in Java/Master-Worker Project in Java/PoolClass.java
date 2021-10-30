package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayDeque;

public class PoolClass<T> extends UnicastRemoteObject implements Pool<T>{

	public ArrayDeque<T> _queue;

	public void put(T t) throws RemoteException {
		_queue.add(t);
	}

	public T get() throws RemoteException {
		T res = _queue.poll();
		return res;
	}

	public synchronized int size() throws RemoteException {
		return _queue.size();
	}

}