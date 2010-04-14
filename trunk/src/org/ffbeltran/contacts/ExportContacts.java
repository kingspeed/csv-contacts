package org.ffbeltran.contacts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.formatters.CSVFormatter;
import org.ffbeltran.contacts.objects.formatters.ContactFormatter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExportContacts extends Activity implements View.OnClickListener {
    
    private ContactManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.boton);
        button.setOnClickListener(this);
        manager = new ContactManager();
    }

    @Override
    public void onClick(View arg0) {
        TextView textView = (TextView) findViewById(R.id.texto);
        List<MyContact> people = manager.requestContacts(getContentResolver());
        FileWriter f = null;
        try {
            File file = new File("/sdcard/download");
            if (file.canWrite()) {
                f = new FileWriter("/sdcard/download/contacts.txt");
                ContactFormatter formatter = new CSVFormatter();
                String line;
                for (MyContact myContact : people) {
                    line = formatter.formatContact(myContact);
                    f.append(line + "\n");
                }
            } else {
                textView.setText(getString(R.string.error_write));
            }
        } catch (IOException e) {
            textView.setText(e.getMessage());
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    
                }
            }
        }
    }

}