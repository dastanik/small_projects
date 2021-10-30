/******************************************************************************

@author Henning Erdweg, Dastan Kasmamytov

*******************************************************************************/

public class TestArrayQueue 
{
	public static void main(String[] args) 
	{
		System.out.println("BEGIN TESTS");
		System.out.println("");
		
		MyQueue<String> test = new MyQueue<String>();
		
		System.out.println("Nachricht Hallo1 einreihen");
		test.enqueue("Hallo1");
		
		try 
		{
			System.out.print("Nachricht entfernen: ");
			System.out.println(test.dequeue());
		} 
		catch (EmptyQueueException e) 
		{			
			e.printStackTrace();
		}
		
		System.out.println("Nachricht Hallo2 einreihen");
		test.enqueue("Hallo2");
		System.out.println("Nachricht Hallo3 einreihen");
		test.enqueue("Hallo3");
		System.out.println("Nachricht Hallo4 einreihen");
		test.enqueue("Hallo4");
		System.out.println("Nachricht Hallo5 einreihen");
		test.enqueue("Hallo5");
		
		MyQueue<String>.QueueIterator iterator = test.getIterator();
		
		System.out.println("durch alle Nachrichten iterieren: ");
		while (iterator.hasNext())
		{
			System.out.println(iterator.next());
		}
		
		try 
		{
			System.out.print("Nachricht entfernen: ");
			System.out.println(test.dequeue());
		} 
		catch (EmptyQueueException e) 
		{			
			e.printStackTrace();
		}
		
		try 
		{
			System.out.print("erste Nachricht ansehen: ");
			System.out.println(test.first());
		} 
		catch (EmptyQueueException e) 
		{			
			e.printStackTrace();
		}
		
		System.out.print("Schlange ist leer: ");
		System.out.println(test.empty());
		
		while (!test.empty())
		{
			try 
			{
				System.out.print("Nachricht entfernen: ");
				System.out.println(test.dequeue());
			} 
			catch (EmptyQueueException e) 
			{			
				e.printStackTrace();
			}
		}
		
		System.out.print("Schlange ist leer: ");
		System.out.println(test.empty());
		
		try 
		{
			test.dequeue();
		} 
		catch (EmptyQueueException e) 
		{			
			System.out.println("EmptyQueueException, da Schlange leer ist.");
		}
		
		System.out.println("");
		System.out.println("END TESTS");		
	}
}
