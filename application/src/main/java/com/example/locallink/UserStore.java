package com.example.locallink;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStore {

    public static final String DEMO_EMAIL = "test@locallink.com";
    public static final String DEMO_PASSWORD = "123456";
    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_PROVIDER = "provider";

    private static final String PREFS = "locallink_prefs";
    private static final String KEY_SESSION = "session_email";

    private final Context context;
    private final SharedPreferences prefs;

    public UserStore(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = this.context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private String key(String field, String email) {
        return field + "_" + email.trim().toLowerCase();
    }

    private boolean isDemo(String email) {
        return DEMO_EMAIL.equalsIgnoreCase(email.trim());
    }

    public boolean exists(String email) {
        return isDemo(email) || prefs.contains(key("password", email));
    }

    public boolean register(String name, String email, String password, String role) {
        if (exists(email)) {
            return false;
        }
        prefs.edit()
                .putString(key("name", email), name)
                .putString(key("password", email), password)
                .putString(key("role", email), role)
                .apply();
        return true;
    }

    public boolean checkLogin(String email, String password) {
        if (!exists(email)) {
            return false;
        }
        String defaultPassword = isDemo(email) ? DEMO_PASSWORD : null;
        String saved = prefs.getString(key("password", email), defaultPassword);
        return password.equals(saved);
    }

    public void updatePassword(String email, String newPassword) {
        prefs.edit().putString(key("password", email), newPassword).apply();
    }

    public String getName(String email) {
        String fallback = isDemo(email) ? context.getString(R.string.demo_name) : email;
        return prefs.getString(key("name", email), fallback);
    }

    public String getRole(String email) {
        return prefs.getString(key("role", email), ROLE_CUSTOMER);
    }

    public void setSession(String email) {
        prefs.edit().putString(KEY_SESSION, email.trim().toLowerCase()).apply();
    }

    public String getSessionEmail() {
        return prefs.getString(KEY_SESSION, null);
    }

    public void logout() {
        prefs.edit().remove(KEY_SESSION).apply();
    }
}