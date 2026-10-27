package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etNew;
    private EditText etConfirm;
    private String email;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        store = new UserStore(this);
        email = getIntent().getStringExtra(Extras.EMAIL);
        if (email == null) {
            email = UserStore.DEMO_EMAIL;
        }

        TextView tvSubtitle = findViewById(R.id.tvResetSubtitle);
        tvSubtitle.setText(getString(R.string.reset_subtitle, email));

        etNew = findViewById(R.id.etNewPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        Button btnReset = findViewById(R.id.btnResetPassword);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptReset();
            }
        });
    }

    private void attemptReset() {
        String password = etNew.getText().toString();
        String confirm = etConfirm.getText().toString();

        if (Validator.isEmpty(password)) {
            etNew.setError(getString(R.string.error_password_required));
            etNew.requestFocus();
            return;
        }
        if (!Validator.isValidPassword(password)) {
            etNew.setError(getString(R.string.error_password_short));
            etNew.requestFocus();
            return;
        }
        if (Validator.isEmpty(confirm)) {
            etConfirm.setError(getString(R.string.error_confirm_required));
            etConfirm.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            etConfirm.setError(getString(R.string.error_password_mismatch));
            etConfirm.requestFocus();
            return;
        }

        store.updatePassword(email, password);
        Toast.makeText(this, R.string.toast_password_updated, Toast.LENGTH_SHORT).show();
        openLoginFresh();
    }

    public void goToLogin(View view) {
        openLoginFresh();
    }

    private void openLoginFresh() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Extras.EMAIL, email);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}