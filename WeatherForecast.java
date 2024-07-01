package monuSirTasks;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class WeatherForecast {
    private static final String FILE_PATH = "X:\\FileTesting\\weather.txt";
    private static ArrayList<LocationManager> locData;
    private static WeatherReader wr = new WeatherReader();

    WeatherForecast() {
        locData = new ArrayList<>();
        loadLocations();
    }

    public void addLocation(LocationManager data) {
        locData.add(data);
        saveLocations();
        System.out.println(">>>> Location added Successfully <<<<");
    }

    public void removeLocation(String locRemKey) {
        Iterator<LocationManager> itr = locData.iterator();
        while (itr.hasNext()) {
            LocationManager hereData = itr.next();
            if (hereData.getLocation().equalsIgnoreCase(locRemKey)) {
                itr.remove();
            }
        }
        saveLocations();
        System.out.println(">>>> Location removed successfully <<<<\n");
    }

    static void viewLocations() {
        for (LocationManager run : locData) {
            System.out.println(run);
        }
    }

    private void saveLocations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(locData);
        } catch (IOException ie) {
            System.out.println("File Not found");
        }
    }

    private void loadLocations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            locData = (ArrayList<LocationManager>) ois.readObject();
        } catch (IOException | ClassNotFoundException ie) {
            locData = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        WeatherForecast wf = new WeatherForecast();
        while (true) {
            System.out.println("Enter:\n1 - View saved locations\n2 - Remove saved locations\n3 - Add new locations\n4 - To fetch weather data for saved locations\n5 - Search\n6 - Exit");
            Scanner sc = new Scanner(System.in);
            int mainInput = sc.nextInt();
            sc.nextLine();
            switch (mainInput) {
                case 1: {
                    viewLocations();
                    break;
                }
                case 2: {
                    System.out.println("Enter the location name");
                    String locName = sc.nextLine();
                    wf.removeLocation(locName);
                    break;
                }
                case 3: {
                    System.out.println("Enter location name to add");
                    String locName = sc.nextLine();
                    wf.addLocation(new LocationManager(locName));
                    break;
                }
                case 4: {
                    System.out.println(">>>> Weather Update <<<<");
                    for (LocationManager run : locData) {
                        wr.apiCaller(run.getLocation());
                    }
                    break;
                }
                case 5: {
                    System.out.println("Enter the name of place you want to fetch weather");
                    String newLocation = sc.nextLine();
                    wr.apiCaller(newLocation);
                    break;
                }
                case 6: {
                    System.out.println("Program Closed");
                    System.exit(99);
                }
            }
        }
    }

}

class LocationManager implements Serializable {
    private String location;
    LocationManager(String loca) {
        this.location = loca;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Location : " + location;
    }
}

class WeatherReader {
    public void apiCaller(String location) throws UnsupportedEncodingException {
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        final String apiKey = "a0ce041d80310bc7e8eb672e362617b7";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedLocation + "&appid=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                System.out.println(responseBody);
                int startIndex = responseBody.indexOf("\"temp\":") + "\"temp\":".length();
                int endIndex = responseBody.indexOf(',', startIndex);
                if (endIndex == -1) {
                    endIndex = responseBody.indexOf('}', startIndex);
                }
                String temperatureStr = responseBody.substring(startIndex, endIndex);
                double temp = Double.parseDouble(temperatureStr);
                double tempCelcius = temp - 273.15;
                System.out.println("Temperature for " + location + " is: " +String.format("%.2f",tempCelcius)+" celcius");
            } else {
                System.out.println("Unable to fetch for this location \nStatus code : " + response.statusCode());
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

