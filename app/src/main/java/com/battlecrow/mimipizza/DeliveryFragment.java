package com.battlecrow.mimipizza;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlecrow.mimipizza.databinding.FragmentDeliveryBinding;

import java.util.Locale;

public class DeliveryFragment extends Fragment {

    private FragmentDeliveryBinding binding;

    private final BroadcastReceiver timerTickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long timeLeft = intent.getLongExtra("timeLeft", 0);
            binding.textViewDeliveryTimer.setText(getTimerText(timeLeft));
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("timer_tick");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(timerTickReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(timerTickReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false);

        binding.buttonCancelOrder.setOnClickListener(v -> ((MainActivity) container.getContext()).stopDeliveryTimer());

        return binding.getRoot();
    }

    public String getTimerText(long deliveryTimer) {
        return String.format(Locale.getDefault(), "%02d:%02d", deliveryTimer / (60 * 1000), (deliveryTimer % (60 * 1000)) / 1000);
    }
}