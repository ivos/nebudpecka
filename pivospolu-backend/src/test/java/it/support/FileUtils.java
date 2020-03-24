package it.support;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

	public static String load(Object testInstance, String fileName) {
		try {
			InputStream is = inputStream(testInstance, fileName);
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file " + fileName, e);
		}
	}

	public static InputStream inputStream(Object testInstance, String fileName) {
		try {
			String path = testInstance.getClass().getPackage().getName()
					.replace('.', '/') + '/' + fileName;
			return new ClassPathResource(path).getInputStream();
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file " + fileName, e);
		}
	}
}
