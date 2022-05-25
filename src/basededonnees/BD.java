package basededonnees;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import basededonnees.notification_a_envoyer;
import basededonnees.notification_envoyee;
import basededonnees.heartbeat;
import java.sql.Timestamp;


public class BD {

    //Attributs
    private static String bd = "";
    private static String utilisateur = "";
    private static String motDePasse = "";
    private static Connection connexion = null;

    // <editor-fold defaultstate="collapsed" desc="=> Getteurs et setteurs ici.">
    public static String getBd() {
        return bd;
    }

    public static void setBd(String bd) {
        BD.bd = bd;
    }

    public static String getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(String utilisateur) {
        BD.utilisateur = utilisateur;
    }

    public static String getMotDePasse() {
        return motDePasse;
    }

    public static void setMotDePasse(String motDePasse) {
        BD.motDePasse = motDePasse;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="=> Connection et déconnection de la BD ici.">
    public static void connecter() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = DriverManager.getConnection("jdbc:" + bd, utilisateur, motDePasse);

        } catch (Exception ex) {
            System.err.println("Erreur fatale : " + ex.toString());
            System.exit(-1);
        }
    }

    public static void deconnecter() {
        try {
            connexion.close();
        } catch (SQLException ex) {
            System.err.println("Erreur fatale : " + ex.toString());
            System.exit(-1);
        }
    }
    // </editor-fold>

    //Retourne des "notification_a_envoyer"
    public static ArrayList<notification_a_envoyer> getNotificationAEnvoyer(String req) {
        ArrayList lstNotificationAEnvoyer = new ArrayList();
        notification_a_envoyer aux;
        try {
            Statement requete = connexion.createStatement();
            requete.executeQuery(req);
            ResultSet rs = requete.getResultSet();
            while (rs.next()) {
                aux = new notification_a_envoyer(
                        rs.getInt("id_notif"),
                        rs.getInt("id_signale"),
                        rs.getString("type_notif"),
                        rs.getString("email"),
                        rs.getString("coordonnees")
                );
                lstNotificationAEnvoyer.add(aux);
            }
            rs.close();
            requete.close();
        } catch (Exception ex) {
            System.err.println("Erreur grave : " + ex.toString());
        }
        return lstNotificationAEnvoyer;
    }

    //Retourne des "heartbeat"
    public static ArrayList<heartbeat> getHeartBeat(String req) {
        ArrayList lstHeartBeat = new ArrayList();
        heartbeat aux;
        try {
            Statement requete = connexion.createStatement();
            requete.executeQuery(req);
            ResultSet rs = requete.getResultSet();
            while (rs.next()) {
                aux = new heartbeat(
                        rs.getInt("id"),
                        rs.getInt("id_signal"),
                        rs.getString("latitude"),
                        rs.getString("longitude"),
                        rs.getTimestamp("timestamp")
                );
                lstHeartBeat.add(aux);
            }
            rs.close();
            requete.close();
        } catch (Exception ex) {
            System.err.println("Erreur grave : " + ex.toString());
        }
        return lstHeartBeat;
    }
    
    //Retourne des "notification_envoyee"
    public static ArrayList<notification_envoyee> getNotificationEnvoyee(String req) {
        ArrayList lstNotificationEnvoyee = new ArrayList();
        notification_envoyee aux;
        try {
            Statement requete = connexion.createStatement();
            requete.executeQuery(req);
            ResultSet rs = requete.getResultSet();
            while (rs.next()) {
                aux = new notification_envoyee(
                        rs.getInt("id_notif_envoyee"),
                        rs.getInt("id_notif"),
                        rs.getString("type"),
                        rs.getTimestamp("timestamp")
                );
                lstNotificationEnvoyee.add(aux);
            }
            rs.close();
            requete.close();
        } catch (Exception ex) {
            System.err.println("Erreur grave : " + ex.toString());
        }
        return lstNotificationEnvoyee;
    }

    //Ajouter les coordonnées dans la base de données
    public static void AjoutNotif (int id_notif_envoyee, int id_notif, String type, Timestamp timestamp){
        try{
            String query = "INSERT INTO `notification_envoyee` VALUES (" + id_notif_envoyee + "," + id_notif + ",'" + type + "'," + timestamp + ")";
            BD.connecter();
            Statement st = connexion.createStatement();
            st.executeUpdate(query);
            System.out.print("Notification bien ajouté \n");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Erreur lors de l'ajout !!");
        }
    }

}
