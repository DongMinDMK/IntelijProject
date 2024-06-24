package org.example.springboot_g14.service;

import org.example.springboot_g14.dao.ICustomerDAO;
import org.example.springboot_g14.dto.QnaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    ICustomerDAO customerDAO;

    public List<QnaVO> qnaList() {
        List<QnaVO> list = customerDAO.qnaList();
        return list;
    }

    public QnaVO qnaView(int qseq) {
        QnaVO qnaVO = customerDAO.qnaView(qseq);

        return qnaVO;
    }

    public void insertQna(String userid, String pass, String security, String subject, String content) {
        customerDAO.insertQna(userid, pass, security, subject, content);
    }
}
