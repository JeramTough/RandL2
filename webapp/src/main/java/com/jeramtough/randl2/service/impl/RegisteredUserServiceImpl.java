package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.registereduser.UpdateRegisteredUserParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilder;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilderGetter;
import com.jeramtough.randl2.component.verificationcode.VerificationCodeHolder;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.RegisteredUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RegisteredUserServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements
        RegisteredUserService, WithLogger {

    private RegisteredUserBuilderGetter registeredUserPlantGetter;
    private VerificationCodeHolder verificationCodeHolder;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc,
                                     MapperFacade mapperFacade,
                                     RegisteredUserBuilderGetter registeredUserPlantGetter,
                                     VerificationCodeHolder verificationCodeHolder,
                                     PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);
        this.registeredUserPlantGetter = registeredUserPlantGetter;
        this.verificationCodeHolder = verificationCodeHolder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        return registeredUserDto;
    }

    @Override
    public String verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyDto(params);

        //初始化注册方式
        registeredUserPlantGetter.initRegisterUserWay(params.getWay(), 7005);

        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        builder.setAccount(params.getPhoneOrEmail(), 7001, 7002, 7003, 7004);
        return "该账号可以注册";
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        builder.setPassword(params.getPassword(), params.getRepeatedPassword(), 7020);
        return "密码校验通过";
    }

    @Override
    public synchronized RegisteredUserDto register() {
        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        RegisteredUser registeredUser = builder.build(7030);

        if (!verificationCodeHolder.getVerificationResult().isPassed()) {
            throw new ApiResponseException(7031);
        }

        boolean isTheSamePhoneNumber =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getPhoneNumber());
        boolean isTheSameEmailAddress =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getEmailAddress());
        if (!(isTheSamePhoneNumber || isTheSameEmailAddress)) {
            throw new ApiResponseException(7032);
        }

        getBaseMapper().insert(registeredUser);
        builder.resetRegisteredUser();
        return getBaseDto(registeredUser);
    }

    @Override
    public String removeRegisteredUser(Long uid) {
        boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(7050);
        }
        return "移除普通注册用户成功";
    }

    @Override
    public String updateRegisteredUser(UpdateRegisteredUserParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUser currentRegisteredUser = getById(params.getUid());

        if (currentRegisteredUser == null) {
            throw new ApiResponseException(7060);
        }


        if (!currentRegisteredUser.getAccount().equals(params.getAccount())) {
            if (getBaseMapper().selectOne(new QueryWrapper<RegisteredUser>().eq("account",
                    params.getAccount())) != null) {
                //存在同名用户
                throw new ApiResponseException(7062);
            }
        }

        if (currentRegisteredUser.getPhoneNumber() != null) {
            if (!currentRegisteredUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RegisteredUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(7066);
                }
            }
        }

        if (currentRegisteredUser.getEmailAddress() != null) {
            if (!currentRegisteredUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RegisteredUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(7067);
                }
            }
        }


        RegisteredUser registeredUser = getMapperFacade().map(params, RegisteredUser.class);
        if (params.getPassword() != null) {
            registeredUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }

        updateById(registeredUser);
        return "更新普通注册用户信息成功！";
    }

    @Override
    public List<RegisteredUserDto> getRegisteredUsersByKeyword(String keyword) {
        QueryWrapper<RegisteredUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("account", "%" + keyword + "%").or().like("phone_number",
                "%" + keyword + "%").or().like("email_address", "%" + keyword + "%");
        List<RegisteredUser> registeredUserList = getBaseMapper().selectList(queryWrapper);
        return getDtoList(registeredUserList);
    }


    //****************************

}
