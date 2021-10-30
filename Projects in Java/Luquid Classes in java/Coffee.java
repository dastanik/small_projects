import java.awt.*; 

public class Coffee extends Liquid {    //Coffe is a subclass of Liquid
    protected String name = "JavaCoffee";
    final protected Color color = new Color(102, 51, 0);
    protected boolean drinkable = true;
    protected int temperature;
    
    protected Coffee( String name, Color color, boolean drinkable){
        super(name, color, drinkable);
        this.temperature = 48;
    }

    public String getName(){
        return name;
    }
    
    public Color getColor(){
        return color;
    }
    
    public boolean isDrinkable(){
        return drinkable;
    }
    
    public void hitUp( int temperature){
        this.temperature = temperature;
    }
    public int getTemperature(){
        return temperature;
    }
} // end of class Liquid