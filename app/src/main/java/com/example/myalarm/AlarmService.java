package com.example.myalarm;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class AlarmService extends Service {
    public AlarmService() {
    }
    Ringtone r;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent it = new Intent(this,MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Vibrator v=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            v.vibrate(VibrationEffect.createOneShot(3000,VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else{
            v.vibrate(3000);
        }
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(this,uri);
        r.play();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        r.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}