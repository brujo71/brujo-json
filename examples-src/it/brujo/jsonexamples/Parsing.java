package it.brujo.jsonexamples;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import it.brujo.json.JSonElem;
import it.brujo.json.JSonFormatter;
import it.brujo.json.JSonParser;
import it.brujo.json.JSonParser.JSonParseEx;

public class Parsing {

	private static final String LARGE_JSON_URI="https://raw.githubusercontent.com/json-iterator/test-data/master/large-file.json";
	
	JSonFormatter formatter=JSonFormatter.builder().build();
	
	public static void main(String[] args) {
		Parsing p=new Parsing();
		p.parseString();
		System.out.println("\n\n=================================\n\n");
		p.parseStream();
		System.out.println("\n\n=================================\n\n");
		p.parseStreamLarge();
		System.out.println("\n\n=================================\n\n");
		p.parsePerformance();
	}

	
	private InputStream resourceIS(String fileName) throws FileNotFoundException, URISyntaxException {
		URL url= getClass().getResource(fileName);
		return new FileInputStream(Paths.get(url.toURI()).toFile());
	}
	
	public void parseString() {
		String jsonStr=ExamplesCommon.loadJSonResource("ex1.json");
		JSonElem json=null;
		try {
			json=JSonParser.parse(jsonStr);
		} catch (JSonParseEx e) {
			e.printStackTrace();
		}
		try {
			formatter.append(json, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseStream() {
		JSonElem json=null;
		try (InputStream is=resourceIS("ex2.json")) {
			json=JSonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
		} catch (IOException | URISyntaxException | JSonParseEx e) {
			e.printStackTrace();
		}
		String jsonStr=JSonFormatter.defaultFormatter().writeToString(json);
		System.out.println(jsonStr);
	}
	
	public void parseStreamLarge() {
		URL u=null;
		JSonElem json=null;
		try {
			u=new URL(LARGE_JSON_URI);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try (InputStream is=u.openStream()) {
			json=JSonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
		} catch (IOException  | JSonParseEx e) {
			e.printStackTrace();
		}
		System.out.println(json);
	}
	
	public void parsePerformance() {
		URL u=null;
		try {
			u=new URL(LARGE_JSON_URI);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String jsonStr=null;
		try (InputStream is=u.openStream()) {
			ByteArrayOutputStream baos=new ByteArrayOutputStream(3_000_000);
			is.transferTo(baos);
			jsonStr=new String(baos.toByteArray(),StandardCharsets.UTF_8);
		} catch (IOException e ) {
			e.printStackTrace();
		}
		System.out.println("Downloaded json from "+LARGE_JSON_URI);
		System.out.println("json size="+jsonStr.length());
		System.out.println("starting parsing");
		
		JSonElem json=null;
		Instant start=Instant.now();
		try {
			json=JSonParser.parse(jsonStr);
		} catch (JSonParseEx e) {
			e.printStackTrace();
		}
		Instant stop=Instant.now();
		System.out.println(json);
		System.out.println("parsed in "+(stop.toEpochMilli()-start.toEpochMilli())+" milli");
	}
	

	
}
