package it.brujo.json;


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

	public double doubleValue() {
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

	@Override
	String writeValue() {
		return value.toString();
	}
	
}
