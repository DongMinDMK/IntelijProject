package org.example.springboot_g14.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.springboot_g14.dto.MemberVO;
import org.example.springboot_g14.dto.QnaVO;
import org.example.springboot_g14.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    public String customer(){
        return "customer/intro";
    }

    @GetMapping("/qnaList")
    public ModelAndView qnaList(){
        ModelAndView mav = new ModelAndView();

        List<QnaVO> list = customerService.qnaList();

        mav.addObject("qnaList", list);
        mav.setViewName("customer/qnaList");

        return mav;
    }

    @GetMapping("/qnaView")
    public ModelAndView qnaView(@RequestParam("qseq") int qseq){
        ModelAndView mav = new ModelAndView();

        QnaVO qnaVO = customerService.qnaView(qseq);

        mav.addObject("qnaVO", qnaVO);
        mav.setViewName("customer/qnaView");

        return mav;
    }

    @GetMapping("/writeQnaForm")
    public String writeQnaForm(HttpServletRequest request){
        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        if(memberVO == null){
            return "member/login";
        }else{
            return "customer/writeQnaForm";
        }
    }

    @PostMapping("/writeQna")
    public String writeQna(@RequestParam(value = "pass", required = false, defaultValue = "") String pass, @RequestParam(value = "secret", required = false, defaultValue = "N") String security, @RequestParam("subject") String subject, @RequestParam("content") String content, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");
        String url="redirect:/login";

        if(memberVO == null){
            return "member/login";
        }else{
            if(subject == null || subject.equals("")){
                model.addAttribute("message", "제목을 입력하세요...");
            }else if(content == null || content.equals("")){
                model.addAttribute("message", "내용을 입력하세요...");
            }else{
                url="redirect:/qnaList";
                customerService.insertQna(memberVO.getUserid(), pass, security, subject, content);
            }
        }

        return url;
    }
}

