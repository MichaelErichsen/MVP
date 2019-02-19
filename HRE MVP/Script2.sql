--<ScriptOptions statementTerminator=";"/>

CREATE TABLE LOCATION_NAME_PARTS (
		LOCATION_NAME_PART_PID INTEGER NOT NULL,
		LOCATION_NAME_PID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL,
		PART_NO INTEGER NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE EVENT_NAMES (
		EVENT_NAME_PID INTEGER NOT NULL,
		TABLE_ID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL,
		LANGUAGE_PID INTEGER,
		EVENT_TYPE_PID INTEGER
	);

CREATE TABLE PARTNERS (
		PARTNER_PID INTEGER NOT NULL,
		PARTNER1 INTEGER NOT NULL,
		PARTNER2 INTEGER NOT NULL,
		PRIMARY_PARTNER BOOLEAN NOT NULL,
		ROLE VARCHAR(2147483647) NOT NULL,
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER,
		LANGUAGE_PID INTEGER
	);

CREATE TABLE NAMES (
		NAME_PID INTEGER NOT NULL,
		PERSON_PID INTEGER NOT NULL,
		PRIMARY_NAME BOOLEAN,
		NAME_STYLE_PID INTEGER NOT NULL,
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER
	);

CREATE TABLE EVENTS (
		EVENT_PID INTEGER NOT NULL,
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER,
		EVENT_NAME_PID INTEGER NOT NULL
	);

CREATE TABLE SEXES (
		SEXES_PID INTEGER NOT NULL,
		PERSON_PID INTEGER NOT NULL,
		SEX_TYPE_PID INTEGER NOT NULL,
		PRIMARY_SEX BOOLEAN NOT NULL,
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER
	);

CREATE TABLE LOCATION_EVENTS (
		LOCATION_EVENTS_PID INTEGER NOT NULL,
		EVENT_PID INTEGER NOT NULL,
		LOCATION_PID INTEGER NOT NULL,
		PRIMARY_EVENT BOOLEAN NOT NULL,
		PRIMARY_LOCATION BOOLEAN NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE COMMIT_LOGS (
		COMMIT_LOG_PID INTEGER NOT NULL,
		TABLE_PID INTEGER NOT NULL,
		CHANGED_TABLE_PID INTEGER NOT NULL,
		CHANGED_RECORD_PID INTEGER NOT NULL,
		CHANGED_TIMESTAMP TIMESTAMP NOT NULL,
		USERID_PID INTEGER NOT NULL,
		COLUMN_NAME_LIST VARCHAR(2147483647) NOT NULL,
		COLUMN_DATA_LIST VARCHAR(2147483647) NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE LOCATION_NAMES (
		LOCATION_NAME_PID INTEGER NOT NULL,
		LOCATION_PID INTEGER NOT NULL,
		PRIMARY_LOCATION_NAME BOOLEAN NOT NULL,
		LOCATION_NAME_STYLE_PID INTEGER NOT NULL,
		PREPOSITION VARCHAR(2147483647),
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER
	);

CREATE TABLE NAME_STYLES (
		NAME_STYLE_PID INTEGER NOT NULL,
		LABEL CHAR(30) NOT NULL,
		LANGUAGE_PID INTEGER,
		TABLE_ID INTEGER
	);

CREATE TABLE NAME_MAPS (
		NAME_MAP_PID INTEGER NOT NULL,
		NAME_STYLE_PID INTEGER NOT NULL,
		PART_NO INTEGER NOT NULL,
		LABEL CHAR(30) NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE HDATES (
		HDATE_PID INTEGER NOT NULL,
		TABLE_ID INTEGER DEFAULT 751 NOT NULL,
		ORIGINAL_TEXT VARCHAR(160) NOT NULL,
		DATE DATE NOT NULL,
		SORT_DATE DATE NOT NULL,
		SURETY VARCHAR(10) NOT NULL
	);

CREATE TABLE LANGUAGES (
		LANGUAGE_PID INTEGER NOT NULL,
		ISOCODE CHAR(4) NOT NULL,
		LABEL CHAR(30) NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE USERIDS (
		USERID_PID INTEGER NOT NULL,
		TABLE_PID INTEGER NOT NULL,
		PERSON_PID INTEGER NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE LOCATION_NAME_STYLES (
		LOCATION_NAME_STYLE_PID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL,
		LANGUAGE_PID INTEGER NOT NULL,
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER
	);

CREATE TABLE DICTIONARY (
		DICTIONARY_PID INTEGER NOT NULL,
		LABEL_PID INTEGER NOT NULL,
		ISO_CODE CHAR(4) NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL,
		TABLE_ID INTEGER NOT NULL
	);

CREATE TABLE EVENT_TYPES (
		EVENT_TYPE_PID INTEGER NOT NULL,
		TABLE_ID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL
	);

CREATE TABLE LOCATION_NAME_MAPS (
		LOCATION_NAME_MAP_PID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647) NOT NULL,
		PART_NO INTEGER NOT NULL,
		LOCATION_NAME_STYLE_PID INTEGER NOT NULL,
		LABEL_POSITION CHAR(4) NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE PERSONS (
		PERSON_PID INTEGER NOT NULL,
		TABLE_ID INTEGER,
		BIRTH_DATE_PID INTEGER,
		DEATH_DATE_PID INTEGER
	);

CREATE TABLE PARENTS (
		PARENT_PID INTEGER NOT NULL,
		CHILD INTEGER NOT NULL,
		PARENT INTEGER NOT NULL,
		PARENT_ROLE VARCHAR(2147483647) NOT NULL,
		PRIMARY_PARENT BOOLEAN NOT NULL,
		TABLE_ID INTEGER,
		LANGUAGE_PID INTEGER
	);

CREATE TABLE NAME_PARTS (
		NAME_PART_PID INTEGER NOT NULL,
		NAME_PID INTEGER NOT NULL,
		LABEL VARCHAR(2147483647),
		PART_NO INTEGER NOT NULL,
		TABLE_ID INTEGER
	);

CREATE TABLE LOCATIONS (
		LOCATION_PID INTEGER NOT NULL,
		PRIMARY_LOCATION BOOLEAN NOT NULL,
		X_COORDINATE DECIMAL(65535 , 32767),
		Y_COORDINATE DECIMAL(65535 , 32767),
		Z_COORDINATE DECIMAL(65535 , 32767),
		TABLE_ID INTEGER,
		FROM_DATE_PID INTEGER,
		TO_DATE_PID INTEGER
	);

CREATE TABLE PERSON_EVENTS (
		PERSON_EVENT_PID INTEGER NOT NULL,
		EVENT_PID INTEGER NOT NULL,
		PERSON_PID INTEGER NOT NULL,
		ROLE VARCHAR(2147483647) NOT NULL,
		PRIMARY_PERSON BOOLEAN NOT NULL,
		PRIMARY_EVENT BOOLEAN NOT NULL,
		TABLE_ID INTEGER,
		LANGUAGE_PID INTEGER
	);

CREATE TABLE SEX_TYPES (
		SEX_TYPE_PID INTEGER NOT NULL,
		ABBREVIATION CHAR(1) NOT NULL,
		LABEL_PID INTEGER
	);

CREATE UNIQUE INDEX PRIMARY_KEY_D ON COMMIT_LOGS (COMMIT_LOG_PID ASC);

CREATE INDEX PERSONS_PARTNERS_FK_INDEX_7 ON PARTNERS (PARTNER1 ASC);

CREATE INDEX CONSTRAINT_371_INDEX_3 ON LOCATION_NAME_STYLES (TO_DATE_PID ASC);

CREATE INDEX LOCATIONS_LOCATION_NAMES_FK_INDEX_B ON LOCATION_NAMES (LOCATION_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_BCB ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);

CREATE INDEX NAMES_NAME_PARTS_FK_INDEX_3 ON NAME_PARTS (NAME_PID ASC);

CREATE INDEX CONSTRAINT_D1_INDEX_D ON EVENT_NAMES (LANGUAGE_PID ASC);

CREATE INDEX SEX_TYPES_SEXES_FK_INDEX_4 ON SEXES (SEX_TYPE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_75 ON PARTNERS (PARTNER_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_4 ON NAMES (NAME_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_DF ON LOCATIONS (LOCATION_PID ASC);

CREATE INDEX PERSONS_NAMES_FK_INDEX_4 ON NAMES (PERSON_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_37 ON DICTIONARY (DICTIONARY_PID ASC);

CREATE INDEX CONSTRAINT_D_INDEX_D ON EVENT_NAMES (EVENT_TYPE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_506 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_D1 ON EVENT_TYPES (EVENT_TYPE_PID ASC);

CREATE INDEX NAME_STYLES_NAMES_FK_INDEX_4 ON NAMES (NAME_STYLE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_6 ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_9 ON LANGUAGES (LANGUAGE_PID ASC);

CREATE INDEX USERIDS_COMMIT_LOGS_FK_INDEX_D ON COMMIT_LOGS (USERID_PID ASC);

CREATE INDEX CONSTRAINT_4A949_INDEX_4 ON PERSON_EVENTS (LANGUAGE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_7E ON HDATES (HDATE_PID ASC);

CREATE INDEX PERSONS_PARTNERS_FK1_INDEX_7 ON PARTNERS (PARTNER2 ASC);

CREATE INDEX CONSTRAINT_4B2_INDEX_4 ON SEXES (FROM_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_8 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);

CREATE INDEX CONSTRAINT_7584_INDEX_7 ON PARTNERS (LANGUAGE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_A5 ON SEX_TYPES (SEX_TYPE_PID ASC);

CREATE INDEX CONSTRAINT_75_INDEX_7 ON PARTNERS (FROM_DATE_PID ASC);

CREATE INDEX CONSTRAINT_4A9_INDEX_4 ON PERSON_EVENTS (EVENT_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_30 ON NAME_STYLES (NAME_STYLE_PID ASC);

CREATE INDEX CONSTRAINT_4B24_INDEX_4 ON SEXES (TO_DATE_PID ASC);

CREATE INDEX CONSTRAINT_BC_INDEX_B ON LOCATION_NAMES (TO_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_2 ON PERSONS (PERSON_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_5 ON LOCATIONS (LOCATION_PID ASC);

CREATE INDEX PERSONS_PARENTS_FK1_INDEX_F ON PARENTS (PARENT ASC);

CREATE UNIQUE INDEX DICTIONARY_SEX_TYPES_FK_INDEX_3 ON DICTIONARY (LABEL_PID ASC);

CREATE INDEX CONSTRAINT_C8_INDEX_C ON LOCATION_EVENTS (LOCATION_PID ASC);

CREATE INDEX LOCATION_NAME_STYLES_LOCATION_NAMES_FK_INDEX_B ON LOCATION_NAMES (LOCATION_NAME_STYLE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_FB ON PARENTS (PARENT_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_22 ON USERIDS (USERID_PID ASC);

CREATE INDEX CONSTRAINT_25B_INDEX_2 ON PERSONS (DEATH_DATE_PID ASC);

CREATE INDEX CONSTRAINT_INDEX_5 ON LOCATION_NAME_MAPS (LOCATION_NAME_STYLE_PID ASC);

CREATE INDEX CONSTRAINT_758_INDEX_7 ON PARTNERS (TO_DATE_PID ASC);

CREATE INDEX CONSTRAINT_46_INDEX_4 ON NAMES (FROM_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_4B ON SEXES (SEXES_PID ASC);

CREATE INDEX LANGUAGES_LOCATION_NAME_STYLES_FK_INDEX_E ON LOCATION_NAME_STYLES (LANGUAGE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_5E ON LOCATIONS (LOCATION_PID ASC);

CREATE INDEX CONSTRAINT_25_INDEX_2 ON PERSONS (BIRTH_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_F1 ON NAME_MAPS (NAME_MAP_PID ASC);

CREATE INDEX CONSTRAINT_4A94_INDEX_4 ON PERSON_EVENTS (PERSON_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_B ON LOCATION_NAMES (LOCATION_NAME_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_7 ON EVENTS (EVENT_PID ASC);

CREATE UNIQUE INDEX CONSTRAINT_7A9_INDEX_D ON LOCATIONS (LOCATION_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_C ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);

CREATE INDEX CONSTRAINT_C_INDEX_C ON LOCATION_EVENTS (EVENT_PID ASC);

CREATE INDEX CONSTRAINT_469_INDEX_4 ON NAMES (TO_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_4A ON PERSON_EVENTS (PERSON_EVENT_PID ASC);

CREATE INDEX CONSTRAINT_37_INDEX_3 ON LOCATION_NAME_STYLES (FROM_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_E0 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);

CREATE INDEX CONSTRAINT_FB8_INDEX_F ON PARENTS (LANGUAGE_PID ASC);

CREATE INDEX CONSTRAINT_7A9A_INDEX_7 ON EVENTS (TO_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_50 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_3B ON NAME_PARTS (NAME_PART_PID ASC);

CREATE INDEX PERSONS_SEXES_FK_INDEX_4 ON SEXES (PERSON_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_3 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);

CREATE INDEX NAME_STYLES_NAME_MAPS_FK_INDEX_F ON NAME_MAPS (NAME_STYLE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_BC9 ON LOCATION_NAMES (LOCATION_NAME_PID ASC);

CREATE INDEX PERSONS_PARENTS_FK_INDEX_F ON PARENTS (CHILD ASC);

CREATE INDEX PERSONS_USERIDS_FK_INDEX_2 ON USERIDS (PERSON_PID ASC);

CREATE INDEX CONSTRAINT_B_INDEX_B ON LOCATION_NAMES (FROM_DATE_PID ASC);

CREATE INDEX DICTIONARY_SEX_TYPES_FK_INDEX_A ON SEX_TYPES (LABEL_PID ASC);

CREATE INDEX CONSTRAINT_5E_INDEX_5 ON LOCATIONS (TO_DATE_PID ASC);

CREATE INDEX CONSTRAINT_30_INDEX_3 ON NAME_STYLES (LANGUAGE_PID ASC);

CREATE INDEX CONSTRAINT_INDEX_B ON LOCATION_NAME_PARTS (LOCATION_NAME_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_F ON PARENTS (PARENT_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_D16 ON EVENT_NAMES (EVENT_NAME_PID ASC);

CREATE INDEX CONSTRAINT_7A9_INDEX_7 ON EVENTS (FROM_DATE_PID ASC);

CREATE UNIQUE INDEX PRIMARY_KEY_5EB ON LOCATIONS (LOCATION_PID ASC);

CREATE INDEX CONSTRAINT_5_INDEX_5 ON LOCATIONS (FROM_DATE_PID ASC);

ALTER TABLE NAMES ADD CONSTRAINT CONSTRAINT_4 PRIMARY KEY (NAME_PID);

ALTER TABLE SEXES ADD CONSTRAINT CONSTRAINT_4B PRIMARY KEY (SEXES_PID);

ALTER TABLE EVENT_TYPES ADD CONSTRAINT EVENT_TYPES_PK PRIMARY KEY (EVENT_TYPE_PID);

ALTER TABLE SEX_TYPES ADD CONSTRAINT CONSTRAINT_E PRIMARY KEY (SEX_TYPE_PID);

ALTER TABLE HDATES ADD CONSTRAINT PRIMARY_KEY_7E UNIQUE (HDATE_PID);

ALTER TABLE LOCATIONS ADD CONSTRAINT LOCATIONS_PK PRIMARY KEY (LOCATION_PID);

ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_7 PRIMARY KEY (PARTNER_PID);

ALTER TABLE LOCATION_NAME_MAPS ADD CONSTRAINT LOCATION_NAME_MAPS_PK PRIMARY KEY (LOCATION_NAME_MAP_PID);

ALTER TABLE USERIDS ADD CONSTRAINT PRIMARY_KEY_22 UNIQUE (USERID_PID);

ALTER TABLE NAMES ADD CONSTRAINT PRIMARY_KEY_4 UNIQUE (NAME_PID);

ALTER TABLE COMMIT_LOGS ADD CONSTRAINT COMMIT_LOGS_PK PRIMARY KEY (COMMIT_LOG_PID);

ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A PRIMARY KEY (EVENT_PID);

ALTER TABLE LANGUAGES ADD CONSTRAINT PRIMARY_KEY_9 UNIQUE (LANGUAGE_PID);

ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A PRIMARY KEY (PERSON_EVENT_PID);

ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT LOCATION_NAME_STYLES_PK PRIMARY KEY (LOCATION_NAME_STYLE_PID);

ALTER TABLE EVENT_NAMES ADD CONSTRAINT EVENT_NAMES_PK PRIMARY KEY (EVENT_NAME_PID);

ALTER TABLE NAME_MAPS ADD CONSTRAINT CONSTRAINT_F PRIMARY KEY (NAME_MAP_PID);

ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT PRIMARY_KEY_3 UNIQUE (LOCATION_NAME_STYLE_PID);

ALTER TABLE EVENT_TYPES ADD CONSTRAINT PRIMARY_KEY_D1 UNIQUE (EVENT_TYPE_PID);

ALTER TABLE LANGUAGES ADD CONSTRAINT CONSTRAINT_9 PRIMARY KEY (LANGUAGE_PID);

ALTER TABLE DICTIONARY ADD CONSTRAINT DICTIONARY_PK PRIMARY KEY (DICTIONARY_PID);

ALTER TABLE EVENTS ADD CONSTRAINT PRIMARY_KEY_7 UNIQUE (EVENT_PID);

ALTER TABLE DICTIONARY ADD CONSTRAINT DICTIONARY_SEX_TYPES_FK_INDEX_3 UNIQUE (LABEL_PID);

ALTER TABLE SEX_TYPES ADD CONSTRAINT PRIMARY_KEY_A5 UNIQUE (SEX_TYPE_PID);

ALTER TABLE PERSONS ADD CONSTRAINT PRIMARY_KEY_2 UNIQUE (PERSON_PID);

ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_2 PRIMARY KEY (PERSON_PID);

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT PRIMARY_KEY_B UNIQUE (LOCATION_NAME_PID);

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATION_NAMES_PK PRIMARY KEY (LOCATION_NAME_PID);

ALTER TABLE LOCATIONS ADD CONSTRAINT PRIMARY_KEY_5 UNIQUE (LOCATION_PID);

ALTER TABLE PARENTS ADD CONSTRAINT CONSTRAINT_FB PRIMARY KEY (PARENT_PID);

ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT LOCATION_EVENTS_PK PRIMARY KEY (LOCATION_EVENTS_PID);

ALTER TABLE USERIDS ADD CONSTRAINT USERIDS_PK PRIMARY KEY (USERID_PID);

ALTER TABLE NAME_STYLES ADD CONSTRAINT CONSTRAINT_3 PRIMARY KEY (NAME_STYLE_PID);

ALTER TABLE NAME_PARTS ADD CONSTRAINT CONSTRAINT_3B PRIMARY KEY (NAME_PART_PID);

ALTER TABLE LOCATION_NAME_PARTS ADD CONSTRAINT LOCATION_NAME_PARTS_PK PRIMARY KEY (LOCATION_NAME_PART_PID);

ALTER TABLE HDATES ADD CONSTRAINT HDATES_PK PRIMARY KEY (HDATE_PID);

ALTER TABLE NAME_STYLES ADD CONSTRAINT PRIMARY_KEY_30 UNIQUE (NAME_STYLE_PID);

ALTER TABLE NAME_PARTS ADD CONSTRAINT NAMES_NAME_PARTS_FK FOREIGN KEY (NAME_PID)
	REFERENCES NAMES (NAME_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE COMMIT_LOGS ADD CONSTRAINT USERIDS_COMMIT_LOGS_FK FOREIGN KEY (USERID_PID)
	REFERENCES USERIDS (USERID_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAMES ADD CONSTRAINT NAME_STYLES_NAMES_FK FOREIGN KEY (NAME_STYLE_PID)
	REFERENCES NAME_STYLES (NAME_STYLE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAME_MAPS ADD CONSTRAINT LOCATION_NAME_STYLES_LOCATION_NAME_MAPS_FK FOREIGN KEY (LOCATION_NAME_STYLE_PID)
	REFERENCES LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_7584 FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATIONS ADD CONSTRAINT CONSTRAINT_5 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE SEXES ADD CONSTRAINT CONSTRAINT_4B24 FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAMES ADD CONSTRAINT CONSTRAINT_469 FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_25B FOREIGN KEY (DEATH_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAMES ADD CONSTRAINT PERSONS_NAMES_FK FOREIGN KEY (PERSON_PID)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT CONSTRAINT_B FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT CONSTRAINT_371D FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE EVENT_NAMES ADD CONSTRAINT CONSTRAINT_D1 FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAMES ADD CONSTRAINT CONSTRAINT_46 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE SEXES ADD CONSTRAINT PERSONS_SEXES_FK FOREIGN KEY (PERSON_PID)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARTNERS ADD CONSTRAINT PERSONS_PARTNERS_FK1 FOREIGN KEY (PARTNER2)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A94 FOREIGN KEY (PERSON_PID)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE SEX_TYPES ADD CONSTRAINT DICTIONARY_SEX_TYPES_FK FOREIGN KEY (LABEL_PID)
	REFERENCES DICTIONARY (LABEL_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE EVENT_NAMES ADD CONSTRAINT CONSTRAINT_D FOREIGN KEY (EVENT_TYPE_PID)
	REFERENCES EVENT_TYPES (EVENT_TYPE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATIONS_LOCATION_NAMES_FK FOREIGN KEY (LOCATION_PID)
	REFERENCES LOCATIONS (LOCATION_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAME_MAPS ADD CONSTRAINT NAME_STYLES_NAME_MAPS_FK FOREIGN KEY (NAME_STYLE_PID)
	REFERENCES NAME_STYLES (NAME_STYLE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_75 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATION_NAME_STYLES_LOCATION_NAMES_FK FOREIGN KEY (LOCATION_NAME_STYLE_PID)
	REFERENCES LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARENTS ADD CONSTRAINT CONSTRAINT_FB8 FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_758 FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A9A FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATIONS ADD CONSTRAINT CONSTRAINT_5E FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARENTS ADD CONSTRAINT PERSONS_PARENTS_FK1 FOREIGN KEY (PARENT)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARENTS ADD CONSTRAINT PERSONS_PARENTS_FK FOREIGN KEY (CHILD)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT CONSTRAINT_C FOREIGN KEY (EVENT_PID)
	REFERENCES EVENTS (EVENT_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAME_PARTS ADD CONSTRAINT CONSTRAINT_6 FOREIGN KEY (LOCATION_NAME_PID)
	REFERENCES LOCATION_NAMES (LOCATION_NAME_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PARTNERS ADD CONSTRAINT PERSONS_PARTNERS_FK FOREIGN KEY (PARTNER1)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_25 FOREIGN KEY (BIRTH_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE USERIDS ADD CONSTRAINT PERSONS_USERIDS_FK FOREIGN KEY (PERSON_PID)
	REFERENCES PERSONS (PERSON_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE SEXES ADD CONSTRAINT SEX_TYPES_SEXES_FK FOREIGN KEY (SEX_TYPE_PID)
	REFERENCES SEX_TYPES (SEX_TYPE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A9 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAMES ADD CONSTRAINT CONSTRAINT_BC FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A9 FOREIGN KEY (EVENT_PID)
	REFERENCES EVENTS (EVENT_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT CONSTRAINT_371 FOREIGN KEY (TO_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE NAME_STYLES ADD CONSTRAINT CONSTRAINT_30 FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT CONSTRAINT_37 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A949 FOREIGN KEY (LANGUAGE_PID)
	REFERENCES LANGUAGES (LANGUAGE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT CONSTRAINT_C8 FOREIGN KEY (LOCATION_PID)
	REFERENCES LOCATIONS (LOCATION_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

ALTER TABLE SEXES ADD CONSTRAINT CONSTRAINT_4B2 FOREIGN KEY (FROM_DATE_PID)
	REFERENCES HDATES (HDATE_PID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;

