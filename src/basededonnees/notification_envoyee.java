package basededonnees;


import java.sql.Timestamp;

public class notification_envoyee {
    
    public Integer id_notif_envoyee;
    public Integer id_notif;
    public String type;
    public Timestamp timestamp;

    public notification_envoyee(Integer id_notif_envoyee, Integer id_notif, String type, Timestamp timestamp) {
        this.id_notif_envoyee = id_notif_envoyee;
        this.id_notif = id_notif;
        this.type = type;
        this.timestamp = timestamp;
    }

    public notification_envoyee(){
        id_notif_envoyee = 0;
        id_notif = 0;
        type = "";
        timestamp = null;
    }

    @Override
    public String toString() {
        return "notification_envoyee{" + "id_notif_envoyee=" + id_notif_envoyee + ", id_notif=" + id_notif + ", type=" + type + ", timestamp=" + timestamp + '}';
    }
    
}
