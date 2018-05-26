package com.husen.mall2.product;

import java.io.Serializable;
import java.util.List;

/**
 * @author husen
 */
public class ItemIdListAndUserNote implements Serializable {
    private List<Integer> itemIdList;
    private String userNote;

    public List<Integer> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<Integer> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }
}
