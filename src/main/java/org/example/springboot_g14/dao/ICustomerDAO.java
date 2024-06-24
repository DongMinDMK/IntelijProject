package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.QnaVO;

import java.util.List;

@Mapper
public interface ICustomerDAO {
    List<QnaVO> qnaList();

    QnaVO qnaView(int qseq);

    void insertQna(String userid, String pass, String security, String subject, String content);
}
