package org.example.springboot_g14.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.springboot_g14.dto.AdminVO;
import org.example.springboot_g14.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String admin(){
        return "admin/adminLoginForm";
    }

    @PostMapping("/adminlogin")
    public String adminlogin(@ModelAttribute("adminVO") @Valid AdminVO adminVO, BindingResult bindingResult, HttpServletRequest request, Model model){

        String url="admin/adminLoginForm";

        if(bindingResult.getFieldError("adminid") != null){
            model.addAttribute("message", "아이디를 입력해주세요.");
        }else if(bindingResult.getFieldError("pwd") != null){
            model.addAttribute("message", "패스워드를 입력해주세요.");
        }else{
            AdminVO adminVO1 = adminService.getAdmin(adminVO.getAdminid());

            if(adminVO1 == null){
                model.addAttribute("message", "아이디가 일치하지 않습니다.");
            }else if(!adminVO1.getPwd().equals(adminVO.getPwd())){
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            }else{
                url="redirect:/adminProductList";
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", adminVO1);
            }
        }

        return url;
    }

    @GetMapping("/adminProductList")
    public ModelAndView adminProductList(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

        AdminVO adminVO = (AdminVO) session.getAttribute("adminUser");

        if(adminVO == null){
            mav.setViewName("admin/adminLoginForm");
        }else{
            HashMap<String, Object> hm = adminService.getAdminProductList(request);

            mav.addObject("productList", hm.get("productList"));
            mav.addObject("paging", hm.get("paging"));
            mav.addObject("key", hm.get("key"));

            mav.setViewName("admin/product/adminProductList");
        }

        return mav;
    }
}
