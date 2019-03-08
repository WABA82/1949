CREATE OR REPLACE PROCEDURE insert_sign_up_proc(
	i_id IN VARCHAR2, i_pass IN VARCHAR2, i_name IN VARCHAR2,
	i_ssn IN CHAR, i_tel IN VARCHAR2, i_email IN VARCHAR2,
	i_seq IN NUMBER, i_addr_detail IN VARCHAR2, i_q_type IN CHAR,
	i_answer IN VARCHAR2, i_user_type IN CHAR,
	msg OUT VARCHAR2
)
IS
	i_age NUMBER := 0;
	yy CHAR(2);
	year_of_birth VARCHAR(4);
	i_gender CHAR(1); 
	flag CHAR(1);   -- �Ǵ� �������� ����ϴ� flag ����
BEGIN

	flag := SUBSTR(i_ssn, 8, 1);                         
	yy := SUBSTR(i_ssn, 1, 2);	
		
	IF flag IN (1,2,5,6) THEN
		year_of_birth := '19'||yy;
		IF flag IN (1, 5) THEN
			i_gender := 'M';
		ELSE
			i_gender := 'F';
		END IF;
	ELSIF flag IN (3,4,7,8) THEN
		year_of_birth := '20'||yy;  
		IF flag IN (3, 7) THEN
			i_gender := 'M';
		ELSE
			i_gender := 'F'; 
		END IF;
	END IF;	
	
	i_age := TO_CHAR(SYSDATE, 'yyyy') - year_of_birth + 1;
	
	IF i_user_type = 'E' THEN
		INSERT INTO user_table(id, pass, name, ssn, age, gender, tel, addr_seq, addr_detail, email, question_type, answer, user_type)
		VALUES (i_id, i_pass, i_name, i_ssn, i_age, i_gender, i_tel, i_seq, i_addr_detail, i_email, i_q_type, i_answer, 'E');
		msg := '�Ϲݻ���ڷ� ������ �Ϸ�Ǿ����ϴ�. �⺻������ ��� �� �������� ��ȸ�� �����մϴ�.';
	ELSE
		INSERT INTO user_table(id, pass, name, ssn, age, gender, tel, addr_seq, addr_detail, email, question_type, answer, user_type)
		VALUES (i_id, i_pass, i_name, i_ssn, i_age, i_gender, i_tel, i_seq, i_addr_detail, i_email, i_q_type, i_answer, 'R');	
		msg := '�������ڷ� ������ �Ϸ�Ǿ����ϴ�. ȸ�������� ��� �� �������� ��ȸ�� �����մϴ�.';
	END IF;
	

END;
/
