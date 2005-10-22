/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SimpleGBKTokenizer extends Tokenizer {
    private static final int IO_BUFFER_SIZE = 256;

    private final char[] ioBuffer = new char[IO_BUFFER_SIZE];

    private List wordBuffer = new ArrayList();

    private int bufferIndex = 0;

    private int dataLen = 0;

    private String tokenType = "";

    public SimpleGBKTokenizer(Reader reader) {
        this.input = reader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.analysis.TokenStream#next()
     */
    public Token next() throws IOException {
        while (true) {
            if (bufferIndex >= dataLen) {
                dataLen = input.read(ioBuffer);
                bufferIndex = 0;
            }

            if (dataLen == -1) {
                if (wordBuffer.size() != 0)
                    break;

                return null;
            }

            char ch = ioBuffer[bufferIndex++];
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);

            if ((ub == Character.UnicodeBlock.BASIC_LATIN)
                    || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
                if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                    // convert HALFWIDTH_AND_FULLWIDTH_FORMS to BASIC_LATIN
                    int i = (int) ch;
                    i = i - 65248;
                    ch = (char) i;
                }

                if (Character.isLetterOrDigit(ch)
                        || ((ch == '_') || (ch == '+') || (ch == '#'))) {
                    tokenType = "NON_NLS";
                    wordBuffer.add(new Character(Character.toLowerCase(ch)));
                } else {
                    if (wordBuffer.size() == 0)
                        continue;
                    else
                        break;
                }
            } else {
                // NLS Character:
                if (Character.isLetter(ch)) {

                    // Xerdoc��
                    // ^
                    if (tokenType == "NON_NLS") {
                        --bufferIndex;
                        tokenType = "NLS";
                        break;
                    }
                    wordBuffer.add(new Character(ch));
                }
                break;
            }
        }

        String word = getWord();
        Token token = new Token(word, 0, word.length(), tokenType);
        return token;
    }

    protected String getWord() {
        StringBuffer sb = new StringBuffer();

        int length = wordBuffer.size();
        for (int i = 0; i < length; i++) {
            Character ch = (Character) wordBuffer.get(i);
            sb.append(ch.charValue());
        }

        wordBuffer.clear();
        return sb.toString();
    }
}
