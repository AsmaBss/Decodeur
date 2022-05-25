package outils;


import java.util.ArrayList;

public class Convertisseur {

    public static ArrayList<Point> GPSversPoint(String coord, String sep) {
        ArrayList<Point> lstPoint = new ArrayList<Point>();
        Point p;
        try {
            while (coord.length() > 1) {
                p = new Point();
                p.latitude = Double.parseDouble(coord.substring(0, coord.indexOf(sep)));
                coord = coord.substring(coord.indexOf(sep) + 1, coord.length());
                p.longitude = Double.parseDouble(coord.substring(0, coord.indexOf(sep)));
                coord = coord.substring(coord.indexOf(sep) + 1, coord.length());
                lstPoint.add(p);
            }
        } catch (Exception ex) {
            //
        }
        try {
            if (lstPoint.size() == 0) {
                throw new Exception("Coordonnées GPS non valides.");
            }
        } catch (Exception ex) {
            System.err.println("Erreur grave : " + ex.toString());
        }
        return lstPoint;
    }
}
