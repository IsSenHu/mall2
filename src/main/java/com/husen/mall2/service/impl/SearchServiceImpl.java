package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.husen.mall2.enums.HttpWebService;
import com.husen.mall2.enums.SortStyle;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.Good;
import com.husen.mall2.repository.CouponRepository;
import com.husen.mall2.repository.GoodRepository;
import com.husen.mall2.repository.SearchRepository;
import com.husen.mall2.service.SearchService;
import com.husen.mall2.util.HttpClientUtil;
import com.husen.mall2.vo.*;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Brand;
import ecjtu.husen.pojo.DAO.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author husen
 */
@Service
@Transactional
public class SearchServiceImpl implements SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    @Autowired
    private SearchRepository searchRepository;

    @Override
    public com.husen.mall2.util.Page<GoodVO> search(Search search) throws IOException {
        LOGGER.info("接收到的查询参数为：{}", search.toString());
        String itemIdsStr = HttpClientUtil.getData(HttpWebService.ITEMS.getValue() + paramsStr(search));
        LOGGER.info("调用接口查询结果为：{}", itemIdsStr);
        List<Integer> itemIds = JSONObject.parseArray(itemIdsStr, Integer.class);
        Sort.Order order = order(search);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(search.getCurrentPage() - 1, search.getPageSize(), sort);
        LOGGER.info("itemIds:{}", itemIds);
        LOGGER.info("search:{}", search);
        LOGGER.info("pageable:{}", pageable);
        Page<Good> page = searchRepository.findAllByItemIdInAndGoodNameContainingAndIsShelves(itemIds, search.getGoodName(), GlobalVar.UP, pageable);
        LOGGER.info("查询出的结果为：{}", page.getContent());
        return pageVO(page);
    }

    @Override
    public FindGood findGood(Integer sportItemId) throws IOException {
        String brandsStr = HttpClientUtil.getData(HttpWebService.FIND_BRANDS.getValue() + "/" + sportItemId);
        String applyersStr = HttpClientUtil.getData(HttpWebService.FIND_APPLYERS.getValue() + "/" + sportItemId);
        String materialsStr = HttpClientUtil.getData(HttpWebService.FIND_MATERIALS.getValue() + "/" + sportItemId);
        List<Brand> brands = JSON.parseArray(brandsStr, Brand.class);
        List<Applyer> applyers = JSON.parseArray(applyersStr, Applyer.class);
        List<Material> materials = JSON.parseArray(materialsStr, Material.class);
        List<FindBrand> findBrands = new ArrayList<>();
        List<FindApplyer> findApplyers = new ArrayList<>();
        List<FindMaterial> findMaterials = new ArrayList<>();
        brands.stream().forEach(x -> {
            FindBrand findBrand = new FindBrand();
            findBrand.setBrandId(x.getBrandId());
            findBrand.setBrandName(x.getBrandName());
            findBrands.add(findBrand);
        });
        materials.stream().forEach(y -> {
            FindMaterial findMaterial = new FindMaterial();
            findMaterial.setMaterialId(y.getMaterialId());
            findMaterial.setMaterialName(y.getMaterialName());
            findMaterials.add(findMaterial);
        });
        applyers.stream().forEach(z -> {
            FindApplyer findApplyer = new FindApplyer();
            findApplyer.setApplyerId(z.getApplyerId());
            findApplyer.setApplyerName(z.getApplyerName());
            findApplyers.add(findApplyer);
        });
        FindGood findGood = new FindGood();
        findGood.setBrands(findBrands);
        findGood.setApplyers(findApplyers);
        findGood.setMaterials(findMaterials);
        return findGood;
    }

    @Override
    public List<GoodVO> recommend(Integer sportItemId) {
        try {
            String dataStr = HttpClientUtil.getData(HttpWebService.RECOMMEND.getValue() + "/" + sportItemId);
            if(dataStr == null){
                return new ArrayList<>();
            }
            LOGGER.info("查询到的推荐物品id是：{}", dataStr);
            List<Integer> itemIds = JSON.parseArray(dataStr, Integer.class);
            //取3个就好了，没有3个就全取
            List<Good> goods = searchRepository.findAllByItemIdIn(itemIds);
            LOGGER.info("查询到的推荐商品是：{}", goods);

            if(goods.size() <= 3){
                return goodsToGoodVOs(goods);
            }else {
                Random random = new Random();
                List<Integer> indexs = new ArrayList<>();
                List<Good> newGoods = new ArrayList<>();
                while (true){
                    if(indexs.size() >= 3){
                        break;
                    }
                    int index = random.nextInt(goods.size());
                    if(indexs.contains(index)){
                        continue;
                    }else {
                        indexs.add(index);
                        newGoods.add(goods.get(index));
                    }
                }
                return goodsToGoodVOs(newGoods);
            }
        } catch (IOException e) {
            LOGGER.error("请求接口查询推荐发生异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<GoodVO> goodsToGoodVOs(List<Good> goods){
        List<GoodVO> goodVOS = new ArrayList<>();
        goods.stream().forEach(x -> {
            GoodVO goodVO = new GoodVO();
            if(x.getPisc() == null || x.getPisc().trim().length() == 0){
                //后期没有图片的话要显示默认值
                goodVO.setFirstPic(null);
            }else {
                goodVO.setFirstPic(x.getPisc().split("connectionRegex")[0]);
            }
            goodVO.setPrice(x.getLastPrice());
            goodVO.setGoodId(x.getGoodId());
            goodVO.setAccumulatedSales(x.getAccumulatedSales());
            goodVO.setGoodName(x.getGoodName());
            goodVOS.add(goodVO);
        });
        return goodVOS;
    }
    /**
     * 生成参数字符串
     * @param search
     * @return
     */
    public static String paramsStr(Search search){
        if(search == null){
            return "/0/0/0/0";
        }
        StringBuilder params = new StringBuilder();
        if(search.getApplyerId() != null){
            params.append("/" + search.getApplyerId());
        }else {
            params.append("/" + 0);
        }
        if(search.getBrandId() != null){
            params.append("/" + search.getBrandId());
        }else {
            params.append("/" + 0);
        }
        if(search.getMaterialId() != null){
            params.append("/" + search.getMaterialId());
        }else {
            params.append("/" + 0);
        }
        if(search.getSportItemId() != null){
            params.append("/" + search.getSportItemId());
        }else {
            params.append("/" + 0);
        }
        return params.toString();
    }

    private static Sort.Order order(Search search){
        if(search.getSort() == null || SortStyle.COMPREHENSIVE.getValue().equals(search.getSort())){
            return new Sort.Order(Sort.Direction.DESC, "comprehensive");
        }else if(SortStyle.SALE.getValue().equals(search.getSort())){
            return new Sort.Order(Sort.Direction.DESC, "accumulatedSales");
        }else if(SortStyle.PRICE.getValue().equals(search.getSort())){
            return new Sort.Order(Sort.Direction.ASC, "lastPrice");
        }else if (SortStyle.REVIEW.getValue().equals(search.getSort())){
            return new Sort.Order(Sort.Direction.DESC, "reviewsScore");
        }
        return new Sort.Order(Sort.Direction.DESC, "comprehensive");
    }
    private static com.husen.mall2.util.Page<GoodVO> pageVO(Page<Good> page){
        List<GoodVO> content = new ArrayList<>();
        page.getContent().forEach(x -> {
            GoodVO goodVO = new GoodVO();
            goodVO.setGoodName(x.getGoodName());
            goodVO.setAccumulatedSales(x.getAccumulatedSales());
            goodVO.setGoodId(x.getGoodId());
            goodVO.setPrice(x.getLastPrice());
            if(x.getPisc() != null && x.getPisc().trim().length() > 0){
                String[] strs = x.getPisc().split("connectionRegex");
                goodVO.setFirstPic(strs[0]);
            }else {
                goodVO.setFirstPic("");
            }
            content.add(goodVO);
        });
        com.husen.mall2.util.Page<GoodVO> voPage = new com.husen.mall2.util.Page<>();
        voPage.setContent(content);
        voPage.setCurrentPage(page.getNumber() + 1);
        voPage.setPageSize(page.getSize());
        voPage.setRowsTotal(page.getTotalElements());
        voPage.setTotalPage(page.getTotalPages());
        return voPage;
    }

    public static void main(String[] args) throws IOException {
        Search search = new Search();
        search.setSportItemId(1);
        String itemIdsStr = HttpClientUtil.getData(HttpWebService.ITEMS.getValue() + paramsStr(search));
        System.out.println(itemIdsStr);
    }
}
