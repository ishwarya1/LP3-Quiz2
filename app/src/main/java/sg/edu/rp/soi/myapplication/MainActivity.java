package sg.edu.rp.soi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    ///

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";


    ////



    RadioGroup rg;
    EditText etTitle, etRemarks;
    Button btnLaunch;
    NotificationCompat.Builder builder;
    RadioButton rbNormal,rbImportant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY},1);
        }

        etTitle=findViewById(R.id.etTitle);
        etRemarks=findViewById(R.id.etRemarks);

        rbNormal=findViewById(R.id.rbNormal);
        rbImportant=findViewById(R.id.rbImportant);
        btnLaunch=findViewById(R.id.btnLaunch);

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(rbNormal.isChecked()) {
                 createNotification(etTitle.getText().toString().trim(), etRemarks.getText().toString().trim(),1);
                }
                else if(rbImportant.isChecked())
                {
                 createNotification(etTitle.getText().toString().trim(), etRemarks.getText().toString().trim(),2);
                }
            }
        });
    }



    public void createNotification(String title, String message,int type)
    {
        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(MainActivity.this , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(MainActivity.this,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(MainActivity.this);

        if(type==1)
        {
            mBuilder.setSmallIcon(android.R.drawable.ic_menu_info_details);
        }
        else
        {
            mBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
        }

        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }


}
//updated

