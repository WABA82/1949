package user.common.vo;

public class SetPassVO {

	private String id, newPass;

	public SetPassVO(String id, String newPass) {
		super();
		this.id = id;
		this.newPass = newPass;
	}

	public String getId() {
		return id;
	}

	public String getNewPass() {
		return newPass;
	}

	@Override
	public String toString() {
		return "SetPassVO [id=" + id + ", newPass=" + newPass + "]";
	}
	
	
}
