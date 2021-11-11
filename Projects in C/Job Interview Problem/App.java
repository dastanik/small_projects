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
    
    public static void main(String... args) { 
        
        List<WeatherOrTeam> weatherList = readFromCSV("Weather.csv");
        
        System.out.println(outputSpread(weatherList));
        
        List<WeatherOrTeam> teams = readFromCSV("Football.csv");
        
        System.out.println(outputSpread(teams));
        
        /* To print all object instances */
        /*
        for(WeatherOrTeam model : weatherList) {
            System.out.println(model);
        }
        
        for(WeatherOrTeam model : teams) {
            System.out.println(model);
        }
        */
        
    } 
    
    private static List<WeatherOrTeam> readFromCSV(String fileName) { 
        
        List<WeatherOrTeam> weatherOrTeamList = new ArrayList<>(); 
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) { 
         
            String line = br.readLine(); 
            String firstWord = line.split(",")[0]; 
            line = br.readLine(); //read next line 
            while (line != null) {
                String[] attributes = line.split(","); 
                WeatherOrTeam obj = createWeatherOrTeam(firstWord, attributes); 
                weatherOrTeamList.add(obj);  
                line = br.readLine(); 
            }
         
        } catch (IOException ioe) { 
            
            ioe.printStackTrace(); 
            
        } 
        return weatherOrTeamList; 
        
    } 
    
    private static WeatherOrTeam createWeatherOrTeam(String type, String[] metadata) { 
        
        String identificator = metadata[0]; 
        
        int val1 = 0;
        int val2 = 0;
        
        if (type.equals("Day")){
            
            val1 = Integer.parseInt(metadata[1]); 
            val2 = Integer.parseInt(metadata[2]); 
         
        } else {
            
            val1 = Integer.parseInt(metadata[5]); 
            val2 = Integer.parseInt(metadata[6]); 
            
        }
        
        return new WeatherOrTeam (type, identificator, val1, val2); 
    }
    
    private static String outputSpread(List<WeatherOrTeam> weatherOrTeamList) { 
        
        Collections.sort(weatherOrTeamList,new Comparator<WeatherOrTeam>() {
            public int compare(WeatherOrTeam obj1, WeatherOrTeam obj2) {
                return Integer.compare(obj1.getSpread(), obj2.getSpread());
            }
        });
  
        return weatherOrTeamList.get(0).getType() + " " + weatherOrTeamList.get(0).getIdentificator() +
        " has the smallest spread that equals " + weatherOrTeamList.get(0).getSpread(); 
    }

}
