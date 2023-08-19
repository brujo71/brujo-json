package it.brujo.json;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

	/**
	 * An uncommon choice. The '_' (underscore) is neutral in RegExp {@link java.util.regex.Pattern}
	 * Can be configured by {@link  MapBuilder#withKeyDelimiter}
	 */
	public final static char DEFAULT_KEY_DELIMITER='_';
	/**
	 * 
	 */
	public final static String DEFAULT_ROOT_NAME="root";
	/**
	 * 
	 */
	public final static String DEFAULT_ARRAY_SIZE_LABEL="size";
	
	private char keyDelimiter=DEFAULT_KEY_DELIMITER;
	private char arrayDelimiter=DEFAULT_KEY_DELIMITER;
	private String rootName=DEFAULT_ROOT_NAME;
	private String arraySizeLabel=DEFAULT_ARRAY_SIZE_LABEL;
			
	
	private boolean enrollArrays=true;
	
	private Map<String,JSonElem> map=null;
	
	private JSonPath() {} //use builder
	
	private void map(JSonElem json) {
		map=new TreeMap<String, JSonElem>();
		mapInner(rootName, json);
	}
	
	private void mapInner(String key,JSonElem json) {
		map.put(key,json);
		if (json instanceof JSonObj) {
			for (JSonEntry e:((JSonObj)json).list()) {
				mapInner(key+keyDelimiter+e.label().value(), e.value());
			}
		}
		else if (enrollArrays && json instanceof JSonArray) {
			int count=0;
			mapInner(key+arrayDelimiter+arraySizeLabel, new JSonNumber(((JSonArray)json).size()));
			for (JSonElem e:((JSonArray)json)) {
				mapInner(key+arrayDelimiter+count++, e);
			}
		}
	}

	//TODO improve javadoc 
	/**
	 * 
	 * @param key a full key (e.g. root_a_b)
	 * @return the value
	 */
	public JSonValue getValue(String key) {
		return (JSonValue)map.get(key);
	}
	
	//TODO improve javadoc 
	/**
	 * 
	 * @param key a full key (e.g. root_a_b)
	 * @return the element
	 */
	public JSonElem get(String key) {
		return map.get(key);
	}
	
	//TODO improve javadoc 
	/**
	 * 
	 * @param keyRegEx a regular expression for matching the keys
	 * @param cons a {@link java.util.functions.BiConsumer} as callback function
	 */
	public void filter(String keyRegEx,BiConsumer<String,JSonElem> cons) {
		Predicate<String> p= Pattern.compile(keyRegEx).asPredicate();
		map.forEach((k,v)-> {
			if (p.test(k))
				cons.accept(k,v);
		});
	}
	
	/**
	 * 
	 * @param out e.g. {@link System#out}
	 */
	public void dumpLeafs(PrintStream out) {
		map.forEach((k,v) -> {
			if (v instanceof JSonValue) {
				out.println(k+": "+((JSonValue)v).stringValue());
			}
		});
	}
	
	/**
	 * 
	 * @param out e.g. {@link System#out}
	 */
	public void dumpAll(PrintStream out) {
		map.forEach((k,v) -> {
			out.println(k+": "+((v instanceof JSonValue) ? ((JSonValue)v).stringValue() :  v));
		});
	}
	
	/**Start from here. With {#link MapBuilder} you can set varios options and you can obtain
	 * a {@link JSonPath} instance invoking {@link MapBuilder#build(JSonElem)} 
	 * 
	 * @return a builder
	 */
	public static MapBuilder builder() {
		return new MapBuilder();
	}
	
	/** Merges each name with the current keyDelimiter.
	 *  E.g.:
	 *  <code><pre>
	 *  	builder.keyOf(&quot;aaaa&quot;,&quot;bb&quot;,&quot;cc&quot; );
	 *  </pre></coede>
	 *  results in <code>aaaa_bb_cc</code>
	 * 
	 * @param names the parts of the key
	 * @return a key 
	 */
	public String keyOf(String... names ) {
		return rootName+keyDelimiter+Arrays.stream(names).collect(Collectors.joining(String.valueOf(keyDelimiter)));
	}
	
	/** Extract the simple name from a composed key
	 * 
	 * @param key a key e.g. aaaa_bb_cc
	 * @return the simple name e.g. cc
	 */
	public String simpleName(String key) {
		int idx=key.lastIndexOf(keyDelimiter);
		return idx>=0 && idx+1<key.length() ? key.substring(idx+1) : key;
	}
	
	/** the configuration class
	 * 
	 *
	 */
	public static class MapBuilder {
		private JSonPath buildingMap=new JSonPath();
		
		private MapBuilder() {}
		
		/**
		 * 
		 * @param enrollArrays (default true) 
		 * @return <code>this</code>
		 */
		public MapBuilder withEnrollArrays(boolean enrollArrays) {
			buildingMap.enrollArrays=enrollArrays;
			return this;
		}
		/**
		 * 
		 * @param delimiter an alternative delimiter (e.g. '.')
		 * @return <code>this</code>
		 */
		public MapBuilder withKeyDelimiter(char delimiter) {
			buildingMap.keyDelimiter=delimiter;
			return this;
		}
		/**
		 * 
		 * @param delimiter an alternative delimiter (e.g. '.')
		 * @return <code>this</code>
		 */
		public MapBuilder withArrayDelimiter(char delimiter) {
			buildingMap.arrayDelimiter=delimiter;
			return this;
		}
		/**
		 * 
		 * @param rootName the alternative value for main element
		 * @return <code>this</code>
		 */
		public MapBuilder withRootName(String rootName) {
			buildingMap.rootName=rootName;
			return this;
		}
		/**Create a {@link JSonPath} object
		 * 
		 * @param json create a {@link JSonPath} based on this JSON element
		 * @return <code>this</code>
		 */
		public JSonPath build(JSonElem json) {
			buildingMap.map(json);
			return buildingMap;
		}
		
	}
	
	
}
