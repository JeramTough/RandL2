package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.UpdateCurrentAdminUserParams;
import com.jeramtough.randl2.common.model.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.common.model.dto.AdminUserDto;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface AdminUserService extends IService<AdminUser> {

    SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials);

    String adminLogout();

    String addAdminUser(RegisterAdminUserParams registerAdminUserParams);

    String removeAdminUser(Long uid);

    List<AdminUserDto> getAllAdminUser();

    String updateAdminUser(UpdateAdminUserParams params);

    AdminUserDto getOneAdminUser(Long uid);

    PageDto<AdminUserDto> getAdminUserListByPage(QueryByPageParams queryByPageParams);

    AdminUserDto getAdminUserByKeyword(String keyword);

    String updateCurrentAdminUser(UpdateCurrentAdminUserParams params);
}