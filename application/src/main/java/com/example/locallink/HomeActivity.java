package com.example.locallink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private String email;
    private String name;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate");

        UserStore store = new UserStore(this);
        email = store.getSessionEmail();
        if (email == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        name = store.getName(email);
        role = store.getRole(email);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText(getString(R.string.greeting, name));
    }

    public void onCategoryClick(View view) {
        ViewGroup card = (ViewGroup) view;
        TextView label = (TextView) card.getChildAt(1);
        Toast.makeText(this,
                getString(R.string.toast_category, label.getText()),
                Toast.LENGTH_SHORT).show();
    }

    public void openProfile(View view) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        intent.putExtra(Extras.NAME, name);
        intent.putExtra(Extras.EMAIL, email);
        intent.putExtra(Extras.ROLE, role);
        startActivity(intent);
    }

    @Override protected void onStart()   { super.onStart();   Log.d(TAG, "onStart"); }
    @Override protected void onResume()  { super.onResume();  Log.d(TAG, "onResume"); }
    @Override protected void onPause()   { super.onPause();   Log.d(TAG, "onPause"); }
    @Override protected void onStop()    { super.onStop();    Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}