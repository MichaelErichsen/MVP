package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class Eventwitness {
	private static final Logger LOGGER = Logger.getLogger("global");

	private int dsid;
	private int eper;
	private int gnum;
	private int namerec;
	private String role;
	private int sequence;
	private String tt;
	private String witmemo;
	private String wsentence;
	private String xprimary;
	private final String SELECT = "SELECT EPER, GNUM, XPRIMARY, WSENTENCE, ROLE, DSID, NAMEREC, WITMEMO, SEQUENCE FROM EVENTWITNESS WHERE EPER = ?";

	private PreparedStatement pst = null;
	private TmgEvent event;
	private Name name;

	public Eventwitness() {
	}

	public Eventwitness(Connection conn, int i) {
		this.eper = i;
		try {
			ScopedPreferenceStore store = new ScopedPreferenceStore(
					InstanceScope.INSTANCE,
					"org.historyresearchenvironment.usergui");
			conn = DriverManager.getConnection(
					"jdbc:h2:" + store.getString("DBNAME") + ";IFEXISTS=TRUE",
					store.getString("H2USERID"), store.getString("H2PASSWORD"));
			this.pst = conn.prepareStatement(
					"SELECT EPER, GNUM, XPRIMARY, WSENTENCE, ROLE, DSID, NAMEREC, WITMEMO, SEQUENCE FROM EVENTWITNESS WHERE EPER = ?");

			this.pst.setInt(1, this.eper);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setDsid(rs.getInt("DSID"));
				setGnum(rs.getInt("GNUM"));
				setEvent(new TmgEvent(conn, this.gnum));
				setNamerec(rs.getInt("NAMEREC"));
				setName(new Name(conn, this.namerec));
				setRole(rs.getString("ROLE"));
				setSequence(rs.getInt("SEQUENCE"));
				setWsentence(rs.getString("WSENTENCE"));
				setXprimary(rs.getString("XPRIMARY"));
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

	public int getEper() {
		return this.eper;
	}

	public TmgEvent getEvent() {
		return this.event;
	}

	public int getGnum() {
		return this.gnum;
	}

	public Name getName() {
		return this.name;
	}

	public int getNamerec() {
		return this.namerec;
	}

	public String getRole() {
		return this.role;
	}

	public int getSequence() {
		return this.sequence;
	}

	public String getTt() {
		return this.tt;
	}

	public String getWitmemo() {
		return this.witmemo;
	}

	public String getWsentence() {
		return this.wsentence;
	}

	public String getXprimary() {
		return this.xprimary;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setEper(int eper) {
		this.eper = eper;
	}

	public void setEvent(TmgEvent event) {
		this.event = event;
	}

	public void setGnum(int gnum) {
		this.gnum = gnum;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setNamerec(int namerec) {
		this.namerec = namerec;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public void setWitmemo(String witmemo) {
		this.witmemo = witmemo;
	}

	public void setWsentence(String wsentence) {
		this.wsentence = wsentence;
	}

	public void setXprimary(String xprimary) {
		this.xprimary = xprimary;
	}

	public String toString() {
		return

		"Eventwitness [dsid=" + this.dsid + ", eper=" + this.eper + ", gnum="
				+ this.gnum + ", namerec=" + this.namerec + ", "
				+ (this.role != null ? "role=" + this.role + ", " : "")
				+ "sequence=" + this.sequence + ", "
				+ (this.tt != null ? "tt=" + this.tt + ", " : "")
				+ (this.witmemo != null ? "witmemo=" + this.witmemo + ", " : "")
				+ (this.wsentence != null ? "wsentence=" + this.wsentence + ", "
						: "")
				+ (this.xprimary != null ? "xprimary=" + this.xprimary + ", "
						: "")
				+ (this.event != null ? "event=" + this.event + ", " : "")
				+ (this.name != null ? "name=" + this.name : "") + "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\
 * Eventwitness.class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */