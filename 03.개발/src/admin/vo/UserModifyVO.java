package admin.vo;

public class UserModifyVO {
	private String id, pass, name, ssn, gender, tel, addrSeq, 
		addrDetail, email, questionType, answer, userType;
	private int age;
	
	public UserModifyVO(String id, String pass, String name, String ssn, String gender, String tel, String addrSeq,
			String addrDetail, String email, String questionType, String answer, String userType, int age) {
		super();
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.ssn = ssn;
		this.gender = gender;
		this.tel = tel;
		this.addrSeq = addrSeq;
		this.addrDetail = addrDetail;
		this.email = email;
		this.questionType = questionType;
		this.answer = answer;
		this.userType = userType;
		this.age = age;
	} 
	
	@Override
	public String toString() {
		return "UserModifyVO [id=" + id + ", pass=" + pass + ", name=" + name + ", ssn=" + ssn + ", gender=" + gender
				+ ", tel=" + tel + ", addrSeq=" + addrSeq + ", addrDetail=" + addrDetail + ", email=" + email
				+ ", questionType=" + questionType + ", answer=" + answer + ", userType=" + userType + ", age=" + age
				+ "]";
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
	public String getGender() {
		return gender;
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
	public int getAge() {
		return age;
	}
}
