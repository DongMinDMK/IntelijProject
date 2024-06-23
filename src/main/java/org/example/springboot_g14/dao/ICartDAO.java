package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.CartVO;

import java.util.List;

@Mapper
public interface ICartDAO {
    void insertCart(CartVO cartVO);

    List<CartVO> cartSelectList(String userid);

    void deleteCart(int cseq);
}
