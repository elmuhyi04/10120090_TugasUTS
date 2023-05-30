package model;
// NIM      : 10120090
// Nama     : Muhammad Rizky Muhyi
// Kelas    : IF-3


public class NoteModel {
    private int id;
    private String title;
    private String category;
    private String content;
    private String date;
    public NoteModel() {
    }
    public NoteModel(int id, String title, String category, String content, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}