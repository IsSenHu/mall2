package com.husen.mall2.product;

import com.husen.mall2.model.Item;
import com.husen.mall2.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 将ItemID字符串转为按店铺分组的ItemID集合
 * @author husen
 */
@Component
public class ItemIdListProduct {
    @Autowired
    private ItemRepository itemRepository;
    public List<ItemIdListAndUserNote> itemIdStrToListAndUserNoteByStoresId(String itemIdStr, String userNote){
        /**
         * 根据店铺来切分订单
         * 1分割ItemID字符串得到ItemID数组
         * 2将数组转换为集合
         * 3根据ItemID集合查询到所有的Item
         * 4根据Items来获取所有的店铺ID
         * 5使用Set集合来去重
         * 6根据StoresID来分组生成list集合
         * */
        String[] itemIdArray = itemIdStr.split(",");
        List<Integer> itemIdList = Arrays.asList(itemIdArray).stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
        List<Item> items = itemRepository.findAllByItemIdIn(itemIdList);
        List<Integer> storesIdList = items.stream().map(x -> x.getGood().getStores().getStoresId()).collect(Collectors.toList());
        Set<Integer> storesIdSet = new HashSet<>();
        storesIdList.stream().forEach(x -> storesIdSet.add(x));
        List<ItemIdListAndUserNote> lists = new ArrayList<>();
        storesIdSet.stream().forEach(x -> {
            List<Integer> list = new ArrayList<>();
            ItemIdListAndUserNote itemIdListAndUserNote = new ItemIdListAndUserNote();
            items.forEach(y -> {
                if(x.equals(y.getGood().getStores().getStoresId())){
                    list.add(y.getItemId());
                }
            });
            Arrays.stream(userNote.split(",")).forEach(z -> {
                if (z.split("_")[0].equals(x.toString())){
                    if(z.split("_").length <= 1){
                        itemIdListAndUserNote.setUserNote("");
                    }else {
                        itemIdListAndUserNote.setUserNote(z.split("_")[1]);
                    }
                }
            });
            itemIdListAndUserNote.setItemIdList(list);
            lists.add(itemIdListAndUserNote);
        });
        return lists;
    }

    public static void main(String[] args) {
        String string = "1_";
        String[] strings = string.split("_");
        System.out.println(strings.length);
    }
}
