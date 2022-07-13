/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.pdf.PdfService;
import com.example.demo.service.pdf.SearchResult;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

/**
 * @author Felix
 *
 */
@RestController
public class PdfPocController {
	@Autowired
	private PdfService pdfService;
	
	@RequestMapping("/{fileName}/{pageNo}")
	public String getTextTest(@PathVariable String fileName, @PathVariable String pageNo) throws IOException {
		int parsedPageNo = Integer.parseInt(pageNo);
		PdfReader reader = new PdfReader(String.format("files/%s.pdf", fileName));
		PdfTextExtractor extractor = new PdfTextExtractor(reader);
		return extractor.getTextFromPage(parsedPageNo);
	}
	
	@RequestMapping("/search")
	public List<SearchResult> findMatches(@RequestParam String searchTerm) {
		return pdfService.doTextSearch(searchTerm);
	}
}
