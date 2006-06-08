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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;

import corner.orm.lucene.cd.WebLuceneHighlighter;

/**
 * 
 * 提供高亮支持的Extractor.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-10-21
 */
public interface HighlighterHitExtractor {
	/**
	 * 对Lucene的每个Hit进行处理.
	 * @param id hit的ID号.
	 * @param doc Lucene文档.
	 * @param score 得分.
	 * @param highlighter 高亮类.
	 * @param analyzier 分析器.
	 * @return 处理的结果.
	 * @throws IOException 假如发生错误.
	 */

	public abstract Object mapHit(int id, Document doc, float score,
			WebLuceneHighlighter highlighter, Analyzer analyzier)
			throws IOException;

}
