/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: CodeModelAutocompleter.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-24
 */
package corner.orm.tapestry.component.prototype.autocompleter;

import org.apache.tapestry.annotations.Parameter;

/**
 * 一个自动完成的组件.
 * 使用到的js：
 *  http://wiki.script.aculo.us/scriptaculous/show/Ajax.Autocompleter
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class CodeModelAutocompleter extends BaseAutocompleter {
	 


    
	/** 选择的过滤器 **/
	@Parameter(defaultValue="ognl:new corner.orm.tapestry.component.prototype.autocompleter.CodeSelectModel()")
	public abstract ISelectModel getSelectModel();
	
	/**
	 * 获得样式模板
	 */
	@Parameter(defaultValue = "literal:<span class=\"selectme\">%1$s</span>/%3$s/%2$s")
	public abstract String getTemplate();
	
	@Parameter(defaultValue="literal:{select:'selectme'}")
	public abstract String getOptions();
	

	@Override
	protected ISelectModel constructSelectModel() {
		return this.getSelectModel();
	}

}
