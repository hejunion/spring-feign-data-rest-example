package com.example;

public class IDCAssets {
	public IDCAsset[] items;
	public IDCPage paging;
	
	public boolean hasNextPage(){
		if (paging==null) return false;
		return paging.end < paging.numTotal -1;
	}
	
	public String getNextPageUrl(){
		if (this.hasNextPage())
			return paging.next;
		else return null;
	}
}
