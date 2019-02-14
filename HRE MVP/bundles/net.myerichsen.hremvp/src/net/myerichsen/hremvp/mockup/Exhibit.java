package net.myerichsen.hremvp.mockup;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class Exhibit {
	protected static final Logger LOGGER = Logger.getLogger("global");

	private static final String SELECT = "SELECT IDEXHIBIT, IDREF, RLTYPE, RLNUM, XNAME, VFILENAME, IFILENAME, AFILENAME, TFILENAME, REFERENCE, TEXT, IMAGE, AUDIO, DESCRIPT, RLPER1, RLPER2, RLGTYPE, OLE_OBJECT, XPRIMARY, VIDEO, PROPERTY, DSID, TT, ID_PERSON, RECNO, ID_EVENT, ID_SOURCE, ID_REPOS, THUMB, ID_CIT, ID_PLACE, CAPTION, SORTEXH, IMAGEFORE, IMAGEBACK, TRANSPAR FROM EXHIBIT WHERE IDEXHIBIT = ?";

	private int idexhibit;

	private String afilename;

	private String audio;

	private String caption;

	private String descript;

	private int dsid;

	private int idCit;
	private int idEvent;
	private int idPerson;
	private int idPlace;
	private int idRepos;
	private int idSource;
	private int idref;
	private String ifilename;
	private String image;
	private String imageback;
	private String imagefore;
	private String oleObject;
	private String property;
	private int recno;
	private String reference;
	private int rlgtype;
	private int rlnum;
	private int rlper1;
	private int rlper2;
	private String rltype;
	private int sortexh;
	private String text;
	private String tfilename;
	private String thumb;
	private BigDecimal transpar;
	private String vfilename;
	private String video;
	private String xname;
	private String xprimary;
	private Person person;
	private TmgEvent event;
	private PreparedStatement pst = null;

	public Exhibit(Connection conn, int i) {
		this.idexhibit = i;
		try {
			ScopedPreferenceStore store = new ScopedPreferenceStore(
					InstanceScope.INSTANCE,
					"org.historyresearchenvironment.usergui");
			conn = DriverManager.getConnection(
					"jdbc:h2:" + store.getString("DBNAME") + ";IFEXISTS=TRUE",
					store.getString("H2USERID"), store.getString("H2PASSWORD"));
			this.pst = conn.prepareStatement(
					"SELECT IDEXHIBIT, IDREF, RLTYPE, RLNUM, XNAME, VFILENAME, IFILENAME, AFILENAME, TFILENAME, REFERENCE, TEXT, IMAGE, AUDIO, DESCRIPT, RLPER1, RLPER2, RLGTYPE, OLE_OBJECT, XPRIMARY, VIDEO, PROPERTY, DSID, TT, ID_PERSON, RECNO, ID_EVENT, ID_SOURCE, ID_REPOS, THUMB, ID_CIT, ID_PLACE, CAPTION, SORTEXH, IMAGEFORE, IMAGEBACK, TRANSPAR FROM EXHIBIT WHERE IDEXHIBIT = ?");

			this.pst.setInt(1, this.idexhibit);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setXname(rs.getString("XNAME"));
				setIdPerson(rs.getInt("ID_PERSON"));
				if (this.idPerson > 0) {
					this.person = new Person(conn, this.idPerson);
				}
				setIdEvent(rs.getInt("ID_EVENT"));
				if (this.idEvent > 0) {
					this.event = new TmgEvent(conn, this.idEvent);
				}
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

	public String getAfilename() {
		return this.afilename;
	}

	public String getAudio() {
		return this.audio;
	}

	public String getCaption() {
		return this.caption;
	}

	public String getDescript() {
		return this.descript;
	}

	public int getDsid() {
		return this.dsid;
	}

	public TmgEvent getEvent() {
		return this.event;
	}

	public int getIdCit() {
		return this.idCit;
	}

	public int getIdEvent() {
		return this.idEvent;
	}

	public int getIdexhibit() {
		return this.idexhibit;
	}

	public int getIdPerson() {
		return this.idPerson;
	}

	public int getIdPlace() {
		return this.idPlace;
	}

	public int getIdref() {
		return this.idref;
	}

	public int getIdRepos() {
		return this.idRepos;
	}

	public int getIdSource() {
		return this.idSource;
	}

	public String getIfilename() {
		return this.ifilename;
	}

	public String getImage() {
		return this.image;
	}

	public String getImageback() {
		return this.imageback;
	}

	public String getImagefore() {
		return this.imagefore;
	}

	public String getOleObject() {
		return this.oleObject;
	}

	public Person getPerson() {
		return this.person;
	}

	public String getProperty() {
		return this.property;
	}

	public int getRecno() {
		return this.recno;
	}

	public String getReference() {
		return this.reference;
	}

	public int getRlgtype() {
		return this.rlgtype;
	}

	public int getRlnum() {
		return this.rlnum;
	}

	public int getRlper1() {
		return this.rlper1;
	}

	public int getRlper2() {
		return this.rlper2;
	}

	public String getRltype() {
		return this.rltype;
	}

	public int getSortexh() {
		return this.sortexh;
	}

	public String getText() {
		return this.text;
	}

	public String getTfilename() {
		return this.tfilename;
	}

	public String getThumb() {
		return this.thumb;
	}

	public BigDecimal getTranspar() {
		return this.transpar;
	}

	public String getVfilename() {
		return this.vfilename;
	}

	public String getVideo() {
		return this.video;
	}

	public String getXname() {
		return this.xname;
	}

	public String getXprimary() {
		return this.xprimary;
	}

	public void setAfilename(String afilename) {
		this.afilename = afilename;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setEvent(TmgEvent event) {
		this.event = event;
	}

	public void setIdCit(int idCit) {
		this.idCit = idCit;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public void setIdexhibit(int idexhibit) {
		this.idexhibit = idexhibit;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}

	public void setIdref(int idref) {
		this.idref = idref;
	}

	public void setIdRepos(int idRepos) {
		this.idRepos = idRepos;
	}

	public void setIdSource(int idSource) {
		this.idSource = idSource;
	}

	public void setIfilename(String ifilename) {
		this.ifilename = ifilename;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImageback(String imageback) {
		this.imageback = imageback;
	}

	public void setImagefore(String imagefore) {
		this.imagefore = imagefore;
	}

	public void setOleObject(String oleObject) {
		this.oleObject = oleObject;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setRecno(int recno) {
		this.recno = recno;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setRlgtype(int rlgtype) {
		this.rlgtype = rlgtype;
	}

	public void setRlnum(int rlnum) {
		this.rlnum = rlnum;
	}

	public void setRlper1(int rlper1) {
		this.rlper1 = rlper1;
	}

	public void setRlper2(int rlper2) {
		this.rlper2 = rlper2;
	}

	public void setRltype(String rltype) {
		this.rltype = rltype;
	}

	public void setSortexh(int sortexh) {
		this.sortexh = sortexh;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTfilename(String tfilename) {
		this.tfilename = tfilename;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public void setTranspar(BigDecimal transpar) {
		this.transpar = transpar;
	}

	public void setVfilename(String vfilename) {
		this.vfilename = vfilename;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public void setXname(String xname) {
		this.xname = xname;
	}

	public void setXprimary(String xprimary) {
		this.xprimary = xprimary;
	}

	public String toString() {
		return "Exhibit [idexhibit=" + this.idexhibit + ", "
				+ (this.person != null ? "person=" + this.person + ", " : "")
				+ (this.event != null ? "event=" + this.event : "") + "]";
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Exhibit.
 * class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */