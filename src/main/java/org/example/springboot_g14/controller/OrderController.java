package org.example.springboot_g14.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.springboot_g14.dto.MemberVO;
import org.example.springboot_g14.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orderInsert")
    public String orderInsert(@RequestParam("cseq") String[] cseqs, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        int oseq = 0;

        if(memberVO == null){
            return "redirect:/loginForm";
        }else{
            oseq = orderService.orderInsert(cseqs, memberVO.getUserid());

            return "redirect:/orderList?oseq=" + oseq;
        }
    }

    @GetMapping("/orderList")
    public ModelAndView orderList(@RequestParam("oseq") int oseq, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            mav.setViewName("redirect:/loginForm");
        }else{
            HashMap<String, Object> hm = orderService.getOrderByOseq(oseq);

            mav.addObject("orderList", hm.get("orderList"));
            mav.addObject("totalPrice", hm.get("totalPrice"));
            mav.setViewName("mypage/orderList");
        }

        return mav;
    }

    @PostMapping("/orderInsertOne")
    public ModelAndView orderInsertOne(@RequestParam("pseq") int pseq, @RequestParam("quantity") int quantity, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int oseq = 0;

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            mav.setViewName("redirect:/loginForm");
        }else{
            oseq = orderService.orderInsertOne(memberVO.getUserid(), pseq, quantity);

            mav.setViewName("redirect:/orderList?oseq="+oseq);
        }

        return mav;
    }

    @GetMapping("/mypage")
    public ModelAndView mypage(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            mav.setViewName("redirect:/loginForm");
        }else{
            mav.setViewName("mypage/mypage");
            mav.addObject("finalList", orderService.mypageList(memberVO.getUserid()));
            mav.addObject("title", "진행중인 주문내역");
        }

        return mav;
    }

    @GetMapping("/orderAll")
    public ModelAndView orderAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            mav.setViewName("redirect:/loginForm");
        }else{
            mav.setViewName("mypage/mypage");
            mav.addObject("finalList", orderService.mypageListAll(memberVO.getUserid()));
            mav.addObject("title", "총 주문내역");
        }

        return mav;
    }
}
