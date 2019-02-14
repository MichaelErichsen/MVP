package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class Placeparttype {
	protected static final Logger LOGGER = Logger.getLogger("global");
	private int id;
	private int dsid;
	private String shortvalue;
	private String system;
	private String tt;
	private int type;
	private String xvalue;
	private final String SELECT = "SELECT ID, TYPE, VALUE, SYSTEM, SHORTVALUE, TT, DSID FROM PLACEPARTTYPE WHERE ID = ?";

	private PreparedStatement pst = null;

	public Placeparttype(Connection conn, int i) {
		this.id = i;
		try {
			this.pst = conn.prepareStatement(
					"SELECT ID, TYPE, VALUE, SYSTEM, SHORTVALUE, TT, DSID FROM PLACEPARTTYPE WHERE ID = ?");
			this.pst.setInt(1, this.id);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setDsid(rs.getInt("DSID"));
				setShortvalue(rs.getString("SHORTVALUE"));
				setSystem(rs.getString("SYSTEM"));
				setType(rs.getInt("TYPE"));
				setXvalue(rs.getString("VALUE"));
			} else {
				setId(0);
			}
			try {
				this.pst.close();
			} catch (Exception localException1) {
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line "
					+ e.getStackTrace()[0].getLineNumber());
		}
	}

	public int getDsid() {
		return this.dsid;
	}

	public int getId() {
		return this.id;
	}

	public String getShortvalue() {
		return this.shortvalue;
	}

	public String getSystem() {
		return this.system;
	}

	public String getTt() {
		return this.tt;
	}

	public int getType() {
		return this.type;
	}

	public String getXvalue() {
		return this.xvalue;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setShortvalue(String shortvalue) {
		this.shortvalue = shortvalue;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setXvalue(String xvalue) {
		this.xvalue = xvalue;
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\
 * Placeparttype.class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */