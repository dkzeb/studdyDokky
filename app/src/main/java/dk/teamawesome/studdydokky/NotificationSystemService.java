package dk.teamawesome.studdydokky;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;


/**
 * Created by zeb on 24-05-16.
 * NotificationSystemService
 * Bør sammenholde sidst kendte position med listen af aktiviteter
 * og alarmere hvis der er 15 min el. mindre til næste aktivitet
 * på samme placering.
 * Bør ligeledes gennemløbe aktiviteter der er nye hvis de matcher
 * med tilkendegivne interresser
 *
 */
public class NotificationSystemService extends IntentService {

    private Handler mHandler = new Handler();
    private final int UPDATE_TIME = 60000; // i milis
    private String TAG = "NotificationSystemService";

    private int activityNotificationID = 0010;
    private int interresseNotificationID = 0020;

    private NotificationManager mNotifyMgr;

    public NotificationSystemService() {
        super("NotificationSystemService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // first run -
        mHandler.postDelayed(BGRunnable, 0);
    }

    /* Notify on activity */
    public void BuildActivityNotification(String activityName, String locationName, String activityTime){

        mNotifyMgr = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Der er aktivitet i dit område");
        inboxStyle.addLine("Aktivitet: "+activityName);
        inboxStyle.addLine("Sted: "+locationName);
        inboxStyle.addLine("Kl. " + activityTime);

        Intent resultIntent = new Intent(this, StuddyDokkyMap.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
                stackBuilder.addParentStack(StuddyDokkyMap.class);
        // Adds the Intent to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.alert_light_frame)
                        .setContentTitle("Der er aktiviteter i dit område")
                        .setContentText("Kl. " + activityTime + " - " + activityName + " - " + locationName)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX);


        mNotifyMgr.notify(activityNotificationID, mBuilder.build());
    }

    final Runnable BGRunnable = new Runnable(){
        public void run(){

            //BuildActivityNotification("Børneteater", "Scenetrappen", "13:37");

            // run every 15th second :D rekursivt!
            mHandler.postDelayed( BGRunnable, UPDATE_TIME);
        }
    };
}
