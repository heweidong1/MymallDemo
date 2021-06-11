package com.macro.mall.tiny.service.Impl;

import com.macro.mall.tiny.mbg.mapper.EsProductDao;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.nosql.elasticsearch.repository.EsProductReoisitory;
import com.macro.mall.tiny.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EsProductServiceImpl implements EsProductService {

    @Resource
    private EsProductDao esProductDao;

    @Autowired
    private EsProductReoisitory productRepository;





    @Override
    public int importAll() {
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(null);
        Iterable<EsProduct> esProducts = productRepository.saveAll(allEsProductList);
        Iterator<EsProduct> iterator = esProducts.iterator();
        int result = 0;
        while (iterator.hasNext())
        {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(id);
        if(allEsProductList.size()>0)
        {
            EsProduct esProduct = allEsProductList.get(0);
            result =productRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if(!CollectionUtils.isEmpty(ids))
        {
            List<EsProduct> esProducts = new ArrayList<>();
            for(Long id :ids)
            {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProducts.add(esProduct);
            }
            productRepository.deleteAll(esProducts);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
