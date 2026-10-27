package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirm;
    private RadioGroup rgRole;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        store = new UserStore(this);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        rgRole = findViewById(R.id.rgRole);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();

        if (Validator.isEmpty(name)) {
            etName.setError(getString(R.string.error_name_required));
            etName.requestFocus();
            return;
        }
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
        if (Validator.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_password_required));
            etPassword.requestFocus();
            return;
        }
        if (!Validator.isValidPassword(password)) {
            etPassword.setError(getString(R.string.error_password_short));
            etPassword.requestFocus();
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

        String role = (rgRole.getCheckedRadioButtonId() == R.id.rbProvider)
                ? UserStore.ROLE_PROVIDER
                : UserStore.ROLE_CUSTOMER;

        if (!store.register(name, email, password, role)) {
            etEmail.setError(getString(R.string.error_email_taken));
            etEmail.requestFocus();
            return;
        }

        Toast.makeText(this, R.string.toast_registered, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Extras.EMAIL, email);
        startActivity(intent);
        finish();
    }

    public void goToLogin(View view) {
        finish();
    }
}