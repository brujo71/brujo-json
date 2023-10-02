package it.brujo.json;


/** The base class for each JSON element
 * 
 *
 */
public abstract class JSonElem {

	JSonElem() {

	}
	
	abstract JSonElem myClone();
	
	/**
	 * 
	 * @return true if instanceof {@link JSonValue}
	 */
	public boolean isValue() {
		return this instanceof JSonValue;
	}
	
	/**
	 * 
	 * @return this object casted to {@link JSonValue}
	 */
	public JSonValue asValue() {
		return (JSonValue) this;
	}

	/**
	 * 
	 * @return true if instanceof {@link JSonObj}
	 */
	public boolean isObject() {
		return this instanceof JSonObj;
	}
	
	/**
	 * 
	 * @return this object casted to {@link JSonObj}
	 */
	public JSonObj asObj() {
		return (JSonObj) this;
	}

	/**
	 * 
	 * @return true if instanceof {@link JSonArray}
	 */
	public boolean isArray() {
		return this instanceof JSonArray;
	}
	
	/**
	 * 
	 * @return this object casted to {@link JSonArray}
	 */
	public JSonArray asArray() {
		return (JSonArray) this;
	}
	
}
