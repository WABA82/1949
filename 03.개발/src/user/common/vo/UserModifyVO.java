package user.common.vo;

public class UserModifyVO {
	private String id, name, pass, tel, seq, addrDetail, email;

	public UserModifyVO(String id, String name, String pass, String tel, String seq, String addrDetail, String email) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
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

	public String getPass() {
		return pass;
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
		return "UserModifyVO [id=" + id + ", name=" + name + ", pass=" + pass + ", tel=" + tel + ", seq=" + seq
				+ ", addrDetail=" + addrDetail + ", email=" + email + "]";
	}
	
	
}
