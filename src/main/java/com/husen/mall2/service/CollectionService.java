package com.husen.mall2.service;

import com.husen.mall2.vo.GoodCollectionVO;

import java.util.List;

/**
 * @author husen
 */
public interface CollectionService {
    List<GoodCollectionVO> findAllCollectionByUserId(String username);
}
