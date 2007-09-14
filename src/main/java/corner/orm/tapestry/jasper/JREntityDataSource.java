/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id: JREntityDataSource.java 3757 2007-09-13 10:13:14Z jcai $
 * created at:2007-9-13
 */

package corner.orm.tapestry.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.tapestry.IBinding;
import org.apache.tapestry.IPage;
import org.apache.tapestry.binding.BindingConstants;
import org.apache.tapestry.binding.BindingSource;

/**
 * 利用ognl来实现的Jasper的datasource.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JREntityDataSource implements JRDataSource{
	private int i=0;
	private BindingSource bindingSource;
	private IPage page;
	public JREntityDataSource(BindingSource source,IPage page){
		this.bindingSource=source;
		this.page = page;
	}
	/**
	 * 
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	public Object getFieldValue(JRField jrField) throws JRException {
		//TODO 
		//1. 采用缓存的方式
		//2. location采用组件的location，这样可以方便提示错误信息.
	  IBinding binding = bindingSource.createBinding(page,jrField.getName(), jrField.getDescription(),BindingConstants.OGNL_PREFIX,page.getLocation());
	  return binding.getObject();
	}

	/**
	 * 
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	public boolean next() throws JRException {
		return i++==0;
	}
}
