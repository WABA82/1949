package user.common.vo;

public class UserInfoVO {

	private String id, name, tel, seq, zipcode, addr1, addr2, email;

	public UserInfoVO(String id, String name, String tel, String seq, String zipcode, String addr1, String addr2,
			String email) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.seq = seq;
		this.zipcode = zipcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
	}

	public String getSeq() {
		return seq;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "UserInfoVO [id=" + id + ", name=" + name + ", tel=" + tel + ", seq=" + seq + ", zipcode=" + zipcode
				+ ", addr1=" + addr1 + ", addr2=" + addr2 + ", email=" + email + "]";
	}
	
	
}
