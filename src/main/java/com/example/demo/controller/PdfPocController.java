/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

/**
 * @author Felix
 *
 */
@RestController
public class PdfPocController { 
	@RequestMapping("/{fileName}/{pageNo}")
	public String getTextTest(@PathVariable String fileName, @PathVariable String pageNo) throws IOException {
		int parsedPageNo = Integer.parseInt(pageNo);
		PdfReader reader = new PdfReader(String.format("files/%s.pdf", fileName));
		PdfTextExtractor extractor = new PdfTextExtractor(reader);
		return extractor.getTextFromPage(parsedPageNo);
	}
}
