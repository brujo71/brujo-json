package it.brujo.jsonexamples;

import it.brujo.json.JSonElem;
import it.brujo.json.JSonParser;
import it.brujo.json.JSonParser.JSonParseEx;
import it.brujo.json.JSonPath;

public class Querying {

	public static void main(String[] args) {
		new Querying().run();
	}

	public void run() {
		query1();
	}
	
	public void query1() {
		String jsonStr=ExamplesCommon.loadJSonResource("ex3.json");
		JSonElem json;
		try {
			json=JSonParser.parse(jsonStr);
		}
		catch (JSonParseEx e) {
			e.printStackTrace();
			return;
		}
		
		JSonPath path=JSonPath.builder()
				.withKeyDelimiter('.')
				.withArrayDelimiter('.')
				.withEnrollArrays(true)
					.build(json);
		
		path.dumpAll(System.out);

		boolean servlet0useJSP=path.get("root.web-app.servlet.0.init-param.useJSP").asValue().booleanValue();
		
		String taglibLocation=path.get("root.web-app.taglib.taglib-location").asValue().stringValue();
		
		System.out.println("servlet0useJSP="+servlet0useJSP+" taglibLocation="+taglibLocation);
		
	}
}
