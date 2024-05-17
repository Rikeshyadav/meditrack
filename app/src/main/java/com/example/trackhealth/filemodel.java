package com.example.trackhealth;

public class filemodel {
    String filename, fileurl,date;

    public filemodel(String filename, String filter,String date) {
        this.filename = filename;
        this.fileurl = filter;
        this.date=date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }



    public String getDatee() {
        return date;
    }

    public void setDatee(String date) {
        this.date =date;
    }
}
