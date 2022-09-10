package com.my.wobinichapp.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.HomeActivity;
import com.my.wobinichapp.act.LoginActivity;
import com.my.wobinichapp.act.PersonalChat;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    String status ="";
    String Msg ="";
    String Title ="";
    String key ="";
    String jobType="";
    String type="";
    String MemberName="";
    String MemberImage="";
    String ReceiverId="";
    String Sender_Id="";
    String userimage="";
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        Log.e("remote_message>>>", String.valueOf(remoteMessage));

        // log the getting message from firebase
        // Log.d(TAG, "From: " + remoteMessage.getFrom());

        //  if remote message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload" + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();

            //jobType = data.get("type");


            /* Check the message contains data If needs to be processed by long running job
               so check if data needs to be processed by long running job */

            // Handle message within 10 seconds
            handleNow(data);

             /* if (jobType.equalsIgnoreCase(JobType.LONG.name())) {
                 // For long-running tasks (10 seconds or more) use WorkManager.
                 scheduleLongRunningJob();
            } else {} */

        }

        // if message contains a notification payload.
      /*  if (remoteMessage.getNotification() != null) {

            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
            // Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // make a own server request here using your http client
    }

    private void handleNow(Map<String, String> data) {
        sendNotification(data.get("status"), data.get("key"), data);
         /* if (data.containsKey("title") && data.containsKey("message")) {
            sendNotification(data.get("title"), data.get("message"));
        } */
    }

    private void sendNotification(String title, String messageBody, Map<String, String> data) {

            Log.e("title", "title = " + title);
            Log.e("title", "messageBody = " + messageBody);
            try
            {
                JSONObject object = new JSONObject(data.get("message"));
                 status = object.optString("result");
                 Msg = object.optString("message");

               //  Title = object.optString("name");
                 key = object.optString("key");
                 type = object.optString("type");
                if(type.equals("Group Pos")) Title = "New Post";
                else if (type.equals("Group Chat")) Title = "New Group Message";
                else if(type.equals("Personal")) {
                    Title = "New Message";
                      MemberName = object.optString("name");
                       MemberImage = object.optString("userimage");
                      ReceiverId = object.optString("receiver_id");
                       Sender_Id = object.optString("sender_id");
                    //   userimage = object.optString("userimage");
                }
                else if (type.equals("User Ans")) Title = "New Answer";

                //  Intent intent;

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            int requestCode = (int) System.currentTimeMillis();


       /*     if (status.equals("Accept")) {
                try {


                    msg = object.getString("key");
                    driver_id = object.getString("driver_id");
                    request_id = object.getString("request_id");
                    driver_imgUrl = object.optString("image");
                    driver_number = object.optString("provider_number");
                    provider_firstname = object.getString("provider_firstname");

                    intent = new Intent(this, DriverInfoActivity.class);

                    intent.putExtra("driver_id", driver_id);
                    intent.putExtra("provider_firstname", provider_firstname);
                    intent.putExtra("request_id", request_id);
                    intent.putExtra("pickup_status", status);
                    intent.putExtra("driver_imgUrl", driver_imgUrl);
                    intent.putExtra("driver_number", driver_number);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            else if (status.equals("Complete")) {

                try {


                    msg = object.getString("key");//  * http://bai-hai.com/webservice/add_rating?
                    // user_id=1&provider_id=1&request_id=1&review=good%20service&rating=5
                    //driver_id = object.getString("driver_id");
                    request_id = object.getString("request_id");
                    intent = new Intent(this, RatingActivity.class);
                    //intent.putExtra("driver_id", driver_id);
                    intent.putExtra("request_id", request_id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("excep", String.valueOf(e));

                }


            }
            else if (chatRequestStatus.equals("Chat request")) {

                try {
                    msg = object.getString("key");

                    intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            else if (chatRequestStatus.equals("Your Chat request is Accepted")) {

                try {
                    msg = object.getString("key");

                    intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }


            if (Chatresult.equals("successful")) {

                try {
                    title = object.getString("name");
                    msg = object.getString("key");//  * http://bai-hai.com/webservice/add_rating?
                    // user_id=1&provider_id=1&request_id=1&review=good%20service&rating=5
                    //driver_id = object.getString("driver_id");
                    //request_id = object.getString("request_id");
                    intent = new Intent(this, HomeActivity.class);
                    //intent.putExtra("driver_id", driver_id);
                    //intent.putExtra("request_id", request_id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("excep", String.valueOf(e));

                }
            }
*/
            // msg = object.getString("key");

         // Drawable drawable = getApplicationInfo().loadIcon(getPackageManager());
        //  Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

       boolean importantShift = Preference.getBool(this, Preference.key_switch_shift_change);

   /*    if(importantShift)
       {*/

        if(!Preference.get(this,Preference.KEY_USER_ID).equalsIgnoreCase(Sender_Id))
        {
            if(type.equalsIgnoreCase("personal"))
            {
                //Preference.save(this,Preference.KEY_USER_ID,Sender_Id);

                intent = new Intent(this, PersonalChat.class);
                intent.putExtra("MemberName",MemberName);
                intent.putExtra("MemberImage",MemberImage);
                intent.putExtra("ReceiverId",ReceiverId);
                intent.putExtra("senderId",Sender_Id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent intent1 = new Intent("filter_string");
                intent.putExtra("key", "My Data");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);

            }else
            {
                intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent intent1 = new Intent("filter_string");
                intent.putExtra("key", "My Data");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            }

        }else
            {
            intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

          PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                   PendingIntent.FLAG_ONE_SHOT);

        Bitmap bmp = null;
        try {
            InputStream in = new URL(userimage).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

           String channelId = "1";
           Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                   .setStyle(new NotificationCompat.BigTextStyle().bigText(Msg))
                   .setSmallIcon(R.mipmap.logo)
                   .setLargeIcon(bmp)
                   .setContentTitle(Title)
                   .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                   .setContentText(key)
                   .setAutoCancel(true)
                   .setSound(defaultSoundUri)
                   .setContentIntent(pendingIntent);

           NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           // Since android Oreo notification channel is needed.
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               // Channel human readable title
               NotificationChannel channel = new NotificationChannel(channelId,
                       "Cloud Messaging Service",
                       NotificationManager.IMPORTANCE_DEFAULT);

               notificationManager.createNotificationChannel(channel);
           }

           notificationManager.notify(getNotificationId(), notificationBuilder.build());

             }

            private static int getNotificationId () {
                Random rnd = new Random();
                return 100 + rnd.nextInt(9000);
            }

}

