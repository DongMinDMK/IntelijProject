package org.example.springboot_g14.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.springboot_g14.dao.IAdminDAO;
import org.example.springboot_g14.dto.AdminVO;
import org.example.springboot_g14.dto.Paging;
import org.example.springboot_g14.dto.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    IAdminDAO adminDAO;

    public AdminVO getAdmin(String adminid) {
        AdminVO adminVO = adminDAO.getAdmin(adminid);
        return adminVO;
    }

    public HashMap<String, Object> getAdminProductList(HttpServletRequest request) {
        HashMap<String, Object> hm = new HashMap<>();
        HttpSession session = request.getSession();

        int page = 1;
        if( request.getParameter("page") != null ) {
            page = Integer.parseInt( request.getParameter("page") );
            session.setAttribute("page", page);
        }else if( session.getAttribute("page") != null ) {
            page = (Integer)session.getAttribute("page");
        }else {
            session.removeAttribute("page");
        }

        String key="";
        if( request.getParameter("key") != null) {
            key = request.getParameter("key");
            session.setAttribute("key", key);
        }else if( session.getAttribute("key") != null ) {
            key = (String)session.getAttribute("key");
        }else {
            session.removeAttribute("key");
        }

        Paging paging = new Paging();
        paging.setPage(page);

        int count = adminDAO.getAllCount("product", "name", key);
        paging.setTotalCount(count);
        paging.calPaging();

        paging.setStartNum(paging.getStartNum()-1);

        List<ProductVO> list = adminDAO.getProductList(paging, key);

        hm.put("productList", list);
        hm.put("paging", paging);
        hm.put("key", key);

        return hm;
    }
}
