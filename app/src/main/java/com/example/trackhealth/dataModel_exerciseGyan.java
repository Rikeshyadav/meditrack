package com.example.trackhealth;

public class dataModel_exerciseGyan {
    private String Title;
    private String datadesc;
    private String dataLanguage;
    private int image;

    public dataModel_exerciseGyan(String title, String datadesc, String dataLanguage, int image) {
        Title = title;
        this.datadesc = datadesc;
        this.dataLanguage = dataLanguage;
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDatadesc() {
        return datadesc;
    }

    public void setDatadesc(String datadesc) {
        this.datadesc = datadesc;
    }

    public String getDataLanguage() {
        return dataLanguage;
    }

    public void setDataLanguage(String dataLanguage) {
        this.dataLanguage = dataLanguage;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
