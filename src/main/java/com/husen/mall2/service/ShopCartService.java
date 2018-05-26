package com.husen.mall2.service;

import com.husen.mall2.vo.ItemMoney;
import com.husen.mall2.vo.ShopCart;

/**
 * @author husen
 */
public interface ShopCartService {
    void saveItem(Integer userId, Integer goodId, Integer number);

    ShopCart shopCart(Integer userId);

    void deleteItem(Integer itemId, String username);

    ItemMoney changeNumber(Integer userId, Integer goodId, String number);

    Integer saveItem2(Integer userId, Integer goodId, Integer number);
}
