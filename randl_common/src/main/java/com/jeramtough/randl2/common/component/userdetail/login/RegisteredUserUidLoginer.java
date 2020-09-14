package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/23 18:29
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class RegisteredUserUidLoginer extends BaseRegisteredUserLoginer
        implements UserLoginer {

    @Autowired
    public RegisteredUserUidLoginer(
            PasswordEncoder passwordEncoder,
            MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper
            , RoleMapper roleMapper) {
        super(passwordEncoder, mapperFacade, registeredUserMapper, roleMapper);
    }


    @Override
    public SystemUser login(Object credentials) {
        long uid = (long) credentials;
        RegisteredUser registeredUser = registeredUserMapper.selectById(uid);
        return processSystemUser(registeredUser);
    }
}