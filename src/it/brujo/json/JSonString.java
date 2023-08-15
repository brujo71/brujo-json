package it.brujo.json;

import java.io.IOException;

public class JSonString extends JSonValue {

	private String value;
	public JSonString(String value) {
		if (value==null)
			throw new NullPointerException();
		this.value=value;
	}
	
	public String stringValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	public String value() {
		return value;
	}

	@Override
	JSonElem myClone() {
		return this;
	}

	String writeValue() {
		return JSonFormatter.escape(value);
	}

	@Override
	int appendTo(Appendable out) throws IOException {
		return JSonFormatter.escape(value,out);
	}

	@Override
	public Integer intValue() {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
		}
		return null;
	}

	@Override
	public Long longValue() {
		try {
			return Long.parseLong(value);
		}
		catch (NumberFormatException e) {
		}
		return null;
	}
	
	@Override
	public Double doubleValue() {
		try {
			return Double.parseDouble(value);
		}
		catch (NumberFormatException e) {
		}
		return null;
	}
	
}
