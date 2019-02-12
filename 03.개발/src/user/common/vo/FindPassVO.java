package user.common.vo;

public class FindPassVO {
	private String id, qType, answer;

	public FindPassVO(String id, String qType, String answer) {
		super();
		this.id = id;
		this.qType = qType;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public String getqType() {
		return qType;
	}

	public String getAnswer() {
		return answer;
	}
	
	
}
