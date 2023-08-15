package it.brujo.json;

import java.io.IOException;

/** Base class for a basic values (Number,String,true,false or null)
 * 
 * 
 *
 */
public abstract class JSonValue extends JSonElem {
	
	abstract int appendTo(Appendable out) throws IOException;
	
	/**This method is intended to be as lenient as possibile and return a string
	 * also if the underlying class is a Number o a Constant 
	 * 
	 * @return a String representation of the value
	 */
	public abstract String stringValue();

	/**This method is intended to be as lenient as possible and return the most suitable 
	 * integer value. If the underlying class is a Number the result is 
	 * obtained from {@link java.lang.Number#intValue}
	 * 
	 * 
	 * @return a integer representation of the value. Can be <code>null</code>
	 */
	public abstract Integer intValue();

	/**This method is intended to be as lenient as possible.
	 * 
	 * @see #intValue
	 * @return a {@link java.lang.Long} representation of the value. Can be <code>null</code>
	 */
	public abstract Long longValue();

	/**This method is intended to be as lenient as possible.
	 * 
	 * @see #intValue
	 * @return a {@link java.lang.Double} representation of the value. Can be <code>null</code>
	 */
	public abstract Double doubleValue();
	
	/**This method is intended to be as lenient as possible.
	 * 
	 * @see #intValue
	 * @return a <code>boolean</code> representation of the value. Can be <code>null</code>
	 */
	public abstract boolean booleanValue();

}
