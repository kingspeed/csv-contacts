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
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExportContacts extends Activity {
    
    private TextView console;
    private ProgressDialog pd;
    private Map<Integer, Boolean> prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        
        console = (TextView) findViewById(R.id.export_console);
        pd = ProgressDialog.show(this, "Working..", 
                "Requesting contacts, exporting to CSV and writing to a file", true, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefs = new HashMap<Integer, Boolean>();
        for (int i = 0; i < Preferences.prefsInt.length; i++) {
            prefs.put(Preferences.prefsInt[i], 
                    settings.getBoolean(getString(Preferences.prefsInt[i]), true));
        }
        
        Thread thread = new Thread(new Worker());
        thread.start();
    }
    
    class Worker implements Runnable {
        
        private int numContacts;
        private int fileResult;
        
        public void run() {
            
            ContactManager manager = new ContactManager();
            numContacts = manager.contactsCount(getContentResolver());
            List<MyContact> contacts = manager.requestContacts(getContentResolver(), prefs);        
            
            FileManager fm = new FileManager();
            ContactFormatter formatter = new CSVFormatter(prefs);
            fileResult = fm.createFile(contacts, "/sdcard/download/", 
                    "contacts.txt", formatter);        
            
            Button button = (Button) findViewById(R.id.main_button);
            button.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    ExportContacts.this.finish();
                }
            });
            
            handler.sendEmptyMessage(0);
        }

        private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                pd.dismiss();
                StringBuilder sb = new StringBuilder();
                sb.append("Se encontraron " + numContacts + " contactos\n");
                boolean fileError = false;
                if (fileResult == FileManager.ERROR_NOT_EXISTS) {
                    sb.append("El directorio no existe.");
                    fileError = true;
                } else if (fileResult == FileManager.ERROR_NOT_WRITABLE) {
                    sb.append("No se pudo escribir en el directorio.");
                    fileError = true;                    
                } else if (fileResult == FileManager.ERROR_EXCEPTION) {
                    sb.append("Se produjo un error inesperado.");
                    fileError = true;                    
                }
                if (fileError) {
                    sb.append(" Es posible que no exista el fichero o esté corrupto");
                }
                console.append(getString(R.string.export_finished));
            }

        };
    }

}
