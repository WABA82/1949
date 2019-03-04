package admin.run;

import java.util.Random;

public class GenerateSsn {
	
	public static String generate() {
		/*-- '880101-1234567'
		-- �� �ڸ��� ������ ���� ����
		--  234567 892345
		-- ������ �ֹι�ȣ ���ڸ��� ���� ��
		-- �� �ڸ��� ����� �� ���� �� 11�� ���� �������� ����
		-- �� ����� 11���� ����
		-- �� ����� 10���� ���� �������� ����
		-- ���� ������� �ֹι�ȣ �������ڸ��� ������ ��ȿ
		-- ���� ������ ��ȿ*/
		
		Random r = new Random();
		
		int[] arrSsn = new int[13];
		int[] validVal = new int[12];
		int sumVal = 0;
		int j = 2;
		int[] nineTeen = { 2, 3, 4, 5, 6, 7, 8, 9 };
		boolean nineTeenFlag = false;
		
		for(int i=0; i<12; i++) {
			// ����ε� �ֹι�ȣ�� ���鵵�� ������ ����
			// 55������� 99 + 00������� 
			// 01~12��
			// 01~31��
			// �ֹι�ȣ ���ڸ��� 1~8
			
			if(i < 2) { // ��
				arrSsn[i] = r.nextInt(5)+5;
			}
			if (i == 2) { // �� ù�ڸ�
				arrSsn[i] = r.nextInt(2);
			}
			if (i == 3) { // �� ��°�ڸ�
				if (arrSsn[i-1] == 1) {
					arrSsn[i] = r.nextInt(3);
				} else {
					arrSsn[i] = r.nextInt(10);
				}
			}
			if (i == 4) { // �� ù�ڸ�
				arrSsn[i] = r.nextInt(4);
			}
			if (i == 5) { // �� ��°�ڸ�
				if (arrSsn[i-1] == 3) {
					arrSsn[i] = r.nextInt(2);
				} else {
					arrSsn[i] = r.nextInt(10);
				}
			}
			if (i == 6) { // �ιι�ȣ �� ù�ڸ�(1~4), 1256�̸� 19, 3478�̸� 20
				for(int k=0; k<nineTeen.length; k++) {
					if (arrSsn[0] == nineTeen[k]) {
						nineTeenFlag = true; // ���ڸ��� 19�� �پ�� �ȴٸ�
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
					if (r.nextBoolean()) { // 2000����(���⼱ �۵����ϰ� ����)
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
		for(int i=0; i<15; i++) {
			System.out.println(GenerateSsn.generate());
		}
	}

}