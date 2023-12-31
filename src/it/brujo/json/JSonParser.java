package it.brujo.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/** The class for JSON parsing
 * 
 *
 */
public class JSonParser {

	private char cur;
	private Reader reader;
	
	private JSonParser(Reader reader) throws IOException {
		this.reader=reader;
		cur=(char) reader.read();
	
	}
	
	private void next() throws JSonParseEx,IOException {
		int curInt=reader.read();
		
		if (curInt<0) {
			if (cur=='\0') 
				throw new JSonParseEx();
			else
				cur='\0';
		}
		else {
			cur=(char)curInt;
		}
		
	}
	
	final static String toSkip=" \t\r\n";
	private void skip() throws JSonParseEx,IOException {
		while (toSkip.indexOf(cur)>=0 && cur!='\0')  {
			next();
		}
	}
	
	private void nextskip() throws JSonParseEx,IOException {
		next();
		skip();
	}
	
	private JSonLabel parseLabel() throws JSonParseEx,IOException {
		StringBuilder nome=new StringBuilder(32);
		skip();
		if (cur!='"') {
			throw new JSonParseEx();
		}
		next();
		while (cur!='"') {
			nome.append(cur);
			next();
		}
		nextskip();
		return new JSonLabel(nome.toString());
	}
	
	private JSonConst parseConstant(JSonConst cons) throws JSonParseEx,IOException {
		
		for (int cidx=0;cidx<cons.length();cidx++) {
			if (cur!=cons.charAt(cidx))
				throw new JSonParseEx();
			next();
		}
		skip();
		return cons;
	}
	
	private static final Map<Character,Character> parseEscapedChars=Map.of(
			'\\','\\',
			'r','\r',
			'n','\n',
			'f','\f',
			't','\t',
			'/','/',
			'"','"',
			'\'','\''
		);
	
	private int parseExa() throws JSonParseEx,IOException {
		char charsExa[]=new char[4];
		for (int i=0;i<4;i++) {
			next();
			charsExa[i]=cur;
		}
		String strExa=String.valueOf(charsExa);
		int valExa;
		try {
			valExa=Integer.parseInt(strExa, 16);
		}
		catch (Exception e) {
			throw new JSonParseEx();
		}
		return valExa;
	}
	
	private JSonString parseString() throws JSonParseEx,IOException {
		StringBuilder nome=new StringBuilder(32);
		if (cur!='"') {
			throw new JSonParseEx();
		}
		next();
		while (cur!='"') {
			if (cur=='\\') {
				next();
				Character mappedChar=parseEscapedChars.get(cur);
				if (mappedChar!=null) 
					nome.append(mappedChar);
				else if (cur=='u') {
					int valExa=parseExa();
					nome.append((char)valExa);
				}
				else
					throw new JSonParseEx();
			}
			else {
				nome.append(cur);
			}
			next();
		}
		nextskip();
		return new JSonString(nome.toString());
	}
	
	private JSonElem parseNumber() throws JSonParseEx,IOException {
		StringBuilder valuestr=new StringBuilder(16);
		boolean decimal=false;
		while (Character.isDigit(cur) || cur=='.' || cur=='+'|| cur=='-'|| cur=='e'|| cur=='E') {
			if (cur=='.' || cur=='e'|| cur=='E')
				decimal=true;
			
			valuestr.append(cur);
			next();
		}
		JSonElem res;
		
		if (decimal) { 
			res= new JSonNumber(Double.parseDouble(valuestr.toString()));
		}
		else {
			Long vl=Long.parseLong(valuestr.toString());
			if ((long)(vl.intValue())==vl.longValue()) {		
				res= new JSonNumber(vl.intValue());
			}
			else {
				res= new JSonNumber(vl);
			}
		}
		skip();
		return res;
	}
	
	private JSonEntry parseEntry() throws JSonParseEx,IOException {
		
		JSonLabel l=parseLabel();
		if (cur!=':') {
			throw new JSonParseEx();
		}
		nextskip();
		JSonElem el=parseElem();
		skip();
		
		return new JSonEntry(l, el);
	}
	
	private JSonObj parseObj() throws JSonParseEx,IOException {
		JSonObj res=new JSonObj();
		nextskip();
		while (cur!='}') {
			JSonEntry nv=parseEntry();
			res.add(nv);
			if (cur==',')
				nextskip();
		}
		nextskip();
		return res;
	}
	private JSonArray parseArray() throws JSonParseEx,IOException {
		JSonArray res=new JSonArray();
		nextskip();
		while (cur!=']') {
			JSonElem el=parseElem();
			res.add(el);
			if (cur==',')
				nextskip();
		}
		nextskip();
		return res;
	}
	
	private JSonElem parseElem() throws JSonParseEx,IOException {
		if (cur=='{') {
			return parseObj();
		}
		else if (cur=='[') {
			return parseArray();
		}
		else if (cur=='"') {
			return parseString();
		}
		else if (Character.isDigit(cur) || cur=='-') {
			return parseNumber();
		}
		else if (cur==JSonConst.Null.charAt(0)) {
			return parseConstant(JSonConst.Null);
		}
		else if (cur==JSonConst.True.charAt(0)) {
			return parseConstant(JSonConst.True);
		}
		else if (cur==JSonConst.False.charAt(0)) {
			return parseConstant(JSonConst.False);
		}
		else {
			throw new RuntimeException("cur="+cur);
		}
		
	}
	
	private JSonElem parseInner() throws JSonParseEx,IOException {
		skip();
		return parseElem();
	}
	
	/**
	 * 
	 * @param source the string to parse
	 * @return an internal representation of the JSON
	 * @throws JSonParseEx if parsing error
	 */
	public static JSonElem parse(String source) throws JSonParseEx {
		try {
			return parse(new StringReader(source));
		}
		catch (IOException e) {
			throw new RuntimeException(e); // impossible!
		}
	}

	/**
	 * 
	 * @param source the JSON to parse
	 * @return an internal representation of the JSON
	 * @throws JSonParseEx if parsing error
	 * @throws IOException from underlying stream
	 */
	public static JSonElem parse(Reader source) throws JSonParseEx,IOException {
		JSonParser jp=new JSonParser(source);
		try {
			return jp.parseInner();
		}
		finally {
			source.close();
		}
	}

	/** This exception is thrown both in case of syntax problems and internal errors.
	 * 
	 *
	 */
	public static class JSonParseEx extends Exception {

		private static final long serialVersionUID = 1L;

		/** Empty constructor
		 * 
		 */
		public JSonParseEx() {
			super();
		}
		
	}
	
}
