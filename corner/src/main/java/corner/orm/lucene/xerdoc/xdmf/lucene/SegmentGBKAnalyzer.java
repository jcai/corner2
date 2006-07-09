/*
 * Created on 2005-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

/**
 * @author Jia Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SegmentGBKAnalyzer extends Analyzer {
    private Set stopWords;

	private DictionaryFactory factory;

    /**
     * An array containing some common English words that are not usually useful
     * for searching.
     */
    public static final String[] STOP_WORDS = { "a", "an", "and", "are", "as",
            "at", "be", "but", "by", "for", "if", "in", "into", "is", "it",
            "no", "not", "of", "on", "or", "s", "such", "t", "that", "the",
            "their", "then", "there", "these", "they", "this", "to", "was",
            "will", "with", "你们", "我们", "他们", "这个", "那个", "能够" };

    public SegmentGBKAnalyzer() {
        stopWords = StopFilter.makeStopSet(STOP_WORDS);
    }

    public SegmentGBKAnalyzer(String[] stopWords) {
        this.stopWords = StopFilter.makeStopSet(stopWords);
    }

    public final TokenStream tokenStream(String filename, Reader reader) {
        return new StopFilter(new SegmentGBKTokenizer(reader,factory), stopWords);
    }
    public void setDictionaryFactory(DictionaryFactory factory){
    	this.factory=factory;
    }
}