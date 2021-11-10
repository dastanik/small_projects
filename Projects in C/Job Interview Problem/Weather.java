public class Weather { 
    private int day; 
    private int mxT; 
    private int mnT; 
    private int tempSpread;
    
    public Weather(int day, int mxT, int mnT) { 
        this.day = day; 
        this.mxT = mxT; 
        this.mnT = mnT; 
        this.tempSpread = mxT - mnT;
    } 
    
    public int getDay() { 
        return day; 
    } 
    
    public int getTempSpread() { 
        return tempSpread; 
    } 
    
    @Override public String toString() { 
        return "Weather [day=" + day + ", MxT =" + mxT + ", MnT=" + mnT + ", TempSpread=" + tempSpread + "]"; 
    } 
}

