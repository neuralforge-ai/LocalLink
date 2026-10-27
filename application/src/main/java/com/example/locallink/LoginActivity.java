package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        store = new UserStore(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        CheckBox cbShowPassword = findViewById(R.id.cbShowPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        String prefill = getIntent().getStringExtra(Extras.EMAIL);
        if (prefill != null) {
            etEmail.setText(prefill);
        }

        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean checked) {
                etPassword.setTransformationMethod(checked
                        ? HideReturnsTransformationMethod.getInstance()
                        : PasswordTransformationMethod.getInstance());
                etPassword.setSelection(etPassword.getText().length());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

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

        if (store.checkLogin(email, password)) {
            store.setSession(email);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
        }
    }

    public void goToRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void goToForgot(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}