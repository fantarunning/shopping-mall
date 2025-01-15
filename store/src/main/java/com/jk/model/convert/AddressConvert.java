package com.jk.model.convert;


import lombok.experimental.UtilityClass;
import com.jk.entity.Address;
import com.jk.model.dto.AddressDTO;
import com.jk.model.vo.AddressVO;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cry
 * @date 2025-01-15 14:11:05
 * @description 转换类
 */

@UtilityClass
public class AddressConvert {


    public static Address toPO(AddressDTO dto){
        Address result = new Address();
            result.setId(dto.getId());
            result.setUsername(dto.getUsername());
            result.setPassword(dto.getPassword());
            result.setName(dto.getName());
            result.setAvatar(dto.getAvatar());
            result.setRole(dto.getRole());
            result.setPhone(dto.getPhone());
            result.setEmail(dto.getEmail());
            result.setCreateTime(dto.getCreateTime());
        return result;
    }

    public static AddressVO toVO(Address po){
        AddressVO result = new AddressVO();
                result.setId(po.getId());
                result.setUsername(po.getUsername());
                result.setPassword(po.getPassword());
                result.setName(po.getName());
                result.setAvatar(po.getAvatar());
                result.setRole(po.getRole());
                result.setPhone(po.getPhone());
                result.setEmail(po.getEmail());
                result.setCreateTime(po.getCreateTime());
            return result;
    }
    public static List<AddressVO> toVO(List<Address> pos){
        if(pos == null){
            return null;
        }
        List<AddressVO> list = new ArrayList<>();
        for(Address po:pos){
            list.add(toVO(po));
        }
        return list;
    }

    public static List<Address> toPO(List<AddressDTO> dtos){
        if(dtos == null) {
            return null;
        }
        List<Address> list = new ArrayList<>();
        for(AddressDTO dto:dtos) {
            list.add(toPO(dto));
        }
        return list;
    }


}
