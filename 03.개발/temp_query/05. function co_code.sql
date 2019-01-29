CREATE OR REPLACE FUNCTION co_code
RETURN CHAR
IS

BEGIN
	RETURN concat('co_', LPAD(seq_co_num.nextval,6,0));
END;
/
