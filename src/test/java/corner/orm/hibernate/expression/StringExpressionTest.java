package corner.orm.hibernate.expression;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class StringExpressionTest extends TestCase {
	Criteria criteria;
	CriteriaQuery criteriaQuery;
	private SessionFactoryImplementor factory; 
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		criteria=EasyMock.createMock(Criteria.class);
		criteriaQuery=EasyMock.createMock(CriteriaQuery.class);
		factory=EasyMock.createMock(SessionFactoryImplementor.class);
	}

	

	public void testToSqlString() {
		doTest("column","propertyName","him and me","(   column=? and column=?)","=");
	}
	private void doTest(String column,String propertyName,String expression,String expectSql,String op){
		Type str=Hibernate.STRING;
		 EasyMock.expect(criteriaQuery.getColumnsUsingProjection(criteria,propertyName)).andReturn(new String[]{column}).times(1);
		 EasyMock.expect(criteriaQuery.getTypeUsingProjection(criteria,propertyName)).andReturn(str).times(1);
		 EasyMock.expect(criteriaQuery.getFactory()).andReturn(factory);
		 
		 EasyMock.replay(criteria,criteriaQuery);
		 StringExpression exp=new  StringExpression(propertyName,expression,op);
		 String r=exp.toSqlString(criteria, criteriaQuery);
		 assertEquals(expectSql,r);
		 EasyMock.verify(criteria,criteriaQuery);
		 
	}
	public void testSingleSqlString() {
		doTest("column","propertyName","him","   column like ?"," like ");
	}

	public void testSingleEqualsSqlString() {
		doTest("column","propertyName","him","   column=?","=");
	}

}
