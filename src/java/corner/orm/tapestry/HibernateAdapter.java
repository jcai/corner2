package corner.orm.tapestry;

import java.io.Serializable;

import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.util.io.SqueezeAdaptor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.IPersistent;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 对hibernate的类进行序列化.
 * <p>提供对所有的hibernate的bean进行序列化功能.
 * 注意hibernate的model类必须实现 IPersistent接口.
 *
 * @author jun
 * @see IPersistent
 */
public class HibernateAdapter implements SqueezeAdaptor {
	/** 序列化的前缀 **/
	public static final String PREFIX = "C";
	/** 类增强的标记**/
	private static final String CGLIB_FLAG="$$";
	/** 实体服务类. **/
	private EntityService entityService;

	public EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public String getPrefix() {

		return PREFIX;
	}

	public Class getDataClass() {
		return IPersistent.class;
	}

	/**
	 * 得到序列化的字符串.
	 */
	public String squeeze(DataSqueezer squeezer, final Object data) {
		String name = data.getClass().getCanonicalName();
		int pos=name.indexOf(CGLIB_FLAG);
		if(pos>-1){
			name=name.substring(0,pos);
		}

		Serializable id = (Serializable) ((HibernateObjectRelativeUtils) getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						try {
							return session.getIdentifier(data);
						} catch (HibernateException hex) {
//							// ok then the entity was loaded with a different
//							// session
//							// it is too risky to try to attach it, it may
//							// already be loaded
//							try {
//								Object copy = session.merge(data);
//								return session.getIdentifier(copy);
//							} catch (Exception hex1) {
//								return null;
//							}
						}
						return null;
					}
				});

		StringBuffer sb = new StringBuffer();
		sb.append(PREFIX);
		sb.append(name);
		sb.append(":");
		sb.append(squeezer.squeeze(id));
		return sb.toString();
	}

	/**
	 * 反序列化
	 * 如果不能从库中反序列化该对象,则返回一个新建实例.
	 */
	@SuppressWarnings("unchecked")
	public Object unsqueeze(DataSqueezer squeezer, String string) {
		string = string.substring(1);

		int pos = string.indexOf(':');
		String clazzName = string.substring(0, pos);
		String idString = string.substring(pos + 1);
		Serializable id = (Serializable) squeezer.unsqueeze(idString);

		Class c;
		try {
			c = Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try {
			if(id != null){
			Object obj = this.getEntityService().loadEntity(c, id);
				if (obj != null)
					return obj;
			}
		} catch (Throwable t) {
			// Handle error
		}

		return BeanUtils.instantiateClass(c);
	}

}
