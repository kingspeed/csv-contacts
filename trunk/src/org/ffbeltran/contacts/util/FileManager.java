package org.ffbeltran.contacts.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.formatters.ContactFormatter;

public class FileManager {
    
    public void createFile(List<MyContact> people, String dirName, String fileName, ContactFormatter formatter) {
        FileWriter f = null;
        try {
            File file = new File(dirName);
            if (file.canWrite()) {
                f = new FileWriter(dirName + fileName);
                String line;
                for (MyContact myContact : people) {
                    line = formatter.formatContact(myContact);
                    f.append(line + "\n");
                }
            } else {
                //textView.setText(getString(R.string.error_write));
            }
        } catch (IOException e) {
            //textView.setText(e.getMessage());
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
