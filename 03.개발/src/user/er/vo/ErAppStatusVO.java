package user.er.vo;

/**
 * ������ �� ���� â�� ���ۿ� ���� app_status�� �����ϱ� ���� VO.
 * 
 * @author ���� : 0214
 */
public class ErAppStatusVO {
	private String app_num, app_status;

	public ErAppStatusVO(String app_num, String app_status) {
		super();
		this.app_num = app_num;
		this.app_status = app_status;
	}// ������

	/******** getter �޼��� ********/
	public String getApp_num() {
		return app_num;
	}

	public String getApp_status() {
		return app_status;
	}

	@Override
	public String toString() {
		return "ErAppStatusVO [app_num=" + app_num + ", app_status=" + app_status + "]";
	}// toString()

}// class