/* 회원 */
DROP TABLE user_table 
	CASCADE CONSTRAINTS;

/* 회사 */
DROP TABLE company 
	CASCADE CONSTRAINTS;

/* 기본정보 */
DROP TABLE ee_info 
	CASCADE CONSTRAINTS;

/* 구인정보 */
DROP TABLE er_info 
	CASCADE CONSTRAINTS;

/* 관심 구인정보 */
DROP TABLE interest_er 
	CASCADE CONSTRAINTS;

/* 관심 구직자 */
DROP TABLE interest_ee 
	CASCADE CONSTRAINTS;

/* 지원 */
DROP TABLE application 
	CASCADE CONSTRAINTS;

/* 주소 */
DROP TABLE zipcode 
	CASCADE CONSTRAINTS;

/* 기술스택 */
DROP TABLE skill 
	CASCADE CONSTRAINTS;

/* 필요기술스택 */
DROP TABLE selected_skill 
	CASCADE CONSTRAINTS;

/* 회원 */
CREATE TABLE user_table (
	id VARCHAR2(15) NOT NULL, /* 아이디 */
	pass VARCHAR2(12) NOT NULL, /* 비밀번호 */
	name VARCHAR2(15) NOT NULL, /* 이름 */
	ssn CHAR(14) NOT NULL, /* 주민번호 */
	age NUMBER(3) NOT NULL, /* 나이 */
	gender CHAR(1) NOT NULL, /* 성별 */
	tel VARCHAR2(13) NOT NULL, /* 연락처 */
	addr_seq NUMBER(5) NOT NULL, /* 주소시퀀스번호 */
	addr_detail VARCHAR2(60) NOT NULL, /* 상세주소 */
	email VARCHAR2(20) NOT NULL, /* 이메일 */
	question_type CHAR(1) NOT NULL, /* 질문타입 */
	answer VARCHAR2(30) NOT NULL, /* 질문답 */
	user_type CHAR(1) NOT NULL, /* 회원타입 */
	activation CHAR(1) DEFAULT 'N', /* 필수정보등록여부 */
	input_date DATE DEFAULT SYSDATE /* 등록일 */
);

COMMENT ON TABLE user_table IS '회원';

COMMENT ON COLUMN user_table.id IS '아이디';

COMMENT ON COLUMN user_table.pass IS '비밀번호';

COMMENT ON COLUMN user_table.name IS '이름';

COMMENT ON COLUMN user_table.ssn IS '주민번호';

COMMENT ON COLUMN user_table.age IS '나이';

COMMENT ON COLUMN user_table.gender IS '성별';

COMMENT ON COLUMN user_table.tel IS '연락처';

COMMENT ON COLUMN user_table.addr_seq IS '주소시퀀스번호';

COMMENT ON COLUMN user_table.addr_detail IS '상세주소';

COMMENT ON COLUMN user_table.email IS '이메일';

COMMENT ON COLUMN user_table.question_type IS '질문타입';

COMMENT ON COLUMN user_table.answer IS '질문답';

COMMENT ON COLUMN user_table.user_type IS '회원타입';

COMMENT ON COLUMN user_table.activation IS '필수정보등록여부';

COMMENT ON COLUMN user_table.input_date IS '등록일';

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

/* 회사 */
CREATE TABLE company (
	co_num CHAR(9) NOT NULL, /* 회사번호 */
	co_name VARCHAR2(30) DEFAULT 'no_co_img0.png' NOT NULL, /* 회사명 */
	img1 VARCHAR2(90) DEFAULT 'no_co_img1.png' NOT NULL, /* 이미지1 */
	img2 VARCHAR2(90) DEFAULT 'no_co_img2.png', /* 이미지2 */
	img3 VARCHAR2(90) DEFAULT 'no_co_img3.png', /* 이미지3 */
	img4 VARCHAR2(90), /* 이미지4 */
	est_date DATE NOT NULL, /* 설립년도 */
	member_num NUMBER(4) NOT NULL, /* 사원수 */
	co_desc VARCHAR2(4000) NOT NULL, /* 기업설명 */
	input_date DATE DEFAULT SYSDATE, /* 등록일 */
	er_id VARCHAR2(15) NOT NULL /* 기업사용자아이디 */
);

COMMENT ON TABLE company IS '회사';

COMMENT ON COLUMN company.co_num IS '회사번호';

COMMENT ON COLUMN company.co_name IS '회사명';

COMMENT ON COLUMN company.img1 IS '이미지1';

COMMENT ON COLUMN company.img2 IS '이미지2';

COMMENT ON COLUMN company.img3 IS '이미지3';

COMMENT ON COLUMN company.img4 IS '이미지4';

COMMENT ON COLUMN company.est_date IS '설립년도';

COMMENT ON COLUMN company.member_num IS '사원수';

COMMENT ON COLUMN company.co_desc IS '기업설명';

COMMENT ON COLUMN company.input_date IS '등록일';

COMMENT ON COLUMN company.er_id IS '기업사용자아이디';

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

/* 기본정보 */
CREATE TABLE ee_info (
	ee_num CHAR(9) NOT NULL, /* 기본정보번호 */
	img VARCHAR2(90) DEFAULT 'no_img.png' NOT NULL, /* 이미지 */
	rank CHAR(1) NOT NULL, /* 직급 */
	loc CHAR(6) NOT NULL, /* 근무지역 */
	education VARCHAR2(10) NOT NULL, /* 학력 */
	portfolio CHAR(1) DEFAULT 'N', /* 포트폴리오유무 */
	ext_resume VARCHAR2(90), /* 외부이력서 */
	input_date DATE DEFAULT SYSDATE, /* 등록일 */
	ee_id VARCHAR2(15) NOT NULL /* 일반사용자아이디 */
);

COMMENT ON TABLE ee_info IS '기본정보';

COMMENT ON COLUMN ee_info.ee_num IS '기본정보번호';

COMMENT ON COLUMN ee_info.img IS '이미지';

COMMENT ON COLUMN ee_info.rank IS '직급';

COMMENT ON COLUMN ee_info.loc IS '근무지역';

COMMENT ON COLUMN ee_info.education IS '학력';

COMMENT ON COLUMN ee_info.portfolio IS '포트폴리오유무';

COMMENT ON COLUMN ee_info.ext_resume IS '외부이력서';

COMMENT ON COLUMN ee_info.input_date IS '등록일';

COMMENT ON COLUMN ee_info.ee_id IS '일반사용자아이디';

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

/* 구인정보 */
CREATE TABLE er_info (
	er_num CHAR(9) NOT NULL, /* 구인정보번호 */
	subject VARCHAR2(60) NOT NULL, /* 제목 */
	education VARCHAR2(10) NOT NULL, /* 학력 */
	rank CHAR(1) NOT NULL, /* 직급 */
	loc CHAR(6) NOT NULL, /* 근무지역 */
	sal NUMBER(5) NOT NULL, /* 급여 */
	hire_type CHAR(1) NOT NULL, /* 고용형태 */
	portfolio CHAR(1) DEFAULT 'N', /* 포트폴리오유무 */
	er_desc VARCHAR2(4000) NOT NULL, /* 상세정보 */
	input_date DATE DEFAULT SYSDATE, /* 등록일 */
	co_num CHAR(9) NOT NULL /* 회사번호 */
);

COMMENT ON TABLE er_info IS '구인정보';

COMMENT ON COLUMN er_info.er_num IS '구인정보번호';

COMMENT ON COLUMN er_info.subject IS '제목';

COMMENT ON COLUMN er_info.education IS '학력';

COMMENT ON COLUMN er_info.rank IS '직급';

COMMENT ON COLUMN er_info.loc IS '근무지역';

COMMENT ON COLUMN er_info.sal IS '급여';

COMMENT ON COLUMN er_info.hire_type IS '고용형태';

COMMENT ON COLUMN er_info.portfolio IS '포트폴리오유무';

COMMENT ON COLUMN er_info.er_desc IS '상세정보';

COMMENT ON COLUMN er_info.input_date IS '등록일';

COMMENT ON COLUMN er_info.co_num IS '회사번호';

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

/* 관심 구인정보 */
CREATE TABLE interest_er (
	er_num CHAR(9) NOT NULL, /* 구인정보번호 */
	ee_id VARCHAR2(15) NOT NULL, /* 일반사용자아이디 */
	input_date DATE DEFAULT SYSDATE /* 등록일 */
);

COMMENT ON TABLE interest_er IS '관심 구인정보';

COMMENT ON COLUMN interest_er.er_num IS '구인정보번호';

COMMENT ON COLUMN interest_er.ee_id IS '일반사용자아이디';

COMMENT ON COLUMN interest_er.input_date IS '등록일';

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

/* 관심 구직자 */
CREATE TABLE interest_ee (
	ee_num CHAR(9) NOT NULL, /* 기본정보번호 */
	er_id VARCHAR2(15) NOT NULL, /* 기업사용자아이디 */
	input_date DATE DEFAULT SYSDATE /* 등록일 */
);

COMMENT ON TABLE interest_ee IS '관심 구직자';

COMMENT ON COLUMN interest_ee.ee_num IS '기본정보번호';

COMMENT ON COLUMN interest_ee.er_id IS '기업사용자아이디';

COMMENT ON COLUMN interest_ee.input_date IS '등록일';

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

/* 지원 */
CREATE TABLE application (
	app_num CHAR(10) NOT NULL, /* 지원번호 */
	er_num CHAR(9) NOT NULL, /* 구인정보번호 */
	ee_id VARCHAR2(15) NOT NULL, /* 일반사용자아이디 */
	app_date DATE DEFAULT SYSDATE, /* 지원일 */
	app_status CHAR(1) DEFAULT 'U' NOT NULL /* 지원상태 */
);

COMMENT ON TABLE application IS '지원';

COMMENT ON COLUMN application.app_num IS '지원번호';

COMMENT ON COLUMN application.er_num IS '구인정보번호';

COMMENT ON COLUMN application.ee_id IS '일반사용자아이디';

COMMENT ON COLUMN application.app_date IS '지원일';

COMMENT ON COLUMN application.app_status IS '지원상태';

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

/* 주소 */
CREATE TABLE zipcode (
	seq NUMBER(5) NOT NULL, /* 시퀀스번호 */
	zipcode CHAR(7), /* 우편번호 */
	sido CHAR(6), /* 시도 */
	gugun VARCHAR2(25), /* 구군 */
	dong VARCHAR2(80), /* 동 */
	bunji VARCHAR2(25) /* 번지 */
);

COMMENT ON TABLE zipcode IS '주소';

COMMENT ON COLUMN zipcode.seq IS '시퀀스번호';

COMMENT ON COLUMN zipcode.zipcode IS '우편번호';

COMMENT ON COLUMN zipcode.sido IS '시도';

COMMENT ON COLUMN zipcode.gugun IS '구군';

COMMENT ON COLUMN zipcode.dong IS '동';

COMMENT ON COLUMN zipcode.bunji IS '번지';

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

/* 기술스택 */
CREATE TABLE skill (
	skill_num CHAR(4) NOT NULL, /* 기술코드 */
	skill_name VARCHAR2(14) NOT NULL /* 기술명 */
);

COMMENT ON TABLE skill IS '기술스택';

COMMENT ON COLUMN skill.skill_num IS '기술코드';

COMMENT ON COLUMN skill.skill_name IS '기술명';

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

/* 필요기술스택 */
CREATE TABLE selected_skill (
	er_num CHAR(9) NOT NULL, /* 구인정보번호 */
	skill_num CHAR(4) NOT NULL /* 기술코드 */
);

COMMENT ON TABLE selected_skill IS '필요기술스택';

COMMENT ON COLUMN selected_skill.er_num IS '구인정보번호';

COMMENT ON COLUMN selected_skill.skill_num IS '기술코드';

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

-- FK CASCADE 옵션 설정 
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

-- email은 254자까지 허용하도록 변경
ALTER TABLE user_table MODIFY email VARCHAR2(254);

// 0216 추가
ALTER TABLE er_info DROP CONSTRAINT FK_COMPANY_TO_ER_INFO;

ALTER TABLE er_info
ADD CONSTRAINT FK_COMPANY_TO_ER_INFO
FOREIGN KEY (co_num)
REFERENCES company(co_num)
ON DELETE CASCADE;

ALTER TABLE company DROP CONSTRAINT FK_USER_TABLE_TO_COMPANY;

ALTER TABLE company
ADD CONSTRAINT FK_USER_TABLE_TO_COMPANY
FOREIGN KEY (er_id)
REFERENCES user_table(id)
ON DELETE CASCADE;

ALTER TABLE interest_ee DROP CONSTRAINT fk_user_table_to_interest_ee;

ALTER TABLE interest_ee ADD CONSTRAINT fk_user_table_to_interest_ee
FOREIGN KEY (er_id)
REFERENCES user_table(id)
ON DELETE CASCADE;        

-- 암호화 처리를 위해 길이 변경
ALTER TABLE user_table MODIFY pass VARCHAR2(60);
ALTER TABLE user_table MODIFY ssn VARCHAR2(60);   

COMMIT;