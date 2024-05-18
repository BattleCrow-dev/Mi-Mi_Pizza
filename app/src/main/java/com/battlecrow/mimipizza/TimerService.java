package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {
    private static final String CHANNEL_ID = "delivery_channel";
    private static final int NOTIFICATION_ID = 123;
    public static boolean isDelivering = false;
    private NotificationManager notificationManager;
    public static CountDownTimer timer;
    private long timerTime;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);

        timerTime = intent.getLongExtra("time", 0);

        if (timerTime != 0) {
            timer = new CountDownTimer(timerTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateNotification(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    sendBroadcastTimerExpired();
                    endNotification();
                    isDelivering = false;
                }
            };
            timer.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Доставка Mi-Mi Pizza")
                .setContentText("Ожидайте, курьер скоро будет у Вас...")
                .build();
    }

    private void updateNotification(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Доставка Mi-Mi Pizza")
                .setContentText("Осталось времени: " + timeLeftFormatted)
                .setProgress((int) timerTime, (int) millisUntilFinished, false)
                .setVibrate(new long[]{0L})
                .setOngoing(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void endNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Доставка Mi-Mi Pizza")
                .setContentText("Курьер уже ждёт Вас")
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500, 500, 500})
                .setAutoCancel(true);

        MediaPlayer.create(getApplicationContext(), R.raw.knock).start();

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void sendBroadcastTimerExpired() {
        Intent broadcastIntent = new Intent("timer_expired");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        timer.cancel();
        stopSelf();
    }

    public static void stopTimer() {
        timer.cancel();
        isDelivering = false;
    }
}