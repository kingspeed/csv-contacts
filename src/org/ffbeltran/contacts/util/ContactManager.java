package org.ffbeltran.contacts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ffbeltran.contacts.R;
import org.ffbeltran.contacts.objects.MyAddress;
import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.MyInstantMessenger;
import org.ffbeltran.contacts.objects.MyOrganization;
import org.ffbeltran.contacts.objects.MyPhone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Contacts;
import android.provider.Contacts.People;

public class ContactManager {
    
    public int contactsCount(ContentResolver cr) {
        String[] columns = { People._ID };
        Cursor cur = cr.query(People.CONTENT_URI, columns, null, null, null);
        return cur.getCount();
    }
    
    public List<MyContact> requestContacts(ContentResolver cr, Map<Integer, Boolean> prefs) {
        List<MyContact> people = new ArrayList<MyContact>();
        String[] columns = {People._ID, People.DISPLAY_NAME, People.NOTES, People.TYPE};        
        Cursor cur = cr.query(People.CONTENT_URI, columns, null, null, null);
        String id;
        String name;
        String note;
        MyContact myContact;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                id = cur.getString(cur.getColumnIndex(People._ID));
                name = cur.getString(cur.getColumnIndex(People.DISPLAY_NAME));
                note = cur.getString(cur.getColumnIndex(People.NOTES));
                myContact = new MyContact(id);
                myContact.setName(name);
                if (prefs.get(R.string.field_note)) {
                    myContact.setNotes(note);
                }
                if (prefs.get(R.string.field_phone)) {
                    myContact.setPhones(requestPhoneNumbers(cr, myContact.getId()));
                }
                if (prefs.get(R.string.field_email)) {
                    myContact.setEmails(requestEmails(cr, myContact.getId()));
                }
                if (prefs.get(R.string.field_address)) {
                    myContact.setAddresses(requestAddresses(cr, myContact.getId()));
                }
                if (prefs.get(R.string.field_im)) {
                    myContact.setInstantMessengers(requestInstantMessenger(cr, myContact.getId()));
                }
                if (prefs.get(R.string.field_organization)) {
                    myContact.setOrganizations(requestOrganizations(cr, myContact.getId()));
                }
                people.add(myContact);
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return people;
    }
    
    public List<MyPhone> requestPhoneNumbers(ContentResolver cr, String idContact) {
        List<MyPhone> phones = new ArrayList<MyPhone>();
        String[] columns = { Contacts.Phones.NUMBER, Contacts.Phones.TYPE };
        Cursor pCur = cr.query(Contacts.Phones.CONTENT_URI, columns,
                Contacts.Phones.PERSON_ID + " = ?", new String[] { idContact }, null);
        MyPhone phone;
        String number;
        String type;
        while (pCur.moveToNext()) {
            number = pCur.getString(pCur.getColumnIndex(Contacts.Phones.NUMBER));
            type = pCur.getString(pCur.getColumnIndex(Contacts.Phones.TYPE));
            phone = new MyPhone(number, type);
            phones.add(phone);
        }
        pCur.close();

        return phones;
    }
    
    public List<String> requestEmails(ContentResolver cr, String idContact) {
        String[] columns = { Contacts.ContactMethods.DATA };
        Cursor emailCur = cr.query(Contacts.ContactMethods.CONTENT_EMAIL_URI,
                columns, Contacts.ContactMethods.PERSON_ID + " = ?",
                new String[] { idContact }, null);
        List<String> emails = new ArrayList<String>();
        while (emailCur.moveToNext()) {
            emails.add(emailCur.getString(emailCur.getColumnIndex(Contacts.ContactMethods.DATA)));
        }
        emailCur.close();
        return emails;
    }
    
    public List<MyAddress> requestAddresses(ContentResolver cr, String idContact) {
        String[] columns = { Contacts.ContactMethodsColumns.DATA, Contacts.ContactMethodsColumns.TYPE};
        String addrWhere = Contacts.ContactMethods.PERSON_ID 
                + " = ? AND " + Contacts.ContactMethods.KIND + " = ?"; 
        String[] addrWhereParams = new String[]{ idContact, 
                Integer.toString(Contacts.KIND_POSTAL)};        
        Cursor addrCur = cr.query(Contacts.ContactMethods.CONTENT_URI, columns,
                addrWhere, addrWhereParams, null);
        List<MyAddress> addresses = new ArrayList<MyAddress>();
        MyAddress address;
        String addr;
        String type;
        while (addrCur.moveToNext()) { 
            addr = addrCur.getString(addrCur.getColumnIndex(Contacts.ContactMethodsColumns.DATA));
            type = addrCur.getString(addrCur.getColumnIndex(Contacts.ContactMethodsColumns.TYPE));
            address = new MyAddress(addr, type);
            addresses.add(address);
        }
        addrCur.close();
        return addresses;
    }
    
    public List<MyInstantMessenger> requestInstantMessenger(ContentResolver cr, String idContact) {
        String[] columns = { Contacts.ContactMethodsColumns.DATA, Contacts.ContactMethodsColumns.TYPE };
        String imWhere = Contacts.ContactMethods.PERSON_ID + " = ? AND "
                + Contacts.ContactMethods.KIND + " = ?";
        String[] imWhereParams = new String[] { idContact,
                Integer.toString(Contacts.KIND_IM) };
        Cursor imCur = cr.query(Contacts.ContactMethods.CONTENT_URI, columns,
                imWhere, imWhereParams, null);
        List<MyInstantMessenger> instantMessengers = new ArrayList<MyInstantMessenger>();
        MyInstantMessenger im;
        String imName;
        String imType;
        if (imCur.moveToFirst()) {
            imName = imCur.getString(imCur.getColumnIndex(Contacts.ContactMethodsColumns.DATA));
            imType = imCur.getString(imCur.getColumnIndex(Contacts.ContactMethodsColumns.TYPE));
            im = new MyInstantMessenger(imName, imType);
            instantMessengers.add(im);
        }
        imCur.close();
        return instantMessengers;
    }
    
    public List<MyOrganization> requestOrganizations(ContentResolver cr, String idContact) {
        String[] columns = { Contacts.Organizations.COMPANY, Contacts.Organizations.TITLE };
        String orgWhere = Contacts.ContactMethods.PERSON_ID + " = ?"; 
        String[] orgWhereParams = new String[]{ idContact }; 
        Cursor orgCur = cr.query(Contacts.Organizations.CONTENT_URI, 
                  columns, orgWhere, orgWhereParams, null);
        List<MyOrganization> organizations = new ArrayList<MyOrganization>();
        MyOrganization myOrganization;
        String orgName;
        String title;
        if (orgCur.moveToFirst()) { 
            orgName = orgCur.getString(orgCur.getColumnIndex(Contacts.Organizations.COMPANY));
            title = orgCur.getString(orgCur.getColumnIndex(Contacts.Organizations.TITLE));
            myOrganization = new MyOrganization(orgName, title);
            organizations.add(myOrganization);
        } 
        orgCur.close();
        return organizations;
    }

}
