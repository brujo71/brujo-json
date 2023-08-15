package it.brujo.json;

import java.io.IOException;

/** Questa classe deve essere mantenuta immutable o si deve cambiare myClone()
 * 
 * @author andrea
 *
 */
public final class JSonConst extends JSonValue {

	private String constant;
	public static final JSonConst False=new JSonConst("false");
	public static final JSonConst True=new JSonConst("true");
	public static final JSonConst Null=new JSonConst("null");
	
	JSonConst(String constant) {
		this.constant=constant;
	}
	
	@Override
	public String toString() {
		return constant;
	}
	
	char charAt(int i) {
		return constant.charAt(i);
	}
	
	int length() {
		return constant.length();
	}

	@Override
	JSonElem myClone() {
		return this;
	}

	String writeValue() {
		return constant.intern();
	}

	@Override
	int appendTo(Appendable out) throws IOException {
		String s=writeValue();
		out.append(s);
		return s.length();
	}

	@Override
	public String stringValue() {
		return constant.intern();
	}

	@Override
	public Integer intValue() {
		if (this==False)
			return 0;
		else if (this==True)
			return 1;
		return null;
	}

	@Override
	public Long longValue() {
		if (this==False)
			return 0L;
		else if (this==True)
			return 1L;
		return null;
	}

	@Override
	public Double doubleValue() {
		return null;
	}
	
}
