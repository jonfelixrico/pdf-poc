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

/**
 * @author Felix
 *
 */
@RestController
public class PdfPocController {
	@Autowired
	private PdfService pdfService;
	
	@RequestMapping("/search")
	public List<SearchResult> searchAll(@RequestParam(required=true) String searchTerm) {
		return pdfService.searchAllFiles(searchTerm);
	}
	
	@RequestMapping("/search/{filename}")
	public SearchResult searchFile(@RequestParam(required=true) String searchTerm, @PathVariable(required=true) String filename) throws IOException {
		return pdfService.searchFile(searchTerm, filename);
	}
}
