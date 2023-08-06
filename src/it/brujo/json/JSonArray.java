package it.brujo.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSonArray extends JSonElem implements Iterable<JSonElem> {

	List<JSonElem> lst=new ArrayList<>();
	
	public JSonArray() {
	}
	public JSonArray(Iterable<? extends JSonElem> lst) {
		for (JSonElem e:lst)
			add(e);
	}

	public int size() {
		return lst.size();
	}

	public boolean isEmpty() {
		return lst.isEmpty();
	}

	public JSonArray add(JSonElem e) {
		lst.add(e);
		return this;
	}

	public JSonArray add(String s) {
		return add(new JSonString(s));
	}

	public JSonArray add(Integer i) {
		return add(new JSonNumber(i));
	}

	public JSonArray add(Boolean value) {
		if (value==null)
			return add(JSonConst.Null);
		return add(value ?  JSonConst.True : JSonConst.False );
	}

	public JSonElem get(int idx) {
		return lst.get(idx);
	}
	
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
	
	public boolean isBasic() {
		for (var el : lst) {
			if (! (el instanceof JSonValue ))
				return false;
		}
		return true;
	}
	

}
