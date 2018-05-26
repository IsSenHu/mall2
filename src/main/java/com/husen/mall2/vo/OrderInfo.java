package com.husen.mall2.vo;

import com.husen.mall2.model.Address;

/**
 * 订单详细信息的vo
 * @author husen
 */
public class OrderInfo extends OrderVO {
    private Address address;
    private LogisticsVO logisticsVO;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LogisticsVO getLogisticsVO() {
        return logisticsVO;
    }

    public void setLogisticsVO(LogisticsVO logisticsVO) {
        this.logisticsVO = logisticsVO;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "address=" + address +
                ", logisticsVO=" + logisticsVO +
                '}';
    }
}
