package com.japanwork.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.ERROR_401, MessageConstant.ERROR_401_MSG);
        BaseDataResponse baseDataResponse = new BaseDataResponse(baseMessageResponse);
        httpServletResponse.getWriter().write(CommonFunction.convertToJSONString(baseDataResponse));
    }
}
