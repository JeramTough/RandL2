package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserType;
import com.jeramtough.randl2.common.model.entity.AdminUser;
import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.common.mapper.AdminUserMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/1/30 10:31
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class AdminUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final SuperAdmin superAdmin;
    private final AdminUserMapper adminUserMapper;
    private final RoleMapper roleMapper;
    private final MapperFacade mapperFacade;


    public AdminUserLoginer(
            PasswordEncoder passwordEncoder,
            SuperAdmin superAdmin,
            AdminUserMapper adminUserMapper,
            RoleMapper roleMapper, MapperFacade mapperFacade) {
        this.passwordEncoder = passwordEncoder;
        this.superAdmin = superAdmin;
        this.adminUserMapper = adminUserMapper;
        this.roleMapper = roleMapper;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public SystemUser login(Object credentials) {
        AdminUserCredentials adminUserCredentials = (AdminUserCredentials) credentials;

        //如果是超级管理员登录
        if (superAdmin.getUsername().equals(
                adminUserCredentials.getUsername()) && passwordEncoder
                .matches(adminUserCredentials.getPassword(),
                        superAdmin.getPassword())) {
            return superAdmin.getSystemUser();
        }

        //如果是普通的系统管理员登录
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminUserCredentials.getUsername());
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        if (adminUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(adminUserCredentials.getPassword(),
                            adminUser.getPassword());
            if (passwordIsRight) {

                //所有用户只能拥有一种角色
                Role role = roleMapper.selectById(adminUser.getRoleId());
                SystemUser systemUser = mapperFacade.map(adminUser, SystemUser.class);
                systemUser.setUserType(UserType.ADMIN);
                systemUser.setRole(role);

                return systemUser;
            }
        }

        return null;
    }
}