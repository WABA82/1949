package admin.vo;

public class CoInfoVO {
   String erId, coNum, img1, img2, img3, img4, coName, estDate, coDesc;
   int memberNum;
   
   public CoInfoVO(String erId, String coNum, String img1, String img2, String img3, String img4, String coName,
         String estDate, String coDesc, int memberNum) {
      this.erId = erId;
      this.coNum = coNum;
      this.img1 = img1;
      this.img2 = img2;
      this.img3 = img3;
      this.img4 = img4;
      this.coName = coName;
      this.estDate = estDate;
      this.coDesc = coDesc;
      this.memberNum = memberNum;
   }

   public String getErId() {
      return erId;
   }

   public String getCoNum() {
      return coNum;
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

   public String getCoName() {
      return coName;
   }

   public String getEstDate() {
      return estDate;
   }

   public String getCoDesc() {
      return coDesc;
   }

   public int getMemberNum() {
      return memberNum;
   }

   @Override
   public String toString() {
      return "CoInfoVO [erId=" + erId + ", coNum=" + coNum + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3
            + ", img4=" + img4 + ", coName=" + coName + ", estDate=" + estDate + ", coDesc=" + coDesc
            + ", memberNum=" + memberNum + "]";
   }
}