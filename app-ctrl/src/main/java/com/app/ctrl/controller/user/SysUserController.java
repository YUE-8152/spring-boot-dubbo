package com.app.ctrl.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.model.SysUser;
import com.app.api.service.user.SysUserService;
import com.app.common.core.Result;
import com.app.common.core.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: app-ctrl
 * @Package: com.app.consumer.controller
 * @ClassName: SysUserController
 * @Author: YX
 * @Description: SysUser 接口
 * @Date: 2020/7/13 14:37
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Reference
    private SysUserService sysUserService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<SysUser> sysUsers = sysUserService.selectAll();
        return ResultGenerator.success(sysUsers);
    }
}
