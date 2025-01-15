package com.jk.service.impl;

import com.jk.entity.Address;
import com.jk.mapper.AddressMapper;
import com.jk.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.model.dto.AddressDTO;
import com.jk.model.vo.AddressVO;
import com.jk.model.query.AddressQuery;
import java.util.List;
/**
 * @author cry
 * @date 2025-01-15 14:11:05
 * @description  服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

	@Override
	public IPage<Address> getWithPage(Page page, AddressQuery query) {
	    return baseMapper.getWithPage(page, query);
	}

	@Override
    public Address getOneByQuery(AddressQuery query) {
        return baseMapper.getOneByQuery(query);
    }

    @Override
    public List<Address> getAllByQuery(AddressQuery query) {
        return baseMapper.getAllByQuery(query);
    }
}
