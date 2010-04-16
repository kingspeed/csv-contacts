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
    private String fileName;
    
    private static final String FOLDER = "/sdcard/download/";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        
        console = (TextView) findViewById(R.id.export_console);
        String pdSummary = getString(R.string.export_pd_summary);
        String pdTitle = getString(R.string.export_pd);
        pd = ProgressDialog.show(this, pdSummary, pdTitle, true, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefs = new HashMap<Integer, Boolean>();
        for (int i = 0; i < Preferences.PREFS_INT.length; i++) {
            prefs.put(Preferences.PREFS_INT[i], 
                    settings.getBoolean(getString(Preferences.PREFS_INT[i]), true));
        }
        fileName = settings.getString(getString(R.string.file_name), 
                getString(R.string.default_file_name));
        
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
            fileResult = fm.createFile(contacts, FOLDER, fileName, formatter);        
            
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
                sb.append(getString(R.string.export_contacts_found, numContacts));
                sb.append("\n");
                boolean fileError = false;
                if (fileResult == FileManager.ERROR_NOT_EXISTS) {
                    sb.append(getString(R.string.export_error_not_exists, FOLDER));
                    fileError = true;
                } else if (fileResult == FileManager.ERROR_NOT_WRITABLE) {
                    sb.append(getString(R.string.export_error_not_writable, FOLDER));
                    fileError = true;                    
                } else if (fileResult == FileManager.ERROR_EXCEPTION) {
                    sb.append(getString(R.string.export_error_exception));
                    fileError = true;                    
                }
                if (fileError) {
                    sb.append(getString(R.string.export_error));
                } else {
                    String absolutePath = FOLDER + fileName;
                    console.append(getString(R.string.export_finished, absolutePath));
                }
            }

        };
    }

}
