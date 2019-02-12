package user.ee.vo;

public class EeAppVO {
	private String app_num, er_num, subject, co_name, rank, loc, education, hire_type, app_date, app_status;
	private int sal;

	public EeAppVO(String app_num, String er_num, String subject, String co_name, String rank, String loc,
			String education, String hire_type, String app_date, String app_status, int sal) {
		this.app_num = app_num;
		this.subject = subject;
		this.co_name = co_name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.hire_type = hire_type;
		this.app_date = app_date;
		this.app_status = app_status;
		this.sal = sal;
	}// »ý¼ºÀÚ

	public String getApp_num() {
		return app_num;
	}

	public String getEr_num() {
		return er_num;
	}

	public String getSubject() {
		return subject;
	}

	public String getCo_name() {
		return co_name;
	}

	public String getRank() {
		return rank;
	}

	public String getLoc() {
		return loc;
	}

	public String getEducation() {
		return education;
	}

	public String getHire_type() {
		return hire_type;
	}

	public String getApp_date() {
		return app_date;
	}

	public String getApp_status() {
		return app_status;
	}

	public int getSal() {
		return sal;
	}

	@Override
	public String toString() {
		return "EeAppVO [app_num=" + app_num + ", er_num=" + er_num + ", subject=" + subject + ", co_name=" + co_name
				+ ", rank=" + rank + ", loc=" + loc + ", education=" + education + ", hire_type=" + hire_type
				+ ", app_date=" + app_date + ", app_status=" + app_status + ", sal=" + sal + "]";
	}

}// class
