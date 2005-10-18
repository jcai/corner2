/*
 * Created on 2004-10-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Winters.J.Mi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChunkWordIdentifier extends WordIdentifier {
	private static final double DELTA = 10e-6;

	/*
	 * WordChunk is the chunk that holds the 3 temp words to analysis, and the
	 * WordChunk is just a exploring tree. The first level wordChunk is just a
	 * container, it does not contain any useful word, then the level 1, 2, 3
	 * contain the exact words
	 */
	class WordChunk {
		public String word_;

		public int nLevel;

		public int offset;

		public WordChunk father_;

		public List children = new ArrayList();

		public WordChunk() {
			word_ = "";
			nLevel = 0;
			offset = 0;
			father_ = null;
		}

		public WordChunk(WordChunk father) {
			word_ = "";
			nLevel = father.nLevel + 1;
			offset = father.offset + father.word_.length();
			father.children.add(this);
			father_ = father;
		}
	}

	private List chunkLeaves_ = new ArrayList();

	/**
	 * @param dic
	 */
	public ChunkWordIdentifier(AbstractDictionary dic) {
		super(dic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xerdoc.indexer.wordseg.WordIdentifier#identifyWord(java.lang.String)
	 */
	public String[] identifyWord(String content) {
		chunkLeaves_.clear();
		String buffer = content;
		List result = new ArrayList();

		WordChunk chunk = new WordChunk();
		int nIndex = 0;
		while (true) {
			getWordChunk(buffer, chunk);
			WordChunk[] chunks = filterLongestChunk();
			if (chunks.length == 0) {
				System.out.println("Error: Did not find any chunks");
			} else {
				WordChunk state = chunks[0];
				chunks = filterAverageWordLength(chunks);
				chunks = filterVarianceWordLength(chunks);
				chunks = filterMophemicDegree(chunks);

				WordChunk fstChunk;
				if (chunks == null || chunks.length == 0) {
					fstChunk = state;
				} else
					fstChunk = chunks[0];
				while (fstChunk.father_.nLevel != 0) {
					fstChunk = fstChunk.father_;
				}
				String word = fstChunk.word_;
				result.add(word);

				nIndex += word.length();
				if (nIndex >= content.length())
					break;

				buffer = content.substring(nIndex);
			}
			chunkLeaves_.clear();
			chunk = new WordChunk();
		}
		String[] retValue = new String[result.size()];
		result.toArray(retValue);
		return retValue;
	}

	protected WordChunk[] filterLongestChunk() {
		WordChunk[] result = null;
		List chunks = new ArrayList();

		// Find the longest size;
		int nMax = 0;
		int length = chunkLeaves_.size();
		for (int i = 0; i < length; i++) {
			WordChunk chunk = (WordChunk) chunkLeaves_.get(i);
			int size = chunk.offset + chunk.word_.length();
			if (size > nMax)
				nMax = size;
		}

		for (int i = 0; i < length; i++) {
			WordChunk chunk = (WordChunk) chunkLeaves_.get(i);
			if (chunk.offset + chunk.word_.length() == nMax)
				chunks.add(chunk);
		}

		result = new WordChunk[chunks.size()];
		chunks.toArray(result);
		return result;
	}

	protected WordChunk[] filterAverageWordLength(WordChunk[] chunks) {
		if (chunks.length == 1)
			return chunks;

		WordChunk[] result = null;
		List resList = new ArrayList();

		double nMax = 0;
		for (int i = 0; i < chunks.length; ++i) {
			int size = chunks[i].offset + chunks[i].word_.length();
			double avgLength = (double) (((double) size) / (double) (chunks[i].nLevel));
			if (nMax < avgLength)
				nMax = avgLength;
		}

		for (int i = 0; i < chunks.length; ++i) {
			int size = chunks[i].offset + chunks[i].word_.length();
			double avgLength = (double) (((double) size) / (double) (chunks[i].nLevel));
			if (Math.abs(nMax - avgLength) <= DELTA)
				resList.add(chunks[i]);
		}

		result = new WordChunk[resList.size()];
		resList.toArray(result);
		return result;
	}

	protected WordChunk[] filterMophemicDegree(WordChunk[] chunks) {
		if (chunks.length == 1)
			return chunks;

		WordChunk[] result = null;
		List resList = new ArrayList();

		double nMax = 0;
		for (int i = 0; i < chunks.length; ++i) {
			double mphDegreeSum = computeMorphemicDegreeSum(chunks[i]);
			if (nMax < mphDegreeSum)
				nMax = mphDegreeSum;
		}

		for (int i = 0; i < chunks.length; ++i) {
			double mphDegreeSum = computeMorphemicDegreeSum(chunks[i]);
			if (Math.abs(nMax - mphDegreeSum) <= DELTA)
				resList.add(chunks[i]);
		}

		result = new WordChunk[resList.size()];
		resList.toArray(result);
		return result;
	}

	protected WordChunk[] filterVarianceWordLength(WordChunk[] chunks) {
		if (chunks.length == 1)
			return chunks;

		WordChunk[] result = null;
		List resList = new ArrayList();

		double nMin = 9999;
		for (int i = 0; i < chunks.length; ++i) {
			int size = chunks[i].offset + chunks[i].word_.length();
			double avgLength = (double) (((double) size) / (double) (chunks[i].nLevel));
			double varLength = computeVarianceWordLength(chunks[i], avgLength);
			if (nMin > varLength)
				nMin = varLength;
		}

		for (int i = 0; i < chunks.length; ++i) {
			int size = chunks[i].offset + chunks[i].word_.length();
			double avgLength = (double) (((double) size) / (double) (chunks[i].nLevel));
			double varLength = computeVarianceWordLength(chunks[i], avgLength);
			if (Math.abs(nMin - varLength) <= DELTA)
				resList.add(chunks[i]);
		}

		result = new WordChunk[resList.size()];
		resList.toArray(result);
		return result;
	}

	protected double computeMorphemicDegreeSum(WordChunk chunk) {
		double morDegreeSum = 0;
		WordChunk node = chunk;
		while (node.nLevel != 0) {
			if (node.word_.length() == 1) {
				morDegreeSum += Math.log(dictionary_
						.getCharacterFreq(node.word_));
			}
			node = node.father_;
		}
		return morDegreeSum;
	}

	protected double computeVarianceWordLength(WordChunk chunk, double avgLength) {
		double varLength = 0;
		WordChunk node = chunk;
		while (node.nLevel != 0) {
			varLength += (node.word_.length() - avgLength)
					* (node.word_.length() - avgLength);
			node = node.father_;
		}
		return varLength;
	}

	protected void helperPrintChunkLeaf(WordChunk chunk) {
		// if (chunk.nLevel == 0) {
		// System.out.print("Chunk: ");
		// return;
		// }
		//
		// helperPrintChunkLeaf(chunk.father_);
		// System.out.print(" " + chunk.word_);
		// if (chunk.children.size() == 0) {
		// System.out.println(" Length: "
		// + new Integer(chunk.offset + chunk.word_.length()));
		// }
	}

	protected void getWordChunk(String content, WordChunk chunk) {		
		if (content.trim().equals(""))
			return;

		if (content.length() == 1) {
			WordChunk newChunk = new WordChunk(chunk);
			newChunk.word_ = content;
			helperPrintChunkLeaf(newChunk);
			chunkLeaves_.add(newChunk);
			return;
		}

		int nBegin = 0;
		int nEnd = nBegin + 1;
		String key = content.substring(nBegin, nBegin + 1);

		// First single character as a new chunk entrance
		WordChunk newChunk = new WordChunk(chunk);
		newChunk.word_ = key;
		if (newChunk.nLevel < 3) {
			if (nEnd >= content.length()) {
				helperPrintChunkLeaf(newChunk);
				chunkLeaves_.add(newChunk);
				return;
			}
			getWordChunk(content.substring(1), newChunk);
		} else {
			helperPrintChunkLeaf(newChunk);
			chunkLeaves_.add(newChunk);
		}

		String candidate = "";
		candidate += content.charAt(nEnd);
		boolean bFound = false;
		while (dictionary_.maybeHaveEntry(key + candidate)) {
			bFound = true;
			newChunk = new WordChunk(chunk);
			newChunk.word_ = key + candidate;
			if (newChunk.nLevel < 3) {
				if (nEnd + 1 >= content.length()) {
					helperPrintChunkLeaf(newChunk);
					chunkLeaves_.add(newChunk);
					return;
				}
				getWordChunk(content.substring(nEnd + 1), newChunk);
			} else {
				helperPrintChunkLeaf(newChunk);
				chunkLeaves_.add(newChunk);
				return;
			}

			candidate += content.charAt(++nEnd);
		}
	}
}
