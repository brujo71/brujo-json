package it.brujo.json;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;


public class JSonExplorer {
	
	/** Ricerca ricorsivamente la label e  il JSonElem corrispondente. Indipendentemente
	 *  dalla profondità dell'albero a cui si trova o dalla label dell'oggetto che la contiene.
	 *  Se ce ne sono più di una ritorna l'ultima. 
	 * 
	 * @param elem l'ggetto della ricerca
	 * @param label la label da trovare
	 * @return il JSonElem con la label cercata
	 */
	public static JSonElem findByName(JSonElem elem,String label) {
		final Ref<JSonElem> res=new Ref<>();
		inspect(elem, null, nv -> {
			if (nv.label().isEqual(label)) 
				res.o=nv.value();
		}, false);
		return res.o;
	}
	
	public static void forEachObj(JSonElem el, Consumer<JSonObj> cons) {
		if (el instanceof JSonObj) {
			cons.accept((JSonObj)el);
		}
		else if (el instanceof JSonArray) {
			((JSonArray)el).forEach(e -> forEachObj(e,cons));
		}
		else {
			throw new RuntimeException("unsupported class "+el.getClass().getName());
		}
	}
	
	/**ritorna il valore della proprietà di un oggetto quanto si sa anticipatamente si tratta di un array
	 * 
	 * @param jsObj un oggetto json
	 * @param name nome della proprietà a cui è associato il valore atteso
	 * @return the value as JSonArray
	 */
	public static JSonArray getObjValueArray(JSonObj jsObj,String name) {
		JSonElem elRes=jsObj.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonArray)
			return (JSonArray)elRes;
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}
	
	/**ritorna il valore della proprietà di un oggetto quanto si sa anticipatamente si tratta di numero (meglio se intero)
	 * 
	 * @param jsObj in intero (anche se il valore potrebbe essere un double)
	 * @param name nome della proprietà a cui è associato il valore atteso
	 * @return The Value a Integer
	 */
	public static Integer getObjValueInt(JSonObj jsObj,String name) {
		JSonElem elRes=jsObj.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonNumber)
			return ((JSonNumber)elRes).intValue();
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}

	/**ritorna il valore della proprietà di un oggetto quanto si sa anticipatamente si tratta di numero (meglio se intero)
	 * 
	 * @param jsObj in intero (anche se il valore potrebbe essere un double)
	 * @param name nome della proprietà a cui è associato il valore atteso
	 * @return The Value a Integer
	 */
	public static String getObjValueString(JSonObj jsObj,String name) {
		JSonElem elRes=jsObj.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonString)
			return ((JSonString)elRes).stringValue();
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}

	/**ritorna il valore della proprietà di un oggetto quanto si sa anticipatamente si tratta di numero (meglio se intero)
	 * 
	 * @param jsObj in intero (anche se il valore potrebbe essere un double)
	 * @param name nome della proprietà a cui è associato il valore atteso
	 * @return The Value a Integer
	 */
	public static Long getObjValueLong(JSonObj jsObj,String name) {
		JSonElem elRes=jsObj.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonNumber)
			return ((JSonNumber)elRes).longValue();
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}

	/**Se un array è di tutti Obj, come spesso accade in strutture dati regolari, risparmio controlli e cast
	 * 
	 * @param jsArray un array che deve essere di solo Objects
	 * @return Iterable on JSonObj
	 */
	public static Iterable<JSonObj> objArrayIterable(JSonArray jsArray) {
		return new IterableCast<JSonElem, JSonObj>(jsArray);
	}
	
	/**Se un array è di tutte Stringhe, come spesso accade in strutture dati regolari, risparmio controlli e cast
	 * 
	 * @param jsArray un array che deve essere di sole stringhe
	 * @return Iterable on String
	 */
	public static Iterable<String> stringArrayIterable(JSonArray jsArray) {
		return IterableConv.iterableConv(new IterableCast<JSonElem, JSonString>(jsArray), jss -> jss.value());
	}
	
	/* ----------- INSPECT INIZIO ----------- */
	
	public static void inspect(JSonElem elem, Consumer<JSonElem> elemCons, Consumer<JSonEntry> nvCons, boolean includeValues) {
		inspectinner(elem, elemCons, nvCons, includeValues);
	}

	private static void inspectinner(JSonElem elem, Consumer<JSonElem> elemCons, Consumer<JSonEntry> nvCons, boolean includeValues) {
		if (elem instanceof JSonArray) {
			if (elemCons!=null) elemCons.accept(elem);
			JSonArray jarr=(JSonArray)elem;
			jarr.lst.forEach(e -> inspectinner(e, elemCons, nvCons,includeValues));
		}
		else if (elem instanceof JSonObj) {
			if (elemCons!=null) elemCons.accept(elem);
			JSonObj jobj=(JSonObj)elem;
			jobj.forEachEntry(nv -> inspectinner(nv, elemCons, nvCons,includeValues));
		}
		else if (includeValues) {
			if (elemCons!=null) elemCons.accept(elem);
		}
	}
	
	private static void inspectinner(JSonEntry nv, Consumer<JSonElem> elemCons, Consumer<JSonEntry> nvCons, boolean includeValues) {
		if (nvCons!=null) nvCons.accept(nv);
		inspectinner(nv.value(), elemCons, nvCons,includeValues);
	}
	/* ----------- INSPECT FINE  -------------- */
	
	
	private static class Ref<T> {
		public T o=null;
		public Ref() {}
	}
	
	private static class IterableCast<DA, A> extends IterableConv<DA, A> {

		public IterableCast(Iterable<DA> sourceIterable) {
			super(sourceIterable);
		}

		@SuppressWarnings("unchecked")
		public A convert(DA da) {
			return (A)da;
		}
		
	}
	
	private static  abstract class IterableConv<DA, A> implements Iterable<A> {

		Iterable<DA> sourceIterable=null;
		
		public IterableConv(Iterable<DA> sourceIterable) {
			super();
			if (sourceIterable==null)
				throw new NullPointerException("IterableConv<DA, A> sourceIterable is null");
			this.sourceIterable = sourceIterable;
		}

		public Iterator<A> iterator() {
			return new IteratorConv(sourceIterable.iterator());
		}
		
		public static<DA,A> Iterable<A> iterableConv(Iterable<DA> iter,Function<DA, A> conv) {
			return new IterableConvFunc<DA,A>(iter, conv);
		}

		public abstract A convert(DA da);

		private class IteratorConv implements Iterator<A> {
			
			Iterator<DA> sourceIterator=null;
			
			public IteratorConv(Iterator<DA> sourceIterator) {
				super();
				this.sourceIterator = sourceIterator;
			}

			public boolean hasNext() {
				return sourceIterator.hasNext();
			}

			public A next() {
				return convert(sourceIterator.next());
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		}
		
		private static class IterableConvFunc<DA,A> extends IterableConv<DA, A> {

			Function<DA, A> conv;
			public IterableConvFunc(Iterable<DA> sourceIterable,Function<DA, A> conv) {
				super(sourceIterable);
				this.conv=conv;
			}

			@Override
			public A convert(DA da) {
				return conv.apply(da);
			}
			
		}
	}


}
