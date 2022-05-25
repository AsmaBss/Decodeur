package decodeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import basededonnees.BD;
import basededonnees.notification_a_envoyer;
import basededonnees.notification_envoyee;
import java.util.ArrayList;
import java.util.Iterator;

public class Decodeur {

    public static void main(String[] args) {
        //Initialisation des paramétres de la BD
        BD.setBd("mysql://localhost:3306/gardiennage");
        BD.setUtilisateur("root");
        BD.setMotDePasse("");

        // <editor-fold defaultstate="collapsed" desc="=> Appel de la méthode decode() chaque 2 minutes.">
        while (true) {
            try {
                Thread.sleep(3000);//120000
            } catch (InterruptedException ex) {
                System.err.println("Erreur fatale : " + ex.getMessage());
                System.exit(-1);
            }
            decode();
        }
        // </editor-fold>
    }

    private static void decode() {
        notification_a_envoyer notificationAEnvoyer;
        notification_envoyee notificationEnvoyee = new notification_envoyee();

        BD.connecter();

        ArrayList<notification_a_envoyer> lstNotificationAEnvoyer = BD.getNotificationAEnvoyer("SELECT * FROM `notification_a_envoyer`");

        Iterator<notification_a_envoyer> itr = lstNotificationAEnvoyer.iterator();
        while (itr.hasNext()) {
            notificationAEnvoyer = itr.next();
            System.err.println("-------------------");
            if (notificationAEnvoyer.type_notif.equals("entree")) {
                //Traitement entrée de zone
                Notificateur.Notification.entreeZone(notificationAEnvoyer);
                BD.AjoutNotif(notificationEnvoyee.id_notif_envoyee, notificationAEnvoyer.id_notif, notificationAEnvoyer.type_notif, null);

            }
            if (notificationAEnvoyer.type_notif.equals("sortie")) {
                //Traitement sortie de zone
                Notificateur.Notification.sortieZone(notificationAEnvoyer);
                BD.AjoutNotif(notificationEnvoyee.id_notif_envoyee, notificationAEnvoyer.id_notif, notificationAEnvoyer.type_notif, null);

            }
            if (notificationAEnvoyer.type_notif.equals("rencontre")) {
                //Traitement rencontre de zone
                Notificateur.Notification.rencontreSignals(notificationAEnvoyer);
            }
        }

        BD.deconnecter();
    }
}
