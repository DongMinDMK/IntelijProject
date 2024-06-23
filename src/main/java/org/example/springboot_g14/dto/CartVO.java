package org.example.springboot_g14.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CartVO {
    private int cseq;
    private String userid;
    private int pseq;
    private String mname;
    private String pname;
    private int quantity;
    private int price2;
    private Timestamp indate;

}
