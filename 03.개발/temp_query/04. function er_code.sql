CREATE OR REPLACE FUNCTION er_code
RETURN CHAR
IS

BEGIN
	RETURN concat('er_', LPAD(seq_er_num.nextval,6,0));
END;
/
