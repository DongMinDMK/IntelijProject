package org.example.springboot_g14.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springboot_g14.dto.ProductVO;
import org.example.springboot_g14.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        HashMap<String, Object> hm = productService.getBestNewList();

        mav.addObject("bestList", hm.get("bestList"));
        mav.addObject("newList", hm.get("newList"));


        mav.setViewName("main");

        return mav;
    }

    @GetMapping("/category")
    public ModelAndView category(@RequestParam("kind") String kind){
        ModelAndView mav = new ModelAndView();

        List<ProductVO> list = productService.getKindList(kind);

        mav.addObject("kindProduct", list);

        mav.setViewName("product/productKind");

        return mav;
    }

    @GetMapping("/productDetail")
    public ModelAndView productDetail(@RequestParam("pseq") int pseq, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        ProductVO productVO = productService.getOneProduct(pseq);

        mav.addObject("productVO", productVO);

        mav.setViewName("product/productDetail");

        return mav;
    }
}
