package it.brujo.json;


public class JSonString extends JSonValue {

	private String value;
	public JSonString(String value) {
		if (value==null)
			throw new NullPointerException();
		this.value=value;
	}
	
	@Override
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

	@Override
	String writeValue() {
		return JSonFormatter.escape(value);
	}
	
}
