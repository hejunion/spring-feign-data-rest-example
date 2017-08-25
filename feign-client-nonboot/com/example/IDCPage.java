package com.example;

public class IDCPage {
	public long numTotal;
	public String previous;
	public String next;
	public long pageSize;
	public long end;
	public long begin;
	
	@Override
	public String toString() {
		return "IDCPage [numTotal=" + numTotal + ", previous=" + previous + ", next=" + next + ", pageSize=" + pageSize
				+ ", end=" + end + ", begin=" + begin + "]";
	}

	
	
}
