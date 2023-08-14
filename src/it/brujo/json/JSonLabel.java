package it.brujo.json;

import java.io.IOException;

public class JSonLabel {

	private String value=null;
	
	public JSonLabel(String value) {
		this.value=value;
	}

	public int writeTo(Appendable out) throws IOException{
		out.append('"');
		JSonFormatter.escape(value,out);
		out.append('"');
		return value.length()+2;
	}

	public String value() {
		return value;
	}

	@Override
	public int hashCode() {
		return value==null ? 0 : value.hashCode();
	}

	/** serve ad evitare il warning chiamando equal con String
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
