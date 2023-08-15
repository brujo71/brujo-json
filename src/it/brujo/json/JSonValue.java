package it.brujo.json;

import java.io.IOException;

/** Base class for a basic value such as Number or String
 * 
 * 
 *
 */
public abstract class JSonValue extends JSonElem {
	
	abstract int appendTo(Appendable out) throws IOException;
	public abstract String stringValue();
	public abstract Integer intValue();
	public abstract Long longValue();
	public abstract Double doubleValue();

}
