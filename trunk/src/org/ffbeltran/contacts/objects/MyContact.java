package org.ffbeltran.contacts.objects;

import java.util.List;

public class MyContact {
    
    private String id;
    private String name;
    private String notes;
    private List<MyPhone> phones;
    private List<String> emails;
    private List<MyAddress> addresses;
    private List<MyInstantMessenger> instantMessengers;
    private List<MyOrganization> organizations;
    
    public MyContact(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<MyPhone> getPhones() {
        return phones;
    }
    
    public void setPhones(List<MyPhone> phones) {
        this.phones = phones;
    }
    
    public List<String> getEmails() {
        return emails;
    }
    
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<MyAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<MyAddress> addresses) {
        this.addresses = addresses;
    }

    public List<MyInstantMessenger> getInstantMessengers() {
        return instantMessengers;
    }

    public void setInstantMessengers(List<MyInstantMessenger> instantMessengers) {
        this.instantMessengers = instantMessengers;
    }

    public List<MyOrganization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<MyOrganization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "MyContact [id=" + id + ", name=" + name + ", phones=" + phones
                + ", emails=" + emails + ", addresses=" + addresses
                + ", instantMessengers=" + instantMessengers
                + ", organizations=" + organizations + ", notes=" + notes + "]";
    }
    
}
