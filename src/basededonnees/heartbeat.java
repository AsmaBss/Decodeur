package basededonnees;


import java.sql.Timestamp;

public class heartbeat {

    public Integer id;
    public Integer id_signal;
    public String latitude;
    public String longitude;
    public Timestamp timestamp;

    public heartbeat(Integer id, Integer id_signal, String latitude, String longitude, Timestamp timestamp) {
        this.id = id;
        this.id_signal = id_signal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "heartbeat{" + "id=" + id + ", id_signal=" + id_signal + ", latitude=" + latitude + ", longitude=" + longitude + ", timestamp=" + timestamp + '}';
    }   
    
}
