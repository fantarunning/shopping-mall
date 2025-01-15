package com.jk.controller;

import com.jk.common.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jk.entity.Address;
import com.jk.service.IAddressService;
import com.jk.model.dto.AddressDTO;
import com.jk.model.vo.AddressVO;
import com.jk.model.query.AddressQuery;
import com.jk.model.convert.AddressConvert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;

/**
 * @author cry
 * @date 2025-01-15 14:11:05
 * @description 控制器
 */
@Controller
@RequestMapping("address")
public class AddressController {

    @Autowired
    private IAddressService addressService;


    /**
     * 获取分页列表
     */
    @GetMapping(value = "/list/page")
    public ApiResult<IPage<Address>> list( AddressQuery query, Page page) {
        IPage<Address> resultPage = addressService.getWithPage(page, query);
        return ApiResult.ok(resultPage);
    }

    /**
     * 根据ID获取详情
     */
    @GetMapping("/get")
    public ApiResult<AddressVO> get(@RequestParam("id") Long id) {
        Address entity = addressService.getById(id);
        AddressVO vo = AddressConvert.toVO(entity);
        return ApiResult.ok(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ApiResult<String> add( @RequestBody AddressDTO dto) {
        Address po = AddressConvert.toPO(dto);
        boolean save = addressService.save(po);
        return save ? ApiResult.ok() : ApiResult.fail();
    }

    /**
     *编辑
     */
    @PostMapping("/update")
    public ApiResult update( @RequestBody AddressDTO dto) {
        Address po = AddressConvert.toPO(dto);
        boolean update = addressService.updateById(po);
        return update ? ApiResult.ok() : ApiResult.fail();
    }

    /**
     *移除
     */
    @PostMapping("/remove")
    public ApiResult<String> remove(@RequestParam(value = "id") Long id) {
        boolean remove = addressService.removeById(id);
        return remove ? ApiResult.ok() : ApiResult.fail();
    }

    /**
     *批量移除
     */
    // @PostMapping("/batch/remove")
    public ApiResult batchRemove(@RequestParam(value = "ids") String ids) {
        boolean remove = addressService.removeByIds(Arrays.asList(ids.split(",")));
        return remove ? ApiResult.ok() : ApiResult.fail();
    }
}
