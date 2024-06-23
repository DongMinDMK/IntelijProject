package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.CartVO;
import org.example.springboot_g14.dto.OrderVO;

import java.util.List;

@Mapper
public interface IOrderDAO {
    void insertOrders(String userid);

    int lookUpMaxOseq();

    CartVO getCart(int cseq);

    void insertOrderDetail(int oseq, CartVO cartVO);

    List<OrderVO> getOrderByOseq(int oseq);

    void insertOrderDetailOne(int oseq, int pseq, int quantity);

    List<Integer> getOseqIng(String userid);

    List<Integer> getOseqIngAll(String userid);
}
