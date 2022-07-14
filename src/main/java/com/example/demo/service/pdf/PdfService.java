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
	
	private List<String> getTextContentFromPdfReader(PdfReader reader) {
		PdfTextExtractor extractor = new PdfTextExtractor(reader);

		return IntStream.range(1, reader.getNumberOfPages() + 1) // 2nd arg is exclusive, hence the +1
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
		return matcher.results()
				.map(result -> Pair.of(
						result.start(),
						result.end() - 1 // -1 since end returns the index AFTER the last matched character
				))
				.collect(Collectors.toList());
	}
	
	private List<PageResult> getPageResultsFromPdfReader(PdfReader reader, String searchTerm) {
		List<String> textContentList = getTextContentFromPdfReader(reader);
		
		// includes only pages with matches
		List<PageResult> results = new ArrayList<>();
		for (int i = 0; i < textContentList.size(); i++) {
			List<Pair<Integer, Integer>> matches = getMatchIndexes(textContentList.get(i), searchTerm);
			if (matches.size() == 0) {
				continue; // do not include pages without matches
			}
			
			results.add(new PageResult(i + 1, matches));
		}
		
		return results;
	}
	
	private SearchResult getSearchResult(String filename, String searchTerm) throws IOException {
		PdfReader reader = new PdfReader(String.format("%s/%s", DIRECTORY_ROOT, filename));
		List<PageResult> results = getPageResultsFromPdfReader(reader, searchTerm);
		
		if (results.size() == 0) {
			return null;
		}
		
		return new SearchResult(filename, results);
	}
	
	private static final String DIRECTORY_ROOT = "files";
	
	public List<SearchResult> searchAllFiles(String searchTerm) {
		Stream<String> paths = getAllPdfsInDirectory(DIRECTORY_ROOT).stream();
		
		return paths
				.map(filename -> {
					try {
						return getSearchResult(filename, searchTerm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				})
				.filter(result -> result != null)
				.collect(Collectors.toList());
	}
	
	public SearchResult searchFile(String searchTerm, String filename) throws IOException {
		return getSearchResult(String.format("%s.pdf", filename), searchTerm);
	}
}
