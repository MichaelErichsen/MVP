package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class Place {
	protected static final Logger LOGGER = Logger.getLogger("global");
	private int recno;
	private String comment;
	private int dsid;
	private String endyear;
	private String shortplace;
	private String startyear;
	private int styleid;
	private String tt;
	private final String SELECT = "SELECT RECNO, STYLEID, DSID, TT, STARTYEAR, ENDYEAR, COMMENT, SHORTPLACE FROM PLACE WHERE RECNO = ?";

	private PreparedStatement pst = null;

	private Placepartvalue ppv;

	public Place(Connection conn, int i) {
		this.recno = i;
		try {
			this.pst = conn.prepareStatement(
					"SELECT RECNO, STYLEID, DSID, TT, STARTYEAR, ENDYEAR, COMMENT, SHORTPLACE FROM PLACE WHERE RECNO = ?");
			this.pst.setInt(1, this.recno);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setComment(rs.getString("COMMENT"));
				setDsid(rs.getInt("DSID"));
				setEndyear(rs.getString("ENDYEAR"));
				setShortplace(rs.getString("SHORTPLACE"));
				setStartyear(rs.getString("STARTYEAR"));
				setStyleid(rs.getInt("STYLEID"));
				setPpv(new Placepartvalue(conn, this.recno));
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

	public String getComment() {
		return this.comment;
	}

	public int getDsid() {
		return this.dsid;
	}

	public String getEndyear() {
		return this.endyear;
	}

	public Placepartvalue getPpv() {
		return this.ppv;
	}

	public int getRecno() {
		return this.recno;
	}

	public String getShortplace() {
		return this.shortplace;
	}

	public String getStartyear() {
		return this.startyear;
	}

	public int getStyleid() {
		return this.styleid;
	}

	public String getTt() {
		return this.tt;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}

	public void setPpv(Placepartvalue ppv) {
		this.ppv = ppv;
	}

	public void setRecno(int recno) {
		this.recno = recno;
	}

	public void setShortplace(String shortplace) {
		this.shortplace = shortplace;
	}

	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}

	public void setStyleid(int styleid) {
		this.styleid = styleid;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String toString() {
		return

		"Place [recno=" + this.recno + ", "
				+ (this.comment != null ? "comment=" + this.comment + ", " : "")
				+ "dsid=" + this.dsid + ", "
				+ (this.endyear != null ? "endyear=" + this.endyear + ", " : "")
				+ (this.shortplace != null
						? "shortplace=" + this.shortplace + ", "
						: "")
				+ (this.startyear != null ? "startyear=" + this.startyear + ", "
						: "")
				+ "styleid=" + this.styleid + "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Place.class
 * Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */