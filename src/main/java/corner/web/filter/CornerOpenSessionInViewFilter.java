package corner.web.filter;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

import corner.orm.spring.SpringContainer;

public class CornerOpenSessionInViewFilter extends OpenSessionInViewFilter {
	@Override
	protected SessionFactory lookupSessionFactory() {

		return (SessionFactory) SpringContainer.getInstance().getApplicationContext().getBean("sessionFactory");

}
