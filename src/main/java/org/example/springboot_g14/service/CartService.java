package org.example.springboot_g14.service;

import org.example.springboot_g14.dao.ICartDAO;
import org.example.springboot_g14.dto.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CartService {

    @Autowired
    ICartDAO cartDAO;

    public void insertCart(CartVO cartVO) {
        cartDAO.insertCart(cartVO);


    }

    public HashMap<String, Object> getCartList(String userid) {
        HashMap<String, Object> hm = new HashMap<String, Object>();

        // 1. cart_view 뷰에서 userid 를 통해 조회

        List<CartVO> list = cartDAO.cartSelectList(userid);

        hm.put("cartList", list);

        //2. 그 조회한 결과에서 총 가격 계산
        int totalPrice = 0;

        for(CartVO cartVO : list){
            totalPrice += cartVO.getPrice2() * cartVO.getQuantity();
        }

        hm.put("totalPrice", totalPrice);

        //3. 해시맵 으로 리턴

        return hm;


    }

    public void deleteCart(String[] cseqs) {
        for(String cseq : cseqs){
            cartDAO.deleteCart(Integer.parseInt(cseq));
        }
    }
}
