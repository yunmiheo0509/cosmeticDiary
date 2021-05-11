package com.example.cosmeticdiary;

public class WritingListData {
    private int img;
    private String name;
    private String condition;
    private String satisfy;

    public WritingListData(int img, String name, String condition, String satisfy) {
        this.img = img;
        this.name = name;
        this.condition = condition;
        this.satisfy = satisfy;
    }

    public int getImg() { return img; }

    public String getName() { return name; }

    public String getCondition() { return condition; }

    public String getSatisfy() { return satisfy; }

    public void setImg(int img) { this.img = img; }

    public void setName(String name) { this.name = name; }

    public void setCondition(String condition) { this.condition = condition; }

    public void setSatisfy(String satisfy) { this.satisfy = satisfy; }
}
