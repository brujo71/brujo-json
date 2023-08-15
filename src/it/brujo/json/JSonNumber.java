package it.brujo.json;

import java.io.IOException;

/** This class represents a JSON numeric value
 * 
 *
 */
public class JSonNumber extends JSonValue {

	private Number value;
	public JSonNumber(Number value) {
		if (value==null)
			throw new NullPointerException();
		this.value=value;
	}

	@Override
	public String toString() {
		return value==null ? "null" : value.toString() ;
	}

	public Number value() {
		return value;
	}

	@Override
	public Double doubleValue() {
		return value.doubleValue();
	}

	/** {@inheritDoc}
	 * 
	 */
	@Override
	public Integer intValue() {
		return value.intValue();
	}

	/** {@inheritDoc}
	 * 
	 */
	@Override
	public Long longValue() {
		return value.longValue();
	}

	@Override
	JSonElem myClone() {
		return this;
	}

	String writeValue() {
		return value.toString();
	}

	@Override
	int appendTo(Appendable out) throws IOException {
		String s=writeValue();
		out.append(s);
		return s.length();
	}

	/** {@inheritDoc}
	 * 
	 */
	@Override
	public String stringValue() {
		return writeValue();
	}

	/** {@inheritDoc}
	 * 
	 */
	@Override
	public boolean booleanValue() {
		Long l=longValue();
		return l==null || l==0L ? false : true;
	}
	
	
	
}
