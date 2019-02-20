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
		for(int i=0; i<12; i++) {
			arrSsn[i] = r.nextInt(10);
			
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
		for(int i=0; i<15; i++) {
			System.out.println(GenerateSsn.generate());
		}
	}

}