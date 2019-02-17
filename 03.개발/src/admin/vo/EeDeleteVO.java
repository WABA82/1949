package admin.vo;

public class EeDeleteVO {
	
	String eeNum;
	String ee_id;
	
	public EeDeleteVO(String eeNum, String ee_id) {
		this.eeNum = eeNum;
		this.ee_id = ee_id;
	}

	public String getEeNum() {
		return eeNum;
	}

	public String getEe_id() {
		return ee_id;
	}

	@Override
	public String toString() {
		return "EeDeleteVO [eeNum=" + eeNum + ", ee_id=" + ee_id + "]";
	}
}
