package user.ee.dto;


public class EeHiringCdtDTO {
	private String sort, cdt, coName;
	private static EeHiringCdtDTO ehc_dto;
	public static EeHiringCdtDTO getInstance() {
		if(ehc_dto==null) {
			ehc_dto= new EeHiringCdtDTO();
		}//end if
		return ehc_dto;
	}//getInstance
	

	public void setSort(String sort) {
		this.sort = sort;
	}


	public void setCdt(String cdt) {
		this.cdt = cdt;
	}


	public void setCoName(String coName) {
		this.coName = coName;
	}


	public String getSort() {
		return sort;
	}

	public String getCdt() {
		return cdt;
	}

	public String getCoName() {
		return coName;
	}


	@Override
	public String toString() {
		return "EeHiringCdtDTO [sort=" + sort + ", cdt=" + cdt + ", coName=" + coName + "]";
	}
	
	
}
