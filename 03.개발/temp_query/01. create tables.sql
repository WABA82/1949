/* ȸ�� */
DROP TABLE user_table 
	CASCADE CONSTRAINTS;

/* ȸ�� */
DROP TABLE company 
	CASCADE CONSTRAINTS;

/* �⺻���� */
DROP TABLE ee_info 
	CASCADE CONSTRAINTS;

/* �������� */
DROP TABLE er_info 
	CASCADE CONSTRAINTS;

/* ���� �������� */
DROP TABLE interest_er 
	CASCADE CONSTRAINTS;

/* ���� ������ */
DROP TABLE interest_ee 
	CASCADE CONSTRAINTS;

/* ���� */
DROP TABLE application 
	CASCADE CONSTRAINTS;

/* �ּ� */
DROP TABLE zipcode 
	CASCADE CONSTRAINTS;

/* ������� */
DROP TABLE skill 
	CASCADE CONSTRAINTS;

/* �ʿ������� */
DROP TABLE selected_skill 
	CASCADE CONSTRAINTS;

/* ȸ�� */
CREATE TABLE user_table (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	pass VARCHAR2(12) NOT NULL, /* ��й�ȣ */
	name VARCHAR2(15) NOT NULL, /* �̸� */
	ssn CHAR(14) NOT NULL, /* �ֹι�ȣ */
	age NUMBER(3) NOT NULL, /* ���� */
	gender CHAR(1) NOT NULL, /* ���� */
	tel VARCHAR2(13) NOT NULL, /* ����ó */
	addr_seq NUMBER(5) NOT NULL, /* �ּҽ�������ȣ */
	addr_detail VARCHAR2(60) NOT NULL, /* ���ּ� */
	email VARCHAR2(20) NOT NULL, /* �̸��� */
	question_type CHAR(1) NOT NULL, /* ����Ÿ�� */
	answer VARCHAR2(30) NOT NULL, /* ������ */
	user_type CHAR(1) NOT NULL, /* ȸ��Ÿ�� */
	activation CHAR(1) DEFAULT 'N', /* �ʼ�������Ͽ��� */
	input_date DATE DEFAULT SYSDATE /* ����� */
);

COMMENT ON TABLE user_table IS 'ȸ��';

COMMENT ON COLUMN user_table.id IS '���̵�';

COMMENT ON COLUMN user_table.pass IS '��й�ȣ';

COMMENT ON COLUMN user_table.name IS '�̸�';

COMMENT ON COLUMN user_table.ssn IS '�ֹι�ȣ';

COMMENT ON COLUMN user_table.age IS '����';

COMMENT ON COLUMN user_table.gender IS '����';

COMMENT ON COLUMN user_table.tel IS '����ó';

COMMENT ON COLUMN user_table.addr_seq IS '�ּҽ�������ȣ';

COMMENT ON COLUMN user_table.addr_detail IS '���ּ�';

COMMENT ON COLUMN user_table.email IS '�̸���';

COMMENT ON COLUMN user_table.question_type IS '����Ÿ��';

COMMENT ON COLUMN user_table.answer IS '������';

COMMENT ON COLUMN user_table.user_type IS 'ȸ��Ÿ��';

COMMENT ON COLUMN user_table.activation IS '�ʼ�������Ͽ���';

COMMENT ON COLUMN user_table.input_date IS '�����';

CREATE UNIQUE INDEX PK_user_table
	ON user_table (
		id ASC
	);

ALTER TABLE user_table
	ADD
		CONSTRAINT PK_user_table
		PRIMARY KEY (
			id
		);

ALTER TABLE user_table
	ADD
		CONSTRAINT UK_user_table
		UNIQUE (
			ssn
		);

/* ȸ�� */
CREATE TABLE company (
	co_num CHAR(9) NOT NULL, /* ȸ���ȣ */
	co_name VARCHAR2(30) DEFAULT 'no_co_img0.png' NOT NULL, /* ȸ��� */
	img1 VARCHAR2(90) DEFAULT 'no_co_img1.png' NOT NULL, /* �̹���1 */
	img2 VARCHAR2(90) DEFAULT 'no_co_img2.png', /* �̹���2 */
	img3 VARCHAR2(90) DEFAULT 'no_co_img3.png', /* �̹���3 */
	img4 VARCHAR2(90), /* �̹���4 */
	est_date DATE NOT NULL, /* �����⵵ */
	member_num NUMBER(4) NOT NULL, /* ����� */
	co_desc VARCHAR2(4000) NOT NULL, /* ������� */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	er_id VARCHAR2(15) NOT NULL /* �������ھ��̵� */
);

COMMENT ON TABLE company IS 'ȸ��';

COMMENT ON COLUMN company.co_num IS 'ȸ���ȣ';

COMMENT ON COLUMN company.co_name IS 'ȸ���';

COMMENT ON COLUMN company.img1 IS '�̹���1';

COMMENT ON COLUMN company.img2 IS '�̹���2';

COMMENT ON COLUMN company.img3 IS '�̹���3';

COMMENT ON COLUMN company.img4 IS '�̹���4';

COMMENT ON COLUMN company.est_date IS '�����⵵';

COMMENT ON COLUMN company.member_num IS '�����';

COMMENT ON COLUMN company.co_desc IS '�������';

COMMENT ON COLUMN company.input_date IS '�����';

COMMENT ON COLUMN company.er_id IS '�������ھ��̵�';

CREATE UNIQUE INDEX PK_company
	ON company (
		co_num ASC
	);

ALTER TABLE company
	ADD
		CONSTRAINT PK_company
		PRIMARY KEY (
			co_num
		);

/* �⺻���� */
CREATE TABLE ee_info (
	ee_num CHAR(9) NOT NULL, /* �⺻������ȣ */
	img VARCHAR2(90) DEFAULT 'no_img.png' NOT NULL, /* �̹��� */
	rank CHAR(1) NOT NULL, /* ���� */
	loc CHAR(6) NOT NULL, /* �ٹ����� */
	education VARCHAR2(10) NOT NULL, /* �з� */
	portfolio CHAR(1) DEFAULT 'N', /* ��Ʈ���������� */
	ext_resume VARCHAR2(90), /* �ܺ��̷¼� */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	ee_id VARCHAR2(15) NOT NULL /* �Ϲݻ���ھ��̵� */
);

COMMENT ON TABLE ee_info IS '�⺻����';

COMMENT ON COLUMN ee_info.ee_num IS '�⺻������ȣ';

COMMENT ON COLUMN ee_info.img IS '�̹���';

COMMENT ON COLUMN ee_info.rank IS '����';

COMMENT ON COLUMN ee_info.loc IS '�ٹ�����';

COMMENT ON COLUMN ee_info.education IS '�з�';

COMMENT ON COLUMN ee_info.portfolio IS '��Ʈ����������';

COMMENT ON COLUMN ee_info.ext_resume IS '�ܺ��̷¼�';

COMMENT ON COLUMN ee_info.input_date IS '�����';

COMMENT ON COLUMN ee_info.ee_id IS '�Ϲݻ���ھ��̵�';

CREATE UNIQUE INDEX PK_ee_info
	ON ee_info (
		ee_num ASC
	);

ALTER TABLE ee_info
	ADD
		CONSTRAINT PK_ee_info
		PRIMARY KEY (
			ee_num
		);

/* �������� */
CREATE TABLE er_info (
	er_num CHAR(9) NOT NULL, /* ����������ȣ */
	subject VARCHAR2(60) NOT NULL, /* ���� */
	education VARCHAR2(10) NOT NULL, /* �з� */
	rank CHAR(1) NOT NULL, /* ���� */
	loc CHAR(6) NOT NULL, /* �ٹ����� */
	sal NUMBER(5) NOT NULL, /* �޿� */
	hire_type CHAR(1) NOT NULL, /* ������� */
	portfolio CHAR(1) DEFAULT 'N', /* ��Ʈ���������� */
	er_desc VARCHAR2(4000) NOT NULL, /* ������ */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	co_num CHAR(9) NOT NULL /* ȸ���ȣ */
);

COMMENT ON TABLE er_info IS '��������';

COMMENT ON COLUMN er_info.er_num IS '����������ȣ';

COMMENT ON COLUMN er_info.subject IS '����';

COMMENT ON COLUMN er_info.education IS '�з�';

COMMENT ON COLUMN er_info.rank IS '����';

COMMENT ON COLUMN er_info.loc IS '�ٹ�����';

COMMENT ON COLUMN er_info.sal IS '�޿�';

COMMENT ON COLUMN er_info.hire_type IS '�������';

COMMENT ON COLUMN er_info.portfolio IS '��Ʈ����������';

COMMENT ON COLUMN er_info.er_desc IS '������';

COMMENT ON COLUMN er_info.input_date IS '�����';

COMMENT ON COLUMN er_info.co_num IS 'ȸ���ȣ';

CREATE UNIQUE INDEX PK_er_info
	ON er_info (
		er_num ASC
	);

ALTER TABLE er_info
	ADD
		CONSTRAINT PK_er_info
		PRIMARY KEY (
			er_num
		);

/* ���� �������� */
CREATE TABLE interest_er (
	er_num CHAR(9) NOT NULL, /* ����������ȣ */
	ee_id VARCHAR2(15) NOT NULL, /* �Ϲݻ���ھ��̵� */
	input_date DATE DEFAULT SYSDATE /* ����� */
);

COMMENT ON TABLE interest_er IS '���� ��������';

COMMENT ON COLUMN interest_er.er_num IS '����������ȣ';

COMMENT ON COLUMN interest_er.ee_id IS '�Ϲݻ���ھ��̵�';

COMMENT ON COLUMN interest_er.input_date IS '�����';

CREATE UNIQUE INDEX PK_interest_er
	ON interest_er (
		er_num ASC,
		ee_id ASC
	);

ALTER TABLE interest_er
	ADD
		CONSTRAINT PK_interest_er
		PRIMARY KEY (
			er_num,
			ee_id
		);

/* ���� ������ */
CREATE TABLE interest_ee (
	ee_num CHAR(9) NOT NULL, /* �⺻������ȣ */
	er_id VARCHAR2(15) NOT NULL, /* �������ھ��̵� */
	input_date DATE DEFAULT SYSDATE /* ����� */
);

COMMENT ON TABLE interest_ee IS '���� ������';

COMMENT ON COLUMN interest_ee.ee_num IS '�⺻������ȣ';

COMMENT ON COLUMN interest_ee.er_id IS '�������ھ��̵�';

COMMENT ON COLUMN interest_ee.input_date IS '�����';

CREATE UNIQUE INDEX PK_interest_ee
	ON interest_ee (
		ee_num ASC,
		er_id ASC
	);

ALTER TABLE interest_ee
	ADD
		CONSTRAINT PK_interest_ee
		PRIMARY KEY (
			ee_num,
			er_id
		);

/* ���� */
CREATE TABLE application (
	app_num CHAR(10) NOT NULL, /* ������ȣ */
	er_num CHAR(9) NOT NULL, /* ����������ȣ */
	ee_id VARCHAR2(15) NOT NULL, /* �Ϲݻ���ھ��̵� */
	app_date DATE DEFAULT SYSDATE, /* ������ */
	app_status CHAR(1) DEFAULT 'U' NOT NULL /* �������� */
);

COMMENT ON TABLE application IS '����';

COMMENT ON COLUMN application.app_num IS '������ȣ';

COMMENT ON COLUMN application.er_num IS '����������ȣ';

COMMENT ON COLUMN application.ee_id IS '�Ϲݻ���ھ��̵�';

COMMENT ON COLUMN application.app_date IS '������';

COMMENT ON COLUMN application.app_status IS '��������';

CREATE UNIQUE INDEX PK_application
	ON application (
		app_num ASC
	);

ALTER TABLE application
	ADD
		CONSTRAINT PK_application
		PRIMARY KEY (
			app_num
		);

/* �ּ� */
CREATE TABLE zipcode (
	seq NUMBER(5) NOT NULL, /* ��������ȣ */
	zipcode CHAR(7), /* �����ȣ */
	sido CHAR(6), /* �õ� */
	gugun VARCHAR2(25), /* ���� */
	dong VARCHAR2(80), /* �� */
	bunji VARCHAR2(25) /* ���� */
);

COMMENT ON TABLE zipcode IS '�ּ�';

COMMENT ON COLUMN zipcode.seq IS '��������ȣ';

COMMENT ON COLUMN zipcode.zipcode IS '�����ȣ';

COMMENT ON COLUMN zipcode.sido IS '�õ�';

COMMENT ON COLUMN zipcode.gugun IS '����';

COMMENT ON COLUMN zipcode.dong IS '��';

COMMENT ON COLUMN zipcode.bunji IS '����';

CREATE UNIQUE INDEX PK_zipcode
	ON zipcode (
		seq ASC
	);

ALTER TABLE zipcode
	ADD
		CONSTRAINT PK_zipcode
		PRIMARY KEY (
			seq
		);

/* ������� */
CREATE TABLE skill (
	skill_num CHAR(4) NOT NULL, /* ����ڵ� */
	skill_name VARCHAR2(14) NOT NULL /* ����� */
);

COMMENT ON TABLE skill IS '�������';

COMMENT ON COLUMN skill.skill_num IS '����ڵ�';

COMMENT ON COLUMN skill.skill_name IS '�����';

CREATE UNIQUE INDEX PK_skill
	ON skill (
		skill_num ASC
	);

ALTER TABLE skill
	ADD
		CONSTRAINT PK_skill
		PRIMARY KEY (
			skill_num
		);

/* �ʿ������� */
CREATE TABLE selected_skill (
	er_num CHAR(9) NOT NULL, /* ����������ȣ */
	skill_num CHAR(4) NOT NULL /* ����ڵ� */
);

COMMENT ON TABLE selected_skill IS '�ʿ�������';

COMMENT ON COLUMN selected_skill.er_num IS '����������ȣ';

COMMENT ON COLUMN selected_skill.skill_num IS '����ڵ�';

CREATE UNIQUE INDEX PK_selected_skill
	ON selected_skill (
		er_num ASC,
		skill_num ASC
	);

ALTER TABLE selected_skill
	ADD
		CONSTRAINT PK_selected_skill
		PRIMARY KEY (
			er_num,
			skill_num
		);

ALTER TABLE user_table
	ADD
		CONSTRAINT FK_zipcode_TO_user_table
		FOREIGN KEY (
			addr_seq
		)
		REFERENCES zipcode (
			seq
		);

ALTER TABLE company
	ADD
		CONSTRAINT FK_user_table_TO_company
		FOREIGN KEY (
			er_id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE ee_info
	ADD
		CONSTRAINT FK_user_table_TO_ee_info
		FOREIGN KEY (
			ee_id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE er_info
	ADD
		CONSTRAINT FK_company_TO_er_info
		FOREIGN KEY (
			co_num
		)
		REFERENCES company (
			co_num
		);

ALTER TABLE interest_er
	ADD
		CONSTRAINT FK_er_info_TO_interest_er
		FOREIGN KEY (
			er_num
		)
		REFERENCES er_info (
			er_num
		);

ALTER TABLE interest_er
	ADD
		CONSTRAINT FK_user_table_TO_interest_er
		FOREIGN KEY (
			ee_id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE interest_ee
	ADD
		CONSTRAINT FK_ee_info_TO_interest_ee
		FOREIGN KEY (
			ee_num
		)
		REFERENCES ee_info (
			ee_num
		);

ALTER TABLE interest_ee
	ADD
		CONSTRAINT FK_user_table_TO_interest_ee
		FOREIGN KEY (
			er_id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE application
	ADD
		CONSTRAINT FK_er_info_TO_application
		FOREIGN KEY (
			er_num
		)
		REFERENCES er_info (
			er_num
		);

ALTER TABLE application
	ADD
		CONSTRAINT FK_user_table_TO_application
		FOREIGN KEY (
			ee_id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE selected_skill
	ADD
		CONSTRAINT FK_er_info_TO_selected_skill
		FOREIGN KEY (
			er_num
		)
		REFERENCES er_info (
			er_num
		);

ALTER TABLE selected_skill
	ADD
		CONSTRAINT FK_skill_TO_selected_skill
		FOREIGN KEY (
			skill_num
		)
		REFERENCES skill (
			skill_num
		);        
		
ALTER TABLE company MODIFY(
	img4 VARCHAR2(90)
DEFAULT 'no_co_img4.png');      

-- FK CASCADE �ɼ� ���� 
ALTER TABLE application DROP CONSTRAINT FK_USER_TABLE_TO_APPLICATION;

ALTER TABLE application
ADD CONSTRAINT FK_USER_TABLE_TO_APPLICATION
FOREIGN KEY (ee_id)
REFERENCES user_table(id)
ON DELETE CASCADE;

ALTER TABLE ee_info DROP CONSTRAINT FK_USER_TABLE_TO_EE_INFO;

ALTER TABLE ee_info
ADD CONSTRAINT FK_USER_TABLE_TO_EE_INFO
FOREIGN KEY (ee_id)
REFERENCES user_table(id)
ON DELETE CASCADE;

ALTER TABLE interest_er DROP CONSTRAINT FK_USER_TABLE_TO_INTEREST_ER;

ALTER TABLE interest_er
ADD CONSTRAINT FK_USER_TABLE_TO_INTEREST_ER
FOREIGN KEY (ee_id)
REFERENCES user_table(id)
ON DELETE CASCADE;

ALTER TABLE interest_ee DROP CONSTRAINT FK_EE_INFO_TO_INTEREST_EE;

ALTER TABLE interest_ee
ADD CONSTRAINT FK_EE_INFO_TO_INTEREST_EE
FOREIGN KEY (ee_num)
REFERENCES ee_info(ee_num)
ON DELETE CASCADE;

ALTER TABLE interest_er DROP CONSTRAINT FK_ER_INFO_TO_INTEREST_ER;

ALTER TABLE interest_er
ADD CONSTRAINT FK_ER_INFO_TO_INTEREST_ER
FOREIGN KEY (er_num)
REFERENCES er_info(er_num)
ON DELETE CASCADE;

ALTER TABLE application DROP CONSTRAINT FK_ER_INFO_TO_APPLICATION;

ALTER TABLE application
ADD CONSTRAINT FK_ER_INFO_TO_APPLICATION
FOREIGN KEY (er_num)
REFERENCES er_info(er_num)
ON DELETE CASCADE;

ALTER TABLE selected_skill DROP CONSTRAINT FK_ER_INFO_TO_SELECTED_SKILL;

ALTER TABLE selected_skill
ADD CONSTRAINT FK_ER_INFO_TO_SELECTED_SKILL
FOREIGN KEY (er_num)
REFERENCES er_info(er_num)
ON DELETE CASCADE;


COMMIT;