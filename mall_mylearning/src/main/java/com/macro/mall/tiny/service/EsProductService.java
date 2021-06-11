package com.macro.mall.tiny.service;

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品搜索管理Service
 * Created by macro on 2018/6/19.
 */

public interface EsProductService
{
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据指定id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    EsProduct create(Long id);

    /**
     * 批量伤处商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称过着副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);
}
