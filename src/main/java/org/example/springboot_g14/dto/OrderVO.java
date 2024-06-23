package org.example.springboot_g14.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderVO {
    private int odseq;
    private int oseq;
    private String userid;
    private Timestamp indate;
    private String mname;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    private String phone;
    private int pseq;
    private String pname;
    private int quantity;
    private int price2;
    private String result;
}
