package org.example.springboot_g14.service;

import org.example.springboot_g14.dao.IMemberDAO;
import org.example.springboot_g14.dto.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDAO memberDAO;

    public MemberVO getMember(String userid) {
        MemberVO memberVO = memberDAO.getMember(userid);
        return memberVO;
    }

    public void insertMember(MemberVO memberVO) {
        memberDAO.insertMember(memberVO);
    }
}
