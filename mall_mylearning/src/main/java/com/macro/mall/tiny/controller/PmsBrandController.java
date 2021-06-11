package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 */
@Api(tags = "PmsBrandController",description = "商品品牌管理")
@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/brand")
public class PmsBrandController
{
    @Autowired
    private PmsBrandService pmsBrandService;


    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value = "listAll",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<List<PmsBrand>> getBrandList()
    {
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('pms:brand:create')")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand)
    {
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if(count==1)
        {
            commonResult = CommonResult.success(pmsBrand);
        }else
        {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }


    @ApiOperation("更新指定id品牌信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public CommonResult updateBrand(@PathVariable("id") Long id , @RequestBody PmsBrand pmsBrand, BindingResult result)
    {
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrand);
        if(count==1)
        {
            commonResult = CommonResult.success(pmsBrand);
        }else
        {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }


    @ApiOperation("删除指定id的品牌")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult deleteBrand(@PathVariable("id") Long id)
    {
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(id);
        if(count==1)
        {
            commonResult = CommonResult.success(null);
        }else
        {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @ApiOperation("分页查询品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize)
    {
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsBrands));
    }

    @ApiOperation("获取指定id的品牌详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id)
    {
        PmsBrand brand = pmsBrandService.getBrand(id);
        return CommonResult.success(brand);
    }
}
