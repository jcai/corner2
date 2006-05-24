package corner.model;

/**
 * 抽象的代码表。
 * @author Jun Tsai
 * @version $Revison$
 * @since 2006-4-18
 */
public abstract class AbstractMarkCodeModel {
	/**
	 * code，保存在数据库中为数字类型,譬如:2345。
	 * @hibernate.property column="MARK_Code" length="20"
	 * 
	 */
	private String code=null;
	/**
	 * 拼音，供查询使用，保存在数据库中为字母类型，譬如 abcdj。
	 * @hibernate.property column="MARK_PINYIN" length="20"
	 * 
	 */
	private String pinyin;
	/**
	 * 
	 * 项目，供查询使用，保存在数据库中为汉子类型，譬如 我们。
	 * @hibernate.property column="MARK_ITEM" length="20"
	 * 
	 */
	private String item;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	
	
}
