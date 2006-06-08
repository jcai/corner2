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
public class LexiconIndexEntry {
    private String character_;

    private long offset_;

    private int count_;

    public LexiconIndexEntry() {

    }

    public String toString() {
        return character_ + ": " + offset_ + " " + count_;
    }

    /**
     * @return Returns the character_.
     */
    public String getCharacter() {
        return character_;
    }

    /**
     * @param character_
     *            The character_ to set.
     */
    public void setCharacter(String character_) {
        this.character_ = character_;
    }

    /**
     * @return Returns the count_.
     */
    public int getCount() {
        return count_;
    }

    /**
     * @param count_
     *            The count_ to set.
     */
    public void setCount(int count_) {
        this.count_ = count_;
    }

    /**
     * @return Returns the offset_.
     */
    public long getOffset() {
        return offset_;
    }

    /**
     * @param offset_
     *            The offset_ to set.
     */
    public void setOffset(long offset_) {
        this.offset_ = offset_;
    }
}
