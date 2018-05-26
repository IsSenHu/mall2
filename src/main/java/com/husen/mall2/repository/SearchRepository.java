package com.husen.mall2.repository;

import com.husen.mall2.model.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Good, Integer> {

    Page<Good> findAllByItemIdInAndGoodNameContainingAndIsShelves(List<Integer> itemIds, String goodName, String shelves, Pageable pageable);

    List<Good> findAllByItemIdIn(List<Integer> itemIds);
}
