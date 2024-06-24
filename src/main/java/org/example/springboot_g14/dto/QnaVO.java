package org.example.springboot_g14.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QnaVO {
    private Integer qseq;
    private String subject;
    private String content;
    private String pass;
    private String security;
    private String reply;
    private String userid;
    private Timestamp indate;
}
