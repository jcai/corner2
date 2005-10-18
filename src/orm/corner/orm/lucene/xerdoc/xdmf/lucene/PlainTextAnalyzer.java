/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermFreqVector;

/**
 * @author Jia Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PlainTextAnalyzer {
	final class TermFreqPair {
		String term;

		int freq;
	}

	private List freqList = new LinkedList();

	private String filename;

	public PlainTextAnalyzer() {
	}

	private static String getTextCacheDir() {
        return "";
	}

	public static void initlize() {
		String indexDir = getTextCacheDir();
		File indexFile = new File(indexDir);
		if (indexFile.exists()) {
			File[] child = indexFile.listFiles();
			for (int i = 0; i < child.length; ++i)
				child[i].delete();

			indexFile.delete();
		}

	}

	/**
	 * doParse will be called by the parser, and store the term vectors into
	 * another index system.
	 * 
	 * @param filename
	 */
	public void doParse(String filename) throws IOException {
		this.filename = filename;
		long start = System.currentTimeMillis();
		File plainTextFile = new File(filename);

		String indexDir = getTextCacheDir();
		File indexFile = new File(indexDir);
		boolean bCreated = false;
		if (!indexFile.exists() || !indexFile.isDirectory()) {
			indexFile.mkdir();
			bCreated = true;
		}

		Analyzer analyzer = new SegmentGBKAnalyzer();
		IndexWriter writer = new IndexWriter(indexDir, analyzer, bCreated);
		writer.mergeFactor = 100;
		writer.setUseCompoundFile(false);

		Document doc = new Document();
		doc.add(Field.Keyword("URI", plainTextFile.getAbsolutePath()));

		BufferedReader reader = new BufferedReader(
				new FileReader(plainTextFile));
		doc.add(Field.Text("Analysis", reader, true));

		writer.addDocument(doc);

		writer.optimize();
		writer.close();
		// System.out.println("PlainTextAnalyzer doParse ellapsed "
		// + (System.currentTimeMillis() - start) + "mm");
	}

	/**
	 * doLoad will be called by the render and send back the stat infor such as
	 * abstract or keywords via the interface
	 * 
	 * @param uri
	 * @return if load failed, return false, else true.
	 */
	public boolean doLoad(String uri) {
		filename = uri;
		String indexDir = getTextCacheDir();
		try {
			IndexReader reader = IndexReader.open(indexDir);
			int nID;
			for (nID = 0; nID < reader.numDocs(); ++nID) {
				Document doc = reader.document(nID);
				String fileuri = doc.get("URI");
				if (fileuri.equals(uri))
					break;
			}

			if (nID == reader.numDocs())
				return false;

			TermFreqVector termVector = reader.getTermFreqVector(nID,
					"Analysis");
			String[] terms = termVector.getTerms();
			int[] freqs = termVector.getTermFrequencies();
			normalizeFrequence(terms, freqs);

			reader.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String[] getKeywords(int count) {
		List tokenList = new ArrayList();
		for (int i = 0, counter = 0; counter < count && i < freqList.size(); ++i) {
			TermFreqPair pair = (TermFreqPair) freqList.get(i);
			if (pair.term.length() < 2)
				continue;
			tokenList.add(pair.term);
			++counter;
		}

		String[] keywords = new String[tokenList.size()];
		tokenList.toArray(keywords);
		return keywords;
	}

	public String getAbstract(String key, int windowSize, String filename) {
		char[] buffer = new char[windowSize * 10];
		StringBuffer retValue = new StringBuffer();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			int dataLen = 0;
			int bufferIndex = 0;
			boolean bFirst = true;
			String firstOccur = null;
			String secondOccur = null;

			while (true) {
				if (bufferIndex >= dataLen) {
					dataLen = reader.read(buffer);
					bufferIndex = 0;
				}
				if (dataLen == -1)
					break;

				String content = new String(buffer, bufferIndex, dataLen
						- bufferIndex);
				String toCompare = content.toLowerCase();
				bufferIndex = toCompare.indexOf(key.toLowerCase());
				if (bufferIndex != -1) {
					int begin = bufferIndex - windowSize / 3;
					begin = begin >= 0 ? begin : 0;
					int end = begin + windowSize;
					end = end <= dataLen ? end : dataLen;
					if (bFirst) {
						firstOccur = content.substring(begin, end);
						bFirst = false;
					} else {
						secondOccur = content.substring(begin, end);
						break;
					}
					bufferIndex = end;
				} else {
					bufferIndex = dataLen;
				}
			}

			if (firstOccur != null)
				retValue.append(firstOccur);

			if (secondOccur != null) {
				retValue.append("......");
				retValue.append(secondOccur);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		String absValue = retValue.toString().replace('\r', '.');
		absValue = absValue.replace('\n', ' ');
		return absValue;
	}

	protected void normalizeFrequence(String[] terms, int[] freqs) {
		for (int i = 0; i < terms.length; ++i) {
			String term = terms[i];
			boolean bAdd = false;
			int freq = freqs[i];
			TermFreqPair pair = new TermFreqPair();
			pair.term = term;
			pair.freq = freq;

			int begin = 0;
			int end = freqList.size();
			int middle = begin + (end - begin) / 2;
			while (true) {
				if (begin >= end)
					break;
				middle = begin + (end - begin) / 2;
				TermFreqPair middleOne = (TermFreqPair) freqList.get(middle);
				int middleOneFreq = middleOne.freq;
				if (freq == middleOneFreq) {
					freqList.add(middle, pair);
					bAdd = true;
					break;
				} else if (freq > middleOneFreq) {
					end = middle;
				} else {
					begin = middle + 1;
				}
			}
			if (!bAdd)
				freqList.add(end, pair);
		}
	}
}