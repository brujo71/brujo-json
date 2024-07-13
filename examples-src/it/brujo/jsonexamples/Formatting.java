package it.brujo.jsonexamples;

import java.io.IOException;

import it.brujo.json.JSonElem;
import it.brujo.json.JSonFormatter;
import it.brujo.json.JSonParser;
import it.brujo.json.JSonParser.JSonParseEx;

public class Formatting {

	public static void main(String[] args) {
		new Formatting().run();
	}

	public void run() {
		format1();
	}
	public void format1() {
		String jsonStr=ExamplesCommon.loadJSonResource("ex1.json");
		JSonElem json=null;
		try {
			json=JSonParser.parse(jsonStr);
		} catch (JSonParseEx e) {
			e.printStackTrace();
		}
		JSonFormatter formatter=JSonFormatter.builder().noSpaces().build();

		try {
			formatter.append(json, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
