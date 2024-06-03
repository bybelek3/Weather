import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class WeatherService implements Observable {
    private static final String API_KEY = "4283fde91a1686516eedaddba3849917";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private List<Observer> observers;
    private Weather currentWeather;

    public WeatherService() {
        observers = new ArrayList<>();
    }

    public void fetchWeatherData(String city) throws Exception {
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonObject main = jsonResponse.getAsJsonObject("main");
        double temperature = main.get("temp").getAsDouble();
        int pressure = main.get("pressure").getAsInt();
        int humidity = main.get("humidity").getAsInt();

        currentWeather = new Weather(temperature, pressure, humidity);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(currentWeather);
        }
    }

    public static void main(String[] args) {
        try {
            WeatherService weatherService = new WeatherService();
            WeatherObserver observer = new WeatherObserver();
            weatherService.addObserver(observer);
            weatherService.fetchWeatherData("London");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
