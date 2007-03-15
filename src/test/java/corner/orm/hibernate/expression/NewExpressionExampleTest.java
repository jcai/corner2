package corner.orm.hibernate.expression;

import java.util.Date;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Example;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.TypedValue;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.Type;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.orm.hibernate.expression.annotations.QueryDefinition;
import corner.orm.hibernate.expression.annotations.QueryDefinition.QueryField;
@Test

public class NewExpressionExampleTest extends TestCase {
	Criteria criteria;
	CriteriaQuery criteriaQuery;
	private SessionFactoryImplementor factory;
	private EntityPersister persister; 
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@BeforeMethod
	protected void setUp() throws Exception {
		super.setUp();
		criteria=EasyMock.createMock(Criteria.class);
		criteriaQuery=EasyMock.createMock(CriteriaQuery.class);
		factory=EasyMock.createMock(SessionFactoryImplementor.class);
		persister=EasyMock.createMock(EntityPersister.class);
		
	}
	public void testSql(){
		 A a=new A();
		 String[] pros={"userName","password"};
		 String [] columns={"user_name","password"};
		 String [] proValues={"acai and other","pasdf"};
		 
		 String expectSql="((   user_name=? and user_name=?) and password=?)";
		 String [] expectParameterValues={"acai","other","pasdf"};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	public void testSqltrim(){
		 A a=new A();
		 String[] pros={"userName","password"};
		 String [] columns={"user_name","password"};
		 String [] proValues={"acai and other   ","pasdf"};
		 
		 String expectSql="((   user_name=? and user_name=?) and password=?)";
		 String [] expectParameterValues={"acai","other","pasdf"};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	public void testManySql(){
		 A a=new A();
		 String[] pros={"userName","password"};
		 String [] columns={"user_name","password"};
		 String [] proValues={"acai and other or she","20050607~20090805"};
		 
		 String expectSql="((   user_name=? and user_name=? or user_name=?) and (password>? and password<?))";
		 String [] expectParameterValues={"acai","other","she","20050607","20090805"};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	public void testEmptySql(){
		 A a=new A();
		 String[] pros={"userName","password"};
		 String [] columns={"user_name","password"};
		 String [] proValues={null,null};
		 
		 String expectSql="(1=1)";
		 String [] expectParameterValues={};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	
	public void testMultiSql(){
		 A a=new A();
		 String[] pros={"userName","password"};
		 String [] columns={"user_name","password"};
		 String [] proValues={"acai","20050607~20090805"};
		 
		 String expectSql="(   user_name=? and (password>? and password<?))";
		 String [] expectParameterValues={"acai","20050607","20090805"};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	public void testDateQuery(){
		 A a=new A();
		 String[] pros={"userName","testp"};
		 String [] columns={"user_name","testp"};
		 Date v=new Date();
		 Object [] proValues={"acai",v};
		 
		 String expectSql="(   user_name=? and testp=?)";
		 Object [] expectParameterValues={"acai",v};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	public void testMultipositionSql(){
		 A a=new A();
		 String[] pros={"password","userName"};
		 String [] columns={"password","user_name"};
		 String [] proValues={"20050607~20090805","acai"};
		 
		 String expectSql="((password>? and password<?) and    user_name=?)";
		 String [] expectParameterValues={"20050607","20090805","acai"};
		 doTest(a,pros,columns,proValues,expectSql,expectParameterValues);
	}
	private void doTest(Object obj,String[]pros,String[]columns,Object[]proValues,String expectSql,Object[]expectParameterValues) {
		Type[] types=new Type[pros.length];
		for(int i=0;i<types.length;i++){
			 types[i]=Hibernate.STRING;
		 }
		
		 String entityName="entityName";
		 for(int i=0;i<pros.length;i++){
			 EasyMock.expect(criteriaQuery.getColumnsUsingProjection(criteria,pros[i])).andReturn(new String[]{columns[i]}).anyTimes();
			 EasyMock.expect(criteriaQuery.getTypeUsingProjection(criteria,pros[i])).andReturn(types[i]).anyTimes();
		 }
		 
		 
		 EasyMock.expect(criteriaQuery.getEntityName(criteria)).andReturn(entityName).anyTimes();;
		 EasyMock.expect(criteriaQuery.getFactory()).andReturn(factory).anyTimes();
		 EasyMock.expect(factory.getEntityPersister(entityName)).andReturn(persister).anyTimes();;
		 EasyMock.expect(persister.getPropertyNames()).andReturn(pros).anyTimes();
		 EasyMock.expect(persister.getPropertyTypes()).andReturn(types).anyTimes();
		 EasyMock.expect(persister.guessEntityMode(obj)).andReturn(EntityMode.POJO).anyTimes();
		 EasyMock.expect(persister.getPropertyValues(obj, EntityMode.POJO)).andReturn(proValues).anyTimes();
		 EasyMock.expect(persister.getVersionProperty()).andReturn(new Integer(-1)).anyTimes();
		 
		 EasyMock.replay(criteria,criteriaQuery,factory,persister);
		 
		 
		
		Example example=NewExpressionExample.create(obj);
		
		assertEquals(expectSql,example.toSqlString(criteria, criteriaQuery));
		TypedValue[] values = example.getTypedValues(criteria, criteriaQuery);
		assertEquals(expectParameterValues.length,values.length);
		for(int i=0;i<expectParameterValues.length;i++){
			assertEquals(expectParameterValues[i],values[i].getValue());
			
		}
		EasyMock.verify(criteria,criteriaQuery,factory,persister);
	}
	
	@QueryDefinition({@QueryField(propertyName="userName"),@QueryField(propertyName="password",queryType=QueryDefinition.QueryType.Date)})
	class A{

	}
}
