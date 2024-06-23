package org.example.springboot_g14.service;

import org.example.springboot_g14.dao.IProductDAO;
import org.example.springboot_g14.dto.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    IProductDAO productDAO;

    public HashMap<String, Object> getBestNewList() {
        HashMap<String, Object> hm = new HashMap<String, Object>();

        hm.put("bestList", productDAO.getBestList());
        hm.put("newList", productDAO.getNewList());

        return hm;


    }

    public List<ProductVO> getKindList(String kind) {

        List<ProductVO> list = productDAO.getKindList(kind);

        return list;

    }

    public ProductVO getOneProduct(int pseq) {
        ProductVO productVO = productDAO.getOneProduct(pseq);

        return productVO;
    }
}
