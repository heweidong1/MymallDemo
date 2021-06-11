package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import com.macro.mall.tiny.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "MemberReadHistoryController", description = "会员商品浏览记录管理")
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController
{
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory)
    {
        int i = memberReadHistoryService.create(memberReadHistory);
        if(i>0)
        {
            return CommonResult.success(i);
        }else
        {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除浏览记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids")List<String>ids)
    {
        int delete = memberReadHistoryService.delete(ids);
        if(delete>0)
        {
            return CommonResult.success(delete);
        }else
        {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<MemberReadHistory>> list(Long memberId)
    {
        List<MemberReadHistory> list = memberReadHistoryService.list(memberId);
        return CommonResult.success(list);
    }

















}
