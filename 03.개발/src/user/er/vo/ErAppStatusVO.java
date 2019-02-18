package user.er.vo;

/**
 * 지원자 상세 정보 창의 동작에 따라 app_status를 변경하기 위한 VO.
 * 
 * @author 재현 : 0214
 */
public class ErAppStatusVO {
	private String app_num, app_status;

	public ErAppStatusVO(String app_num, String app_status) {
		super();
		this.app_num = app_num;
		this.app_status = app_status;
	}// 생성자

	/******** getter 메서드 ********/
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