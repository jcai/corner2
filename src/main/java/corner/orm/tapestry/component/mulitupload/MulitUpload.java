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
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.multipart.MultipartDecoder;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class MulitUpload extends BaseComponent implements
		ValidatableField, IFormComponent {
	
	/**
	 * 取得页面中自动增加的文件上传字段的基础名称,默认为'file'
	 * example: test
	 * 自动生成的上传自动名称为: test1,test2....testN
	 * @return String 上传文件自动的基础名称
	 */
	@Parameter(defaultValue="literal:file")
	public abstract String getUploadFieldBaseName();

	/**
	 * html模版中保存上传文件个数的字段,该字段用于保存自动生成的文件的个数，同时用于组合成上传文件的名称
	 */
	private static final String FILE_COUNT_STR = "filecounter";
	
	/**
	 * 有文件上传时request中的标识
	 */
	private static final String MULITPART_UPLOAD_REQUEST_STR = "multipart/form-data";

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		/**
		 * 处理form
		 */
		IForm form = TapestryUtils.getForm(cycle, this);
		form.setEncodingType(MULITPART_UPLOAD_REQUEST_STR);
		setForm(form);

		if (form.wasPrerendered(writer, this))
			return;

		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);

		setName(form);

		if (form.isRewinding()) {
			if (!isDisabled()) {
				rewindFormComponent(writer, cycle);
			}

			// This is for the benefit of the couple of components (LinkSubmit)
			// that allow a body.
			// The body should render when the component rewinds.

			if (getRenderBodyOnRewind())
				renderBody(writer, cycle);
		}
		if (!cycle.isRewinding()) {
			super.renderComponent(writer, cycle);

			/**
			 * inject JS here
			 */
			PageRenderSupport pageRenderSupport = TapestryUtils
					.getPageRenderSupport(cycle, this);

			Map<String, Object> scriptParms = new HashMap<String, Object>();

			getScript().execute(this, cycle, pageRenderSupport, scriptParms);

		}
	}

	/**
	 * 设置全部的文件
	 * 
	 * @param files
	 */
	public abstract void setValue(List<IUploadFile> files);

//	/**
//	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
//	 *      org.apache.tapestry.IRequestCycle)
//	 */
//	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
//		// Force the form to use the correct encoding type for file uploads.
//		IForm form = getForm();
//
//
//		super.render(writer, cycle);
//		// writer.beginEmpty("input type=\"button\" onclick=\"add();\"
//		// value=\"add file\"");
//		// writer.beginEmpty("input type=\"button\" onclick=\"removeAll();\"
//		// value=\"delete all\"");
//		// writer.beginEmpty("input type=\"hidden\" name=\"filecounter\"
//		// id=\"filecounter\" value=\"0\"");
//		// writer.beginEmpty("div id=\"files\"");
//		Map<String, Object> scriptParms = new HashMap<String, Object>();
//		PageRenderSupport pageRenderSupport = TapestryUtils
//				.getPageRenderSupport(cycle, this);
//
//		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
//	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {

		List<IUploadFile> files = null;

		try {
			String fileCounterStr = cycle.getParameter(FILE_COUNT_STR);
			int fileCounter = Integer.parseInt(fileCounterStr);
			files = new ArrayList<IUploadFile>();
			for (int i = 1; i <= fileCounter; i++) {
				String key = getUploadFieldBaseName() + i;
				IUploadFile file = getDecoder().getFileUpload(key);
				if (file != null) {
					getValidatableFieldSupport().validate(this, writer, cycle,file);
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

	public abstract IForm getForm();

	public abstract void setForm(IForm form);

	protected void setName(IForm form) {
		setName(form.getElementId(this));
	}

	protected boolean getRenderBodyOnRewind() {
		return false;
	}
}
