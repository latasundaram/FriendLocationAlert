package com.location;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
 
public class NotificationViewer extends Activity {
 
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.nearby_friends);
                 
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                 
                notificationManager.cancel(Constants.NOTIFICATION_ID);
        }
}