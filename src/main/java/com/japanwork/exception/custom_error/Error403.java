package com.japanwork.exception.custom_error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;

@Controller
public class Error403 {
	@RequestMapping(value = "/403")
	@ResponseBody
	public BaseDataResponse error403() {
		BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.ERROR_403, MessageConstant.ERROR_403_MSG);
		return new BaseDataResponse(baseMessageResponse);
	}
}
