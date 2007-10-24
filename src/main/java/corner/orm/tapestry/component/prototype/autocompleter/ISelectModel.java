/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: ISelectModel.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-24
 */
package corner.orm.tapestry.component.prototype.autocompleter;

import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.services.DataSqueezer;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.orm.tapestry.state.IContext;

/**
 * 一个选择的过滤器
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface ISelectModel {
	/**
	 * 用于Autocompleter的拼音检索的正则表达式
	 */
	public static final String ABC_STR = "[a-z]";
	/**
	 * 默认的拼音字段名称
	 */
	public static final String ABC_FIELD = "abcCode";
	/**
	 * 用于Autocompleter的中文检索的正则表达式
	 */
	public static final String CHN_STR = "[\u4e00-\u9fa5]";
	/**
	 * 默认的中文字段名称
	 */
	public static final String CHN_FIELD = "chnName";
	/**
	 * 字典表中,各种字典实体的简写码
	 */
	public static final String INDEX_CODE_STR = "[A-Z]";
	public static final String INDEX_CODE_FIELD = "indexCode";
	/**
	 * 用于Autocompleter的数字检索的正则表达式
	 */
	public static final String NUM_STR = "[0-9]";
	/**
	 * 默认的数字字段名称
	 */
	public static final String NUM_FIELD = "numCode";
	/**
	 * 搜索
	 * @param ht Hibernate查询模板实体
	 * @param queryClassName 查询的类名.
	 * @param searchString 搜索的字符串
	 * @param company 当前公司
	 * @return 结果
	 */
	public List search(HibernateTemplate ht,String queryClassName,String searchString,IContext context,DataSqueezer squeezer,String [] dependFieldsValue);
	
	/**
	 * 对结果的某一行进行渲染
	 * @param writer 写入浏览器
	 * @param entity 返回结果集中的某一个元素
	 * @param labelFields 回显的结果
	 */
	public void renderResultRow(IMarkupWriter writer,Object entity,String template,DataSqueezer squeezer);
	
	/**
	 * 保持当前组建
	 */
	public void setComponent(IComponent component);
	public IComponent getComponent();
}
