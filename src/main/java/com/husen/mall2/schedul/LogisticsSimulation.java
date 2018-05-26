package com.husen.mall2.schedul;

import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.model.Address;
import com.husen.mall2.model.Logistics;
import com.husen.mall2.model.LogisticsRecord;
import com.husen.mall2.model.Order;
import com.husen.mall2.repository.AddressRepository;
import com.husen.mall2.repository.LogisticsRecordRepository;
import com.husen.mall2.repository.LogisticsRepository;
import com.husen.mall2.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author: HuSen
 * @Description: 模拟物流信息
 * @Date: Created in 10:22 2018/5/20
 * @Modified By:
 */
@Component
public class LogisticsSimulation {
    private static final Logger log = LoggerFactory.getLogger(LogisticsSimulation.class);

    private static final String[] FROM = {"北京市", "上海市", "杭州市", "广州市", "福建市", "成都市"};

    private static final String[] DELIVERY_MAN = {"刘桂源 13628063820", "王德宝 14780104441", "黄星宇 18779168632"};

    private static final String AIRPORT = "机场";

    private static final String TRANSIT_SHIPMENT = "中转站";

    private static final String BUSINESS_DEPARTMENT = "业务部";

    private static final String CAINIAO_YIZHAN = "菜鸟驿站";

    private static Random RANDOM = new Random();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private LogisticsRecordRepository logisticsRecordRepository;


    @Scheduled(fixedDelay = 1000 * 60)
    private void run(){
        /**
         * 1，先获取所有的已发货未收货状态的订单
         * 2，获取该订单的地址
         * 3，根据订单地址模拟出物料信息
         * */
        List<Order> orders = orderRepository.findAllByStatu(2);
        for (Order order : orders){
            Address address = order.getAddress();
            Logistics logistics = order.getLogistics();
            if(!Objects.isNull(address) && !Objects.isNull(logistics)){
                /**
                 * 可以模拟物流信息了
                 * 1，随机从北京，上海，杭州，广州，福建，成都发货
                 * */
                List<LogisticsRecord> records = logisticsRecordRepository.findAllByLogistics_Id(logistics.getId());
                int number = records.size();
                LogisticsRecord record = new LogisticsRecord();
                record.setLogistics(logistics);
                if(number == 1){
                    record.setTime(new Date(records.get(number - 1).getTime().getTime() + 1000 * 60 * 60));
                    String from = randomFrom();
                    if(address.getCity().contains(from) || address.getProvince().contains(from)){
                        record.setContent(from + BUSINESS_DEPARTMENT + " 揽件");
                    }else {
                        record.setContent(from + AIRPORT + " 揽件");
                    }
                }else if(number == 2){
                    record.setTime(new Date(records.get(number - 1).getTime().getTime() + 1000 * 60 * 60));
                    record.setContent(records.get(number - 1).getContent().replace("揽件", "发件"));
                }else if(number == 3){
                    record.setTime(new Date(records.get(number - 1).getTime().getTime() + 1000 * 60 * 60 * 5));
                    record.setContent("到达" + address.getCity() + TRANSIT_SHIPMENT);
                }else if(number == 4){
                    record.setTime(new Date(records.get(number - 1).getTime().getTime() + 1000 * 60 * 60 * 1));
                    record.setContent("派件员:" + randomDeliverMan() + "  正在为您派件");
                }else if(number == 5){
                    record.setTime(new Date(records.get(number - 1).getTime().getTime() + 1000 * 60 * 60 * 2));
                    record.setContent("已由" + address.getAddress() + CAINIAO_YIZHAN + "代收");
                }else if(number == 6){
                    continue;
                }
                logisticsRecordRepository.save(record);
            }
        }
    }

    /**
     * 随机获取出发地
     * @return
     */
    private String randomFrom(){
        return FROM[RANDOM.nextInt(FROM.length)];
    }

    /**
     * 随机获取派件员
     * @return
     */
    private String randomDeliverMan(){
        return DELIVERY_MAN[RANDOM.nextInt(DELIVERY_MAN.length)];
    }
}
