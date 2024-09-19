package it.brujo.jsonexamples;

import java.time.DayOfWeek;
import java.util.Map;

import it.brujo.json.JSonArray;
import it.brujo.json.JSonBuilder;
import it.brujo.json.JSonElem;
import it.brujo.json.JSonFormatter;
import it.brujo.json.JSonObj;

public class Building {

	public static void main(String[] args) {
		new Building().run();
	}

	public void run() {
		JSonFormatter formatter=JSonFormatter.defaultFormatter();
		JSonElem ex1=build1();
		System.out.println(formatter.writeToString(ex1));
		
		System.out.println("\n\n====================\n\n");
		
		JSonElem ex2=build2();
		System.out.println(formatter.writeToString(ex2));
	}
	
	public JSonElem build1() {
		JSonBuilder b=JSonBuilder.builder();
		JSonObj ex=b.obj()
				.add("testString", "hello")
				.add("testInteger", 17)
				.add("testArray", b.arrayOf("aa","bb",17,17.4).add(true))
				.add("nested", b.obj(Map.of("one",1,"two",2,"three",3)))
			;	
		return ex;
	}
	
	public JSonElem build2() {
		JSonBuilder b=JSonBuilder.builder();
		JSonArray weekDays=b.array();
		for (int wd=1;wd<=7;wd++) {
			weekDays.add(DayOfWeek.of(wd));
		}
		JSonObj obj=b.obj("test1", 1, "test2", "two", "test3", null);
		
		JSonObj ex=b.obj()
				.add("weelDays",weekDays)
				.add("obj",obj)
			;
		
		JSonObj exMerge=b.obj(new String[] {"name","surname","age"}
						,new Object[] {"Andrea","Sodomaco",52});
		
		ex.merge(exMerge);
		
		return ex;
	}
	
}
