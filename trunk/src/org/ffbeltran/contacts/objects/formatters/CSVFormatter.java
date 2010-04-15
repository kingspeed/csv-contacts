package org.ffbeltran.contacts.objects.formatters;

import java.util.Map;

import org.ffbeltran.contacts.R;
import org.ffbeltran.contacts.objects.MyAddress;
import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.MyInstantMessenger;
import org.ffbeltran.contacts.objects.MyOrganization;
import org.ffbeltran.contacts.objects.MyPhone;

public class CSVFormatter implements ContactFormatter {
    
    private Map<Integer, Boolean> fields;
    
    public CSVFormatter(Map<Integer, Boolean> fields) {
        this.fields = fields;
    }

    @Override
    public String formatContact(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        result.append(myContact.getName());
        if (fields.get(R.string.field_phone)) {
            result.append(";");
            result.append(formatPhones(myContact));
        }
        
        if (fields.get(R.string.field_email)) {
            result.append(";");
            result.append(formatEMails(myContact));
        }
        
        if (fields.get(R.string.field_address)) {
            result.append(";");
            result.append(formatAddresses(myContact));
        }
        
        if (fields.get(R.string.field_im)) {
            result.append(";");
            result.append(formatIM(myContact));
        }
        
        if (fields.get(R.string.field_organization)) {
            result.append(";");
            result.append(formatOrganizations(myContact));
        }
        
        if (fields.get(R.string.field_note)) {
            result.append(";");
            result.append(formatNotes(myContact));
        }
        return result.toString();
    }
    
    private String formatPhones(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        if (myContact.getPhones() != null) {
            for (MyPhone phone : myContact.getPhones()) {
                result.append(phone.getNumber());
                result.append(":");
            }
            if (myContact.getPhones().size()>1) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
    
    private String formatEMails(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        if (myContact.getEmails() != null) {
            for (String email : myContact.getEmails()) {
                result.append(email);
                result.append(":");
            }
            if (myContact.getEmails().size()>1) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
    
    private String formatAddresses(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        if (myContact.getAddresses() != null) {
            for (MyAddress address : myContact.getAddresses()) {
                result.append(address.getAddress());
                result.append(":");
            }
            if (myContact.getAddresses().size()>1) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
    
    private String formatIM(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        if (myContact.getInstantMessengers() != null) {
            for (MyInstantMessenger im : myContact.getInstantMessengers()) {
                result.append(im.getName());
                result.append(":");
            }
            if (myContact.getInstantMessengers().size()>1) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
    
    private String formatOrganizations(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        if (myContact.getOrganizations() != null) {
            for (MyOrganization org : myContact.getOrganizations()) {
                result.append(org.getCompany() + "-" + org.getTitle());
                result.append(":");
            }
            if (myContact.getOrganizations().size()>1) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
    
    private String formatNotes(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        result.append(myContact.getNotes());
        return result.toString();
    }

}
