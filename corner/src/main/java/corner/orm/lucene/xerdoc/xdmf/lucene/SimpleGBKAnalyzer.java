/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SimpleGBKAnalyzer extends Analyzer {
    public SimpleGBKAnalyzer() {

    }

    public final TokenStream tokenStream(String filename, Reader reader) {
        return new SimpleGBKTokenizer(reader);
    }
}
