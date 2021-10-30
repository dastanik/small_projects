public interface Queue<E> 
{     
	/* f�gt ein Element am Ende der Warteschlange ein */      
	public void enqueue( E element );
	
    /* das erste Element der Warteschlange wird entfernt und als R�ckgabewert der Operation zur�ckgegeben */      
	public E dequeue() throws EmptyQueueException; 
	
     /* die Referenz des ersten Elements der Warteschlange wird zur�ckgegeben ohne diese zu entfernen */      
	public E first() throws EmptyQueueException;
	
     /* �berpr�ft, ob die Warteschlange leer ist */      
	public boolean empty();
	
     /* die Elemente der Warteschlange werden in die richtige Reihenfolge als Zeichenkette zur�ckgegeben */       
	public String toString(); 
} 