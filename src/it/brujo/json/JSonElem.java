package it.brujo.json;


/** The base class for each JSON element
 * 
 *
 */
public abstract class JSonElem {

	JSonElem() {

	}
	
	abstract JSonElem myClone();
	

//	public Boolean toBoolValue() {
//		if (this==JSonConst.Null) {
//			return null;
//		}
//		else if (this==JSonConst.True) {
//			return true;
//		}
//		else if (this==JSonConst.False) {
//			return false;
//		}
//		else if (this instanceof JSonNumber) {
//			return ((JSonNumber)this).longValue()!=0L;
//		}
//		else {
//			throw new RuntimeException(""+toString());
//		}
//	}

//	public void forEachObj(Consumer<JSonObj> cons) {
//		if (this instanceof JSonObj) {
//			cons.accept((JSonObj)this);
//		}
//		else if (this instanceof JSonArray) {
//			((JSonArray)this).forEach(e -> e.forEachObj(cons));
//		}
//		else {
//			throw new RuntimeException("unsupported class "+this.getClass().getName());
//		}
//	}
	
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
