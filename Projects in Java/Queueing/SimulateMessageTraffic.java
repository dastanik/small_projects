import java.util.ArrayList; 
import java.util.List; 
import java.util.Random; 

public class SimulateMessageTraffic{
    public static void main(String[] args) 
	{
	    Random rand = new Random(); 
	    
		List<String> list = new ArrayList<>();
		
		PriorityQueue<String> test = new PriorityQueue<String>();
		
		list.add("Hallo1"); 
        list.add("Hallo2"); 
        list.add("Hallo3"); 
        list.add("Hallo4"); 
        list.add("Hallo5"); 
  
		System.out.println("Nachrichten einreihen");
		for (int i = 0; i < list.size(); i++){
		    test.enqueue(list.get(rand.nextInt(list.size()))); // take a random element from list and enqueue it
		}
		
		System.out.print("Nachricht entfernen");
		
		while (!test.empty())
		{
			System.out.println(test.dequeue());
		}
	
	}
}