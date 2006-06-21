package corner.test.model;

/**
 * 抽象的model
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 */
public class AbstractModel {
	/**
	 * @hibernate.id generator-class="uuid"
	 *
	 * */
	private String id;
	/**
	 * @hibernate.property
	 */
	private String name;
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
