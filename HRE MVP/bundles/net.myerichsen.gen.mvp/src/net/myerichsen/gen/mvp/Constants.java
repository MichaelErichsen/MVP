package net.myerichsen.gen.mvp;

/**
 * Constants used by database access and event handling
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 11. dec. 2018
 */
public class Constants {
	/**
	 * H2 Data types
	 */
	public static final int BIGINT = -5;
	public static final int BLOB = 2004;
	public static final int BOOLEAN = 16;
	public static final int CHAR = 1;
	public static final int CLOB = 2005;
	public static final int DATE = 91;
	public static final int DOUBLE = 8;
	public static final int INTEGER = 4;
	public static final int SMALLINT = 5;
	public static final int TIMESTAMP = 93;
	public static final int VARBINARY = -3;
	public static final int VARCHAR = 12;

	/**
	 * H2 Database table ids
	 */
	public static final int EVENTS_TABLE_ID = 123;

	/**
	 * Eclipse events
	 */
	public static final String EVENT_PID_UPDATE_TOPIC = "EVENT_PID_UPDATE_TOPIC";
	public static final String EVENT_TYPE_PID_UPDATE_TOPIC = "EVENT_TYPE_PID_UPDATE_TOPIC";
	public static final String HDATE_PID_UPDATE_TOPIC = "HDATE_PID_UPDATE_TOPIC";
	public static final String LANGUAGE_PID_UPDATE_TOPIC = "LANGUAGE_PID_UPDATE_TOPIC";
	public static final String LOCATION_GOOGLE_MAP_UPDATE_TOPIC = "LOCATION_GOOGLE_MAP_UPDATE_TOPIC";
	public static final String LOCATION_NAME_MAP_PID_UPDATE_TOPIC = "LOCATION_NAME_MAP_PID_UPDATE_TOPIC";
	public static final String LOCATION_NAME_PART_PID_UPDATE_TOPIC = "LOCATION_NAME_PART_PID_UPDATE_TOPIC";
	public static final String LOCATION_NAME_PID_UPDATE_TOPIC = "LOCATION_NAME_PID_UPDATE_TOPIC";
	public static final String LOCATION_NAME_STYLE_PID_UPDATE_TOPIC = "LOCATION_NAME_STYLE_PID_UPDATE_TOPIC";
	public static final String LOCATION_PID_UPDATE_TOPIC = "LOCATION_PID_UPDATE_TOPIC";
	public static final String LOG_REFRESH_UPDATE_TOPIC = "LOG_REFRESH_UPDATE_TOPIC";
	public static final String NAME_MAP_PID_UPDATE_TOPIC = "NAME_MAP_PID_UPDATE_TOPIC";
	public static final String NAME_PART_PID_UPDATE_TOPIC = "NAME_PART_PID_UPDATE_TOPIC";
	public static final String NAME_PID_UPDATE_TOPIC = "NAME_PID_UPDATE_TOPIC";
	public static final String NAME_STYLE_PID_UPDATE_TOPIC = "NAME_STYLE_PID_UPDATE_TOPIC";
	public static final String PERSON_PID_UPDATE_TOPIC = "PERSON_PID_UPDATE_TOPIC";
	public static final String SEX_PID_UPDATE_TOPIC = "SEX_PID_UPDATE_TOPIC";
	public static final String SEX_TYPE_PID_UPDATE_TOPIC = "SEX_TYPE_PID_UPDATE_TOPIC";
	public static final String TABLENAME_UPDATE_TOPIC = "TABLENAME_UPDATE_TOPIC";
}
