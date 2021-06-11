package com.macro.mall.tiny.service;

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

public interface PmsBrandService
{
    public List<PmsBrand> listAllBrand();
    public int createBrand(PmsBrand brand);
    public int updateBrand(Long id, PmsBrand brand);
    public int deleteBrand(Long id);
    public List<PmsBrand> listBrand(int pageNum, int pageSize);
    public PmsBrand getBrand(Long id);
}
