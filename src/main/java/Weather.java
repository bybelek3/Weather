class Weather {
    private double temperature;
    private int pressure;
    private int humidity;

    public Weather(double temperature, int pressure, int humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
