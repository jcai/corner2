package corner.orm.tapestry.component.select;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.form.Hidden;
import org.apache.tapestry.json.JSONArray;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.util.BeanUtils;

/**
 * 抽象的选择过滤器。
 * 提供实现filter的大部分操作.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision:2023 $
 * @since 2.2.1
 */
public abstract class AbstractSelectFilter implements ISelectFilter{
	/** 供选择的模型 **/
	protected IPoSelectorModel model;
	
	/**
	 * @see corner.orm.tapestry.component.select.ISelectFilter#query(java.lang.String)
	 */
	public Map query(final String match, final IPoSelectorModel model) {
		this.model=model;
		List list=((HibernateDaoSupport) model.getEntityService().getObjectRelativeUtils()).getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c=createCriteria(session);//创建 criteria
				appendCriteria(c,match); //提供查询条件
				appendOrder(c); //排序操作.
				c.setMaxResults(20); //设定默认选取记录 TODO 转换为参数
				return c.list();
			}});
		return convertListAsMap(list); //把查询到的list转换为map.
		
	}
	/**
	 * 把查询到的list转换为map.
	 * @param list 查到的实体列表。
	 * @return 字符串hashMap.
	 */
	protected Map convertListAsMap(List list) {
		Map<Object,Object> map=new HashMap<Object,Object>();
		
		String [] returnValueFields=model.getReturnValueFields();
		String [] updateFields=model.getUpdateFields();
		int len=returnValueFields.length;
		
		for(Object obj:list){
			Object label=BeanUtils.getProperty(obj,getLabelField()); //得到label.
			if(label==null){//label为空的时候，不展示该记录.
				continue;
			}
			if(len>1){//为连带多个字段内容.
				if(returnValueFields.length!=updateFields.length){
					throw new RuntimeException("查询的字段和更新的字段的长度不相等! returnValueFields:["+Arrays.asList(returnValueFields)+"] updateFields:["+Arrays.asList(updateFields)+"]");
				}
				JSONArray arr=new JSONArray();
				IComponent nestComp=model.getComponent();
				IPage page=nestComp.getPage();
				Map cs=page.getComponents();
				for(int i=0;i<len;i++){	
					if("this".equals(updateFields[i])){
						arr.put(getReturnObject(returnValueFields[i],obj,true));
						continue;
					}
					
					//根据页面对应的组件的来自动进行更新是否需要序列化.
					
					IComponent c=(IComponent) cs.get(returnValueFields[i]);
					
					if(c!=null&&(c instanceof Hidden||c instanceof Autocompleter)){
						arr.put(getReturnObject(returnValueFields[i],obj,true));
					}else{
						arr.put(getReturnObject(returnValueFields[i],obj,false));
					}
				}
				map.put(label, arr.join(","));
			}
			else if(len==1){//仅仅一个字段
				map.put(label,getReturnObject(returnValueFields[0],obj,true));
			}
		}
		return map;
	}
	/**
	 * 返回label字段名称.
	 * @return label字段名称.
	 */
	public String getLabelField(){
		if(model==null){ //回显的时候
			return null;
		}
		if(this.model.getLabelField()==null){
			throw Tapestry.createRequiredParameterException(model.getComponent(), "labelField");
		}
		return this.model.getLabelField();
	}
	
	/**
	 * 得到返回对象，并把他转换为字符串。
	 * @param pro 属性名称。
	 * @param obj 待操作的对象。
	 * @param isSqueeze  是否需要序列化
	 * @return 字符串。
	 */
	protected Object getReturnObject(String pro, Object obj, boolean isSqueeze) {
		Object value=obj;
		if(!Criteria.ROOT_ALIAS.equals(pro)){ 
			value=BeanUtils.getProperty(obj,pro); //得到属性
		}
		if(isSqueeze){
			return model.getSqueezer().squeeze(value);
		}
		else
			return value==null?"":value;
	
	}
	/**
	 * 应用查询条件.
	 * @param criteria 查询的hibernate的Criteria对象.
	 * @param match 待查询的字符串.
	 */
	protected void appendCriteria(Criteria criteria, String match) {
		if(match!=null&&match.trim().length()>0){
			criteria.add(Restrictions.like(getLabelField(),match.trim()+"%"));
		}
	}
	/**
	 * 应用排序操作。
	 * @param criteria criteria 查询的hibernate的Criteria对象.
	 */
	protected void appendOrder(Criteria criteria) {
	}
	/**
	 * 创建criteria对象。
	 * @param session hibernate的session.
	 * @return criteria对象.
	 */
	protected Criteria createCriteria(Session session) {
		return session.createCriteria(model.getPoClass());
	}

}