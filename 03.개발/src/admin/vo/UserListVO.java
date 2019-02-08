package admin.vo;

public class UserListVO {
	
	String id, name, ssn, tel, addr, email, userType, inputDate;

	public UserListVO(String id, String name, String ssn, String tel, String addr, String email, String userType,
			String inputDate) {
		this.id = id;
		this.name = name;
		this.ssn = ssn;
		this.tel = tel;
		this.addr = addr;
		this.email = email;
		this.userType = userType;
		this.inputDate = inputDate;
	}

	public String getId() {
		return id;
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

	public String getAddr() {
		return addr;
	}

	public String getEmail() {
		return email;
	}

	public String getUserType() {
		return userType;
	}

	public String getInputDate() {
		return inputDate;
	}

	@Override
	public String toString() {
		return "UserListVO [id=" + id + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel + ", addr=" + addr
				+ ", email=" + email + ", userType=" + userType + ", inputDate=" + inputDate + "]";
	}
}
