package it.brujo.json;

import java.util.function.Consumer;

public abstract class JSonElem {

	public JSonElem() {

	}
	
	abstract JSonElem myClone();
	
	public String stringValue() {
		return toString();
	}
	
	/**This method is overridden in JSonNumber
	 * 
	 * @return
	 */
	public Integer intValue() {
		if (this==JSonConst.Null) {
			return null;
		}
		else {
			try {
				return Integer.parseInt(stringValue());
			}
			catch (Exception e) {
				return null;
			}
		}
	}

	/**This method is overridden in JSonNumber
	 * 
	 * @return
	 */
	public Long longValue() {
		if (this==JSonConst.Null) {
			return null;
		}
		else {
			try {
				return Long.parseLong(stringValue());
			}
			catch (Exception e) {
				return null;
			}
		}
	}

	public Boolean toBoolValue() {
		if (this==JSonConst.Null) {
			return null;
		}
		else if (this==JSonConst.True) {
			return true;
		}
		else if (this==JSonConst.False) {
			return false;
		}
		else if (this instanceof JSonNumber) {
			return ((JSonNumber)this).longValue()!=0L;
		}
		else {
			throw new RuntimeException(""+toString());
		}
	}

	public void forEachObj(Consumer<JSonObj> cons) {
		if (this instanceof JSonObj) {
			cons.accept((JSonObj)this);
		}
		else if (this instanceof JSonArray) {
			((JSonArray)this).forEach(e -> e.forEachObj(cons));
		}
		else {
			throw new RuntimeException("unsupported class "+this.getClass().getName());
		}
	}
	
	public boolean isValue() {
		return this instanceof JSonValue;
	}
	
	public boolean isObject() {
		return this instanceof JSonObj;
	}
	
	public JSonObj asObj() {
		return (JSonObj) this;
	}

	public boolean isArray() {
		return this instanceof JSonArray;
	}
	
	public JSonArray asArray() {
		return (JSonArray) this;
	}
	
}