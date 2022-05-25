package basededonnees;


public class notification_a_envoyer {

    public Integer id_notif;
    public Integer id_signale;
    public String type_notif;
    public String email;
    public String coordonnees;

    public notification_a_envoyer(Integer id_notif, Integer id_signale, String type_notif, String email, String coordonnees) {
        this.id_notif = id_notif;
        this.id_signale = id_signale;
        this.type_notif = type_notif;
        this.email = email;
        this.coordonnees = coordonnees;
    }

    public notification_a_envoyer() {
        id_notif = 0;
        id_signale = 0;
        type_notif = "";
        email = "";
        coordonnees = "";
    }

    @Override
    public String toString() {
        return "notification_a_envoyer{" + "id_notif=" + id_notif + ", id_signal=" + id_signale + ", type_notif=" + type_notif + ", email=" + email + ", coordonnees=" + coordonnees + '}';
    }
    
    
}
