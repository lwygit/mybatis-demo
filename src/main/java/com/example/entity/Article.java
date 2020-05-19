package com.example.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    private int id;
    private int todayId;
    private String title;
    private String columns;
    private String describes;
    private String content;
    private String category;
    private String author;
    private String editor;
    private String updateBy;
    private Date createDate;
    private Date updateDate;
    private int status;
}
