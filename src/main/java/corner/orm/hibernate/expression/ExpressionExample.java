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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532008217169072143L;

	private boolean isLikeEnabled;

	private boolean isIgnoreCaseEnabled;

	private boolean wave;

	// private static final String STRING_EXPRESSION_PATTERN =
	// "\\s+(AND|OR)\\s+([\u4e00-\u9fa5\\w]+)";

	// 记录拆分之后的值集合.
	private Map<String, Set<String>> values = new HashMap<String, Set<String>>();

	/**
	 * 记录拆分之后的表达式集合.
	 */
	private Map<String, Set<String>> expressions = new HashMap<String, Set<String>>();

	/**
	 * 构造一个表达式的example.
	 * 
	 * @param entity
	 * @param selector
	 */
	protected ExpressionExample(Object entity, PropertySelector selector) {
		super(entity, selector);
	}

	/**
	 * 
	 * @see org.hibernate.criterion.Example#enableLike(org.hibernate.criterion.MatchMode)
	 */
	public Example enableLike(MatchMode matchMode) {
		isLikeEnabled = true;
		super.enableLike(matchMode);
		return this;
	}

	/**
	 * 
	 * @see org.hibernate.criterion.Example#enableLike()
	 */
	public Example enableLike() {
		return enableLike(MatchMode.EXACT);
	}

	/**
	 * 
	 * @see org.hibernate.criterion.Example#ignoreCase()
	 */
	public Example ignoreCase() {
		isIgnoreCaseEnabled = true;
		super.ignoreCase();
		return this;
	}

	/**
	 * 创建一个含有表达式的example
	 * 
	 * @param entity
	 *            待处理的实体.
	 * @return 本实例example
	 */
	public static Example create(Object entity) {
		if (entity == null)
			throw new NullPointerException("null example");
		else
			return new ExpressionExample(entity, NOT_NULL);
	}

	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();

	/**
	 * 
	 * @see org.hibernate.criterion.Example#appendPropertyCondition(java.lang.String,
	 *      java.lang.Object, org.hibernate.Criteria,
	 *      org.hibernate.criterion.CriteriaQuery, java.lang.StringBuffer)
	 */
	@Override
	protected void appendPropertyCondition(final String propertyName,
			final Object propertyValue, final Criteria criteria,
			final CriteriaQuery cq, final StringBuffer buf)
			throws HibernateException {
		Criterion crit;
		if (propertyValue != null) {
			boolean isString = propertyValue instanceof String;
			if (!isString) { // 不是字符串类型.
				crit = Restrictions.eq(propertyName, propertyValue); // 一个等于操作.
				if (isIgnoreCaseEnabled) {
					((SimpleExpression) crit).ignoreCase();
				}
				appendSqlCondition(crit, criteria, cq, buf);
			} else {// 字符串类型.
				String value = propertyValue.toString(); // 得到属性的名称.

				final Set<String> set = new HashSet<String>();// 设定属性所对应的所有值.
				values.put(value, set);

				final Set<String> exps = new HashSet<String>();// 设定所有的表达式.
				expressions.put(value, exps);

				// 采用语法分析器对其进行分析.
				final QueryExpressionLexer lexer = new QueryExpressionLexer(
						new StringReader(value));
				final QueryExpressionParser parser = new QueryExpressionParser(
						lexer);
				parser
						.setCreateCriterionCallback(new CreateCriterionCallback() {
							/**
							 * 
							 * @see corner.orm.hibernate.expression.CreateCriterionCallback#doCreateCriterion(java.lang.String,
							 *      java.lang.String)
							 */
							public void doCreateCriterion(String expression,
									String value) {
//								记录值和表达式
								value = value.trim();
								set.add(value);
								exps.add(expression);
								if ("~".equalsIgnoreCase(expression)) {
									wave = true;
								} 
								
							}
						});
				try {
					if (buf.length() > 1)
						buf.append(" and ");
					buf.append(" (");
					parser.expression();

					if (wave && set.size() == 2) { //对波浪号进行处理
						Iterator it = set.iterator();
						// >
						crit = Restrictions.gt(propertyName, it.next());
						appendSqlCondition(crit, criteria, cq, buf,"");
						
						//<
						crit = Restrictions.lt(propertyName, it.next());
						appendSqlCondition(crit, criteria, cq, buf);
						
						
						
					} else {
						for (String exp : exps) {
							
								crit = isLikeEnabled ? Restrictions.like(
										propertyName, value) : Restrictions.eq(
										propertyName, value);
								if (isIgnoreCaseEnabled) {
									((SimpleExpression) crit).ignoreCase();
								}
								appendSqlCondition(crit, criteria, cq, buf, exp);
							
	
						}
					}

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
		//还原
		wave=false;

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

	protected void addPropertyTypedValue(Object value, Type type, List list) {

		if (type instanceof StringType) {
			Set<String> d = values.get(value);
			if (d == null) {
				return;
			}
			for (Iterator it = d.iterator(); it.hasNext();) {
				super.addPropertyTypedValue(it.next(), type, list);
			}
		} else {
			super.addPropertyTypedValue(value, type, list);
		}

	}

	/**
	 * 不为空的一个属性选择类.
	 * 
	 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
	 * @version $Revision$
	 * @since 2.1
	 */
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

}
