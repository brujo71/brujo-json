package it.brujo.json;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class JSonMap {

	private final static char DEFAULT_KEY_DELIMITER='_';
	private final static String DEFAULT_ROOT_NAME="root";
	
	private char keyDelimiter=DEFAULT_KEY_DELIMITER;
	private char arrayDelimiter=DEFAULT_KEY_DELIMITER;
	private String rootName=DEFAULT_ROOT_NAME;
			
	
	private boolean enrollArrays=false;
	
	private Map<String,JSonElem> map=null;
	
	private JSonMap() {} //use builder
	
	private void map(JSonElem json) {
		map=new HashMap<String, JSonElem>();
		mapInner(rootName, json);
	}
	
	private void mapInner(String key,JSonElem json) {
		map.put(key,json);
		if (json instanceof JSonObj jo) {
			for (JSonEntry e:jo.list()) {
				mapInner(key+keyDelimiter+e.label().value(), e.value());
			}
		}
		else if (enrollArrays && json instanceof JSonArray ja) {
			int count=0;
			for (JSonElem e:ja) {
				mapInner(key+arrayDelimiter+count++, e);
			}
		}
		
	}

	public JSonValue getValue(String k) {
		return (JSonValue)map.get(k);
	}
	
	public JSonElem get(String k) {
		return map.get(k);
	}
	
	public void filter(String keyRegEx,BiConsumer<String,JSonElem> cons) {
		Predicate<String> p= Pattern.compile(keyRegEx).asPredicate();
		map.forEach((k,v)-> {
			if (p.test(k))
				cons.accept(k,v);
		});
	}
	
	public void dumpLeafs(PrintStream out) {
		map.forEach((k,v) -> {
			if (v instanceof JSonValue vv) {
				out.println(k+": "+vv.stringValue());
			}
		});
	}
	
	
	public static MapBuilder builder() {
		return new MapBuilder();
	}
	
	public static class MapBuilder {
		private JSonMap buildingMap=new JSonMap();
		
		private MapBuilder() {}
		
		public MapBuilder withEnrollArrays(boolean enrollArrays) {
			buildingMap.enrollArrays=enrollArrays;
			return this;
		}
		public MapBuilder withKeyDelimiter(char delimiter) {
			buildingMap.keyDelimiter=delimiter;
			return this;
		}
		public MapBuilder withArrayDelimiter(char delimiter) {
			buildingMap.arrayDelimiter=delimiter;
			return this;
		}
		public MapBuilder withRootName(String rootName) {
			buildingMap.rootName=rootName;
			return this;
		}
		
		public JSonMap build(JSonElem json) {
			buildingMap.map(json);
			return buildingMap;
		}
		
	}
	
	
}
