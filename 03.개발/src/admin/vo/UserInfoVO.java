package admin.vo;

public class UserInfoVO {
	
	String id, pass, name, ssn, tel, addrSeq, zipcode, 
		addr1, addr2, email, questionType, answer, userType, inputDate;

	
	public UserInfoVO(String id, String pass, String name, String ssn, String tel, String addrSeq, String zipcode,
			String addr1, String addr2, String email, String questionType, String answer, String userType,
			String inputDate) {
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.ssn = ssn;
		this.tel = tel;
		this.addrSeq = addrSeq;
		this.zipcode = zipcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.email = email;
		this.questionType = questionType;
		this.answer = answer;
		this.userType = userType;
		this.inputDate = inputDate;
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


	public String getZipcode() {
		return zipcode;
	}


	public String getAddr1() {
		return addr1;
	}


	public String getAddr2() {
		return addr2;
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


	public String getInputDate() {
		return inputDate;
	}

	@Override
	public String toString() {
		return "UserInfoVO [id=" + id + ", pass=" + pass + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel
				+ ", addrSeq=" + addrSeq + ", zipcode=" + zipcode + ", addr1=" + addr1 + ", addr2=" + addr2 + ", email="
				+ email + ", questionType=" + questionType + ", answer=" + answer + ", userType=" + userType
				+ ", inputDate=" + inputDate + "]";
	}
}
