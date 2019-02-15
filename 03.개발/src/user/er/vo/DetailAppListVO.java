package user.er.vo;

/**
 * 지원 현황 - 상세 지원 현황 창의 JTable의 한 행을 채우기 위한 VO입니다.
 * 
 * @author 재현
 *
 */
public class DetailAppListVO {

	private String app_num, img, name, rank, loc, education, portfolio, gender, app_date, app_status;
	private int age;

	public DetailAppListVO(String app_num, String img, String name, String rank, String loc, String education,
			String portfolio, String gender, String app_date, String app_status, int age) {
		this.app_num = app_num;
		this.img = img;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.app_date = app_date;
		this.app_status = app_status;
		this.age = age;
	}// 생성자.

	public String getApp_num() {
		return app_num;
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

	public String getPortfolio() {
		return portfolio;
	}

	public String getGender() {
		return gender;
	}

	public String getApp_date() {
		return app_date;
	}

	public String getApp_status() {
		return app_status;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "DetailAppVO [app_num=" + app_num + ", img=" + img + ", name=" + name + ", rank=" + rank + ", loc=" + loc
				+ ", education=" + education + ", portfolio=" + portfolio + ", gender=" + gender + ", app_date="
				+ app_date + ", app_status=" + app_status + ", age=" + age + "]";
	}// toString

}// class
