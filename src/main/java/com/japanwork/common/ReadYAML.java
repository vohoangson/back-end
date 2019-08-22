package com.japanwork.common;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ReadYAML {
	public Map<String, Object> getValueFromYAML(String nameFile) {
		Yaml yaml = new Yaml();
		
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream(nameFile);
		
		Map<String, Object> obj = yaml.load(inputStream);
		return obj;
	}
}
