
import java.util.*; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
 
 /**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
 
public final class App {

    /*
Football
The football.csv file contains results of the English Premier League. 
The columns labeled ‘Goals’ and ‘Goals Allowed’ contain the total number of goals 
scored by and against each team (so Arsenal scored 79 goals themselves and had 36 goals 
scored against them). 
Read the file, then print the name of the team with the smallest distance (absolute difference) 
between ‘Goals’ and ‘Goals Allowed’.
    */
    
    public static void main(String... args) { 
        
        List<Weather> weatherList = readWeathersFromCSV("Weather.csv");
        
        Collections.sort(weatherList,new Comparator<Weather>() {
            public int compare(Weather w1, Weather w2) {
                return Integer.compare(w1.getTempSpread(), w2.getTempSpread());
            }
        });
        
        System.out.println("Day " + weatherList.get(0).getDay() +
        " has the smallest temperature spread that equals " + weatherList.get(0).getTempSpread());
        
    } 
    
    private static List<Weather> readWeathersFromCSV(String fileName) { 
        
        List<Weather> weatherList = new ArrayList<>(); 
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) { 
            String line = br.readLine(); 
            line = br.readLine(); //to skip the first title line
            
            while (line != null) {
                String[] attributes = line.split(","); 
                Weather weather = createWeather(attributes); 
                weatherList.add(weather);  
                line = br.readLine(); 
                
            } 
            
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
            
        } 

        return weatherList; 
        
    } 
    
    private static Weather createWeather(String[] metadata) { 
        
        int day = Integer.parseInt(metadata[0]); 
        int mxT = Integer.parseInt(metadata[1]); 
        int mnT = Integer.parseInt(metadata[2]); 
        
        return new Weather(day, mxT, mnT); 
        
    }

}