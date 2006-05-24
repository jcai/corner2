package corner.orm.hibernate.expression;

/**
 * 用于语法分析器的回调.
 * <p>提供了分析用户输入的查询字符串.譬如: asdf or asdf and fda 等.
 * 
 * @author Jun Tsai
 *
 */
public interface CreateCriterionCallback {
	/**
	 * 创建hibernate的Criterion,根据给定的表达式以及对应的值.
	 * @param expression 表达式符号,譬如: or and 等.
	 * @param value 对应的值.
	 */
	public void doCreateCriterion(String expression,String value);
}
