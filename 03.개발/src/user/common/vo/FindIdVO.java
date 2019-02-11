package user.common.vo;

public class FindIdVO {
	
	private String name, tel;

	public FindIdVO(String name, String tel) {
		this.name = name;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
	}

	@Override
	public String toString() {
		return "FindIdVO [name=" + name + ", tel=" + tel + "]";
	}
	
	

	private String name, tel;

	public FindIdVO(String name, String tel) {
		super();
		this.name = name;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
	}

	@Override
	public String toString() {
		return "FindIdVO [name=" + name + ", tel=" + tel + "]";
	}
	
	
	
}
