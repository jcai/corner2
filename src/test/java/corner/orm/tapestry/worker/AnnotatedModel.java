package corner.orm.tapestry.worker;

import corner.demo.model.AbstractModel;

public class AnnotatedModel extends AbstractModel{

	private String userName;

	/**
	 * @return Returns the userName.
	 */
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
