package admin.run;

import java.util.Random;

public class GenerateSsn {
	
	public static String generate() {
		/*-- '880101-1234567'
		-- 각 자리에 지정한 수를 곱함
		--  234567 892345
		-- 마지막 주민번호 한자리가 검증 값
		-- 각 자리별 결과를 다 더한 후 11로 나눈 나머지를 구함
		-- 그 결과를 11에서 뺀다
		-- 그 결과를 10으로 나눈 나머지를 구함
		-- 최종 결과값이 주민번호 최종끝자리와 같으면 유효
		-- 같지 않으면 무효*/
		
		Random r = new Random();
		
		int[] arrSsn = new int[13];
		int[] validVal = new int[12];
		int sumVal = 0;
		int j = 2;
		int[] nineTeen = { 2, 3, 4, 5, 6, 7, 8, 9 };
		boolean nineTeenFlag = false;
		
		for(int i=0; i<12; i++) {
			// 제대로된 주민번호를 만들도록 제약을 설정
			// 55년생부터 99 + 00년생까지 
			// 01~12월
			// 01~31일
			// 주민번호 앞자리는 1~8
			
			if(i < 2) { // 년
				arrSsn[i] = r.nextInt(5)+5;
			}
			if (i == 2) { // 월 첫자리
				arrSsn[i] = r.nextInt(2);
			}
			if (i == 3) { // 월 둘째자리
				if (arrSsn[i-1] == 1) {
					arrSsn[i] = r.nextInt(3);
				} else {
					arrSsn[i] = r.nextInt(10);
				}
			}
			if (i == 4) { // 일 첫자리
				arrSsn[i] = r.nextInt(4);
			}
			if (i == 5) { // 일 둘째자리
				if (arrSsn[i-1] == 3) {
					arrSsn[i] = r.nextInt(2);
				} else {
					arrSsn[i] = r.nextInt(10);
				}
			}
			if (i == 6) { // 두민번호 뒷 첫자리(1~4), 1256이면 19, 3478이면 20
				for(int k=0; k<nineTeen.length; k++) {
					if (arrSsn[0] == nineTeen[k]) {
						nineTeenFlag = true; // 앞자리가 19가 붙어야 된다면
					}
				}
				
				if (nineTeenFlag) { // 1, 2, 5, 6
					if (r.nextBoolean()) {
						arrSsn[i] = r.nextInt(2)+5;  
					} else {
						arrSsn[i] = r.nextInt(2)+1;  
					}
					nineTeenFlag = false;
				} else {
					if (r.nextBoolean()) { // 2000년대생(여기선 작동안하게 설계)
						arrSsn[i] = r.nextInt(2)+7;  
					} else {
						arrSsn[i] = r.nextInt(2)+3; // 3, 4, 7, 8
					}
				}
			} 
			if (i > 6) {
				arrSsn[i] = r.nextInt(10);
			}
			
			if(j > 9) {
				j = 2;
			}
			
			validVal[i] = arrSsn[i]*j;
			j++;
			sumVal += validVal[i];
		}
		
		arrSsn[12] = (11 - (sumVal%11))%10;

		StringBuilder ssn = new StringBuilder();
		for(int i=0; i<arrSsn.length; i++) {
			if (i == 6) {
				ssn.append("-");
			}
			ssn.append(arrSsn[i]);
		}
		return ssn.toString();
	}
	
	public static void main(String[] args) {
		for(int i=0; i<30; i++) {
			System.out.println(GenerateSsn.generate());
		}
	}

}