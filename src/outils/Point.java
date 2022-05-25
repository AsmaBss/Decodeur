package outils;


public class Point {

    public double latitude = 0;
    public double longitude = 0;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Point() {
    }

    @Override
    public String toString() {
        return "Point{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
        
}
