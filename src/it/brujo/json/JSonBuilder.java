package it.brujo.json;

import java.util.Map;
import java.util.Objects;

/**
 * 
 *
 */
public class JSonBuilder {

	private final static JSonBuilder builder=new JSonBuilder();
	
	private JSonBuilder() {	}
	
	/**
	 * 
	 * @return a singleton JSonBuilder
	 */
	public static JSonBuilder builder() {
		return builder;
	}
	
	/**
	 * 
	 * @return {@link JSonConst#Null}
	 */
	public JSonValue nul() {
		return JSonConst.Null;
	}
	
	/**
	 * 
	 * @return {@link JSonConst#True}
	 */
	public JSonValue tru() {
		return JSonConst.True;
	}
	
	/**
	 * 
	 * @return {@link JSonConst#False}
	 */
	public JSonValue fal() {
		return JSonConst.False;
	}
	
	/**
	 * 
	 * @return an empty {@link JSonObj}
	 */
	public JSonObj obj() {
		return new JSonObj();
	}
	
	//TODO improve javadocs
	/**
	 * 
	 * @param <V> type of the map
	 * @param m the map
	 * @return the representing JSON object
	 */
	public<V> JSonObj obj(Map<String,V> m) {
		JSonObj res=obj();
		m.forEach((s,v) -> {
			res.addInner(s, elem((Object)v));
		});
		return res;
	}
	
	
	//TODO improve javadocs
	/**
	 * 
	 * @param <V> the type of the values
	 * @param names an array of names
	 * @param values on array of values
	 * @return the representing JSON object
	 */
	public<V> JSonObj obj(String[] names,V[] values) {
		JSonObj res=obj();
		if (Objects.requireNonNull(names).length!=Objects.requireNonNull(values).length) {
			throw new IllegalArgumentException("names.length="+names.length+" values.length="+values.length);
		}
		for (int idx=0;idx<names.length;idx++) {
			res.add(names[idx],elem(values[idx]));
		}
		return res;
	}
	
	/**
	 * 
	 * @param n0 a JSON name 
	 * @param v0 a Java object that can be converted in a {@link JSonElem}
	 * @return the representing JSON object
	 */
	public JSonObj obj(String n0,Object v0) {
		return obj(new String[] {n0},new Object[] {v0});
	}
	
	/**
	 * 
	 * @param n0 a JSON name 
	 * @param v0 a Java object that can be converted in a {@link JSonElem}
	 * @param n1 a JSON name 
	 * @param v1 a Java object that can be converted in a {@link JSonElem}
	 * @return the representing JSON object
	 */
	public JSonObj obj(String n0,Object v0
			,String n1,Object v1) {
		return obj(new String[] {n0,n1},new Object[] {v0,v1});
	}
	
	/**
	 * 
	 * @param n0 a JSON name 
	 * @param v0 a Java object that can be converted in a {@link JSonElem}
	 * @param n1 a JSON name 
	 * @param v1 a Java object that can be converted in a {@link JSonElem}
	 * @param n2 a JSON name 
	 * @param v2 a Java object that can be converted in a {@link JSonElem}
	 * @return the representing JSON object
	 */
	public JSonObj obj(String n0,Object v0
			,String n1,Object v1
			,String n2,Object v2
			) {
		return obj(new String[] {n0,n1,n2},new Object[] {v0,v1,v2});
	}
	
	/**
	 * 
	 * @param n0 a JSON name 
	 * @param v0 a Java object that can be converted in a {@link JSonElem}
	 * @param n1 a JSON name 
	 * @param v1 a Java object that can be converted in a {@link JSonElem}
	 * @param n2 a JSON name 
	 * @param v2 a Java object that can be converted in a {@link JSonElem}
	 * @param n3 a JSON name 
	 * @param v3 a Java object that can be converted in a {@link JSonElem}
	 * @return the representing JSON object
	 */
	public JSonObj obj(String n0,Object v0
			,String n1,Object v1
			,String n2,Object v2
			,String n3,Object v3
			) {
		return obj(new String[] {n0,n1,n2,n3},new Object[] {v0,v1,v2,v3});
	}
	
	/**
	 * 
	 * @param n0 a JSON name 
	 * @param v0 a Java object that can be converted in a {@link JSonElem}
	 * @param n1 a JSON name 
	 * @param v1 a Java object that can be converted in a {@link JSonElem}
	 * @param n2 a JSON name 
	 * @param v2 a Java object that can be converted in a {@link JSonElem}
	 * @param n3 a JSON name 
	 * @param v3 a Java object that can be converted in a {@link JSonElem}
	 * @param n4 a JSON name 
	 * @param v4 a Java object that can be converted in a {@link JSonElem}
	 * @return the representing JSON object
	 */
	public JSonObj obj(String n0,Object v0
			,String n1,Object v1
			,String n2,Object v2
			,String n3,Object v3
			,String n4,Object v4
			) {
		return obj(new String[] {n0,n1,n2,n3,n4},new Object[] {v0,v1,v2,v3,v4});
	}
	
	
	/**
	 * 
	 * @param value a {@link Boolean} value
	 * @return the JSON equivalent of {@link Boolean}
	 */
	public JSonValue value(Boolean value) {
		if (value==null)
			return JSonConst.Null;
		return value ? tru() : fal();
	}
	
	/**
	 * 
	 * @param value a {@link Number} value
	 * @return the JSON equivalent of {@link Number}
	 */
	public JSonValue value(Number value) {
		if (value==null)
			return JSonConst.Null;
		return new JSonNumber(value);
	}
	
	/**
	 * 
	 * @param value a {@link Integer} value
	 * @return the JSON equivalent of {@link Integer}
	 */
	public JSonValue value(Integer value) {
		if (value==null)
			return JSonConst.Null;
		return new JSonNumber(value);
	}
	
	/**
	 * 
	 * @param value a {@link Long} value
	 * @return the JSON equivalent of {@link Long}
	 */
	public JSonValue value(Long value) {
		if (value==null)
			return JSonConst.Null;
		return new JSonNumber(value);
	}
	
	/**
	 * 
	 * @param value a {@link Double} value
	 * @return the JSON equivalent of {@link Double}
	 */
	public JSonValue value(Double value) {
		if (value==null)
			return JSonConst.Null;
		return new JSonNumber(value);
	}
	
	/**
	 * 
	 * @param value a {@link Float} value
	 * @return the JSON equivalent of {@link Float}
	 */
	public JSonValue value(Float value) {
		if (value==null)
			return JSonConst.Null;
		return value((double)value);
	}
	
	/**
	 * 
	 * @param value a {@link String} value
	 * @return the JSON equivalent of {@link String}
	 */	
	public JSonValue value(String value) {
		if (value==null)
			return JSonConst.Null;
		return new JSonString(value);
	}
	
	/**
	 * 
	 * @param value a Java object that can be convertet to JSON
	 * @return the more suitable class to represent the value 
	 * @throws IllegalArgumentException if <code>value</code> hs not an equivalent in JSON  
	 */	
	public JSonElem elem(Object value) {
		if (value==null) {
			return nul();
		}
		else if (value instanceof Boolean b) {
			return value(b); 
		}
		else if (value instanceof Number n) {
			return value(n);
		}
		else if (value instanceof String s) {
			return value(s);
		}
		else if (value instanceof JSonElem e) {
			return e;
		}
		else {
			throw new IllegalArgumentException("unsupproted class "+value.getClass().getName());
		}
	}
	
	/**
	 * 
	 * @return an empty {@link JSonArray}
	 */
	public JSonArray array() {
		return new JSonArray();
	}
	
	/**
	 * 
	 * @see JSonBuilder#elem
	 * @param els the elements of the array
	 * @return the equivalent {@link JSonArray} 
	 */
	public JSonArray array(Object... els) {
		JSonArray res=new JSonArray();
		for (Object o:els) {
			res.add(elem(o));
		}
		return res;
	}
	
	/**
	 * 
	 * @param to the object to whom merge
	 * @param from the object from whom merge
	 * @return the &quot;to&quot; {@link JSonObj}
	 */
	public JSonObj objMerge(JSonObj to,JSonObj from) {
		from.forEachEntry(to::add);
		return to;
	}

	/**
	 * 
	 * @param to the array to whom merge
	 * @param from the array from whom merge
	 * @return the &quot;to&quot; {@link JSonArray}
	 */
	public JSonArray arMerge(JSonArray to,JSonArray from) {
		from.forEach(to::add);
		return to;
	}


}