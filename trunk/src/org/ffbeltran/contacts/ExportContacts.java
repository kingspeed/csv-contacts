package org.ffbeltran.contacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.formatters.CSVFormatter;
import org.ffbeltran.contacts.objects.formatters.ContactFormatter;
import org.ffbeltran.contacts.util.ContactManager;
import org.ffbeltran.contacts.util.FileManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExportContacts extends Activity {
    
    private static final int[] prefsInt = {
        R.string.field_phone,
        R.string.field_email,
        R.string.field_address,
        R.string.field_im,
        R.string.field_organization,
        R.string.field_note
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        
        TextView console = (TextView) findViewById(R.id.export_console);
        console.setText(getString(R.string.export_started) + "\n");
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Map<Integer, Boolean> prefs = new HashMap<Integer, Boolean>();
        for (int i = 0; i < prefsInt.length; i++) {
            prefs.put(prefsInt[i], settings.getBoolean(getString(prefsInt[i]), true));
        }
        
        console.append("Buscando contactos...\n");
        ContactManager manager = new ContactManager();
        List<MyContact> contacts = manager.requestContacts(getContentResolver(), prefs);        
        console.append("Obtenidos " + contacts.size() + " contactos\n");
        
        console.append("Exportando a CSV...\n");
        FileManager fm = new FileManager();
        ContactFormatter formatter = new CSVFormatter(prefs);
        fm.createFile(contacts, "/sdcard/download/", "contacts.txt", formatter);        
        
        console.append(getString(R.string.export_finished) + "\n");
        

        Button button = (Button) findViewById(R.id.main_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ExportContacts.this.finish();
            }
        });
    }

}
