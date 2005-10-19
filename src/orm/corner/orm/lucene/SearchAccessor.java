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
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Searcher;
import org.springmodules.lucene.search.core.HitExtractor;
import org.springmodules.lucene.search.core.QueryCreator;
import org.springmodules.lucene.search.core.SearcherCallback;
import org.springmodules.lucene.search.factory.SearcherFactoryUtils;
import org.springmodules.lucene.search.support.LuceneSearchSupport;

public class SearchAccessor extends LuceneSearchSupport{
	public List search(QueryCreator queryCreator,HitExtractor extractor,int start,int offSize){
		this.getTemplate().search(new SearcherCallback(){

			public Object doWithSearcher(Searcher searcher) throws IOException, ParseException {
				return null;
			}});
		
		
		return null;
	}
}
