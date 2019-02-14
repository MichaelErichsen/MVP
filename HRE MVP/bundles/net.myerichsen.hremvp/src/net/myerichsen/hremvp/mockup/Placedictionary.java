package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class Placedictionary {
	protected static final Logger LOGGER = Logger.getLogger("global");
	private int uid;
	private String soundex;
	private String tt;
	private String xvalue;
	private final String SELECT = "SELECT XVALUE, SDX FROM PLACEDICTIONARY WHERE UID = ?";
	private PreparedStatement pst = null;

	public Placedictionary(Connection conn, int i) {
		this.uid = i;
		try {
			this.pst = conn.prepareStatement(
					"SELECT XVALUE, SDX FROM PLACEDICTIONARY WHERE UID = ?");
			this.pst.setInt(1, this.uid);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setSoundex(rs.getString("SDX"));
				setXvalue(rs.getString("XVALUE"));
			} else {
				setUid(0);
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

	public String getSoundex() {
		return this.soundex;
	}

	public String getTt() {
		return this.tt;
	}

	public int getUid() {
		return this.uid;
	}

	public String getXvalue() {
		return this.xvalue;
	}

	public void setSoundex(String sdx) {
		this.soundex = sdx;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setXvalue(String xvalue) {
		this.xvalue = xvalue;
	}

	public String toString() {
		return "Placedictionary [uid=" + this.uid + ", "
				+ (this.soundex != null ? "soundex=" + this.soundex + ", " : "")
				+ (this.xvalue != null ? "xvalue=" + this.xvalue : "") + "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\
 * Placedictionary.class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */