package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "OmsPortalOrderController", description = "订单管理")
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/order")
public class OmsPortalOrderController
{
    @Autowired
    private OmsPortalOrderService omsPortalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return omsPortalOrderService.generateOrder(orderParam);
    }

}
