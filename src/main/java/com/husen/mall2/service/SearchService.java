package com.husen.mall2.service;

import com.husen.mall2.vo.FindGood;
import com.husen.mall2.vo.GoodVO;
import com.husen.mall2.vo.Search;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    com.husen.mall2.util.Page<GoodVO> search(Search search) throws IOException;

    FindGood findGood(Integer sportItemId) throws IOException;

    List<GoodVO> recommend(Integer sportItemId);
}
