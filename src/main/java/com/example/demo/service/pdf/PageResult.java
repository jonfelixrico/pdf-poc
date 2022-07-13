package com.example.demo.service.pdf;

import java.util.List;

import org.springframework.data.util.Pair;

public class PageResult {
	private List<Pair<Integer, Integer>> hits;

	public PageResult(List<Pair<Integer, Integer>> hits) {
		super();
		this.hits = hits;
	}

	public List<Pair<Integer, Integer>> getHits() {
		return hits;
	}
}
