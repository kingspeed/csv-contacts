package org.ffbeltran.contacts;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
    
    public static final int[] prefsInt = {
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
    }
}