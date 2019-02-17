package user.ee.vo;

/**
 * ȸ�� �� ���� â�� �������� �����ϱ� ���� VO�Դϴ�.
 * 
 * @author ����
 *
 */
public class CoDetailVO {
	private String co_name, est_date, img1, img2, img3, img4, co_desc;
	private int member_num;

	public CoDetailVO(String co_name, String est_date, String img1, String img2, String img3, String img4,
			String co_desc, int member_num) {
		super();
		this.co_name = co_name;
		this.est_date = est_date;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.co_desc = co_desc;
		this.member_num = member_num;
	}// ������

	/**** getter�޼��� ****/

	public String getCo_name() {
		return co_name;
	}

	public String getEst_date() {
		return est_date;
	}

	public String getImg1() {
		return img1;
	}

	public String getImg2() {
		return img2;
	}

	public String getImg3() {
		return img3;
	}

	public String getImg4() {
		return img4;
	}

	public String getCo_desc() {
		return co_desc;
	}

	public int getMember_num() {
		return member_num;
	}

	@Override
	public String toString() {
		return "CoDetailVO [co_name=" + co_name + ", est_date=" + est_date + ", img1=" + img1 + ", img2=" + img2
				+ ", img3=" + img3 + ", img4=" + img4 + ", co_desc=" + co_desc + ", member_num=" + member_num + "]";
	}// toString()

}// class
