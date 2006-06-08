/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2004 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Lucene" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Lucene", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * $Id$
 */

package corner.orm.lucene.cd;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;


/**
 * HighLightFilter is a reverse StopFilter:<br>
 * 
 * <ul>
 * <li>
 * StopFilter removed tokens from token stream according to stop words table;
 * </li>
 * <li>
 * HighlightFilter only allow words return from the input TokenStream that are
 * named in the array of words.
 * </li>
 * </ul>
 * 
 *
 * @author Che, Dong
 */
public final class HighlightFilter extends TokenFilter {
    //~ Instance fields --------------------------------------------------------

    /** token buffer */
    private StringBuffer tokenTextBuffer = new StringBuffer();

    /** current token start offset */
    private int currentStart = 0;

    /** current token end offset */
    private int currentEnd = 0;

    /** return words list */
    private ArrayList wordList = new ArrayList();

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructs a filter which allow words return from the input TokenStream
     * that are named in the array of words.
     *
     * @param in input token stream
     * @param stopWords stop words array
     */
    public HighlightFilter(TokenStream in, String[] stopWords) {
        super(in);
        wordList = makeStopTable(stopWords);
    }

    /**
     * Constructs a filter which removes words from the input TokenStream that
     * are named in the ArrayList.
     *
     * @param in input token stream
     * @param words return word list
     */
    public HighlightFilter(TokenStream in, ArrayList words) {
        super(in);
        wordList = words;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Builds a ArrayList from an array of stop words, appropriate for passing
     * into the StopFilter constructor.  This permits this table construction
     * to be cached once when an Analyzer is constructed.
     *
     * @param wordList word list in String[]
     *
     * @return ArrayList word List
     */
    public static final ArrayList makeStopTable(String[] wordList) {
        ArrayList stopTable = new ArrayList(wordList.length);

        for (int i = 0; i < wordList.length; i++) {
            stopTable.add(wordList[i]);
        }

        return stopTable;
    }

    /**
     * return merged token: C1C2 C2C3 C3C4 will be merge to C1C2C3C4 to return
     *
     * @return Token Returns the next input Token whose termText() is named in
     *         word list.
     *
     * @throws IOException ioexception
     */
    public final Token next() throws IOException {
        // return the first non-stop word
        for (Token token = input.next(); token != null; token = input.next()) {
            //get first match token
            if (tokenTextBuffer.length() == 0) {
                if (wordList.indexOf(token.termText()) != -1) {
                    create(token);
                }
            } else {
                /**
                 * find next token:  <br>
                 * if current token overlaped with previous token:  C1C2 C2C3
                 * C2 with same C2 <br>
                 * then merged with cached privious token
                 */
                if (wordList.indexOf(token.termText()) != -1) {
                    if (token.startOffset() > (currentEnd)) {
                        Token returnToken = new Token(tokenTextBuffer.toString(),
                                                      currentStart, currentEnd
                                                     );
                        create(token);

                        return returnToken;
                    } else {
                        //merge with previous neighbor token
                        append(token);
                    }
                }
            }
        }

        //return cached string buffer as on token
        if (tokenTextBuffer.length() > 0) {
            Token tk = new Token(tokenTextBuffer.toString(), currentStart,
                                 currentEnd
                                );

            //empty token text buffer
            tokenTextBuffer = new StringBuffer();

            return tk;
        } else {
            // reached EOS -- return null
            return null;
        }
    }

    /**
     * append token cache: merge duplicate part<br>
     * example: C1C2 C2C3 ==>C1C2C3
     *
     * @param t token
     */
    private final void append(Token t) {
        String appendText = t.termText();
        int start = currentEnd - t.startOffset();
        appendText = appendText.substring(start, appendText.length());
        tokenTextBuffer.append(appendText);
        currentEnd = t.endOffset();
    }

    /**
     * create new token cache
     *
     * @param t input token
     */
    private final void create(Token t) {
        tokenTextBuffer = new StringBuffer();
        tokenTextBuffer.append(t.termText());
        currentStart = t.startOffset();
        currentEnd = t.endOffset();
    }

	
}
