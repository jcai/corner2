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
 * $Id: WebLuceneHighlighter.java,v 1.5 2004/10/30 09:23:26 lhelper Exp $
 */

package corner.orm.lucene.cd;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;


/**
 * highlight and abstract string
 *
 * @author Che, Dong
 */
public final class WebLuceneHighlighter {
    //~ Instance fields --------------------------------------------------------

    /** high light analyzer */
    private HighlightAnalyzer analyzer = null;

    /** highligt term */
    private ArrayList terms = null;

    /** default return string limit */
    private int maxReturnSize = 256;

    /** default input buffer size */
    private int maxBufferSize = 2560;

    /** context block max length */
    private int maxContextLen = 48;

    /**
     * default highlight prefix: &lt;u&gt;  example: &lt;b&gt;  &lt;font
     * color="red"&gt;  ...
     */
    private String highlightPrefix = "<u>";

    /**
     * default highlight prefix: &lt;/u&gt; <br>
     * example: &lt;/b&gt; &lt;/font&gt;
     */
    private String highlightSuffix = "</u>";

    /** customize the highlight tag, default is 'u' - underlined */
    private String highlightTag = "u";

    /** input string buffer */
    private char[] srcBuffer = new char[0];

    //~ Constructors -----------------------------------------------------------

    /**
     * construct Highlighter with String[] t need highlight and use Analyzer
     * anal token from source string.
     *
     * @param t Terms.
     */
    public WebLuceneHighlighter(ArrayList t) {
        terms = t;
        analyzer = new HighlightAnalyzer(terms);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Return highlighted string
     *
     * @param srcString source string need to highlight
     *
     * @return highlighted string
     */
    public String highlight(String srcString) {
        if ((srcString == null) || srcString.trim().equals("")) {
            return "";
        }

        int srcLength = srcString.length();

        //truncate src to maxRetrunSize
        if (srcLength >= maxBufferSize) {
            srcString = srcString.substring(0, maxBufferSize);
            srcLength = maxBufferSize;
        }

        //return src if no term to highlight
        if (terms.size() == 0) {
            return srcString.substring(0, maxReturnSize);
        }

        try {
            //reset buffer and last term offset
            //default previous token end place
            int prevEnd = 0;
            srcBuffer = new char[srcLength];

            StringReader stringReader = new StringReader(srcString);
            stringReader.read(srcBuffer);

            StringReader sr = new StringReader(srcString);
            TokenStream tokenStream = analyzer.tokenStream(null, sr);

            //return string buffer  
            StringBuffer returnBuffer = new StringBuffer();
            String preContextBlock = ""; //previous text block

            //highlight:  [preContextBlock] + <b> + [token] + </b>
            for (Token t = tokenStream.next(); t != null;
                     t = tokenStream.next()
                ) {
                preContextBlock = getContext(prevEnd, t.startOffset());
                returnBuffer.append(preContextBlock);

                //append highlight string
                returnBuffer.append(highlightPrefix);

                for (int i = t.startOffset(); i < t.endOffset(); i++) {
                    returnBuffer.append(srcBuffer[i]);
                }

                returnBuffer.append(highlightSuffix);

                //record current offset
                prevEnd = t.endOffset();

                if (returnBuffer.length() > maxReturnSize) {
                    break;
                }
            }

            tokenStream.close();

            //no highlight token find, return first maxReturnSize of string[]
            if (returnBuffer.length() == 0) {
                if (srcLength > maxReturnSize) {
                    returnBuffer.append(srcBuffer, 0, maxReturnSize);
                } else {
                    returnBuffer.append(srcBuffer, 0, srcLength);
                }

                return returnBuffer.toString();
            }

            //expand return string to MaxReturn
            while ((returnBuffer.length() < maxReturnSize)
                       && (prevEnd < srcLength)
                  ) {
                returnBuffer.append(srcBuffer[prevEnd]);
                prevEnd++;
            }

            return returnBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();

            //return with original value
            return "";
        }
    }

    /**
     * construct Highlighter with String[] t need highlight and use Analyzer
     * anal token from source string
     *
     * @param src src string need to highlight
     * @param mReturn max return string max length
     *
     * @return highlighted string
     */
    public String highlight(String src, int mReturn) {
        maxReturnSize = mReturn;

        return highlight(src);
    }

    /**
     * construct Highlighter with String[] t need highlight and use Analyzer
     * anal token from source string
     *
     * @param src src string need to highlight
     * @param returnSize mReturn return string max length
     * @param readSize mRead highlight string max read
     *
     * @return highted string
     */
    public String highlight(String src, int returnSize, int readSize) {
        maxReturnSize = returnSize;
        maxBufferSize = readSize;

        return highlight(src);
    }

    /**
     * get context block from buffer between previous token end offset and
     * current token start offset
     *
     * @param prevEnd prevEnd previous token end offset
     * @param curStart curStart curren token start offset
     *
     * @return context text
     */
    private String getContext(int prevEnd, int curStart) {
        //added buffer length check
        if (curStart > srcBuffer.length) {
            curStart = srcBuffer.length;
        }

        if (curStart > prevEnd) {
            //return context buffer
            StringBuffer tb = new StringBuffer();
            int between = curStart - prevEnd;

            if (between <= maxContextLen) {
                for (int i = (prevEnd); i < curStart; i++) {
                    tb.append(srcBuffer[i]);
                }
            } else {
                int prevEndTo = getLastPunc(prevEnd,
                                            prevEnd + (maxContextLen / 2)
                                           ) + 1;

                for (int j = prevEnd; j < prevEndTo; j++) {
                    tb.append(srcBuffer[j]);
                }

                tb.append("...");

                int prevStartFrom = getFirstPunc(curStart - (maxContextLen / 2),
                                                 curStart
                                                ) + 1;

                for (int k = prevStartFrom; k < curStart; k++) {
                    tb.append(srcBuffer[k]);
                }
            }

            return tb.toString();
        } else {
            //empty text block
            return "";
        }
    }

    /**
     * find first punctuation offset between <code>start</code> and
     * <code>end</code> in buffer[]
     *
     * @param start start offset of the buffer
     * @param end end offset of the buffer
     *
     * @return punctuation offset
     */
    private int getFirstPunc(int start, int end) {
        //if not find return start offset
        int firstFind = start;

        while (start < end) {
            if (Character.isLetterOrDigit(srcBuffer[start]) == false) {
                firstFind = start;

                break;
            }

            start++;
        }

        return firstFind;
    }

    /**
     * find last punctuation offset between <code>start</code> and
     * <code>end</code> in buffer[]
     *
     * @param start start offset of the buffer
     * @param end end offset of the buffer
     *
     * @return last punctuation offset
     */
    private int getLastPunc(int start, int end) {
        //if not find return end offset
        int lastFind = end;

        while (start < end) {
            if (Character.isLetterOrDigit(srcBuffer[start]) == false) {
                lastFind = start;
            }

            start++;
        }

        return lastFind;
    }

    /**
     * Get the value of highlightTag.
     *
     * @return value of highlightTag.
     */
    public String getHighlightTag() {
        return highlightTag;
    }

    /**
     * Set the value of highlightTag.
     *
     * @param highlightTag Value to assign to highlightTag.
     */
    public void setHighlightTag(String highlightTag) {
        if(highlightTag != null && !highlightTag.trim().equals("")) {
            this.highlightTag = highlightTag;
            this.highlightPrefix = "<" + highlightTag + ">";
            this.highlightSuffix = "</" + highlightTag + ">";
        }
    }
}
