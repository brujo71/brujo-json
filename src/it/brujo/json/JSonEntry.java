package it.brujo.json;


/** A JSON object is represented as an array of JSonEntry elements. Each entry has a JSonLabel and a JSonElem.
 * 
 * 
 */
public class JSonEntry {

	private JSonLabel label;
	private JSonElem value;
	
	public JSonEntry(JSonLabel label, JSonElem value) {
		this.label = label;
		this.value = value;
	}
	
	public JSonLabel label() {
		return label;
	}

	public JSonElem value() {
		return value;
	}

	@Override
	public String toString() {
		return "NameValue [label=" + label + ", value=" + value + "]";
	}

	void setLabel(JSonLabel label) {
		this.label = label;
	}

	void setValue(JSonElem value) {
		this.value = value;
	}
	
	

}
