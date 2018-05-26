package com.husen.mall2.service.impl;

import com.husen.mall2.model.*;
import com.husen.mall2.repository.LogisticsRecordRepository;
import com.husen.mall2.repository.OrderRepository;
import com.husen.mall2.service.LogisticsService;
import com.husen.mall2.vo.LogisticsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class LogisticsServiceImpl implements LogisticsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogisticsServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LogisticsRecordRepository logisticsRecordRepository;

    @Override
    public LogisticsVO logistics(Integer orderIdInt) {
        Order order = orderRepository.findById(orderIdInt).get();
        Logistics logistics = order.getLogistics();
        LOGGER.info("查询到的物流信息为:{}", logistics);
        LogisticsCompany company = logistics.getCompany();
        Sort.Order sortOrder = new Sort.Order(Sort.Direction.DESC, "time");
        Sort sort = new Sort(sortOrder);
        List<LogisticsRecord> records = logisticsRecordRepository.findAllByLogistics_Id(logistics.getId(), sort);
        List<String> notes = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        records.stream().forEach(x -> {
            notes.add(simpleDateFormat.format(x.getTime()) + "  " + x.getContent());
        });
        LogisticsVO vo = new LogisticsVO(logistics.getId(), company.getName(), logistics.getExpressNumber(), notes, company.getPhone(), logistics.getStatu());
        return vo;
    }
}
