package com.example.topic;

class Contents {
    private String name;
    private String summary;
    private String thumb_url;
    private String vidold;


    public Contents(String vidold, String name, String thumb_url, String summary) {
        this.name = name;
        this.summary = summary;
        this.thumb_url = thumb_url;
        this.vidold = vidold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVidold() {return vidold;}

    public void setVidold(String vidold) {
        this.vidold = vidold;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumb_url() {
        return thumb_url;
    }
    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
}
