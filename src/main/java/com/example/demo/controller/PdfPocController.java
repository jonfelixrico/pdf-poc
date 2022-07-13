/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<SearchResult> findMatches(@RequestParam String searchTerm) {
		return pdfService.doTextSearch(searchTerm);
	}
}
