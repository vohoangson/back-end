package com.japanwork.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
//                e.getLocalizedMessage());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_401);
        BaseDataResponse baseDataResponse = new BaseDataResponse(baseMessageResponse);
        httpServletResponse.getWriter().write(CommonFunction.convertToJSONString(baseDataResponse));
    }
}
