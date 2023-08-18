package it.brujo.jsonexamples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExamplesCommon {

	
	public static String loadJSonResource(String fileName) {
		URL url= ExamplesCommon.class.getResource(fileName);
		try {
			return new String(Files.readAllBytes(Paths.get(url.toURI())),StandardCharsets.UTF_8);
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
