package org.ffbeltran.contacts.objects.formatters;

import org.ffbeltran.contacts.objects.MyAddress;
import org.ffbeltran.contacts.objects.MyContact;
import org.ffbeltran.contacts.objects.MyInstantMessenger;
import org.ffbeltran.contacts.objects.MyOrganization;
import org.ffbeltran.contacts.objects.MyPhone;

public class CSVFormatter implements ContactFormatter {

    @Override
    public String formatContact(MyContact myContact) {
        StringBuilder result = new StringBuilder();
        result.append(myContact.getName());
        result.append(";");
        if (myContact.getPhones() != null) {
            for (MyPhone phone : myContact.getPhones()) {
                result.append(phone.getNumber());
            }
        }
        result.append(";");
        if (myContact.getEmails() != null) {
            for (String email : myContact.getEmails()) {
                result.append(email);
            }
        }
        result.append(";");
        if (myContact.getAddresses() != null) {
            for (MyAddress address : myContact.getAddresses()) {
                result.append(address.getAddress());
            }
        }
        result.append(";");
        if (myContact.getInstantMessengers() != null) {
            for (MyInstantMessenger im : myContact.getInstantMessengers()) {
                result.append(im.getName());
            }
        }
        result.append(";");
        if (myContact.getOrganizations() != null) {
            for (MyOrganization org : myContact.getOrganizations()) {
                result.append(org.getCompany() + "-" + org.getTitle());
            }
        }
        result.append(";");
        result.append(myContact.getNotes());
        return result.toString();
    }

}
