package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        store = new UserStore(this);
        etEmail = findViewById(R.id.etEmail);
        Button btnSend = findViewById(R.id.btnSendReset);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();

                if (Validator.isEmpty(email)) {
                    etEmail.setError(getString(R.string.error_email_required));
                    etEmail.requestFocus();
                    return;
                }
                if (!Validator.isValidEmail(email)) {
                    etEmail.setError(getString(R.string.error_email_invalid));
                    etEmail.requestFocus();
                    return;
                }
                if (!store.exists(email)) {
                    etEmail.setError(getString(R.string.error_email_not_found));
                    etEmail.requestFocus();
                    return;
                }

                Toast.makeText(ForgotPasswordActivity.this,
                        R.string.toast_reset_sent, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                intent.putExtra(Extras.EMAIL, email);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goToLogin(View view) {
        finish();
    }
}