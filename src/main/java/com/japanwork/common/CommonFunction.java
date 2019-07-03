package com.japanwork.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

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
	public static List<String> listParam(String param){
		if(!param.equals("")) {
			List<String> listParam = new ArrayList<String>();
			String[] list = param.split(",");
			for (String string : list) {
				listParam.add(string);
			}
			return listParam;
		}
		return null;
	}
	
	public static String generateCode(int lenght) {	 
	    List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1));

		PasswordGenerator generator = new PasswordGenerator();
		String code = generator.generatePassword(lenght, rules);
		return code;
	}
}
