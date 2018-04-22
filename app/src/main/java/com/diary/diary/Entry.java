package com.diary.diary;

public class Entry {
    private String date, title, updatedAt, content;
    private int id;

    Entry(int id, String date, String title, String updatedAt, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.updatedAt = updatedAt;
        this.content = content;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setContent(String entryContent) {
        this.content = entryContent;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}
