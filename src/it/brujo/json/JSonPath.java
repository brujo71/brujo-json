package it.brujo.json;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/** A class to query map all the JSON value in a hierarchical map.
 * 
 *  <code><pre>
 *  { "a" : "aa",
 *    "b" : {
 * 	        "bx" : 1,
 *          "by" : 2
 * 			},
 * 	  "c" : [ 10, 11, 12 ]	
 *  } 
 *  </pre></code>
 *  is mapped to
 *  <pre>
 *  root: JSonObj [content.size=3]
 *  root_a: aa
 *  root_b: JSonObj [content.size=2]
 *  root_b_bx: 1
 *  root_b_by: 2
 *  root_c: JSonArray [lst.size=3]
 *  root_c_0: 10
 *  root_c_1: 11
 *  root_c_2: 12
 *  root_c_size: 3
 *  </pre>
 * 
 */
public class JSonPath {

	public final static char DEFAULT_KEY_DELIMITER='_';
	public final static String DEFAULT_ROOT_NAME="root";
	public final static String DEFAULT_ARRAY_SIZE_LABEL="size";
	
	private char keyDelimiter=DEFAULT_KEY_DELIMITER;
	private char arrayDelimiter=DEFAULT_KEY_DELIMITER;
	private String rootName=DEFAULT_ROOT_NAME;
	private String arraySizeLabel=DEFAULT_ARRAY_SIZE_LABEL;
			
	
	private boolean enrollArrays=false;
	
	private Map<String,JSonElem> map=null;
	
	private JSonPath() {} //use builder
	
	private void map(JSonElem json) {
		map=new TreeMap<String, JSonElem>();
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
			mapInner(key+arrayDelimiter+arraySizeLabel, new JSonNumber(ja.size()));
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
	
	public void dumpAll(PrintStream out) {
		map.forEach((k,v) -> {
			out.println(k+": "+((v instanceof JSonValue vv) ? vv.stringValue() :  v));
		});
	}
	
	public static MapBuilder builder() {
		return new MapBuilder();
	}
	
	public static class MapBuilder {
		private JSonPath buildingMap=new JSonPath();
		
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
		
		public JSonPath build(JSonElem json) {
			buildingMap.map(json);
			return buildingMap;
		}
		
	}
	
	
}
