public class WeatherObserver implements Observer {
    @Override
    public void update(Weather weather) {
        System.out.println("Updated Weather: " + weather);
    }
}
