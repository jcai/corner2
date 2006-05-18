package corner.orm.hibernate.expression;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import corner.orm.hibernate.expression.grammer.QueryExpressionLexer;
import corner.orm.hibernate.expression.grammer.QueryExpressionParser;

/**
 * 含有表达式的查询解析器.
 * 
 * @author jun
 * 
 */
public class ExpressionExample extends Example {
	static final class NotNullPropertySelector implements PropertySelector {

		/**
		 * 
		 */
		private static final long serialVersionUID = -575328669449999563L;

		public boolean include(Object object, String propertyName, Type type) {
			return object != null;
		}

		private Object readResolve() {
			return ExpressionExample.NOT_NULL;
		}

		NotNullPropertySelector() {
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532008217169072143L;

	private boolean isLikeEnabled;

	private boolean isIgnoreCaseEnabled;

	//private static final String STRING_EXPRESSION_PATTERN = "\\s+(AND|OR)\\s+([\u4e00-\u9fa5\\w]+)";
	//记录拆分之后的值集合.
	private Map<String,Set<String>> values=new HashMap<String,Set<String>>();
	/**
	 * 
	 * @param entity
	 * @param selector
	 */
	protected ExpressionExample(Object entity, PropertySelector selector) {
		super(entity, selector);
	}
	public Example enableLike(MatchMode matchMode) {
		isLikeEnabled = true;
		super.enableLike(matchMode);
		return this;
	}

	public Example enableLike() {
		return enableLike(MatchMode.EXACT);
	}

	public Example ignoreCase() {
		isIgnoreCaseEnabled = true;
		super.ignoreCase();
		return this;
	}

	public static Example create(Object entity) {
		if (entity == null)
			throw new NullPointerException("null example");
		else
			return new ExpressionExample(entity, NOT_NULL);
	}

	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();

	@Override
	protected void appendPropertyCondition(final String propertyName,
			final Object propertyValue, final Criteria criteria,
			final CriteriaQuery cq, final StringBuffer buf)
			throws HibernateException {
		Criterion crit;
		if (propertyValue != null) {

			boolean isString = propertyValue instanceof String;

			if (!isString) {
				crit = Restrictions.eq(propertyName, propertyValue);
				if (isIgnoreCaseEnabled) {
					((SimpleExpression) crit).ignoreCase();
				}
				appendSqlCondition(crit, criteria, cq, buf);
			} else {
				String value = propertyValue.toString();
				final Set<String> set=new HashSet<String>();
				values.put(value,set);
				
				
				final QueryExpressionLexer lexer = new QueryExpressionLexer(
						new StringReader(value));
				final QueryExpressionParser parser = new QueryExpressionParser(
						lexer);
				parser
						.setCreateCriterionCallback(new CreateCriterionCallback() {
							public void doCreateCriterion(String expression,
									String value) {
								value=value.trim();
								set.add(value);
								
								Criterion crit = isLikeEnabled ?  Restrictions
										.like(propertyName, value):Restrictions
										.eq(propertyName, value);
								if (isIgnoreCaseEnabled) {
									((SimpleExpression) crit).ignoreCase();
								}
								appendSqlCondition(crit, criteria, cq, buf,
										expression);

							}
						});
				try {
					if (buf.length() > 1)
						buf.append(" and ");
					buf.append(" (");
					parser.expression();
					buf.append(" ) ");
				} catch (RecognitionException e) {
					throw new HibernateException(e);
				} catch (TokenStreamException e) {
					throw new HibernateException(e);
				}
				
			}

		} else {
			crit = Restrictions.isNull(propertyName);
			appendSqlCondition(crit, criteria, cq, buf);
		}

	}

	private void appendSqlCondition(Criterion crit, Criteria criteria,
			CriteriaQuery cq, StringBuffer buf) {
		appendSqlCondition(crit, criteria, cq, buf, "and");
	}

	private void appendSqlCondition(Criterion crit, Criteria criteria,
			CriteriaQuery cq, StringBuffer buf, String expression) {
		String critCondition = crit.toSqlString(criteria, cq);
		if (buf.length() > 1 && critCondition.trim().length() > 0)
			buf.append(" " + expression + " ");
		buf.append(critCondition);
	}
    protected void addPropertyTypedValue(Object value, Type type, List list)
    {
    	
    	if(type instanceof StringType){
    		Set<String> d=values.get(value);
    		for(Iterator it=d.iterator();it.hasNext();){
    			super.addPropertyTypedValue(it.next(),type,list);
    		}
    	}else{
    		super.addPropertyTypedValue(value,type,list);
    	}
    	
    }
}
