package com.jeramtough.randl2.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.service.MyBaseService;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;

/**
 * <pre>
 * Created on 2020/10/25 16:43
 * by @author WeiBoWen
 * </pre>
 */
public abstract class MyBaseServiceImpl<M extends BaseMapper<T>, T, D>
        extends BaseDtoServiceImpl<M, T, D> implements MyBaseService<T, D> {

    /**
     * 数据库主键名
     */
    private static final String PRIMARY_KEY_NAME = "fid";

    public MyBaseServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    public String updateByParams(Object params) {
        BeanValidator.verifyParams(params);

        Long fid = getPrimaryKeyValue(params);
        T entityFromDb = getBaseMapper().selectById(fid);
        if (entityFromDb == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "主键fid");
        }

        T entity = (T) getMapperFacade().map(params, entityFromDb.getClass());

        int resultCount = getBaseMapper().updateById(entity);
        if (resultCount == 0) {
            throw new ApiResponseException(ErrorS.CODE_5.C, "[" + PRIMARY_KEY_NAME + "=" + fid);
        }
        return "更新成功";
    }

    @Override
    public String removeOneById(Long fid) {
        if (getBaseMapper().selectById(fid) == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "fid对应");
        }
        if (getBaseMapper().deleteById(fid) == 0) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "删除资源");
        }
        return "删除成功！";
    }

    @Override
    public PageDto<D> pageByCondition(QueryByPageParams queryByPageParams, BaseConditionParams params) {
        BeanValidator.verifyParams(params);

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        return pageByConditionTwo(queryByPageParams, params, queryWrapper);
    }

    public PageDto<D> pageByConditionTwo(QueryByPageParams queryByPageParams, BaseConditionParams params,
                                         QueryWrapper<T> queryWrapper) {

        return pageByConditionThree(queryByPageParams,params,queryWrapper);
    }

    public PageDto<D> pageByConditionThree(QueryByPageParams queryByPageParams, BaseConditionParams params,
                                         QueryWrapper<T> queryWrapper) {


        if (params.getStartDate() != null && params.getEndDate() != null) {
            queryWrapper.and(wrapper ->
                    wrapper.between("create_time", params.getStartDate(), params.getEndDate()));
        }

        QueryPage<T> queryPage =
                getBaseMapper().selectPage(new QueryPage<>(queryByPageParams),
                        queryWrapper);

        return toPageDto(queryPage);
    }

    //***********************************

    private Long getPrimaryKeyValue(Object params) {
        try {
            Field field = params.getClass().getDeclaredField(PRIMARY_KEY_NAME);
            field.setAccessible(true);
            Long fid = (Long) field.get(params);
            return fid;
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ApiResponseBeanException(ErrorU.CODE_1.C, PRIMARY_KEY_NAME);
        }
    }
}
