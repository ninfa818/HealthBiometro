package org.lab.biometro.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.lab.biometro.model.UserModel;

public class SharedPreferenceUtil {

    private static SharedPreferences mSharedPref;

    static public void getInstance(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    static public void clear() {
        mSharedPref.edit().clear().apply();
    }

    static public boolean isSaved() {
        return mSharedPref.getBoolean("saved", false);
    }

    static public void setSaved(boolean flag) {
        mSharedPref.edit().putBoolean("saved", flag).apply();
    }

    static public String getEmail() {
        return mSharedPref.getString("email", "");
    }

    static public void setEmail(String email) {
        mSharedPref.edit().putString("email", email).apply();
    }

    static public String getMemberID() {
        return mSharedPref.getString("memberid", "");
    }

    static public void setMemberID(String memberid) {
        mSharedPref.edit().putString("memberid", memberid).apply();
    }

    static public String getPassword() {
        return mSharedPref.getString("password", "");
    }

    static public void setPassword(String password) {
        mSharedPref.edit().putString("password", password).apply();
    }

    static public String getToken() {
        return mSharedPref.getString("token", "");
    }

    static public void setToken(String token) {
        mSharedPref.edit().putString("token", token).apply();
    }

    static public void saveCurrentUser(UserModel userModel) {
        mSharedPref.edit().putString("current_user", new Gson().toJson(userModel)).apply();
    }

    static public UserModel getCurrentUser() {
        return new Gson().fromJson(mSharedPref.getString("current_user", ""), UserModel.class);
    }

}
