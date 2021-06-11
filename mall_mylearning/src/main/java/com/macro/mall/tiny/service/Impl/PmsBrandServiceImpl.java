package com.macro.mall.tiny.service.Impl;
import com.github.pagehelper.PageHelper;
import com.macro.mall.tiny.mbg.mapper.PmsBrandMapper;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.mbg.model.PmsBrandExample;
import com.macro.mall.tiny.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService
{

    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public List<PmsBrand> listAllBrand()
    {
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectByExample(new PmsBrandExample());
        return pmsBrands;
    }

    @Override
    public int createBrand(PmsBrand brand)
    {
        int i = pmsBrandMapper.insertSelective(brand);
        return i;
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand)
    {
        brand.setId(id);
        return pmsBrandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id)
    {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id)
    {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }

}
