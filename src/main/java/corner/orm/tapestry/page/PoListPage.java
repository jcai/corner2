package corner.orm.tapestry.page;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.hibernate.criterion.DetachedCriteria;

import corner.orm.hibernate.IPersistent;
import corner.orm.hibernate.expression.ExpressionExample;
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.orm.tapestry.table.PersistentBasicTableModel;

/**
 * 抽象的对所有实体的操作。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-23
 */
public abstract class PoListPage extends AbstractEntityListPage<Object> implements IPersistentQueriable  {
	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#appendDetachedCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public void appendDetachedCriteria(DetachedCriteria criteria) {
		if (this.getQueryEntity() != null)
			criteria.add(ExpressionExample.create(getQueryEntity()).enableLike()
					.ignoreCase());
	}

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#createDetachedCriteria()
	 */
	public DetachedCriteria createDetachedCriteria() {

		return DetachedCriteria.forClass(this.getEntity().getClass());
	}
	/**
	 * 得到列表的source
	 * @return table model
	 */
	public  IBasicTableModel getSource(){
		return new PersistentBasicTableModel(this.getEntityService(),this);
	}





}
