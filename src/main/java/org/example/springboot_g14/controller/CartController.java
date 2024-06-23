package org.example.springboot_g14.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.springboot_g14.dto.CartVO;
import org.example.springboot_g14.dto.MemberVO;
import org.example.springboot_g14.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/cartInsert")
    public String cartInsert(@RequestParam("pseq") int pseq, @RequestParam("quantity") int quantity, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            return "redirect:/loginForm";
        }else{
            CartVO cartVO = new CartVO();
            cartVO.setPseq(pseq);
            cartVO.setQuantity(quantity);
            cartVO.setUserid(memberVO.getUserid());

            cartService.insertCart(cartVO);

            return "redirect:/cartList";
        }
    }

    @GetMapping("/cartList")
    public ModelAndView cartList(HttpServletRequest request){

        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            mav.setViewName("redirect:/loginForm");
        }else{
            HashMap<String, Object> hm = cartService.getCartList(memberVO.getUserid());

            mav.addObject("cartList", hm.get("cartList"));
            mav.addObject("totalPrice", hm.get("totalPrice"));
            mav.setViewName("mypage/cartList");
        }

        return mav;

    }

    @PostMapping("/cartDelete")
    public String cartDelete(@RequestParam("cseq") String[] cseqs){
        cartService.deleteCart(cseqs);
        return "redirect:/cartList";
    }
}
