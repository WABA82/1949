CREATE OR REPLACE FUNCTION ee_code
RETURN CHAR
IS

BEGIN
	RETURN concat('ee_', LPAD(seq_ee_num.nextval,6,0));
END;
/
