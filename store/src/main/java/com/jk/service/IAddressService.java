package com.jk.service;

import com.jk.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.model.dto.AddressDTO;
import com.jk.model.query.AddressQuery;
import java.util.List;
/**
 * @author cry
 * @date 2025-01-15 14:11:05
 * @description  服务类
 */
public interface IAddressService extends IService<Address> {

        IPage<Address> getWithPage(Page page, AddressQuery query);

        Address getOneByQuery(AddressQuery query);

        List<Address> getAllByQuery( AddressQuery query);

}
