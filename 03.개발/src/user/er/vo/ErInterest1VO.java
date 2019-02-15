package user.er.vo;

/**
 * 기업사용자에서 관심구직자 윈도우에 목록 띄워주는 VO.
 * 
 * @author 재현.
 *
 */
public class ErInterest1VO {
	/* 인스턴스 변수 */
	private String ee_num, img, name, rank, loc, education, portfolio, gender, input_date;
	private int age;
	public ErInterest1VO(String ee_num, String img, String name, String rank, String loc, String education, int age, String portfolio, String gender, String input_date) {
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
	}// 생성자

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

	public int getAge() {
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
