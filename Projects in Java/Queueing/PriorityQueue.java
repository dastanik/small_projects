/******************************************************************************

@author Henning Erdweg, Dastan Kasmamytov

*******************************************************************************/

public class PriorityQueue<P extends Comparable<P>, Data> 
{
	/* Innere Klasse für Prioritätswarteschlangeneinträge */
	public class PriorityEntry<K, D>
	{
		private int count;
		private K key;
		private D data;
		
		/* Konstruktor der Klasse PriorityEntry*/ 
		public PriorityEntry(int count)
		{
			assert count >= 0 : " Count should be positive "; 
			this.count = count;
		}
		
		/* Konstruktor der Klasse PriorityEntry*/ 
		public PriorityEntry(K key, D data)
		{
			this.key = key;
			this.data = data;
		}
		
		/* Holen des Schlüssels*/ 
		public K getKey()
		{
			return key;
		}
		
		/* Holen der Daten*/
		public D getData()
		{
			return data;
		}
	}
	
	private PriorityEntry<P, Data>[] heap;
	
	/* Konstruktor der Klasse PriorityQueue*/ 
	public PriorityQueue()
	{
		heap = (PriorityQueue<P, Data>.PriorityEntry<P, Data>[]) new PriorityEntry[100];
		heap[0] = new PriorityEntry<P, Data>(0);
	}
	
	/* die Daten des Objekts mit der höchsten Priorität werden zurückgegeben ohne das Objekt aus der Warteschlange zu entfernen */
	public Data highest() throws EmptyQueueException
	{
		if (!empty())
	    {
	        return (Data) heap[1].getData();
	    }
	    else
	    {
	    	throw new EmptyQueueException();
	    }	
	}

	/* Eine neue Nachricht wird in die Prioritätswarteschlange eingefügt */
	/* speichert die Daten (data) mit der vorgegebenen Priorität P in die Warteschlange */ 
	public void enqueue(P priority, Data data)
	{
		incHeapSize();
		
		if (heap.length == heapSize())
		{
			// Array erweitern
			PriorityEntry<P, Data>[] tmp = (PriorityQueue<P, Data>.PriorityEntry<P, Data>[]) new PriorityEntry[heap.length * 2];
			
			for (int i = 0; i < heap.length; i++)
			{
				tmp[i] = heap[i]; 
			}
			
			heap = tmp;
		}
		
		heap[heapSize()] = new PriorityEntry<P, Data>(priority, data);
		buildMaxHeap();
	}

	/* Ermitteln ob der Schlagen leer ist */
	public boolean empty()
	{
	    return heapSize() == 0;
	}
	
	/* die Daten des Objekts mit der höchsten Priorität werden als Ergebnis zurückgegeben, und das Objekt wird aus der Warteschlange entfernt */
	public Data dequeue() throws EmptyQueueException
	{
		if (!empty())
	    {
			decHeapSize();
	        PriorityQueue<P, Data>.PriorityEntry<P, Data> tmp = heap[1];
	        
	        for (int i = 1; i < heap.length - 1; i++)
	        {	        	
	        	heap[i] = heap[i + 1];	
	        }
	        
	        heap[heap.length - 1] = null;

	        buildMaxHeap();

	        return tmp.getData();
	    }
	    else
	    {
	    	throw new EmptyQueueException();
	    }	        
	}

	/* Umsortieren auf Binärbaumebene */
	private void maxHeapify(int pos)
	{
		assert post >= 0 : " pos should be positive "; 
		int leftT = left(pos);
		int rightT = right(pos);
		int biggest = 0;

		// sortieren nach Priorität
		if (leftT <= heapSize() && heap[leftT].getKey().compareTo(heap[pos].getKey()) > 0)
		{
			biggest = leftT;
		}
		else
		{
			biggest = pos;
		}
			        
		// sortieren nach Priorität
		if (rightT <= heapSize() && heap[rightT].getKey().compareTo(heap[biggest].getKey()) > 0)
		{
			biggest = rightT;
   		}
			        
		if (biggest != pos)
		{
			PriorityQueue<P, Data>.PriorityEntry<P, Data> temp = heap[pos];
			heap[pos] = heap[biggest];
			heap[biggest] = temp;
			
		    maxHeapify(biggest);
		}			        
	}
	
	/* Heap mit den am höchsten priorisierten Elemente sortieren */
	private void buildMaxHeap()
	{
		for (int i = heapSize() / 2; i > 0; i--)
		{
			maxHeapify(i);
		}
	}
	 
	/* Ermitteln der Heapgröße*/
	private int heapSize()
	{
		return heap[0].count;
	}

	/* Heapgröße inkrementieren*/
	private void incHeapSize()
	{
		heap[0].count = heap[0].count + 1;
	}
	
	/* Heapgröße dekrementieren*/
	private void decHeapSize()
	{
		heap[0].count = heap[0].count - 1;
	}
	
	/* linke Kindposition*/
	private int left(int i)
	{
		return i * 2;		
	}
	
	/* rechte Kindposition*/
	private int right(int i)
	{
		return i * 2 + 1;
	}	    
}