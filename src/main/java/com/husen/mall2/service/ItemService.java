package com.husen.mall2.service;

import com.husen.mall2.vo.ShopCart;

import java.util.List; /**
 * @author husen
 */
public interface ItemService {
    ShopCart shopCart(Integer userId, List<Integer> itemIdList);
}
