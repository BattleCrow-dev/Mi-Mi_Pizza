package com.battlecrow.mimipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference database;
    private String currentName;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        com.battlecrow.mimipizza.databinding.ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance().getReference("UserData");

        binding.imageViewBackArrow.setOnClickListener(v -> finish());

        currentName = CurrentUserData.accountLogin;
        binding.textViewUsername.setText("Здравствуйте, " + currentName + "!");

        binding.exitAccountTextView.setOnClickListener(v -> {
            if(!TimerService.isDelivering) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View customView = getLayoutInflater()
                        .inflate(R.layout.dialog_exit_account, null);
                builder.setView(customView);
                AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow())
                        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                Button buttonNo = customView.findViewById(R.id.buttonNo);
                Button buttonYes = customView.findViewById(R.id.buttonYes);

                buttonNo.setOnClickListener(v1 -> dialog.dismiss());

                buttonYes.setOnClickListener(v12 -> {
                    final boolean[] success = {true};
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                UserData user = snap.getValue(UserData.class);

                                assert user != null;
                                if (Objects.equals(user.getAccountLogin(), currentName))
                                    database.child(Objects.requireNonNull(snap.getKey())).child("logined").setValue(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                            success[0] = false;
                        }
                    });

                    if (success[0]) {
                        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                        prefs.edit().clear().apply();
                        prefs = getSharedPreferences("ChatData", MODE_PRIVATE);
                        prefs.edit().clear().apply();
                        CurrentUserData.clear();
                        startActivity(new Intent(this, LoginActivity.class));
                        finishAffinity();
                        dialog.dismiss();
                    }
                });
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View customView = getLayoutInflater()
                        .inflate(R.layout.dialog_exit_account_error, null);
                builder.setView(customView);
                AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow())
                        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button buttonSend = customView.findViewById(R.id.buttonYes);

                buttonSend.setOnClickListener(v12 -> dialog.dismiss());
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}