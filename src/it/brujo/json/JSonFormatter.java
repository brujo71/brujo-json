package it.brujo.json;

import java.io.IOException;

/** Trasform an internal rapresentation to a JSON text.
 * 
 *
 */
public class JSonFormatter {

	private FormatBuilder conf;
	private Appendable append;
	
	private JSonFormatter(FormatBuilder conf) {
		this.conf = conf;
	}

	/**An utility method to obtain a JSON as String
	 * 
	 * @see #append
	 * @param json the element to write
	 * @return the JSON 
	 */
	public String writeToString(JSonElem json) {
		StringBuilder sb=new StringBuilder();
		append=sb;
		try {
			append(json, sb);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		return sb.toString();
	}

	/**
	 * 
	 * @param json the JSON
	 * @param out e.g. {@link StringBuilder} or {@link java.io.FileWriter}
	 * @throws IOException from underlying stream
	 */
	public void append(JSonElem json,Appendable out) throws IOException {
		append=out;
		write(json, 0);
	}

	private void write(JSonElem el,int depth) throws IOException {
		if (el instanceof JSonObj) {
			writeObj((JSonObj)el, depth);
		}
		else if (el instanceof JSonArray) {
			writeAr((JSonArray)el, depth);
		} 
		else if (el instanceof JSonValue) {
			writeVal((JSonValue)el, depth);
		} 
		else {
			throw new RuntimeException("unknown json element "+el.getClass().getName());
		}
	}
	
	private void identation(int depth) throws IOException {
		identation(depth, 0);
	}
	
	private void identation(int depth,int previousCharsNb) throws IOException {
		if (conf.noSpaces)
			return;
		if (conf.useTabs) {
			append.append("\t".repeat(depth*conf.identationNb));
		}
		else {
			int spacesNb=depth*conf.identationNb;
			if (previousCharsNb>0)
				spacesNb-=previousCharsNb;
			append.append(" ".repeat(Math.max(spacesNb,1)));
		}
	}
	
	private void writeAr(JSonArray el,int depth) throws IOException {
		append.append("[");
		identation(1, 1);
		boolean alwaysNL=!el.isBasic();
		int lineLen=0;
		boolean first=true;
		for (JSonElem e : el) {
			if (!first) {
				append.append(",");
				if (!conf.noSpaces && ( alwaysNL || lineLen>80 )) {
					append.append("\n");
					identation(depth);
					lineLen=0;
				}
				else {
					append.append(' ');
				}
			}
			write(e, lineLen==0 && !first ? depth : 0);
			lineLen++;
			first=false;
		}
		identation(1, 1);
		append.append("]");
	}

	private void writeEntry(JSonEntry en,int depth,boolean first) throws IOException {
		if (!first)
			identation(depth);
		append.append(escape(en.label().value()));
		append.append(":");
		if (!conf.noSpaces)	
			append.append(" ");
		write(en.value(), depth+1);
	}

	private void writeObj(JSonObj el,int depth) throws IOException {
		append.append("{");
		identation(1, 1);
		boolean first=true;
		for (JSonEntry nv: el.list()) {
			if (!first) {
				if (conf.noSpaces)
					append.append(",");					
				else
					append.append(",\n");
			}
			writeEntry(nv, depth+1, first);
			first=false;
		}
		identation(1, 1);
		append.append("}");
	}
	
	private void writeVal(JSonValue el,int depth) throws IOException {
		el.appendTo(append);
	}
	
	/** Start from here.<br>
	 * <code><pre>
	 * JSonFormatter formatter=JSonFormatter.builder()
	 * 			.withTabs(false)
	 * 			.build();
	 * </pre></code>
	 * 
	 * @return a cofiguration object
	 */
	public static FormatBuilder builder() {
		return new FormatBuilder();
	}
	
	public static JSonFormatter defaultFormatter() {
		return builder().build();
	}
	
	/** The configuration utility for the Formatter
	 * 
	 *
	 */
	public static class FormatBuilder {
		
		private boolean useTabs=false;
		private int identationNb=4;
		private boolean noSpaces;
		
		private FormatBuilder() {}
		
		/**
		 * 
		 * @return the fromatter
		 */
		public JSonFormatter build() {
			return new JSonFormatter(this);
		}
		
		public FormatBuilder noSpaces() {
			this.noSpaces=true;
			return this;
		}
		
		
		/**Configure tabs vs. spaces
		 * 
		 * @param useTabs true for tabs
		 * @return <code>this</code>
		 */
		public FormatBuilder withTabs(boolean useTabs) {
			this.useTabs=useTabs;
			return this;
		}
		
		/**Define how many indentation characters are used. Use {@link #withTabs} to
		 * configure tabs vs. spaces.
		 * 
		 * @param identationNb how many indentation characters should be used
		 * @return <code>this</code>
		 */
		public FormatBuilder withIdentationNb(int identationNb) {
			this.identationNb=identationNb;
			return this;
		}

		/**
		 * 
		 * @return true if tabs are used instead of spaces
		 */
		public boolean isUseTabs() {
			return useTabs;
		}

		/**
		 * 
		 * @return the number of indentation characters
		 */
		public int getIdentationNb() {
			return identationNb;
		}
		
		
		
		
	}

	static String escape(String s) {
		StringBuilder sb=new StringBuilder(32+s.length()*11/10);
		try {
			escape(s, sb);
		}
		catch (IOException e) { 
			throw new RuntimeException(e); //impossible;
		}
		return sb.toString();
	}
	
	static int escape(String s,Appendable out) throws IOException {
		int len=2;
		out.append('"');
		char c,cprec=' ';
		for (int i=0;i<s.length();i++) {
			c=s.charAt(i);
			if (c=='"') {
				out.append("\\\"");
				len+=2;
			}
			else if (c=='\\') {
				out.append("\\\\");
				len+=2;
			}
			else if (c=='/' && cprec=='<') {
				out.append("\\/");
				len+=2;
			}
			else if (c=='\b') {
				out.append("\\b");
				len+=2;
			}
			else if (c=='\f') {
				out.append("\\f");
				len+=2;
			}
			else if (c=='\n') {
				out.append("\\n");
				len+=2;
			}
			else if (c=='\r') {
				out.append("\\r");
				len+=2;
			}
			else if (c=='\t') {
				out.append("\\t");
				len+=2;
			}
			else if (c<32) { //control chars
				out.append("\\u");
				String hex="000"+Integer.toHexString(c);
				out.append(hex.substring(hex.length()-4));
			}
			else {
				out.append(c);
				len++;
			}
			cprec=c;
		}
		
		out.append('"');
		return len;
	}
}
