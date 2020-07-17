package com.app.user.dao;

import com.app.api.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);
}