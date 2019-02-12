package admin.vo;

public class UserModifyVO {
	private String id, pass, name, ssn, tel, addrSeq, 
		addrDetail, email, questionType, answer, userType;

	public UserModifyVO(String id, String pass, String name, String ssn, String tel, String addrSeq, String addrDetail,
			String email, String questionType, String answer, String userType) {
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.ssn = ssn;
		this.tel = tel;
		this.addrSeq = addrSeq;
		this.addrDetail = addrDetail;
		this.email = email;
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

	public String getAddrSeq() {
		return addrSeq;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public String getEmail() {
		return email;
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
		return "UserModifyVO [id=" + id + ", pass=" + pass + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel
				+ ", addrSeq=" + addrSeq + ", addrDetail=" + addrDetail + ", email=" + email + ", questionType="
				+ questionType + ", answer=" + answer + ", userType=" + userType + "]";
	}
}
