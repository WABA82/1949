package user.common.vo;

public class UserInsertVO {

	private String id, pasas, name, ssn, tel, email, addrSeq, addrDetail, questionType, answer;
	
	
	public UserInsertVO() {
		super();
	}

	public UserInsertVO(String id, String pasas, String name, String ssn, String tel, String email, String addrSeq,
			String addrDetail, String questionType, String answer) {
		super();
		this.id = id;
		this.pasas = pasas;
		this.name = name;
		this.ssn = ssn;
		this.tel = tel;
		this.email = email;
		this.addrSeq = addrSeq;
		this.addrDetail = addrDetail;
		this.questionType = questionType;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public String getPasas() {
		return pasas;
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

	@Override
	public String toString() {
		return "UserInsertVO [id=" + id + ", pasas=" + pasas + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel
				+ ", email=" + email + ", addrSeq=" + addrSeq + ", addrDetail=" + addrDetail + ", questionType="
				+ questionType + ", answer=" + answer + "]";
	}
	
	
	
	
	
}
