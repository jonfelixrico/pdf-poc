package com.example.demo.service.pdf;

import java.util.List;

public class SearchResult {
	private String fileName;
	private List<PageResult> pages;
	
	public SearchResult(String fileName, List<PageResult> pages) {
		super();
		this.fileName = fileName;
		this.pages = pages;
	}

	public String getFileName() {
		return fileName;
	}

	public List<PageResult> getPages() {
		return pages;
	}
}
