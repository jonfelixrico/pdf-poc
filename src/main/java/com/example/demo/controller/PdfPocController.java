/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;

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
	@RequestMapping("/")
	public String getTextTest() throws IOException {
		PdfReader reader = new PdfReader("C:\\Users\\Felix\\Desktop\\pdf\\target.pdf");
		PdfTextExtractor extractor = new PdfTextExtractor(reader);
		return extractor.getTextFromPage(1);
	}
}
