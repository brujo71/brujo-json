package it.brujo.json;

import java.io.IOException;

/** This class is a wrapper for the String that is used as name 
 * in JSON objects name:value pairs.
 * 
 *
 */
public class JSonLabel {

	private String value=null;
	
	/**
	 * 
	 * @param value the name
	 */
	public JSonLabel(String value) {
		this.value=value;
	}

	int writeTo(Appendable out) throws IOException{
		out.append('"');
		JSonFormatter.escape(value,out);
		out.append('"');
		return value.length()+2;
	}

	/**
	 * 
	 * @return the name
	 */
	public String value() {
		return value;
	}

	@Override
	public int hashCode() {
		return value==null ? 0 : value.hashCode();
	}

	/** An utility method to avoid <code> .equals(new JSonLabel(...))</code>
	 * 
	 * @param label la label in formato string
	 * @return true se la label corrisponde
	 */
	public boolean isEqual(String label) {
		return value.equals(label);
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof JSonLabel ) {
			return value.equals(((JSonLabel)obj).value());
		}
		else if (obj instanceof String) {
			return value.equals(obj);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Label [name=" + value + "]";
	}
	
}
