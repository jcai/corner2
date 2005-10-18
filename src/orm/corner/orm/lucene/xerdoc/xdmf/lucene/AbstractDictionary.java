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
public abstract class AbstractDictionary {
    protected String name_;

    protected String fullpath_;

    public AbstractDictionary(String name, String fullpath) {
        name_ = name;
        fullpath_ = fullpath;
    }

    public String getFullpath() {
        return fullpath_;
    }

    abstract void loadDictionary();

    public abstract boolean lookup(String entry, String candidate);

    public abstract boolean maybeHaveEntry(String entry);

    public long getCharacterFreq(String character) {
        return 0;
    }

    public String getName() {
        return name_;
    }
}
