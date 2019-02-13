package user.er.vo;

public class ErDefaultVO {
	private String erId, img1, name, tel, email, coName;

	public ErDefaultVO(String erId, String img1, String name, String tel, String email, String coName) {
		super();
		this.erId = erId;
		this.img1 = img1;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.coName = coName;
	}

	public String getErId() {
		return erId;
	}

	public String getImg1() {
		return img1;
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

	public String getCoName() {
		return coName;
	}
	
}
