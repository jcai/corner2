/*
 * Created on 2004-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class Lexicon {
    public static final String LEXICON_TAG = "Lexicon";

    public static final String LEXICON_PREFIX = "LEXICON";

    public static final String LEXICON_SUFFIX = "LEX";

    public static final String LEXICON_INDEX_SUFFIX = "INX";

    private AbstractDictionary dictionary_;

    public Lexicon(AbstractDictionary dic) {
        dictionary_ = dic;
    }

    abstract void loadLexiconIndex();

    abstract String[] getEntries(String entry);
}
