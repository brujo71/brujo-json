package it.brujo.json;

import java.io.IOException;

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

	@Override
	public Integer intValue() {
		return value.intValue();
	}

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

	@Override
	public String stringValue() {
		return writeValue();
	}
	
}
