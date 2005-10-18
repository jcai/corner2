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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SimpleDictionary extends AbstractDictionary {
    private Map lexMap_ = new HashMap();

    private Map charFreqMap_ = new HashMap();

    public final static int WORD_LENGTH_LOW = 2;

    public final static int WORD_LENGTH_HIGH = 8;

    /**
     * @param name
     */
    public SimpleDictionary(String name, String fullpath) {
        super(name, fullpath);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xerdoc.indexer.dictionary.AbstractDictionary#loadDictionary()
     */
    void loadDictionary() {
        loadCharFreq();
        for (int i = WORD_LENGTH_LOW; i <= WORD_LENGTH_HIGH; ++i) {
            Lexicon lexicon = new FixWLengthLexicon(this, i);
            lexicon.loadLexiconIndex();
            lexMap_.put(new Integer(i), lexicon);
        }
    }

    protected void loadCharFreq() {
        String filePath = fullpath_ + File.separator + "CHARFREQ.DAT";
        File charFreqFile = new File(filePath);
        if (!charFreqFile.exists()) {
            System.out.println("Character Frequence Data file not exists");
            return;
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String entry;
            while ((entry = in.readLine()) != null) {
                parseCharFreqEntry(entry);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot read Character Frequence File: "
                    + filePath);
            e.printStackTrace();
        }
    }

    protected void parseCharFreqEntry(String line) {
        String character = line.substring(0, 1);
        String freq = line.substring(1).trim();

        charFreqMap_.put(character, new Long(Long.parseLong(freq)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xerdoc.indexer.dictionary.AbstractDictionary#lookup(java.lang.String,
     *      java.lang.String)
     */
    public boolean lookup(String entry, String candidate) {
        int nLength = entry.length() + candidate.length();
        Lexicon lexicon = (Lexicon) lexMap_.get(new Integer(nLength));
        if (lexicon == null)
            return false;

        String[] entries = lexicon.getEntries(entry);
        if (entries == null)
            return false;

        for (int i = 0; i < entries.length; ++i) {
            String lexEntry = entries[i];
            if (lexEntry.equals(entry + candidate)) {
                // System.out.println("Find Lexicon Entry: " + entry +
                // candidate);
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xerdoc.indexer.dictionary.AbstractDictionary#maybeHaveEntry(java.lang.String)
     */
    public boolean maybeHaveEntry(String entry) {
        int entryLength = entry.length();
        for (int i = entryLength; i <= WORD_LENGTH_HIGH; ++i) {
            Lexicon lexicon = (Lexicon) lexMap_.get(new Integer(i));
            if (lexicon == null)
                return false;

            String[] entries = lexicon.getEntries(entry.substring(0, 1));
            if (entries == null)
                return false;

            for (int j = 0; j < entries.length; ++j) {
                String lexEntry = entries[j];
                if (lexEntry.indexOf(entry) == 0)
                    return true;
            }
        }
        return false;
    }

    public long getCharacterFreq(String character) {
        Long ret = (Long) charFreqMap_.get(character);
        if (ret == null)
            return 0;

        return ret.longValue();
    }
}
