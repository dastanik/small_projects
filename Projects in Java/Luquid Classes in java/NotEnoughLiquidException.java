public class NotEnoughLiquidException extends Exception {
    public NotEnoughLiquidException()
    {
        super ( "Es gibt nicht genug Flüssigkeit im Becher!" );
    }
}