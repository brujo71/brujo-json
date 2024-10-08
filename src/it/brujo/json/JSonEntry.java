package it.brujo.json;


/** A JSON object is represented as an array of JSonEntry elements. Each entry has a JSonLabel and a JSonElem.
 * 
 * 
 */
class JSonEntry {

	private JSonLabel label;
	private JSonElem value;
	
	/**
	 * 
	 * @param label
	 * @param value
	 */
	public JSonEntry(JSonLabel label, JSonElem value) {
		if (value==null)
			throw new NullPointerException();

		this.label = label;
		this.value = value;
	}
	
	/**
	 * 
	 * @return
	 */
	public JSonLabel label() {
		return label;
	}

	/**
	 * 
	 * @return
	 */
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
