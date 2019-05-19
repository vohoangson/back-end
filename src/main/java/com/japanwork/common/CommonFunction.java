package com.japanwork.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonFunction {
	private static Log logger = LogFactory.getLog(CommonFunction.class);
	public static String convertToJSONString(Object ob) {
        logger.info("CommonFunction.convertToJSONString");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
