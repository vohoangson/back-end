package com.japanwork.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.japanwork.payload.response.ErrorResponse;

public class CommonFunction {
	public static String convertToJSONString(Object ob) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static String generateCode(int lenght) {
	    List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1));

		PasswordGenerator generator = new PasswordGenerator();
		String code = generator.generatePassword(lenght, rules);
		return code;
	}

	public static boolean checkRole(String role) {
		if(!role.isEmpty()) {
			if(!role.equalsIgnoreCase("COMPANY") && !role.equalsIgnoreCase("CANDIDATE")
					&& !role.equalsIgnoreCase("TRANSLATOR") && !role.equalsIgnoreCase("ADMIN")) {
				return false;
			}
		}
		return true;
	}

	public static Timestamp getCurrentDateTime() {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	public static String convertToSnakeCase(String input) {
		return input.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
	}

	public static ErrorResponse getValidationError(String resource, String fieldName, String error, String nameFile) {
		ReadYAML readYAML = new ReadYAML();
		Map<String, Object> errors= readYAML.getValueFromYAML(nameFile);
		Map<String, Object> fields = (Map<String, Object>) errors.get(resource);
		Map<String, Object> objErrors = (Map<String, Object>) fields.get(fieldName);
		Map<String, Object> objError = (Map<String, Object>) objErrors.get(error);
		String code = (String) objError.get("code");
		String message = (String) objError.get("message");
		ErrorResponse errorResponse = new ErrorResponse(code, message);
		return errorResponse;
	}

	public static ErrorResponse getExceptionError(String error, String nameFile) {
		ReadYAML readYAML = new ReadYAML();
		Map<String, Object> errors= readYAML.getValueFromYAML(nameFile);
		Map<String, Object> objError = (Map<String, Object>) errors.get(error);
		String code = (String) objError.get("code");
		String message = (String) objError.get("message");
		ErrorResponse errorResponse = new ErrorResponse(code, message);
		return errorResponse;
	}
}
