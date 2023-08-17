package it.brujo.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** The class for a JSON array
 * 
 *
 */
public class JSonArray extends JSonElem implements Iterable<JSonElem> {

	List<JSonElem> lst=new ArrayList<>();
	
	/**
	 * 
	 */
	public JSonArray() {
	}
	
	
//	public JSonArray(Iterable<? extends JSonElem> lst) {
//		for (JSonElem e:lst)
//			add(e);
//	}
//	
	/**
	 * 
	 * @return the numbre of elements in the array
	 */
	public int size() {
		return lst.size();
	}

	/**
	 * 
	 * @return true if this is an empty array
	 */
	public boolean isEmpty() {
		return lst.isEmpty();
	}

	/**
	 * 
	 * @param e a JSON element
	 * @return <code>this</code>
	 */
	public JSonArray add(JSonElem e) {
		lst.add(e);
		return this;
	}

//	public JSonArray add(String s) {
//		return add(new JSonString(s));
//	}
//
//	public JSonArray add(Integer i) {
//		return add(new JSonNumber(i));
//	}
//
//	public JSonArray add(Boolean value) {
//		if (value==null)
//			return add(JSonConst.Null);
//		return add(value ?  JSonConst.True : JSonConst.False );
//	}

	/**
	 * 
	 * @param idx index of the array
	 * @return the element
	 */
	public JSonElem get(int idx) {
		return lst.get(idx);
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Iterator<JSonElem> iterator() {
		return lst.iterator();
	}
	@Override
	public String toString() {
		return "JSonArray [lst.size=" + lst.size() + "]";
	}
	
	@Override
	JSonElem myClone() {
		JSonArray jsclone=new JSonArray();
		lst.forEach(e -> jsclone.add(e.myClone()) );
		return jsclone;
	}
	
	/**This method checks if there are no complex elements in the array.<br>
	 * E.g.<br>
	 * <pre>
	 * [ 1 , 3 ,17 ] ->  true  
	 * [ "xx", 8, null ] -> true
	 * [ "ww", {} , { "a" : 4} ] -> false
	 * [ [ 2 , 3] , [4 , 5 ] ] -> false
	 * </pre>
	 * 
	 * @return true if all the elements are instance of {@link JSonValue}
	 */
	public boolean isBasic() {
		for (var el : lst) {
			if (! (el instanceof JSonValue ))
				return false;
		}
		return true;
	}
	

}
