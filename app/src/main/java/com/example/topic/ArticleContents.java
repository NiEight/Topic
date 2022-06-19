package com.example.topic;
//ArticleFragment에 들어갈 정보를 저장하고 사용하기 위한 클래스
public class ArticleContents {

    private String title;       //기사 제목
    private String description; //기사 내용
    private String link;        //기사 RUL

    public ArticleContents(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String toString()
    {
        return title + ",,," + description + ",,," + link;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
