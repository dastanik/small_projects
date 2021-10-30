import java.awt.*; 

public class Mug {    //Coffe is a subclass of Liquid
    protected Liquid liquidInMug;
    final protected int capacity = 240; //capacity in ml
    protected int filledMl;
    protected boolean isEmptyVar;

    protected Mug(Liquid liquidInMug) {
        this.liquidInMug = liquidInMug;
        this.filledMl = 0;
        this.isEmptyVar = true;
    }
    
    protected Mug(int ml, Liquid liquidInMug) {
        this.liquidInMug = liquidInMug;
        this.filledMl = ml;
        if (ml <= 0){
            this.isEmptyVar = true;
        }
        else{
            this.isEmptyVar = false;
        }
    }

    public String getName(){
        return liquidInMug.name;
    }
    
    /* hier wird Flüssigkeit in den Becher gegossen */
    public void pour( int ml ) throws NotEnoughCapacityException{
        
        this.filledMl = this.filledMl + ml;
        
        if ( this.filledMl < this.capacity ) {
            return;
        } else {
            throw new NotEnoughCapacityException("Zu viel Flüssigkeit im Becher!");
        } 
    }
    
    /* der Becher wird geleert */
    public void takeOut( int ml ) throws NotEnoughLiquidException{
        this.filledMl = this.filledMl - ml;
        if ( this.filledMl >= 0) {
            return;
        } else {
            throw new NotEnoughLiquidException();
        } 
    }
    
    /* jemand trinkt ml Milliliter aus dem Becher */
    public void drink(int ml) throws UndrinkableException, NotEnoughLiquidException{
        if (this.liquidInMug.drinkable == true){
            this.filledMl = this.filledMl - ml;
            if ( this.filledMl >= 0) {
                return;
            } else {
                throw new NotEnoughLiquidException();
            } 
        }else{
            throw new UndrinkableException();
        }
        
    }
    
    /* der Becher wird geleert */
    public int empty(){
        this.filledMl = 0;
        return filledMl;
    }
    
    /* Frage, ob keine Flüssigkeit drin ist */
    public boolean isEmpty(){
        return isEmptyVar;
    }

    /* Frage, ob die Flüssigkeit zu heiß ist */
    public boolean isHot(){
        if (liquidInMug.temperature >= 30){
            return true;
        }
        else{
            return false;
        }
    }

} // end of class Liquid