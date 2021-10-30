public interface Queue<E> 
{     
	/* fügt ein Element am Ende der Warteschlange ein */      
	public void enqueue( E element );
	
    /* das erste Element der Warteschlange wird entfernt und als Rückgabewert der Operation zurückgegeben */      
	public E dequeue() throws EmptyQueueException; 
	
     /* die Referenz des ersten Elements der Warteschlange wird zurückgegeben ohne diese zu entfernen */      
	public E first() throws EmptyQueueException;
	
     /* überprüft, ob die Warteschlange leer ist */      
	public boolean empty();
	
     /* die Elemente der Warteschlange werden in die richtige Reihenfolge als Zeichenkette zurückgegeben */       
	public String toString(); 
} 