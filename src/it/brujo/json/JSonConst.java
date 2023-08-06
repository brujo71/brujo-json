package it.brujo.json;

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

	@Override
	String writeValue() {
		return constant.intern();
	}
	
}
