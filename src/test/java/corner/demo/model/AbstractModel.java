package corner.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import corner.model.AbstractPersistDomain;

/**
 * 抽象的model
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 */
@MappedSuperclass
public class AbstractModel extends AbstractPersistDomain implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8461175277991575498L;
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
	 * 中文名称
	 * <p>用于中文检索时使用</p>
	 * @hibernate.property
	 */
	private String cnName;
	
	/**
	 * @return Returns the cnName.
	 */
	public String getCnName() {
		return cnName;
	}
	/**
	 * @param cnName The cnName to set.
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	/**
	 * @return Returns the id.
	 */
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(columnDefinition="char(32)")
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
