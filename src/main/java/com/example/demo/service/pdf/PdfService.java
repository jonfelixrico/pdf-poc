package com.example.demo.service.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

@Service
public class PdfService {
	private List<String> getAllPdfsInDirectory(String dir) {
		return Stream
				.of(new File(dir).listFiles())
				.filter(file -> !file.isDirectory())
				.map(File::getName)
				.filter(name -> name.matches("^.*\\.pdf"))
				.collect(Collectors.toList());
	}
	
	private List<String> getTextContentOfPdf(String path) throws IOException {
		PdfReader reader = new PdfReader(path);
		PdfTextExtractor extractor = new PdfTextExtractor(reader);

		return IntStream.range(1, reader.getNumberOfPages() + 1)
				.mapToObj(pageNo -> {
					try {
						return extractor.getTextFromPage(pageNo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				})
				.collect(Collectors.toList());
	}
	
	private List<Pair<Integer, Integer>> getMatchIndexes(String text, String searchTerm) {
		Matcher matcher = Pattern.compile(searchTerm, Pattern.LITERAL).matcher(text);
		return matcher.results().map(result -> Pair.of(result.start(), result.end())).collect(Collectors.toList());
	}
	
	private static final String DIRECTORY_ROOT = "files";
	
	public List<SearchResult> doTextSearch(String searchTerm) {
		Stream<String> paths = getAllPdfsInDirectory(DIRECTORY_ROOT).stream();
		
		return paths.map(filename -> {
			List<PageResult> results;
			try {
				results = getTextContentOfPdf(String.format("%s/%s", DIRECTORY_ROOT, filename))
					.stream()
					.map(textContent -> new PageResult(getMatchIndexes(textContent, searchTerm)))
					.collect(Collectors.toList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				results = new ArrayList<>();
			}

			return new SearchResult(filename, results);
		}).collect(Collectors.toList());
	}
}
