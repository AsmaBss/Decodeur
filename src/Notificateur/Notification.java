package Notificateur;

import basededonnees.BD;
import basededonnees.heartbeat;
import basededonnees.notification_a_envoyer;
import basededonnees.notification_envoyee;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import outils.Point;

public class Notification {
    //notification_envoyee notificationEnvoyee = new notification_envoyee();

    public static void entreeZone(notification_a_envoyer notificationAEnvoyer) {
        heartbeat heartBeat;
        ArrayList<heartbeat> lstDernierHeartBeat = BD.getHeartBeat("SELECT * FROM `heartbeat` WHERE `id_signal` = '" + notificationAEnvoyer.id_signale + "' ORDER BY `timestamp` DESC LIMIT 4");

        //Si le dernier signal date de plus de 3 minutes
        if (System.currentTimeMillis() - lstDernierHeartBeat.get(0).timestamp.getTime() > 30 * 60 * 1000) {
            signalPerdu(notificationAEnvoyer);
        }

        ArrayList<Point> lstPointGPS = outils.Convertisseur.GPSversPoint(notificationAEnvoyer.coordonnees, ",");

        if (Double.parseDouble(lstDernierHeartBeat.get(0).latitude) < lstPointGPS.get(0).latitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).latitude) > lstPointGPS.get(1).latitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).longitude) > lstPointGPS.get(0).longitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).longitude) < lstPointGPS.get(1).longitude) {
            System.err.println("Notification Entree " + notificationAEnvoyer.id_signale
                    + " dans la zone " + lstPointGPS.get(0).toString()
                    + "/"
                    + lstPointGPS.get(1).toString()
                    + "! => " + notificationAEnvoyer.email);
        }
    }

    public static void sortieZone(notification_a_envoyer notificationAEnvoyer) {
        heartbeat heartBeat;
        ArrayList<heartbeat> lstDernierHeartBeat = BD.getHeartBeat("SELECT * FROM `heartbeat` WHERE `id_signal` = '" + notificationAEnvoyer.id_signale + "' ORDER BY `timestamp` DESC LIMIT 4");

        //Si le dernier signal date de plus de 3 minutes
        if (System.currentTimeMillis() - lstDernierHeartBeat.get(0).timestamp.getTime() > 30 * 60 * 1000) {
            signalPerdu(notificationAEnvoyer);
        }

        ArrayList<Point> lstPointGPS = outils.Convertisseur.GPSversPoint(notificationAEnvoyer.coordonnees, ",");

        if (!(Double.parseDouble(lstDernierHeartBeat.get(0).latitude) < lstPointGPS.get(0).latitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).latitude) > lstPointGPS.get(1).latitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).longitude) > lstPointGPS.get(0).longitude
                && Double.parseDouble(lstDernierHeartBeat.get(0).longitude) < lstPointGPS.get(1).longitude)) {
            System.err.println("Notification Sortie " + notificationAEnvoyer.id_signale
                    + " dans la zone " + lstPointGPS.get(0).toString()
                    + "/"
                    + lstPointGPS.get(1).toString()
                    + "! => " + notificationAEnvoyer.email);
        }
    }

    public static void signalPerdu(notification_a_envoyer notificationAEnvoyer) {
        System.err.println("Notification Signal " + notificationAEnvoyer.id_signale + " Perdu! => " + notificationAEnvoyer.email);
        notification_envoyee notificationEnvoyee = new notification_envoyee();
        BD.AjoutNotif(notificationEnvoyee.id_notif_envoyee, notificationAEnvoyer.id_notif, "perdu", null);
    }

    public static void rencontreSignals(notification_a_envoyer notificationAEnvoyer) {
        heartbeat heartBeat;

        //4 derniers heartbeat du signal1
        ArrayList<heartbeat> lstDernierHeartBeat1 = BD.getHeartBeat("SELECT * FROM `heartbeat` WHERE `id_signal` = '" + notificationAEnvoyer.id_signale + "' ORDER BY `timestamp` DESC LIMIT 4");

        //Si le dernier signal date de plus de 3 minutes
        if (System.currentTimeMillis() - lstDernierHeartBeat1.get(0).timestamp.getTime() > 30 * 60 * 1000) {
            signalPerdu(notificationAEnvoyer);
        }

        ArrayList<Point> lstPointGPS = outils.Convertisseur.GPSversPoint(notificationAEnvoyer.coordonnees, ",");

        if (Double.parseDouble(lstDernierHeartBeat1.get(0).latitude) < lstPointGPS.get(0).latitude
                && Double.parseDouble(lstDernierHeartBeat1.get(0).latitude) > lstPointGPS.get(1).latitude
                && Double.parseDouble(lstDernierHeartBeat1.get(0).longitude) > lstPointGPS.get(0).longitude
                && Double.parseDouble(lstDernierHeartBeat1.get(0).longitude) < lstPointGPS.get(1).longitude) {
            notification_a_envoyer notification_a_envoyer = new notification_a_envoyer();
            //Selectionner signal2
            ArrayList<notification_a_envoyer> lstNotification = BD.getNotificationAEnvoyer("SELECT * FROM `notification_a_envoyer` WHERE `coordonnees` = '" + notificationAEnvoyer.coordonnees + "' AND id_signale != '" + notificationAEnvoyer.id_signale + "'");
            //4 derniers heartbeat du signal2
            ArrayList<heartbeat> lstDernierHeartBeat2 = BD.getHeartBeat("SELECT * FROM `heartbeat` WHERE `id_signal` = '" + lstNotification.get(0).id_signale + "' ORDER BY `timestamp` DESC LIMIT 4");

            if (System.currentTimeMillis() - lstDernierHeartBeat2.get(0).timestamp.getTime() > 10 * 60 * 1000) {
                signalPerdu(notificationAEnvoyer);
            }
            if (Double.parseDouble(lstDernierHeartBeat2.get(0).latitude) < lstPointGPS.get(0).latitude
                    && Double.parseDouble(lstDernierHeartBeat2.get(0).latitude) > lstPointGPS.get(1).latitude
                    && Double.parseDouble(lstDernierHeartBeat2.get(0).longitude) > lstPointGPS.get(0).longitude
                    && Double.parseDouble(lstDernierHeartBeat2.get(0).longitude) < lstPointGPS.get(1).longitude) {
                System.err.println("Notification Rencontre " + notificationAEnvoyer.id_signale + " et " + lstNotification.get(0).id_signale
                        + " dans la zone " + lstPointGPS.get(0).toString()
                        + "/"
                        + lstPointGPS.get(1).toString()
                        + "! => " + notificationAEnvoyer.email);
                notification_envoyee notificationEnvoyee = new notification_envoyee();
                BD.AjoutNotif(notificationEnvoyee.id_notif_envoyee, notificationAEnvoyer.id_notif, notificationAEnvoyer.type_notif, null);
            }

        }
    }
}
