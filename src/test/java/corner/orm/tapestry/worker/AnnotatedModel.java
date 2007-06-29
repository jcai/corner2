package corner.orm.tapestry.worker;

import org.apache.tapestry.annotations.Component;

import corner.demo.model.AbstractModel;

public class AnnotatedModel extends AbstractModel{

	private String userName;

	/**
	 * @return Returns the userName.
	 */
	@Component(type="TextArea",bindings={"a=b","value=entity.customValue"})
	
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
