//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.highlight.QueryTermExtractor;
import org.apache.lucene.search.highlight.WeightedTerm;
import org.springmodules.lucene.search.core.QueryCreator;
import org.springmodules.lucene.search.core.SearcherCallback;
import org.springmodules.lucene.search.support.LuceneSearchSupport;

import corner.orm.lucene.cd.WebLuceneHighlighter;

/**
 * 对基于Lucene的Search的支持.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-10-19
 */
public class SearchAccessor extends LuceneSearchSupport {
	
	/**
	 * 对索引库进行搜索.
	 * @param queryCreator Query创建器.
	 * @param extractor hit的Extractor
	 * @param start 开是位置.
	 * @param offset 偏移量
	 * @return 搜索结果列表.
	 */
	public List search(final QueryCreator queryCreator,
			final HighlighterHitExtractor extractor, final int start,
			final int offset) {
		return (List) this.getTemplate().search(new SearcherCallback() {
			public Object doWithSearcher(Searcher searcher) throws IOException,
					ParseException {
				Query query = queryCreator.createQuery(getAnalyzer());
				WeightedTerm[] wts=QueryTermExtractor.getTerms(query);
				List<String> tokens= new ArrayList<String>();
				for(WeightedTerm t : wts) {
					tokens.add(t.getTerm());
				}
				WebLuceneHighlighter highlighter=new WebLuceneHighlighter((ArrayList) tokens);
				
				Hits hits = searcher.search(query);
				return extractHits(hits, extractor, start, offset, highlighter);
			}
		});
	}

	private List extractHits(Hits hits, HighlighterHitExtractor extractor,
			int start, int offSize, WebLuceneHighlighter h) throws IOException {
		System.out.println("search result [" + hits.length() + "] start ["
				+ start + "] offset [" + offSize + "]");
		List list = new ArrayList();
		int length = hits.length();
		list.add(length);
		if (start > length) {
			start = length - offSize;
		}
		if (start < 0) {
			start = 0;
		}
		for (int i = start; i < start + offSize && i < length; i++) {
			// System.out.println("=================start======================");
			list.add(extractor.mapHit(i, hits.doc(i), hits.score(i), h, this
					.getAnalyzer()));
			// System.out.println("=================end======================");

		}
		return list;
	}

}
