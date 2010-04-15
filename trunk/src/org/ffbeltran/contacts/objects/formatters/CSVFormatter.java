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
            if (myContact.getPhones() != null) {
                for (MyPhone phone : myContact.getPhones()) {
                    result.append(phone.getNumber());
                }
            }
        }
        
        if (fields.get(R.string.field_email)) {
            result.append(";");
            if (myContact.getEmails() != null) {
                for (String email : myContact.getEmails()) {
                    result.append(email);
                }
            }
        }
        
        if (fields.get(R.string.field_address)) {
            result.append(";");
            if (myContact.getAddresses() != null) {
                for (MyAddress address : myContact.getAddresses()) {
                    result.append(address.getAddress());
                }
            }
        }
        
        if (fields.get(R.string.field_im)) {
            result.append(";");
            if (myContact.getInstantMessengers() != null) {
                for (MyInstantMessenger im : myContact.getInstantMessengers()) {
                    result.append(im.getName());
                }
            }
        }
        
        if (fields.get(R.string.field_organization)) {
            result.append(";");
            if (myContact.getOrganizations() != null) {
                for (MyOrganization org : myContact.getOrganizations()) {
                    result.append(org.getCompany() + "-" + org.getTitle());
                }
            }
        }
        
        if (fields.get(R.string.field_note)) {
            result.append(";");
            result.append(myContact.getNotes());
        }
        return result.toString();
    }

}
