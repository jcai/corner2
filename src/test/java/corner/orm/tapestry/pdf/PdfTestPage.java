/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PdfTestPage.java 6293 2007-06-06 03:55:25Z ghostbb $
 *	created at:2007-4-11
 */

package corner.orm.tapestry.pdf;

import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Font;

import corner.demo.model.pdf.A;
import corner.orm.tapestry.pdf.components.IPdfTableModel;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfTestPage extends PdfEntityPage {

	public IPdfTableModel<A> getPdfTableModel() {
		return new IPdfTableModel<A>() {
			public float[] getColumPercentWidths() {
				return new float[] { 0.5f, 0.5f };
			}

			public int getColumnCount() {
				return 2;
			}

			public String getCurrentColumnValue(A obj, int seq) {
				switch (seq) {
				case 0:
					return obj.getCnName();

				case 1:
					return obj.getName();
				default:
					return null;
				}

			}

			public String[] getHeaders() {

				return new String[] { "姓", "名" };
			}

			public List<String> getFooters() {
				List<String> cells = new ArrayList<String>();
				cells.add("Ghost1");
				cells.add("Ghost2");
				return cells;
			}

			public Font getFootersFont() {
				return PdfUtils.createHeaderSongLightFont(10);
			}

			public Font getHeadersFont() {
				return PdfUtils.createHeaderSongLightFont(10);
			}

			public Font getContentFont() {
				return PdfUtils.createSongLightFont(10);
			};
		};
	}

	public List<A> getTableSource() {
		List<A> list = new ArrayList<A>();
		for (int i = 0; i < 10; i++) {
			A c = new A();
			c.setCnName(i + " 蔡");
			c.setName(i + " 君");
			list.add(c);
		}
		return list;
	}

	public List<A> getOverflowTableSource() {
		List<A> list = new ArrayList<A>();
		for (int i = 0; i < 100; i++) {
			A c = new A();
			c.setCnName(i + "蔡");
			c.setName(i + "君");
			list.add(c);
		}
		return list;

	}
}
