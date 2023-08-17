package it.brujo.json;

import java.io.IOException;
import java.util.Objects;

/** This class represents a JSON numeric value
 * 
 *
 */
public class JSonNumber extends JSonValue {

	private Number value;
	
	/**
	 * 
	 * @param value the represented number
	 */
	public JSonNumber(Number value) {
		this.value=Objects.requireNonNull(value) ;
	}

	@Override
	public String toString() {
		return value==null ? "null" : value.toString() ;
	}

	/**
	 * 
	 * @return the represented number
	 */
	public Number value() {
		return value;
	}

	/** {@inheritDoc}
	 * 
	 */
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
