package it.brujo.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class JSonObjExplorer {
	
	private JSonObj target;
	private DateTimeFormatter dateFormat=JSonBuilder.DEFAULT_DATE_FORMATTER;
	private DateTimeFormatter dateTimeFormat=JSonBuilder.DEFAULT_DATETIME_FORMATTER;

	public JSonObjExplorer(JSonObj target) {
		this.target = target;
	}
	
	public String getString(String name) {
		JSonElem el=target.getElem(name);
		if (el==null)
			return null;
		if (el==JSonConst.Null)
			return null;
		return el.asValue().stringValue();
	}
	
	public boolean getBooleanNoNull(String name) {
		JSonElem el=target.getElem(name);
		if (el==null)
			return false;
		if (el==JSonConst.Null)
			return false;
		return el.asValue().booleanValue();
	}
	
	public LocalDateTime getLocatDateTime(String name) {
		JSonElem el=target.getElem(name);
		if (el==null)
			return null;
		if (el==JSonConst.Null)
			return null;
		return  LocalDateTime.from( dateTimeFormat.parse(el.asValue().stringValue()) );
	}
	
	public LocalDate getLocatDate(String name) {
		JSonElem el=target.getElem(name);
		if (el==null)
			return null;
		if (el==JSonConst.Null)
			return null;
		return  LocalDate.from( dateFormat.parse(el.asValue().stringValue()) );
	}
	
	public Long getLong(String name) {
		JSonElem elRes=target.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonNumber)
			return ((JSonNumber)elRes).longValue();
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}
	
	public Double getDouble(String name) {
		JSonElem elRes=target.getElem(name);
		if (elRes==null)
			return null;
		if (elRes instanceof JSonNumber)
			return ((JSonNumber)elRes).doubleValue();
		throw new RuntimeException("unexpected type "+elRes.getClass().getSimpleName());
	}
	
	public int size() {
		return target.size();
	}

	public List<String> keys() {
		final List<String> res=new ArrayList<String>(target.size());
		target.list().forEach(e -> res.add(e.label().value()));
		return res;
	}
	
	public void elems(BiConsumer<String, JSonElem> dump) {
		target.list().forEach(e -> dump.accept(e.label().value(), e.value()));
	}
}
