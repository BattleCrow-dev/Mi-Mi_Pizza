package com.battlecrow.mimipizza;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.battlecrow.mimipizza.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final long DELIVERY_TIME = 20000;
    public static final String CHANNEL_ID = "delivery_channel";
    private ActivityMainBinding binding;
    private Fragment menuFragment;
    private Fragment helpFragment;
    private Fragment cartFragment;
    private Fragment deliveryFragment;
    private Intent serviceIntent;

    private final BroadcastReceiver timerExpiredReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopDeliveryTimer();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("timer_expired");
        LocalBroadcastManager.getInstance(this).registerReceiver(timerExpiredReceiver, filter);

        if (binding.bottomNavigation.getSelectedItemId() == R.id.menu_item_3) {
            if (!TimerService.isDelivering)
                switchFragment(cartFragment);
            else
                switchFragment(deliveryFragment);
        }
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timerExpiredReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        createNotificationChannel();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.POST_NOTIFICATIONS }, 1);

        menuFragment = new MenuFragment();
        helpFragment = new HelpFragment();
        cartFragment = new CartFragment();
        deliveryFragment = new DeliveryFragment();
        serviceIntent = new Intent(this, TimerService.class);

        switchFragment(cartFragment);
        switchFragment(menuFragment);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                switchFragment(menuFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_item_2) {
                switchFragment(helpFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_item_3) {
                if (!TimerService.isDelivering)
                    switchFragment(cartFragment);
                else
                    switchFragment(deliveryFragment);
                return true;
            }

            return false;
        });
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }

    public void startDeliveryTimer() {
        serviceIntent.putExtra("time", DELIVERY_TIME);
        TimerService.isDelivering = true;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.POST_NOTIFICATIONS }, 1);

        startForegroundService(serviceIntent);
        switchFragment(deliveryFragment);
    }

    public void stopDeliveryTimer() {
        serviceIntent.putExtra("time", 0);
        TimerService.stopTimer();
        stopService(serviceIntent);

        if (binding.bottomNavigation.getSelectedItemId() == R.id.menu_item_3)
            switchFragment(cartFragment);
    }

    public void addItemToCart(CartItem item) {
        ((CartFragment) cartFragment).addItem(item);
    }
}