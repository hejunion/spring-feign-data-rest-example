package com.example;

import java.io.Serializable;
import java.util.Arrays;

public class IDCAsset implements Serializable {
	public final static String CONTEXT_SPLITTER="/";
	public final static String COMMA_SPLITTER=",";
	
	public String _type;
	public String _id;
	public String _url;
	public String _name;
	public IDCAsset[] _context;
	
	public boolean hasContext(){
		if (_context ==null) return false;
		return _context.length > 0;
	}
	
	public String getFullPathName(){
		return this.getContextPath() +CONTEXT_SPLITTER + _name;
	}
	
	public String getContextPath(){
		StringBuilder sb = new StringBuilder();
		for ( IDCAsset contextAsset : _context){
			sb.append(CONTEXT_SPLITTER);
			sb.append(contextAsset._name);
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "IDCAsset [_type=" + _type + ", _id=" + _id + ", _url=" + _url + ", _name=" + _name + ", _context lenght="
				+ ( _context!=null?_context.length:0) + "]";
	}

	
}
