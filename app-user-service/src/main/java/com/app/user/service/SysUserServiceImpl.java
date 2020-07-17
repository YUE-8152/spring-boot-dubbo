package com.app.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.model.SysUser;
import com.app.api.service.user.SysUserService;
import com.app.user.dao.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: app-api
 * @Package: com.app.provider.service
 * @ClassName: SysUserServiceImpl
 * @Author: YX
 * @Description: SysUserService 接口实现类
 * @Date: 2020/7/13 14:30
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectAll();
    }
}
