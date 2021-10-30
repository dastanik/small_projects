import java.awt.*; 

public class RedWine extends Liquid {    //Coffe is a subclass of Liquid
    protected String name = "RedWineName";
    final protected Color color = new Color(153, 0, 0);
    protected boolean drinkable = true;
    protected int temperature;
    
    protected RedWine( String name, Color color, boolean drinkable){
        super(name, color, drinkable);
        this.temperature = 18;
        
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