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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.form.IFormComponent;
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
public abstract class MulitUpload extends BaseComponent implements
		ValidatableField,IFormComponent {

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
		super.render(writer, cycle);
//		writer.beginEmpty("input type=\"button\" onclick=\"add();\" value=\"add file\"");
//		writer.beginEmpty("input type=\"button\" onclick=\"removeAll();\" value=\"delete all\"");
//		writer.beginEmpty("input type=\"hidden\" name=\"filecounter\" id=\"filecounter\" value=\"0\"");
//		writer.beginEmpty("div id=\"files\"");
		Map<String, Object> scriptParms = new HashMap<String, Object>();
		PageRenderSupport pageRenderSupport = TapestryUtils
		.getPageRenderSupport(cycle, this);
		
		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		List<IUploadFile> files = null;
		
		try {
			String fileCounterStr = cycle.getParameter("filecounter");
			int fileCounter = Integer.parseInt(fileCounterStr);
			files = new ArrayList<IUploadFile>();
			for(int i=1;i<=fileCounter;i++){
				String key = "file"+i;
				IUploadFile file = getDecoder().getFileUpload(key);
				if(file != null){
					getValidatableFieldSupport().validate(this, writer, cycle, file);	
					files.add(file);
				}
			}
			this.setValue(files);
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}
	
	@InjectScript("MulitUpload.script")
	public abstract IScript getScript();

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
