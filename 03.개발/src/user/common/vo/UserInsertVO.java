package user.common.vo;

public class UserInsertVO {

	private String id, pass, name, ssn, tel, email, addrSeq, addrDetail, questionType, answer, userType;
	
	
	public UserInsertVO() {
		super();
	}

	public UserInsertVO(String id, String pass, String name, String ssn, String tel, String email, String addrSeq,
			String addrDetail, String questionType, String answer, String userType) {
		super();
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.ssn = ssn;
		this.tel = tel;
		this.email = email;
		this.addrSeq = addrSeq;
		this.addrDetail = addrDetail;
		this.questionType = questionType;
		this.answer = answer;
		this.userType = userType;

	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public String getName() {
		return name;
	}

	public String getSsn() {
		return ssn;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getAddrSeq() {
		return addrSeq;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public String getQuestionType() {
		return questionType;
	}

	public String getAnswer() {
		return answer;
	}
	
	public String getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "UserInsertVO [id=" + id + ", pass=" + pass + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel
				+ ", email=" + email + ", addrSeq=" + addrSeq + ", addrDetail=" + addrDetail + ", questionType="
				+ questionType + ", answer=" + answer + ", userType=" + userType + "]";
	}



	
}
