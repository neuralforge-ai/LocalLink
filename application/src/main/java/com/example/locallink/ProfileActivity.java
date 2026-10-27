package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String name = getIntent().getStringExtra(Extras.NAME);
        String email = getIntent().getStringExtra(Extras.EMAIL);
        String role = getIntent().getStringExtra(Extras.ROLE);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvRole = findViewById(R.id.tvRole);

        tvName.setText(name);
        tvEmail.setText(email);
        tvRole.setText(UserStore.ROLE_PROVIDER.equals(role)
                ? R.string.role_provider
                : R.string.role_customer);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserStore(ProfileActivity.this).logout();
                Toast.makeText(ProfileActivity.this,
                        R.string.toast_logged_out, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}