package org.ffbeltran.contacts;

import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    
    public static final int[] PREFS_INT = {
        R.string.field_phone,
        R.string.field_email,
        R.string.field_address,
        R.string.field_im,
        R.string.field_organization,
        R.string.field_note
    };
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (key.equals(getString(R.string.file_name))) {
            String pattern = "[A-Za-z0-9_]+\\.?[A-Za-z][A-Za-z][A-Za-z]?";
            String value = sp.getString(key, null);
            if (!Pattern.matches(pattern, value)) {
                Editor editor = sp.edit();
                editor.putString(key, getString(R.string.default_file_name));
                editor.commit();
                showErrorFilename();
            }
        }        
    }
    
    private void showErrorFilename() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);        
        builder.setMessage(getString(R.string.preferences_error_file, getString(R.string.default_file_name)));
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }
}