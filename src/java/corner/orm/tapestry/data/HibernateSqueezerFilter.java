package corner.orm.tapestry.data;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.tapestry.services.DataSqueezer;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;

public class HibernateSqueezerFilter extends AbstractDataSqueezerFilter {
	// ----------------------------------------------------------------------------------------------------------------------
	// Fields
	// ----------------------------------------------------------------------------------------------------------------------
	private static final String DELIMITER = "::";

	private static final String PREFIX = "HB:";

	private EntityService entityService;



	public String squeeze(final Object object, final DataSqueezer next) {
		if (isPersistent(object)) {
			return (String) ((HibernateObjectRelativeUtils) this.entityService
					.getObjectRelativeUtils()).getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							return PREFIX
									+ getEntityClass(object).getName()
									+ DELIMITER
									+ next.squeeze(session
											.getIdentifier(object));

						}
					});

		} else {
			return next.squeeze(object);
		}
	}

	private boolean isPersistent(final Object entity) {
	return	 ((Boolean) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						final ClassMetadata classMetadata = session
								.getSessionFactory().getClassMetadata(
										getEntityClass(entity));
						return classMetadata != null
								&& classMetadata.getIdentifier(entity,
										EntityMode.POJO) != null;

					}
				})).booleanValue();
	}

	public Class getEntityClass(Object entity) {
		if (entity.getClass().getName().contains("CGLIB")) {
			return entity.getClass().getSuperclass();
		}
		return entity.getClass();
	}



	public Object unsqueeze(String string, DataSqueezer next) {
		if (string.startsWith(PREFIX)) {
			string = string.substring(PREFIX.length());
			String[] split = string.split(DELIMITER);
			String clazzName = split[0];
			Class c;
			try {
				c = Class.forName(clazzName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

			Serializable id = (Serializable) next.unsqueeze(split[1]);

			return this.entityService.loadEntity(c,id);
		} else {
			return next.unsqueeze(string);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------
	// Getter/Setter Methods
	// ----------------------------------------------------------------------------------------------------------------------

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

}
