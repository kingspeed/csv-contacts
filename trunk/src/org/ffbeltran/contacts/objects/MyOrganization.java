package org.ffbeltran.contacts.objects;

public class MyOrganization {
    
    private String company;
    private String title;
    
    public MyOrganization() {
        super();
    }

    public MyOrganization(String company, String title) {
        super();
        this.company = company;
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MyOrganization [company=" + company + ", title=" + title + "]";
    }

}
