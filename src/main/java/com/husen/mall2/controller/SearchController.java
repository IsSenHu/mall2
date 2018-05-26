package com.husen.mall2.controller;

import com.husen.mall2.service.SearchService;
import com.husen.mall2.util.JsonResultUtil;
import com.husen.mall2.util.Page;
import com.husen.mall2.vo.FindGood;
import com.husen.mall2.vo.GoodVO;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;
    /**
     * 根据运动物品查询商品
     * 还要返回查询条件（品牌，适用者，材质）
     * @param search
     * @return
     */
    @RequestMapping("/search")
    private ModelAndView search(Search search, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        Search oldSearch = (Search) session.getAttribute("search");
        if(oldSearch != null){
            session.removeAttribute("search");
        }
        if(search.getSportItemId() == null){
            return new ModelAndView("home/home3");
        }
        if(search.getCurrentPage() == null){
            search.setCurrentPage(1);
        }
        if(search.getPageSize() == null){
            search.setPageSize(12);
        }
        if(search.getGoodName() == null){
            search.setGoodName("");
        }
        session.setAttribute("search", search);
        LOGGER.info("接收到的查询条件是：{}", search.toString());
        Page<GoodVO> page = null;
        FindGood findGood = null;
        List<GoodVO> recommend = null;
        try {
            page = searchService.search(search);
            findGood = searchService.findGood(search.getSportItemId());
            recommend = searchService.recommend(search.getSportItemId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("home/search")
                .addObject("page", page)
                .addObject("findGood", findGood)
                .addObject("search", search)
                .addObject("recommend", recommend);
    }

    /**
     * 根据查询条件查询商品
     * @param name
     * @param value
     * @param request
     * @return
     */
    @RequestMapping("/searchJs")
    private @ResponseBody JsonResult<Page<GoodVO>> searchJs(String name, @RequestParam(required = false) Integer value, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Search oldSearch = (Search) session.getAttribute("search");
        LOGGER.info("value:{}", value);
        if(oldSearch == null){
            oldSearch = new Search();
            oldSearch.setCurrentPage(1);
            oldSearch.setPageSize(12);
            oldSearch.setSportItemId(1);
            oldSearch.setSort(1);
        }
        Search newSearch = condition(name, value, oldSearch);
        LOGGER.info("接收到的查询条件是：{}", newSearch.toString());
        Page<GoodVO> page = null;
        try {
            page = searchService.search(newSearch);
            LOGGER.info("条件查询的结果page：{}", page.toString());
            return JsonResultUtil.success(page, "成功");
        }catch (Exception e){
            LOGGER.error("发生错误：{}", e.getMessage());
            e.printStackTrace();
            return JsonResultUtil.faile(e.getMessage(), "失败");
        }
    }

    /**
     * 根据商品名称模糊查询商品
     * @param goodName
     * @param request
     * @return
     */
    @RequestMapping("/searchByName")
    private @ResponseBody JsonResult<Page<GoodVO>> searchByName(String goodName, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Search oldSearch = (Search) session.getAttribute("search");
        if(oldSearch == null){
            oldSearch.setCurrentPage(1);
            oldSearch.setPageSize(12);
            oldSearch.setSort(1);
            oldSearch.setSportItemId(1);
        }
        oldSearch.setGoodName(goodName);
        session.setAttribute("search", oldSearch);
        LOGGER.info("接收到的名称是：{}", oldSearch.getGoodName());
        Page<GoodVO> page = null;
        try {
            page = searchService.search(oldSearch);
            LOGGER.info("名称查询的结果page：{}", page.toString());
            return JsonResultUtil.success(page, "成功");
        }catch (Exception e){
            LOGGER.error("发生错误：{}", e.getMessage());
            return JsonResultUtil.faile(e.getMessage(), "失败");
        }
    }
    /**
     * 返回查询条件集合
     * @param name
     * @param value
     * @param search
     * @return
     */
    private Search condition(String name, Integer value, Search search){
        search.setCurrentPage(1);
        if("brandId".equals(name)){
            search.setBrandId(value);
        }else if("applyerId".equals(name)){
            search.setApplyerId(value);
        }else if("materialId".equals(name)){
            search.setMaterialId(value);
        }else if("sort".equals(name)){
            search.setSort(value);
        }else if("currentPage".equals(name)){
            search.setCurrentPage(value);
        }

        Search newSearch = new Search();
        BeanUtils.copyProperties(search, newSearch);
        return newSearch;
    }
}
