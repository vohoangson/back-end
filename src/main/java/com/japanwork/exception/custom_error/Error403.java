package com.japanwork.exception.custom_error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.payload.response.ErrorResponse;

@Controller
public class Error403 {
	@RequestMapping(value = "/403")
	@ResponseBody
	public ResponseDataAPI error403() {
		ErrorResponse error = CommonFunction.getExceptionError(MessageConstant.FORBIDDEN_ERROR, "errors.yml");
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.FAILURE, error);
	}
}
