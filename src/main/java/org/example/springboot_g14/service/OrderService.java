package org.example.springboot_g14.service;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import org.example.springboot_g14.dao.ICartDAO;
import org.example.springboot_g14.dao.IOrderDAO;
import org.example.springboot_g14.dto.CartVO;
import org.example.springboot_g14.dto.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    ICartDAO cartDAO;

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int orderInsert(String[] cseqs, String userid) {

        // 1. 일단 장바구니에서 주문하기 버튼을 누르면 orders 테이블에 userid를 이용하여 데이터 삽입
        orderDAO.insertOrders(userid);

        // 2. 데이터를 삽입한 그 레코드의 oseq 리턴
        int oseq = orderDAO.lookUpMaxOseq();

        // 3. cseq 들로 클릭한 상품의 상품번호별로 cart_view 뷰를 조회하고
        for(String cseq : cseqs){
            CartVO cartVO = orderDAO.getCart(Integer.parseInt(cseq));

            // 4. order_detail 에 데이터를 삽입
            orderDAO.insertOrderDetail(oseq, cartVO);

            // 5. 그리고 장바구니 리스트에서 주문한 데이터 삭제
            cartDAO.deleteCart(Integer.parseInt(cseq));
        }
        return oseq;
    }

    public HashMap<String, Object> getOrderByOseq(int oseq) {
        HashMap<String, Object> hm = new HashMap<String, Object>();

        List<OrderVO> list = orderDAO.getOrderByOseq(oseq);

        int totalPrice = 0;
        for(OrderVO orderVO : list){
            totalPrice += orderVO.getPrice2() * orderVO.getQuantity();
        }

        hm.put("orderList", list);
        hm.put("totalPrice", totalPrice);

        return hm;
    }

    public int orderInsertOne(String userid, int pseq, int quantity) {
        // 1. 일단 장바구니에서 주문하기 버튼을 누르면 orders 테이블에 userid를 이용하여 데이터 삽입
        orderDAO.insertOrders(userid);

        // 2. 데이터를 삽입한 그 레코드의 oseq 리턴
        int oseq = orderDAO.lookUpMaxOseq();

        // 3. order_detail 에 데이터를 삽입
        orderDAO.insertOrderDetailOne(oseq, pseq, quantity);

        return oseq;

    }

    public ArrayList<OrderVO> mypageList(String userid) {

        ArrayList<OrderVO> finalList = new ArrayList<>();

        List<Integer> oseqList = orderDAO.getOseqIng(userid);

        for(Integer oseq: oseqList){
            List<OrderVO> orderListByOseq = orderDAO.getOrderByOseq(oseq);

            OrderVO orderVO = (OrderVO) orderListByOseq.get(0);

            orderVO.setPname(orderVO.getPname() + "포함 " + orderListByOseq.size() + "건");

            int totalPrice = 0;

            for(OrderVO orderVO1 : orderListByOseq){
                totalPrice += orderVO1.getPrice2() * orderVO1.getQuantity();
            }

            orderVO.setPrice2(totalPrice);
            finalList.add(orderVO);
        }
        return finalList;
    }

    public ArrayList<OrderVO> mypageListAll(String userid) {
        ArrayList<OrderVO> finalList = new ArrayList<>();

        List<Integer> oseqList = orderDAO.getOseqIngAll(userid);

        for(Integer oseq: oseqList){
            List<OrderVO> orderListByOseq = orderDAO.getOrderByOseq(oseq);

            OrderVO orderVO = (OrderVO) orderListByOseq.get(0);

            orderVO.setPname(orderVO.getPname() + "포함 " + orderListByOseq.size() + "건");

            int totalPrice = 0;

            for(OrderVO orderVO1 : orderListByOseq){
                totalPrice += orderVO1.getPrice2() * orderVO1.getQuantity();
            }

            orderVO.setPrice2(totalPrice);
            finalList.add(orderVO);
        }
        return finalList;
    }
}
