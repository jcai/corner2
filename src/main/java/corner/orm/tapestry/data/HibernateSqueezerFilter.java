package corner.orm.tapestry.data;

import java.io.Serializable;

import org.apache.tapestry.services.DataSqueezer;

import corner.service.EntityService;

/**
 * 抽象的针对hiberante的序列化过滤器。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public class HibernateSqueezerFilter extends AbstractDataSqueezerFilter {
	// ----------------------------------------------------------------------------------------------------------------------
	// Fields
	// ----------------------------------------------------------------------------------------------------------------------
	private static final String DELIMITER = "::";

	private static final String PREFIX = "HB:";

	private EntityService entityService;


	public String squeeze(final Object object, final DataSqueezer next) {
		if (this.entityService.isPersistent(object)) {
			return PREFIX + EntityService.getEntityClass(object).getName()
					+ DELIMITER
					+ next.squeeze(entityService.getIdentifier(object));

		} else {
			return next.squeeze(object);
		}
	}

	@SuppressWarnings("unchecked")
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

			return this.entityService.loadEntity(c, id);
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
