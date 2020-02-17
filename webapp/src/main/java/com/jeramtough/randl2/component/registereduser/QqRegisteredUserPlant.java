package com.jeramtough.randl2.component.registereduser;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;

/**
 * <pre>
 * Created on 2020/2/16 20:17
 * by @author JeramTough
 * </pre>
 */
public class QqRegisteredUserPlant extends BaseRegisteredUserPlant {

    public QqRegisteredUserPlant(HttpSession session,
                                 PasswordEncoder passwordEncoder,
                                 RegisteredUserMapper registeredUserMapper) {
        super(session, passwordEncoder, registeredUserMapper);
    }


    @Override
    public void setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {

    }

    @Override
    public RegisteredUser create(int... errorCodes) throws ApiResponseException {
        return null;
    }
}
