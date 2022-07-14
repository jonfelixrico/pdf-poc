package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;

import com.example.demo.service.pdf.SearchResult;

public class SearchResultResp {
	private String docId;
	private List<PageResultResp> pageHits;
	
	private SearchResultResp(String docId, List<PageResultResp> pageHits) {
		this.docId = docId;
		this.pageHits = pageHits;
	}

	public String getDocId() {
		return docId;
	}

	public List<PageResultResp> getPageHits() {
		return pageHits;
	}

	static class PageResultResp {
		private int pageNo;
		private List<int[]> hits;
		
		
		public PageResultResp(int pageNo, List<int[]> hits) {
			this.pageNo = pageNo;
			this.hits = hits;
		}
		
		public int getPageNo() {
			return pageNo;
		}
		
		public List<int[]> getHits() {
			return hits;
		}	
	}
	
	private static int[] convertPairToArray(Pair<Integer, Integer> pair) {
		int[] converted = { pair.getFirst(), pair.getSecond() };
		return converted;
	}
	
	static SearchResultResp prepareSearchResultForResp(SearchResult result) {
		List<PageResultResp> converted = result.getPages().stream()
				.map(page -> {
			
					List<int[]> convertedPairs = page.getHits().stream().map(hit -> convertPairToArray(hit)).collect(Collectors.toList());
					return new SearchResultResp.PageResultResp(page.getPageNo(), convertedPairs);
				})
				.collect(Collectors.toList());
		return new SearchResultResp(result.getFileName(), converted);
	}
}
