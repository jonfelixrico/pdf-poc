package com.example.demo.service.pdf;

import java.util.List;

import org.springframework.data.util.Pair;

public class PageResult {
	private List<Pair<Integer, Integer>> hits;
	private int pageNo;
	
	public PageResult(int pageNo, List<Pair<Integer, Integer>> hits) {
		this.hits = hits;
		this.pageNo = pageNo;
	}

	public List<Pair<Integer, Integer>> getHits() {
		return hits;
	}

	public int getPageNo() {
		return pageNo;
	}
	
	
}
