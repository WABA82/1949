package user.er.vo;

/**
 * ���� ��Ȳ - �� ���� ��Ȳ - ������ �� ���� â�� �������� ä�� VO�Դϴ�.
 * 
 * @author ����.
 *
 */
public class DetailAppEeVO {

	/* �ν��Ͻ� ���� */
	private String img, name, tel, email, rank, loc, education, portfolio, gender, ext_resume, app_status;
	private int age;

	public DetailAppEeVO(String img, String name, String tel, String email, String rank, String loc, String education,
			String portfolio, String gender, String ext_resume, String app_status, int age) {
		this.img = img;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.ext_resume = ext_resume;
		this.app_status = app_status;
		this.age = age;
	}// ������

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

	public String getExt_resume() {
		return ext_resume;
	}

	public String getApp_status() {
		return app_status;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "DetailAppEeVO [img=" + img + ", name=" + name + ", tel=" + tel + ", email=" + email + ", rank=" + rank
				+ ", loc=" + loc + ", education=" + education + ", portfolio=" + portfolio + ", gender=" + gender
				+ ", ext_resume=" + ext_resume + ", app_status=" + app_status + ", age=" + age + "]";
	}// toString()

}// class
