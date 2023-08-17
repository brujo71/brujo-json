package it.brujo.json.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import it.brujo.json.JSonElem;
import it.brujo.json.JSonFormatter;
import it.brujo.json.JSonParser;
import it.brujo.json.JSonParser.JSonParseEx;

public class Parsing {

	JSonFormatter formatter=JSonFormatter.builder().build();
	
	public static void main(String[] args) {
		Parsing p=new Parsing();
		p.parseString();
	}

	private String loadJSonResource(String fileName) {
		URL url= getClass().getResource(fileName);
		try {
			return new String(Files.readAllBytes(Paths.get(url.toURI())),StandardCharsets.UTF_8);
		} catch (IOException | URISyntaxException e1) {
			return null;
		}
	}
	
	public void parseString() {
		String jsonStr=loadJSonResource("ex1.json");
		try {
			JSonElem json=JSonParser.parse(jsonStr);
		} catch (JSonParseEx e) {
			e.printStackTrace();
		}
		formatter.append(jsonStr, new PrintWriter(System.out));
	}
	
}
