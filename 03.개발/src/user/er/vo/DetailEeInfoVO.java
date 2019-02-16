package user.er.vo;

public class DetailEeInfoVO {
	// "��ȣ","�⺻������ȣ","�̹���","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","�����"
	private String eeNum, img, name, tel, email, rank, loc, education, portfolio, gender, extResume, interest;
	private int age;

	public DetailEeInfoVO(String eeNum, String img, String name, String tel, String email, String rank, String loc,
			String education, String portfolio, String gender, String extResume, String interest, int age) {
		super();
		this.eeNum = eeNum;
		this.img = img;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.extResume = extResume;
		this.interest = interest;
		this.age = age;
	}// ������.

	public String getEeNum() {
		return eeNum;
	}

	public String getImg() {
		return img;
	}

	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getRank() {
		return rank;
	}

	public String getLoc() {
		return loc;
	}

	public String getEducation() {
		return education;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public String getGender() {
		return gender;
	}

	public String getExtResume() {
		return extResume;
	}

	public String getInterest() {
		return interest;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "DetailEeInfoVO [eeNum=" + eeNum + ", img=" + img + ", name=" + name + ", tel=" + tel + ", email="
				+ email + ", rank=" + rank + ", loc=" + loc + ", education=" + education + ", portfolio=" + portfolio
				+ ", gender=" + gender + ", extResume=" + extResume + ", interest=" + interest + ", age=" + age + "]";
	}

}// Ŭ����
