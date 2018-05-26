package com.husen.mall2.service.impl;

import com.husen.mall2.enums.AddressDefault;
import com.husen.mall2.model.*;
import com.husen.mall2.repository.*;
import com.husen.mall2.service.AddressService;
import com.husen.mall2.vo.AddressVO;
import com.husen.mall2.vo.CityAndArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private ProvincesRepository provincesRepository;
    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private AreasRepository areasRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public List<Provinces> provinces(){
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        return provincesRepository.findAll(sort);
    }

    @Override
    public List<Cities> cities(String provinceid) {
        return citiesRepository.findAllByProvinceid(provinceid);
    }

    @Override
    public List<Areas> areas(String cityid) {
        return areasRepository.findAllByCityid(cityid);
    }

    @Override
    public CityAndArea cityAndArea(String provinceid) {
        List<Cities> cities = citiesRepository.findAllByProvinceid(provinceid);
        List<Areas> areas = new ArrayList<>();
        if(cities != null && cities.size() > 0){
            areas = areasRepository.findAllByCityid(cities.get(0).getCityid());
        }
        CityAndArea cityAndArea = new CityAndArea();
        cityAndArea.setCities(cities);
        cityAndArea.setAreas(areas);
        return cityAndArea;
    }

    @Override
    public Address addAddress(Integer userId, AddressVO address) {
        Address newAddress = new Address();
        newAddress.setUser(userRepository.findById(userId).get());
        newAddress.setPhone(address.getPhone());
        newAddress.setReceivePersonName(address.getReceivePersonName());
        newAddress.setAddress(address.getDetailAddress());
        newAddress.setProvince(provincesRepository.findByProvinceid(address.getProvinceid()).getProvince());
        newAddress.setCity(citiesRepository.findByCityid(address.getCityid()).getCity());
        newAddress.setArea(areasRepository.findByAreaid(address.getAreaid()).getArea());
        newAddress.setIsDefault(AddressDefault.NO.getValue());
        return addressRepository.save(newAddress);
    }

    @Override
    public void delete(Integer addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public void setDefault(Integer userId, Integer addressId) {
        User user = userRepository.findById(userId).get();
        Integer oldAddressId = user.getAddressId();
        if(oldAddressId != null){
            Address oldAddress = addressRepository.findById(oldAddressId).get();
            oldAddress.setIsDefault(AddressDefault.NO.getValue());
            addressRepository.save(oldAddress);
        }
        user.setAddressId(addressId);
        userRepository.save(user);
        Address address = addressRepository.findById(addressId).get();
        address.setIsDefault(AddressDefault.YES.getValue());
        addressRepository.save(address);
    }

    @Override
    public Address updateAddress(AddressVO address) {
        Address newAddress = addressRepository.findById(address.getAddressId()).get();
        newAddress.setPhone(address.getPhone());
        newAddress.setReceivePersonName(address.getReceivePersonName());
        newAddress.setAddress(address.getDetailAddress());
        newAddress.setProvince(provincesRepository.findByProvinceid(address.getProvinceid()).getProvince());
        newAddress.setCity(citiesRepository.findByCityid(address.getCityid()).getCity());
        newAddress.setArea(areasRepository.findByAreaid(address.getAreaid()).getArea());
        return addressRepository.save(newAddress);
    }

    @Override
    public Address findAddressById(Integer addressId) {
        Address address = addressRepository.findById(addressId).get();
        return address;
    }

    @Override
    public List<Address> findByUser(Integer userId) {
        return addressRepository.findAllByUser_UserId(userId);
    }
}
