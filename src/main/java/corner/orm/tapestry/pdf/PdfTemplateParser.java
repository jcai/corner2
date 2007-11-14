// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;

/**
 * 一个模板分析器
 * 
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfTemplateParser {

	private PdfReader reader;

	private String pageClass;

	public final static String  PAGE_CLASS_DEFINE_FIELD_NAME="page-class";
	/**
	 * 通过给定的InputStream来构造一个解析器
	 * 
	 * @param templateStream
	 */
	public PdfTemplateParser(InputStream templateStream) {
		try {
			reader = new PdfReader(templateStream);
			parse();
		} catch (IOException e) {
			throw new PdfSystemException(e);
		}
	}

	/**
	 * 通过给定的字节数组来构造一个解析器。
	 * 
	 * @param bs
	 *            字节数组
	 */
	public PdfTemplateParser(byte[] bs) {
		try {
			reader = new PdfReader(bs);
			parse();
		} catch (IOException e) {
			throw new PdfSystemException(e);
		}
	}

	public PdfTemplateParser(URL resourceURL) {
		try {
			reader = new PdfReader(resourceURL);
			parse();
		} catch (IOException e) {
			throw new PdfSystemException(e);
		}
	}

	public String getPageClazzName(){
		return this.pageClass;
	}
	/**
	 * 通过给定的reader来分析pdf文件.
	 * 
	 * @param reader
	 *            pdf reader
	 */
	void parse() {
		AcroFields form = reader.getAcroFields();
		HashMap fields = form.getFields();
		for (Iterator i = fields.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			switch (form.getFieldType(key)) {
			case AcroFields.FIELD_TYPE_TEXT: // 仅仅对TextField字段进行处理
				if(PAGE_CLASS_DEFINE_FIELD_NAME.equals(key)){
					this.pageClass=form.getField(key);
					break;
				}
				float[] p = form.getFieldPositions(key);

				int step=5;
				int num = p.length / step;
				for (int j = 0; j < num; j++) { //考虑到重名的情况
					PdfBlock block = new PdfBlock();// createBlockByName(key);

					if (block == null) {
						break;
					}

					block.setPosition(new float [] {p[j*step],p[j*step+1],p[j*step+2],p[j*step+3],p[j*step+4]});

					block.setName(key);

					block.setValue(form.getField(key));

					int pageNum = block.getPageNum();

					List<PdfBlock> pagefs = pageFields.get(pageNum);
					if (pagefs == null) {
						pagefs = new ArrayList<PdfBlock>();
						pageFields.put(pageNum, pagefs);
					}
					pagefs.add(block);

				}

				break;
			default:
				break;
			}
		}

	}

	/**
	 * 得到pdf文档中所有的定义字段.
	 * 
	 * @return
	 */
	public Map<Integer, List<PdfBlock>> getPageFields() {
		return this.pageFields;
	}

	private Map<Integer, List<PdfBlock>> pageFields = new HashMap<Integer, List<PdfBlock>>();

	/**
	 * 得到读模板文件的PdfReader
	 * 
	 * @return PdfReader
	 */
	public PdfReader getPdfReader() {
		return reader;
	}

	/**
	 * 得到页数。
	 * 
	 * @return 得到模板的总页数.
	 */
	public int getNumberOfPages() {
		return reader.getNumberOfPages();
	}

}
