/* Decided for the class instead of an array of array in case if the weather class become complex in future
and we will need to change information in each instance */

/* Alternatively, it is possible to create child classes: weather and team instead.
However, I decided against it, because those two classes do not have additional/different
attributes or methods */

class WeatherOrTeam { 
    private String type; //"weather" or "team"
    private String identificator; //day for weathers and name for teams
    private int value1; //mxT for weather, goals for team
    private int value2; //mnT for weather, goalsAllowed for team
    private int spread;
    
    public WeatherOrTeam(String type, String identificator, int value1, int value2) { 
        this.type = type;
        this.identificator = identificator; 
        this.value1 = value1; 
        this.value2 = value2; 
        this.spread = Math.abs(value1 - value2);
    } 
    
    public String getIdentificator() { 
        return identificator; 
    } 
    
    public String getType() { 
        return type; 
    } 
    
    public int getSpread() { 
        return spread; 
    } 
    
    @Override public String toString() { 
        if (type.equals("Day")){
            return "Weather [day=" + identificator + ", MxT =" + value1 + ", MnT=" + value2 + ", TempSpread=" + spread + "]"; 
        }
        else{
            return "Team [name=" + identificator + ", Goals =" + value1 + ", GoalsAllowed=" + value2 + ", Spread=" + spread + "]"; 
        }
        
    } 
}
