/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-12
 */

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
