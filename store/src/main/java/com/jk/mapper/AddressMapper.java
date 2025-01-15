package com.jk.mapper;

import com.jk.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import com.jk.model.query.AddressQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @author cry
 * @date 2025-01-15 14:11:05
 * @description   Mapper 接口
 */
@Repository
public interface AddressMapper extends BaseMapper<Address> {

    IPage<Address> getWithPage(Page page, @Param("query") AddressQuery query);

    /**
     * 新增后返回id
     * @param po
     * @return
    */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertReturnId(@Param("po") Address po);

    int getCountByQuery(@Param("query") AddressQuery query);

    Address getOneByQuery(@Param("query") AddressQuery query);

    List<Address> getAllByQuery(@Param("query") AddressQuery query);

}
