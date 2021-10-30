import java.util.Iterator;


/******************************************************************************

@author Henning Erdweg, Dastan Kasmamytov

*******************************************************************************/

public class MyQueue<E> implements Queue<E> 
{	
	/* Iteratorklasse */
	public class QueueIterator implements Iterator<E>
	{
		private int current;
		
		/* Konstruktor der Klasse QueueIterator*/ 
		public QueueIterator()
		{
			current = head;
		}
		
		/* Überpüfen, ob es ein nächstes Element gibt*/ 
		public boolean hasNext() 
		{	
			if (tail > head)
			{
				return current < tail;
			}
			else if (tail < head)
			{
				return current < tail || current >= head;
			}
			
			return false;
			
		}

		/* nächstes Element zurückgeben*/
		public E next() 
		{
			E elem = queue[current];            
			 
			if (current == (queue.length-1))                    
			{
				current = 0;            
			}
			else  
			{
				current++;            
			}
			 
			return elem;   			 
		}		
	}
	
	private E[] queue;
	private int head;
	private int tail;

	/* Konstruktor der Klasse MyQueue*/ 
	public MyQueue()
	{
		this((E[]) new Object[10]);
	}
	
	/* Konstruktor der Klasse MyQueue*/
	public MyQueue(E[] queue)
	{
		head = 0;
		tail = 0;
		this.queue = queue;
	}	
	
	/* Iteartor ermitteln*/
	public QueueIterator getIterator()
	{
		return new QueueIterator();
	}

	/* Überprüfen, ob die Schlange voll ist*/
	public boolean full() 
	{
		return ((tail == queue.length - 1) && (head == 0))                     
	     	  || (head == (tail + 1)); 
	}

	/* Element einreihen*/
	public void enqueue(E elem ) 
	{         
		if (full()) 
		{              
			resizeQueue();					          
		}
		
		queue[tail] = elem;              
		 
		if (tail == (queue.length-1))                      
		{
			tail = 0;              
		}
		else  
		{
			tail++;
		}
	}
	 
	/* Element entnehmen*/
	public E dequeue() throws EmptyQueueException 
	{        
		if (!empty())
		{            
			E elem = queue[head];            
			 
			if (head == (queue.length-1))                    
			{
				head = 0;            
			}
			else  
			{
				head++;            
			}
			 
			return elem;      
		} 
		else
		{
			throw new EmptyQueueException();
		}
	}

	/* erstes Element ermitteln ohne es zu entfernen*/
	public E first() throws EmptyQueueException 
	{
		if (!empty())
		{
			return queue[head];
		}
		else
		{
			throw new EmptyQueueException();
		}
	}

	/* Überprüfen, ob die Schlange leer ist*/
	public boolean empty() 
	{
		return head == tail; 
	}
	
	/* dynamisches Array vergrößern*/
	private void resizeQueue()
	{
		int elementCount = 0;
		int newTail = 0;
		
		E[] temp = (E[]) new Object[queue.length * 2];   
		
		if (tail > head)
		{
			for (int i = head; i < queue.length && i < tail; i++)
			{    
				temp[newTail++] = queue[i];
				
				elementCount++;
			}
		}
		else if (tail < head)
		{
			for (int i = head; i < queue.length; i++)
			{    
				temp[newTail++] = queue[i];
				
				elementCount++;
			}
			
			for (int i = 0; i < tail; i++)
			{    
				temp[newTail++] = queue[i];
				
				elementCount++;
			}
		}
		
		head = 0;
		tail = elementCount;
		queue = temp; 
	}
}
