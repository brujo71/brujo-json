package it.brujo.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	
	
	

}
