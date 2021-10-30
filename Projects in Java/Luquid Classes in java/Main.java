/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
import java.awt.*; 

public class Main
{
	public static void main(String[] args) {
	    
	    Color colorBrown = new Color(102, 51, 0); //brown
	    Color colorRed = new Color(153, 0, 0); //dark red
	    int someLiquid = 20;
	    int lotsOfLiquid = 2000;
		
		Liquid coffee = new Coffee("JavaCoffee", colorBrown, true); //create an object instance coffee that inherits from Liquid
		Liquid redWine = new RedWine("RedWineName", colorRed, true); ////create an object instance redWine that inherits from Liquid
		
		System.out.println("Testing a name of the coffee:");
		System.out.println(coffee.getName());
		System.out.println();
		
		System.out.println("Testing a color of the Red Wine:");
		System.out.println(redWine.getColor());
		System.out.println();
		
		System.out.println("Testing a drinkability of the Red Wine:");
		if(redWine.isDrinkable()){
		    System.out.println("It is drinkable");
		}
		else{
		    System.out.println("It is not drinkable");
		}
		System.out.println();
		
		System.out.println("Heat up the coffee up to 65 degrees:");
		coffee.hitUp(65);
		System.out.println("The new temparture is:");
		System.out.println(coffee.getTemperature());
		System.out.println();
		
		Mug mug1 = new Mug(40, coffee); //create mug that contains 40ml coffee
		
		System.out.println("Get the name of the liquid in the first mug:");
		System.out.println(mug1.getName());
		System.out.println();
		
		
		Mug mug2 = new Mug(redWine); //create empty mug that contains redWine
	}
} 
