package com.example.clms;
import java.util.Date;

public class MyBook {

    private int Bid, Pages;
    private long Copies;
    private String ISBN, Title,Author, Publisher, Rack, Subject;
    private Date Idate,Ddate;


    public MyBook(long copies, String ISBN, String title, String Subject,String author, String publisher, int pages, String Rack) {
        Title = title;
        Copies = copies;
        Author = author;
        Publisher = publisher;
        this.ISBN = ISBN;
        this.Subject = Subject;
        Pages = pages;
        this.Rack =Rack;

    }


    public int getBid() {
        return Bid;
    }

    public void setBid(int bid) {
        Bid = bid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getIdate() {
        return Idate;
    }

    public void setIdate(Date idate) {
        Idate = idate;
    }

    public Date getDdate() {
        return Ddate;
    }

    public void setDdate(Date ddate) {
        Ddate = ddate;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public long getCopies() {
        return Copies;
    }

    public void setCopies(int copies) {
        this.Copies = copies;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPages() {
        return Pages;
    }

    public void setPages(int pages) {
        Pages = pages;
    }

    public String getRack() {
        return Rack;
    }

    public void setRack(String rack) {
        Rack = rack;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
