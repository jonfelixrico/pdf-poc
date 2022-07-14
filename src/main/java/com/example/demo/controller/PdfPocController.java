/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.pdf.PdfService;
import com.example.demo.service.pdf.SearchResult;

/**
 * @author Felix
 *
 */
@RestController
public class PdfPocController {
	@Autowired
	private PdfService pdfService;
	
	@RequestMapping("/search")
	public List<SearchResultResp> searchAll(@RequestParam(required=true) String searchTerm) {
		return pdfService.searchAllFiles(searchTerm).stream()
				.map(res -> SearchResultResp.prepareSearchResultForResp(res))
				.collect(Collectors.toList());
	}
	
	@RequestMapping("/search/{filename}")
	public SearchResultResp searchFile(@RequestParam(required=true) String searchTerm, @PathVariable(required=true) String filename) throws IOException {
		return SearchResultResp.prepareSearchResultForResp(pdfService.searchFile(searchTerm, filename));
	}
}
