package it.brujo.json;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import it.brujo.json.JSonParser.JSonParseEx;

public class JSonPersist {
	
	private JSonObj obj;
	private Path path;

	private JSonPersist() {}
	
	public static JSonPersist create(Path path) {
		JSonPersist res=new JSonPersist();
		res.path=path;
		res.init();
		return res;
	}

	private void init() {
		try {
			if (path.toFile().exists()) {
				obj=JSonParser.parse(new FileReader(path.toFile())).asObj();
			}
			else {
				obj=new JSonObj();
			}
		} catch (JSonParseEx | IOException e) {
			
		}
	}
	
	private void store() {
		try {
			Files.writeString(path,JSonFormatter.defaultFormatter().writeToString(obj));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public synchronized void set(String name,Object value) {
		obj.setValue(name, JSonBuilder.builder().elem(value));
		store();
	}
	
	public JSonValue get(String name) {
		var el=obj.getElem(name);
		return el==null ? null : el.asValue();
	}
	
	public synchronized Boolean getBoolean(String name) {
		var res=get(name);
		return res==null ? null : res.booleanValue();
	}
	
	public synchronized void setBoolean(String name,Boolean b) {
		obj.setValue(name, JSonBuilder.builder().value(b));
		store();
	}
	
	public synchronized Integer getInteger(String name) {
		var res=get(name);
		return res==null ? null : res.intValue();
	}
	
	public synchronized void setInteger(String name,Integer i) {
		obj.setValue(name, JSonBuilder.builder().value(i));
		store();
	}
	
	public synchronized String getString(String name) {
		var res=get(name);
		return res==null ? null : res.stringValue();
	}
	
	public synchronized void setString(String name,String i) {
		obj.setValue(name, JSonBuilder.builder().value(i));
		store();
	}
	
	public JSonObj jsonObj() {
		return obj;
	}
	
}
