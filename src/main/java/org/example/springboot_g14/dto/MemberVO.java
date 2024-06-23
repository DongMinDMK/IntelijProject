package org.example.springboot_g14.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberVO {
    @NotEmpty(message = "id를 입력하세요")
    private String userid;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String pwd;
    @NotEmpty(message = "이름을 입력하세요")
    private String name;
    @NotEmpty(message = "이메일을 입력하세요")
    private String email;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    @NotEmpty(message = "전화번호를 입력하세요")
    private String phone;
    private String useyn;
    private Timestamp indate;
    private String provider;
}
