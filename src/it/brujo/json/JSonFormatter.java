package it.brujo.json;

import java.io.IOException;


public class JSonFormatter {

	private FormatBuilder conf;
	private Appendable append;
	
	
	private JSonFormatter(FormatBuilder conf) {
		this.conf = conf;
	}
	
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
				if (alwaysNL || lineLen>80) {
					append.append("\n");
					identation(depth);
					lineLen=0;
				}
				else {
					append.append(' ');
				}
			}
			write(e, lineLen==0 && !first ? depth : 0);
			lineLen+=alwaysNL ? 1 : ((JSonValue)e).writeValue().length()+2;
			first=false;
		}
		identation(1, 1);
		append.append("]");
	}

	private void writeEntry(JSonEntry en,int depth,boolean first) throws IOException {
		if (!first)
			identation(depth);
		append.append(escape(en.label().value()));
		append.append(": ");
		write(en.value(), depth+1);
	}

	private void writeObj(JSonObj el,int depth) throws IOException {
		append.append("{");
		identation(1, 1);
		boolean first=true;
		for (JSonEntry nv: el) {
			if (!first) {
				append.append(",\n");
			}
			writeEntry(nv, depth+1, first);
			first=false;
		}
		identation(1, 1);
		append.append("}");
	}
	
	private void writeVal(JSonValue el,int depth) throws IOException {
		append.append(el.writeValue());
	}
	
	public static FormatBuilder builder() {
		return new FormatBuilder();
	}
	
	public static class FormatBuilder {
		
		private boolean useTabs=false;
		private int identationNb=4;
		
		private FormatBuilder() {}
		
		public JSonFormatter build() {
			return new JSonFormatter(this);
		}
		
		public FormatBuilder withTabs(boolean useTabs) {
			this.useTabs=useTabs;
			return this;
		}
		
		public FormatBuilder withIdentationNb(int identationNb) {
			this.identationNb=identationNb;
			return this;
		}

		public boolean isUseTabs() {
			return useTabs;
		}

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
	
	static void escape(String s,Appendable out) throws IOException {
		out.append("\"");
		char c,cprec=' ';
		for (int i=0;i<s.length();i++) {
			c=s.charAt(i);
			if (c=='"') {
				out.append("\\\"");
			}
			else if (c=='\\') {
				out.append("\\\\");
			}
			else if (c=='/' && cprec=='<') {
				out.append("\\/");
			}
			else if (c=='\b') {
				out.append("\\b");
			}
			else if (c=='\f') {
				out.append("\\f");
			}
			else if (c=='\n') {
				out.append("\\n");
			}
			else if (c=='\r') {
				out.append("\\r");
			}
			else if (c=='\t') {
				out.append("\\t");
			}
			else {
				out.append(c);
			}
			cprec=c;
		}
		
		out.append("\"");
		
	}
}
