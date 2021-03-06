package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.entity.RandlPersonalInfo;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlPersonalInfoService extends BaseDtoService<RandlPersonalInfo, RandlPersonalInfoDto> {

    RandlPersonalInfoDto getPersonalInfoByUid(Long uid);

    RandlPersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid);

    String updatePersonalInfo(UpdatePersonalInfoParams params);

    PageDto<Map<String, Object>> getRandlPersonalInfoDtoListByPage(QueryByPageParams queryByPageParams);
}
