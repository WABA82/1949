package user.er.vo;

/**
 * �������ڿ��� ���ɱ����� �����쿡 ��� ����ִ� VO.
 * 
 * @author ����.
 *
 */
public class ErInterestVO {
	/* �ν��Ͻ� ���� */
	String ee_num, img, name, rank, loc, education, age, portfolio, gender, input_date;

	public ErInterestVO(String ee_num, String img, String name, String rank, String loc, String education, String age,
			String portfolio, String gender, String input_date) {
		this.ee_num = ee_num;
		this.img = img;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.age = age;
		this.portfolio = portfolio;
		this.gender = gender;
		this.input_date = input_date;
	}// ������

	public String getEe_num() {
		return ee_num;
	}

	public String getImg() {
		return img;
	}

	public String getName() {
		return name;
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

	public String getAge() {
		return age;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public String getGender() {
		return gender;
	}

	public String getInput_date() {
		return input_date;
	}

	@Override
	public String toString() {
		return "ErInterestVO [ee_num=" + ee_num + ", img=" + img + ", name=" + name + ", rank=" + rank + ", loc=" + loc
				+ ", education=" + education + ", age=" + age + ", portfolio=" + portfolio + ", gender=" + gender
				+ ", input_date=" + input_date + "]";
	}// toString
	
}// class
