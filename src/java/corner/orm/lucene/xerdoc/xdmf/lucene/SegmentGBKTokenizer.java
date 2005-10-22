/*
 * Created on 2005-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Jia Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SegmentGBKTokenizer extends Tokenizer {
	private static final int IO_BUFFER_SIZE = 256;

	private final char[] ioBuffer = new char[IO_BUFFER_SIZE];

	private int bufferIndex = 0;

	private int dataLen = 0;

	private String tokenType = "";

	private int offset = 0;

	private List tokenList = null;

	private DictionaryFactory dictionaryFactory;

	public SegmentGBKTokenizer(Reader reader, DictionaryFactory factory) {
		this.input = reader;
		this.dictionaryFactory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.lucene.analysis.TokenStream#next()
	 */
	public Token next() throws IOException {
		if (tokenList == null || tokenList.size() == 0)
			tokenize();

		if (tokenList.size() == 0)
			return null;

		Token token = (Token) tokenList.get(0);
		tokenList.remove(0);
		
		////System.out.println("token ["+token.toString()+"]");
		return token;
	}

	private void tokenize() throws IOException {
		List tokenCache = new ArrayList();
		String tokenType = "";
		StringBuffer wordBuffer = new StringBuffer();
		
		int start = offset;
		while (true) {

			if (bufferIndex >= dataLen) {
				dataLen = input.read(ioBuffer);
				bufferIndex = 0;

			}

			if (dataLen == -1) {
				if (wordBuffer.length() > 0 || tokenCache.size() > 0)
					break;

				return;
			}

			char ch = ioBuffer[bufferIndex++];
			offset++;

			Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
			if ((ub == Character.UnicodeBlock.BASIC_LATIN)
					|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
				if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
					// convert HALFWIDTH_AND_FULLWIDTH_FORMS to BASIC_LATIN
					int intValue = (int) ch;
					intValue = intValue - 65248;
					ch = (char) intValue;
				}

				if (Character.isLetter(ch)
						|| ((ch == '_') || (ch == '+') || (ch == '#'))) {
					if (tokenType.equals("NLS") && wordBuffer.length() > 0) {
						Token token = new Token(wordBuffer.toString(), start,
								offset, tokenType);
						
						tokenCache.add(token);
						wordBuffer.delete(0, wordBuffer.length());
					}
					tokenType = "NON_NLS";
					wordBuffer.append(Character.toLowerCase(ch));
				} else if (Character.isDigit(ch)) {
					if (wordBuffer.length() == 0)
						continue;

					if (tokenType.equals("NLS") && wordBuffer.length() > 0) {
						Token token = new Token(wordBuffer.toString(), start,
								offset, tokenType);
						
						tokenCache.add(token);
						wordBuffer.delete(0, wordBuffer.length());
					} else {
						tokenType = "NON_NLS";
						wordBuffer.append(Character.toLowerCase(ch));
					}
				} else {
					if (wordBuffer.length() == 0)
						continue;
					else {
						Token token = new Token(wordBuffer.toString(), start,
								offset, tokenType);
						
						tokenCache.add(token);
						wordBuffer.delete(0, wordBuffer.length());
						break;
					}
				}
			} else {
				// NLS Character:
				if (Character.isLetter(ch)) {
					if (tokenType.equals("NON_NLS") && wordBuffer.length() > 0) {
						Token token = new Token(wordBuffer.toString(), start,
								offset, tokenType);
						
						tokenCache.add(token);
						wordBuffer.delete(0, wordBuffer.length());
					}
					tokenType = "NLS";
					wordBuffer.append(ch);
				} else {
					if (wordBuffer.length() > 0) {
						Token token = new Token(wordBuffer.toString(),
								bufferIndex - wordBuffer.length() + 1,
								bufferIndex, tokenType);
						tokenCache.add(token);
						wordBuffer.delete(0, wordBuffer.length());
					}
				}
			}
		}
		if (wordBuffer.length() > 0) {
			Token token = new Token(wordBuffer.toString(), 0, wordBuffer
					.length(), tokenType);
			tokenCache.add(token);
			wordBuffer.delete(0, wordBuffer.length());
		}

		tokenList = new ArrayList();
		
		for (int i = 0; i < tokenCache.size(); ++i) {
			Token token = (Token) tokenCache.get(i);
			if (token.type().equals("NLS")) {
				
				start=token.startOffset();
				if(start==0){
					System.err.println("ERROR: start==0 ["+token+"]");
				}
				
				String content = token.termText();
				AbstractDictionary dic = dictionaryFactory
						.getDictionary("Normal");
				WordIdentifier identifier = new ChunkWordIdentifier(dic);
				String[] words = identifier.identifyWord(content);

				for (int j = 0; j < words.length; ++j){
					
					tokenList.add(new Token(words[j],start,start+ words[j].length(),
							token.type()));
					start+=words[j].length();
				}
			} else {
				tokenList.add(token);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		StringReader reader = new StringReader("研究生在研究,研究生命起源,我是一命研究生.!");
		DictionaryFactory dictionaryFactory = new DictionaryFactory();
		dictionaryFactory.setDictionariesLocation(new ClassPathResource(
				"/corner/orm/lucene/xerdoc/dictionary",SegmentGBKTokenizer.class));
		dictionaryFactory.afterPropertiesSet();

		SegmentGBKTokenizer tokenizer = new SegmentGBKTokenizer(reader,
				dictionaryFactory);

		Token token;
		int i = 0;
		while ((token = tokenizer.next()) != null) {
//			//System.out.print(token.termText() + "["+token.startOffset()+","+token.endOffset()+"] / ");
//			++i;
//			if (i % 5 == 0)
//				//System.out.println("");
		}
	}

}