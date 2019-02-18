package user.ee.vo;

/**
 * 19.02.11 김건하
 * 
 * @author owner
 *
 */
public class EeRegVO {

	private String eeId, name, gender;
	private int age;

	public EeRegVO(String eeId, String name, String gender, int age) {
		this.eeId=eeId;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}// 생성자

	public String getEeId() {
		return eeId;
	}
	
	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "EeRegVO [eeId=" + eeId + ", name=" + name + ", gender=" + gender + ", age=" + age + "]";
	}


	
}//class
