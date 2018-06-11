package org.boot.bootCRUD.note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import java.util.Date;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Boolean isDone;
    private Date date;
    private String comment;

    public Note() {
    }

    public Note(String text, String comment) {
        this.text = text;
        this.isDone = false;
        this.date = new Date();
        this.comment = comment;
    }

    public String getComment() {
        return comment != null ? comment : "&lt;none&gt";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date != null ? date : new Date();//ЗАТЫЧКА ВЫДАЕТ НЬЮ ДЭЙТ
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text != null ? text : "&lt;none&gt";
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isDone=" + isDone +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                '}';
    }
}
