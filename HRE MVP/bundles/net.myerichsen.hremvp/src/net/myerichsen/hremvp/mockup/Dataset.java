package net.myerichsen.hremvp.mockup;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dataset {
	private static final String SELECT = "SELECT DSID, DSNAME, DSLOCATION, DSTYPE, DSLOCKED, DSENABLED, PROPERTY, DSP, DSP2, DCOMMENT, HOST, NAMESTYLE, PLACESTYLE FROM DATASET WHERE DSID = ?";
	private int dsid;
	private String dcomment;
	private String dsenabled;
	private String dslocation;
	private String dslocked;
	private String dsname;
	private String dsp;
	private String dsp2;
	private BigDecimal dstype;
	private String host;
	private int namestyle;
	private int placestyle;
	private String property;
	private String tt;
	private PreparedStatement pst = null;

	public static void main(String[] args) {
	}

	public Dataset(Connection conn, int dsid) {
		this.dsid = dsid;
		try {
			this.pst = conn.prepareStatement(
					"SELECT DSID, DSNAME, DSLOCATION, DSTYPE, DSLOCKED, DSENABLED, PROPERTY, DSP, DSP2, DCOMMENT, HOST, NAMESTYLE, PLACESTYLE FROM DATASET WHERE DSID = ?");

			this.pst.setInt(1, dsid);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setDcomment(rs.getString("DCOMMENT"));
				setDsenabled(rs.getString("DSENABLED"));
				setDslocation(rs.getString("DSLOCATION"));
				setDslocked(rs.getString("DSLOCKED"));
				setDsname(rs.getString("DSNAME"));
				setDsp(rs.getString("DSP"));
				setDsp2(rs.getString("DSP2"));
				setDstype(rs.getBigDecimal("DSTYPE"));
				setHost(rs.getString("HOST"));
				setNamestyle(rs.getInt("NAMESTYLE"));
				setPlacestyle(rs.getInt("PLACESTYLE"));
				setProperty(rs.getString("PROPERTY"));
			}
			try {
				this.pst.close();
			} catch (Exception localException1) {
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDcomment() {
		return this.dcomment;
	}

	public String getDsenabled() {
		return this.dsenabled;
	}

	public int getDsid() {
		return this.dsid;
	}

	public String getDslocation() {
		return this.dslocation;
	}

	public String getDslocked() {
		return this.dslocked;
	}

	public String getDsname() {
		return this.dsname;
	}

	public String getDsp() {
		return this.dsp;
	}

	public String getDsp2() {
		return this.dsp2;
	}

	public BigDecimal getDstype() {
		return this.dstype;
	}

	public String getHost() {
		return this.host;
	}

	public int getNamestyle() {
		return this.namestyle;
	}

	public int getPlacestyle() {
		return this.placestyle;
	}

	public String getProperty() {
		return this.property;
	}

	public String getTt() {
		return this.tt;
	}

	public void setDcomment(String dcomment) {
		this.dcomment = dcomment;
	}

	public void setDsenabled(String dsenabled) {
		this.dsenabled = dsenabled;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setDslocation(String dslocation) {
		this.dslocation = dslocation;
	}

	public void setDslocked(String dslocked) {
		this.dslocked = dslocked;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public void setDsp(String dsp) {
		this.dsp = dsp;
	}

	public void setDsp2(String dsp2) {
		this.dsp2 = dsp2;
	}

	public void setDstype(BigDecimal dstype) {
		this.dstype = dstype;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setNamestyle(int namestyle) {
		this.namestyle = namestyle;
	}

	public void setPlacestyle(int placestyle) {
		this.placestyle = placestyle;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String toString() {
		return

		"Dataset [dsid=" + this.dsid + ", dcomment=" + this.dcomment
				+ ", dsenabled=" + this.dsenabled + ", dslocation="
				+ this.dslocation + ", dslocked=" + this.dslocked + ", dsname="
				+ this.dsname + ", dsp=" + this.dsp + ", dsp2=" + this.dsp2
				+ ", dstype=" + this.dstype + ", host=" + this.host
				+ ", namestyle=" + this.namestyle + ", placestyle="
				+ this.placestyle + ", property=" + this.property + "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Dataset.
 * class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */