package org.ffbeltran.contacts.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.formatters.ContactFormatter;

public class FileManager {
    
    public static final int SUCCESS = 0;
    public static final int ERROR_NOT_EXISTS = 1;
    public static final int ERROR_NOT_WRITABLE = 2;
    public static final int ERROR_EXCEPTION = 3;
    
    public int createFile(List<MyContact> people, String dirName, String fileName, ContactFormatter formatter) {
        FileWriter f = null;
        try {
            File file = new File(dirName);
            if (!file.exists()) {
                return ERROR_NOT_EXISTS;
            }
            if (!file.canWrite()) {
                return ERROR_NOT_WRITABLE;
            }
            f = new FileWriter(dirName + fileName);
            String line;
            for (MyContact myContact : people) {
                line = formatter.formatContact(myContact);
                f.append(line + "\n");
            }            
        } catch (IOException e) {
            return ERROR_EXCEPTION;
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    return ERROR_EXCEPTION;
                }
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

}
