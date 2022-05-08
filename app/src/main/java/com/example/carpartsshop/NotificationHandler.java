package com.example.carpartsshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private Context mContext;
    private NotificationManager mManager;
    private  static final String CHANNEL_ID = "shop_notification_channel";
    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Shop Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setDescription("Ezt az üzenetet a Car Parts Shop-tól kapod");
        this.mManager.createNotificationChannel(channel);
    }
    public void sendNotification(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle("Car Parts Shop").setContentText(message).setSmallIcon(R.mipmap.ic_launcher);
        this.mManager.notify(0,builder.build());

    }
}
