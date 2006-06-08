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
public abstract class WordIdentifier {
    protected AbstractDictionary dictionary_;

    public WordIdentifier(AbstractDictionary dic) {
        dictionary_ = dic;
    }

    public abstract String[] identifyWord(String content);
}
