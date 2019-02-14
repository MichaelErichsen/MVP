package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class Placepartvalue {
	protected static final Logger LOGGER = Logger.getLogger("global");
	private int dsid;
	private int id;
	private int recno;
	private int type;
	private int uid;
	private Placedictionary pd;
	private final String SELECT = "SELECT RECNO, UID, TYPE, ID, DSID FROM PLACEPARTVALUE WHERE RECNO = ?";
	private PreparedStatement pst = null;

	public Placepartvalue(Connection conn, int i) {
		this.recno = i;
		try {
			this.pst = conn.prepareStatement(
					"SELECT RECNO, UID, TYPE, ID, DSID FROM PLACEPARTVALUE WHERE RECNO = ?");
			this.pst.setInt(1, this.recno);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setDsid(rs.getInt("DSID"));
				setId(rs.getInt("ID"));
				setType(rs.getInt("TYPE"));
				setUid(rs.getInt("UID"));
				setPd(new Placedictionary(conn, this.uid));
			} else {
				setRecno(0);
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

	public Placedictionary getPd() {
		return this.pd;
	}

	public int getRecno() {
		return this.recno;
	}

	public int getType() {
		return this.type;
	}

	public int getUid() {
		return this.uid;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPd(Placedictionary pd) {
		this.pd = pd;
	}

	public void setRecno(int recno) {
		this.recno = recno;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String toString() {
		return "Placepartvalue [dsid=" + this.dsid + ", id=" + this.id
				+ ", recno=" + this.recno + ", type=" + this.type + ", uid="
				+ this.uid + ", " + (this.pd != null ? "pd=" + this.pd : "")
				+ "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\
 * Placepartvalue.class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */