package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.MemberVO;

@Mapper
public interface IMemberDAO {
    MemberVO getMember(String userid);

    void insertMember(MemberVO memberVO);
}
