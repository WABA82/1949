CREATE OR REPLACE FUNCTION app_code
RETURN CHAR
IS

BEGIN
	RETURN concat('app_', LPAD(seq_app_num.nextval,6,0));
END;
/
