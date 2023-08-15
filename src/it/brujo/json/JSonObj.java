package it.brujo.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class JSonObj extends JSonElem  {

	List<JSonEntry> content=new ArrayList<>();
	
	protected JSonObj addInner(String name,JSonElem value) {
		content.add(new JSonEntry(new JSonLabel(name), value));
		return this;
	}

	public int size() {
		return content.size();
	}

	public JSonObj clear() {
		content.clear();
		return this;
	}

	public JSonObj add(JSonEntry nv) {
		content.add(nv);
		return this;
	}
	
	public JSonObj add(String name, JSonElem value) {
		return addInner(name, value);
	}
	
	public JSonObj add(String name, String value) {
		return addInner(name, new JSonString(value));
	}
	
	public JSonObj add(String name, Number value) {
		return addInner(name, new JSonNumber(value));
	}
	
	public JSonObj add(String name, Boolean value) {
		if (value==null)
			return addInner(name, JSonConst.Null);
		return addInner(name, value ?  JSonConst.True : JSonConst.False );
	}

	public JSonObj setValue(String name, JSonElem value) {
		boolean changed=false;
		for (JSonEntry nv: content) {
			if (nv.label().isEqual(name)) {
				nv.setValue(value);
				changed=true;
			}
		}
		if (!changed) {
			addInner(name, value);
		}
		
		return this;
	}

	public void remove(String name) {
		Iterator<JSonEntry> iter=content.iterator();
		while (iter.hasNext()) {
			if (iter.next().label().isEqual(name))
				iter.remove();
		}
	}
	
	/** JSon prevede che un oggetto possa avere più valore con lo stesso name. Una volta parsato in Javascript viene preso l'ultimo.
	 *  Pertanto faccio la ricerca dall'ultimo.
	 *  potrebbe essere utile una funzione {@code List} di {@code JSonElem} getElems(String name) che li ritorni tutti.
	 * @param name {@code String}
	 * @return {@code JSonElem} {@code JSonElem}
	 */
	
	public JSonElem getElem(String name) {
		for (int i = content.size() - 1; i >= 0; i--) {
			if (content.get(i).label().isEqual(name))
				return content.get(i).value();
		}
		return null;
	}
	

	List<JSonEntry> list() {
		return content;
	}
	
	@Override
	public String toString() {
		return "JSonObj [content.size=" + content.size() + "]";
	}

	@Override
	JSonElem myClone() {
		JSonObj jsclone=new JSonObj();
		content.forEach(nv -> jsclone.add(new JSonEntry(nv.label(), nv.value().myClone())));
		return jsclone;
	}

	public Iterator<JSonEntry> iterator() {
		return content.iterator();
	}
	
	void forEachEntry(Consumer<JSonEntry> cons) {
		content.forEach(cons);
	}
	
}
