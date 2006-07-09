/*
 * Created on 2004-10-24
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
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FixWLengthLexicon extends Lexicon {
    private int wordLength_;

    private String fullpath_;

    private String indexFullpath_;

    private Map indexEntries_;

    /**
     * @param name
     */
    public FixWLengthLexicon(AbstractDictionary dic, int wlen) {
        super(dic);
        indexEntries_ = new HashMap();
        wordLength_ = wlen;

        String fullpath = dic.getFullpath() + File.separator
                + Lexicon.LEXICON_TAG + File.separator + Lexicon.LEXICON_PREFIX
                + wordLength_ + ".";
        fullpath_ = fullpath + Lexicon.LEXICON_SUFFIX;
        indexFullpath_ = fullpath + Lexicon.LEXICON_INDEX_SUFFIX;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xerdoc.indexer.dictionary.Lexicon#loadLexiconIndex()
     */
    void loadLexiconIndex() {
        File indexFile = new File(indexFullpath_);

        if (indexFile.exists()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(
                        indexFullpath_));
                String entry;
                while ((entry = in.readLine()) != null) {
                    LexiconIndexEntry indexEntry = new LexiconIndexEntry();
                    parseIndexEntry(entry, indexEntry);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find Lexicon Index File: "
                        + indexFullpath_);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Cannot read Lexicon Index File: "
                        + indexFullpath_);
                e.printStackTrace();
            }
        } else {
            System.out.println("Lexicon Index File not Exists: "
                    + indexFullpath_);
        }
    }

    protected void parseIndexEntry(String line, LexiconIndexEntry entry) {
        String character = line.substring(0, 1);
        String offset = line.substring(1, 7).trim();
        String count = line.substring(7).trim();

        entry.setCharacter(character);
        entry.setOffset(Long.parseLong(offset));
        entry.setCount(Integer.parseInt(count));

        indexEntries_.put(character, entry);
    }

    protected void parseLexiconEntry(String line, String key, String[] result) {
        int nBegin = 1;
        for (int i = 0; i < result.length; ++i) {
            String subString = line.substring(nBegin, nBegin + wordLength_ - 1);
            result[i] = key + subString;
            nBegin += wordLength_ - 1;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xerdoc.indexer.dictionary.Lexicon#getEntries(java.lang.String)
     */
    String[] getEntries(String entry) {
        String[] result = null;
        LexiconIndexEntry indexEntry = (LexiconIndexEntry) indexEntries_
                .get(entry);
        if (indexEntry == null) {
            return null;
        }

        try {
            RandomAccessFile lexFile = new RandomAccessFile(fullpath_, "r");
            lexFile.seek(indexEntry.getOffset());
            byte[] lexBytes = new byte[(1 + (wordLength_ - 1)
                    * indexEntry.getCount()) * 2];
            lexFile.read(lexBytes);
            String lex = new String(lexBytes, "GBK");
            if (lex != null) {
                result = new String[indexEntry.getCount()];
                parseLexiconEntry(lex, entry, result);
            }
            
            lexFile.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Cannot find Lexicon File: " + fullpath_);
            e.printStackTrace();
        } catch (IOException e) {
            //System.out.println("Cannot read Lexicon File: " + fullpath_);
            e.printStackTrace();
        }

        return result;
    }
}
