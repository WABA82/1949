package user.common.vo;

public class UserModifyWithoutPassVO {


	private String id, name, tel, seq, addrDetail, email;

	public UserModifyWithoutPassVO(String id, String name, String tel, String seq, String addrDetail, String email) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.seq = seq;
		this.addrDetail = addrDetail;
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

	public String getAddrDetail() {
		return addrDetail;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "UserModifyVO2 [id=" + id + ", name=" + name + ", tel=" + tel + ", seq=" + seq + ", addrDetail="
				+ addrDetail + ", email=" + email + "]";
	}
	
	
}

