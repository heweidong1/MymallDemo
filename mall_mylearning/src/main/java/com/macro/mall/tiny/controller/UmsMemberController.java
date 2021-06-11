package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员管理service
 */

@RestController
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/sso")
public class UmsMemberController
{

    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone)
    {
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    public CommonResult updatePassword(@RequestBody String telephone,@RequestParam String authCode)
    {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }


}
