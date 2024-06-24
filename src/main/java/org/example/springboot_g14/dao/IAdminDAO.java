package org.example.springboot_g14.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_g14.dto.AdminVO;
import org.example.springboot_g14.dto.Paging;
import org.example.springboot_g14.dto.ProductVO;

import java.util.List;

@Mapper
public interface IAdminDAO {
    AdminVO getAdmin(String adminid);

    int getAllCount(String tableName, String fieldName, String key);

    List<ProductVO> getProductList(Paging paging, String key);
}
