package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.ProductVO;

import java.util.List;

@Mapper
public interface IProductDAO {
    List<ProductVO> getBestList();

    List<ProductVO> getNewList();

    List<ProductVO> getKindList(String kind);

    ProductVO getOneProduct(int pseq);
}
