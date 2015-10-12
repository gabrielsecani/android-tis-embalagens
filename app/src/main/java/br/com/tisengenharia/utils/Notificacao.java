package br.com.tisengenharia.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.HashMap;

import br.com.tisengenharia.tisapp.MapsActivity;
import br.com.tisengenharia.tisapp.R;

/**
 * Created by Gabriel on 06/09/2015.
 *
 */
public class Notificacao {

    private static HashMap hmNotificacoes;

    public static HashMap getHmNotificacoes(){
        if(hmNotificacoes == null)
            hmNotificacoes = new HashMap();
        return hmNotificacoes;
    }

    public static void showNotification(Context context, String msg) {
        int id = getHmNotificacoes().size();
        showNotification(context, id, msg);
    }

    public static void showAlert(Context context, int res_id_msg) {
        showAlert(context, context.getString(res_id_msg));
    }

    public static Dialog showAlert(Context context, String msg){
        return new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    public static void showNotification(Context context, int id, String msg){
            NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(msg);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MapsActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MapsActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        getHmNotificacoes().put(id, mBuilder);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(id, mBuilder.build());

    }

}
