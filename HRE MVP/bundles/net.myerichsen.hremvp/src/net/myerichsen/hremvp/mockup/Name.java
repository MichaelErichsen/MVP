package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class Name {
	protected static final Logger LOGGER = Logger.getLogger("global");

	private int recno;

	private int altype;

	private int dsid;

	private String dsure;
	private String fsure;
	private int givid;
	private String gvnamesort;
	private String infg;
	private String infs;
	private String ispicked;
	private Date lastEdit;
	private String ndate;
	private String nnote;
	private int nper;
	private String nsure;
	private String pbirth;
	private String pdeath;
	private int prefId;
	private String refer;
	private String sentence;
	private String sndxgvn;
	private String sndxsurn;
	private String srnamedisp;
	private String srnamesort;
	private String srtdate;
	private int styleid;
	private int surid;
	private String xprimary;

	public Name(Connection conn, int nper) {
		PreparedStatement pst = null;

		this.nper = nper;
		try {
			new ScopedPreferenceStore(InstanceScope.INSTANCE,
					"org.historyresearchenvironment.usergui");
			pst = conn.prepareStatement(
					"SELECT NPER, ALTYPE, ISPICKED, INFS, INFG, XPRIMARY, NSURE, FSURE, NNOTE, RECNO, SENTENCE, NDATE, SRTDATE, DSURE, DSID, TT, SRNAMESORT, GVNAMESORT, STYLEID, SURID, GIVID, SRNAMEDISP, SNDXSURN, SNDXGVN, PBIRTH, PDEATH, REFER, PREF_ID, LAST_EDIT FROM NAME WHERE NPER = ?");
			pst.setInt(1, nper);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				setSrnamedisp(rs.getString("SRNAMEDISP"));
			} else {
				setSrnamedisp("");
			}
			try {
				pst.close();
			} catch (Exception localException1) {
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}
	}

	public int getAltype() {
		return this.altype;
	}

	public int getDsid() {
		return this.dsid;
	}

	public String getDsure() {
		return this.dsure;
	}

	public String getFsure() {
		return this.fsure;
	}

	public int getGivid() {
		return this.givid;
	}

	public String getGvnamesort() {
		return this.gvnamesort;
	}

	public String getInfg() {
		return this.infg;
	}

	public String getInfs() {
		return this.infs;
	}

	public String getIspicked() {
		return this.ispicked;
	}

	public Date getLastEdit() {
		return this.lastEdit;
	}

	public String getNdate() {
		return this.ndate;
	}

	public String getNnote() {
		return this.nnote;
	}

	public int getNper() {
		return this.nper;
	}

	public String getNsure() {
		return this.nsure;
	}

	public String getPbirth() {
		return this.pbirth;
	}

	public String getPdeath() {
		return this.pdeath;
	}

	public int getPrefId() {
		return this.prefId;
	}

	public int getRecno() {
		return this.recno;
	}

	public String getRefer() {
		return this.refer;
	}

	public String getSentence() {
		return this.sentence;
	}

	public String getSndxgvn() {
		return this.sndxgvn;
	}

	public String getSndxsurn() {
		return this.sndxsurn;
	}

	public String getSrnamedisp() {
		return this.srnamedisp;
	}

	public String getSrnamesort() {
		return this.srnamesort;
	}

	public String getSrtdate() {
		return this.srtdate;
	}

	public int getStyleid() {
		return this.styleid;
	}

	public int getSurid() {
		return this.surid;
	}

	public String getXprimary() {
		return this.xprimary;
	}

	public void setAltype(int altype) {
		this.altype = altype;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setDsure(String dsure) {
		this.dsure = dsure;
	}

	public void setFsure(String fsure) {
		this.fsure = fsure;
	}

	public void setGivid(int givid) {
		this.givid = givid;
	}

	public void setGvnamesort(String gvnamesort) {
		this.gvnamesort = gvnamesort;
	}

	public void setInfg(String infg) {
		this.infg = infg;
	}

	public void setInfs(String infs) {
		this.infs = infs;
	}

	public void setIspicked(String ispicked) {
		this.ispicked = ispicked;
	}

	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}

	public void setNdate(String ndate) {
		this.ndate = ndate;
	}

	public void setNnote(String nnote) {
		this.nnote = nnote;
	}

	public void setNper(int nper) {
		this.nper = nper;
	}

	public void setNsure(String nsure) {
		this.nsure = nsure;
	}

	public void setPbirth(String pbirth) {
		this.pbirth = pbirth;
	}

	public void setPdeath(String pdeath) {
		this.pdeath = pdeath;
	}

	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}

	public void setRecno(int recno) {
		this.recno = recno;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setSndxgvn(String sndxgvn) {
		this.sndxgvn = sndxgvn;
	}

	public void setSndxsurn(String sndxsurn) {
		this.sndxsurn = sndxsurn;
	}

	public void setSrnamedisp(String srnamedisp) {
		this.srnamedisp = srnamedisp;
	}

	public void setSrnamesort(String srnamesort) {
		this.srnamesort = srnamesort;
	}

	public void setSrtdate(String srtdate) {
		this.srtdate = srtdate;
	}

	public void setStyleid(int styleid) {
		this.styleid = styleid;
	}

	public void setSurid(int surid) {
		this.surid = surid;
	}

	public void setXprimary(String xprimary) {
		this.xprimary = xprimary;
	}

	public String toString() {
		return

		"Name [recno=" + this.recno + ", altype=" + this.altype + ", dsid="
				+ this.dsid + ", "
				+ (this.dsure != null ? "dsure=" + this.dsure + ", " : "")
				+ (this.fsure != null ? "fsure=" + this.fsure + ", " : "")
				+ "givid=" + this.givid + ", "
				+ (this.gvnamesort != null
						? "gvnamesort=" + this.gvnamesort + ", "
						: "")
				+ (this.infg != null ? "infg=" + this.infg + ", " : "")
				+ (this.infs != null ? "infs=" + this.infs + ", " : "")
				+ (this.ispicked != null ? "ispicked=" + this.ispicked + ", "
						: "")
				+ (this.lastEdit != null ? "lastEdit=" + this.lastEdit + ", "
						: "")
				+ (this.ndate != null ? "ndate=" + this.ndate + ", " : "")
				+ (this.nnote != null ? "nnote=" + this.nnote + ", " : "")
				+ "nper=" + this.nper + ", "
				+ (this.nsure != null ? "nsure=" + this.nsure + ", " : "")
				+ (this.pbirth != null ? "pbirth=" + this.pbirth + ", " : "")
				+ (this.pdeath != null ? "pdeath=" + this.pdeath + ", " : "")
				+ "prefId=" + this.prefId + ", "
				+ (this.refer != null ? "refer=" + this.refer + ", " : "")
				+ (this.sentence != null ? "sentence=" + this.sentence + ", "
						: "")
				+ (this.sndxgvn != null ? "sndxgvn=" + this.sndxgvn + ", " : "")
				+ (this.sndxsurn != null ? "sndxsurn=" + this.sndxsurn + ", "
						: "")
				+ (this.srnamedisp != null
						? "srnamedisp=" + this.srnamedisp + ", "
						: "")
				+ (this.srnamesort != null
						? "srnamesort=" + this.srnamesort + ", "
						: "")
				+ (this.srtdate != null ? "srtdate=" + this.srtdate + ", " : "")
				+ "styleid=" + this.styleid + ", surid=" + this.surid + ", "
				+ (this.xprimary != null ? "xprimary=" + this.xprimary : "")
				+ "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Name.class
 * Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */