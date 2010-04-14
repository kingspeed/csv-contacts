package org.ffbeltran.contacts.objects;

public class MyAddress {
    
    private String address;
    private String type;
    
    public MyAddress() {
        super();
    }

    public MyAddress(String address, String type) {
        super();
        this.address = address;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MyAddress [address=" + address + ", type=" + type + "]";
    }

}
