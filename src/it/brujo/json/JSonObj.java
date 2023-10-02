package it.brujo.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/** The class for a JSON object
 * 
 *
 */
public class JSonObj extends JSonElem  {

	private List<JSonEntry> content=new ArrayList<>();
	
	JSonObj addInner(String name,JSonElem value) {
		content.add(new JSonEntry(new JSonLabel(name), value));
		return this;
	}

	/**
	 * 
	 * @return the number of entries in the object
	 */
	public int size() {
		return content.size();
	}

	/**
	 * 
	 * @return <code>this</code>
	 */
	public JSonObj clear() {
		content.clear();
		return this;
	}

	JSonObj add(JSonEntry nv) {
		content.add(nv);
		return this;
	}
	
	/**The JSON <a href="json.org">specifications</a> allow multiple values for the same keys.<br>
	 * E.g.<br>
	 * <pre>
	 * { "a" : 1, "a" , 2}
	 * </pre>
	 * This implementations allow this behavior. Notice that in Javascript only the last key is considered.
	 * 
	 * 
	 * @see #setValue(String,JSonElem)
	 * @param name JSON name
	 * @param value any JSON value 
	 * @return <code>this</code>
	 */
	public JSonObj add(String name, JSonElem value) {
		return addInner(name, value);
	}

	/**
	 * 
	 * @see #setValue(String,JSonElem)
	 * @param name JSON name
	 * @param value any Java object that can be converted to JSON (e.g. String, Number)
	 * @return <code>this</code>
	 */
	public JSonObj add(String name, Object value) {
		return addInner(name, JSonBuilder.builder().elem(value));
	}

	/**
	 * 
	 * @see #add(String,Object)
	 * @param name JSON name
	 * @param value any JSON value 
	 * @return <code>this</code>
	 */
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

	/**Remove all accuracies of the name
	 * 
	 * @param name the JSON name
	 */
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

	Iterator<JSonEntry> iterator() {
		return content.iterator();
	}
	
	void forEachEntry(Consumer<JSonEntry> cons) {
		content.forEach(cons);
	}
	
	/**
	 * 
	 * @param from the obj from which to merge data
	 * @return <code>this</code>
	 */
	public JSonObj merge(JSonObj from) {
		return JSonBuilder.builder().objMerge(this, from);
	}
}
