//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.mulitupload;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.AbstractFormComponent;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.multipart.MultipartDecoder;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class MulitUpload extends AbstractFormComponent implements
		ValidatableField {

	/**
	 * 设置全部的文件
	 * 
	 * @param files
	 */
	public abstract void setValue(List<IUploadFile> files);

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		// Force the form to use the correct encoding type for file uploads.
		IForm form = getForm();

		form.setEncodingType("multipart/form-data");

		writer.beginEmpty("input type=\"file\" name=\"file1\" id=\"file1\"");
		writer.beginEmpty("input type=\"file\" name=\"file2\" id=\"file2\"");
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		List<IUploadFile> files = null;
		
		try {
			cycle.getParameter("tes");
			IUploadFile file1 = getDecoder().getFileUpload("file1");
            getValidatableFieldSupport().validate(this, writer, cycle, file1);
			IUploadFile file2 = getDecoder().getFileUpload("file2");
            getValidatableFieldSupport().validate(this, writer, cycle, file2);
			files = new ArrayList<IUploadFile>();
			files.add(file1);
			files.add(file2);
			
			this.setValue(files);
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}

	/**
	 * Injected.
	 */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

	/**
	 * Injected.
	 */
	public abstract MultipartDecoder getDecoder();

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#isRequired()
	 */
	public boolean isRequired() {
		return getValidatableFieldSupport().isRequired(this);
	}
}
