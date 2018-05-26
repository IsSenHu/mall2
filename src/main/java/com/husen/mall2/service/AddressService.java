package com.husen.mall2.service;

import com.husen.mall2.model.Address;
import com.husen.mall2.model.Areas;
import com.husen.mall2.model.Cities;
import com.husen.mall2.model.Provinces;
import com.husen.mall2.vo.AddressVO;
import com.husen.mall2.vo.CityAndArea;

import java.util.List;

/**
 * @author husen
 */
public interface AddressService {
    List<Provinces> provinces();

    List<Cities> cities(String provinceid);

    List<Areas> areas(String cityid);

    CityAndArea cityAndArea(String provinceid);

    Address addAddress(Integer userId, AddressVO address);

    void delete(Integer addressId);

    void setDefault(Integer userId, Integer addressId);

    Address updateAddress(AddressVO address);

    List<Address> findByUser(Integer userId);

    Address findAddressById(Integer addressId);
}
