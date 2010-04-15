package org.ffbeltran.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class CSVContacts extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.export_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                openExportActivity();
            }
        });
        button = (Button) findViewById(R.id.preferences_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                openPreferencesActivity();
            }
        });
        button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                openAbout();
            }
        });
        button = (Button) findViewById(R.id.exit_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                CSVContacts.this.finish();
            }
        });
    }
    
    private void openExportActivity() {
        startActivity(new Intent(this, ExportContacts.class));
    }
    
    private void openPreferencesActivity() {
        startActivity(new Intent(this, Preferences.class));
    }
    
    private void openAbout() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View aboutView = factory.inflate(R.layout.about, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle(R.string.main_title);
        builder.setView(aboutView);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }

}