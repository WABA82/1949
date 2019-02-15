package user.er.dto;

public class ErHiringCdtDTO {
	private String sort, cdt;
	private static ErHiringCdtDTO erhc_dto;
	public static ErHiringCdtDTO getInstance() {
		if(erhc_dto==null) {
			erhc_dto= new ErHiringCdtDTO();
		}//end if
		return erhc_dto;
	}//getInstance
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

	public String getSort() {
		return sort;
	}
	public String getCdt() {
		return cdt;
	}

}
