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

package corner.orm.tapestry.pdf.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.hivemind.util.Defense;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

/**
 * 一个属于当前线程的PdfField的创建器.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class FieldCreator implements IFieldCreator {

	public static final String FIELD_NAME_PREFIX="FIELD";
	private int i=0;
	private List<String> alreadyExistFields=new ArrayList<String>();
	
	/**
	 * @see corner.orm.tapestry.pdf.service.IFieldCreator#createTextField(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Rectangle, java.lang.String)
	 */
	public TextField createTextField(PdfWriter writer,Rectangle rec,String name){
		Defense.notNull(name, "field name");
		if(alreadyExistFields.contains(name)){ //已经有了
			name=createUniqueName();
		}
		TextField textField=new TextField(writer,rec,name);
		textField.setOptions(TextField.READ_ONLY); //设定为只读
		alreadyExistFields.add(name);
		return textField;
	}
	/**
	 * @see corner.orm.tapestry.pdf.service.IFieldCreator#createTextField(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Rectangle)
	 */
	public TextField createTextField(PdfWriter writer,Rectangle rec){
		return createTextField(writer,rec,createUniqueName());
	}
	String createUniqueName(){
		i+=1;
		return FIELD_NAME_PREFIX+i;
	}
	
}
