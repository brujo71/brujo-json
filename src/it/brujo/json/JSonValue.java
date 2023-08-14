package it.brujo.json;

import java.io.IOException;

/** Base class for a basic value such as Number or String
 * 
 * 
 *
 */
public abstract class JSonValue extends JSonElem {
	
	abstract int appendTo(Appendable out) throws IOException;

}
