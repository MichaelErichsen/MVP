package net.myerichsen.hremvp.mockup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class Person {
	protected static final Logger LOGGER = Logger.getLogger("global");

	private int perNo;
	private String adopted;
	private String anceInt;
	private String birthorder;
	private String descInt;
	private int dsid;
	private int father;
	private String flag1;
	private String flag2;
	private String flag3;
	private String flag4;
	private String flag5;
	private String flag6;
	private String flag7;
	private String flag8;
	private String flag9;
	private Date lastEdit;
	private String living;
	private int mother;
	private String multibirth;
	private String pbirth;
	private String pdeath;
	private int refId;
	private String reference;
	private int relate;
	private int relatefo;
	private String scbuff;
	private String sex;
	private int spoulast;
	private String tt;
	private final String SELECT = "SELECT PER_NO, FATHER, MOTHER, LAST_EDIT, DSID, REF_ID, REFERENCE, SPOULAST, SCBUFF, PBIRTH, PDEATH, SEX, LIVING, BIRTHORDER, MULTIBIRTH, ADOPTED, ANCE_INT, DESC_INT, RELATE, RELATEFO, TT, FLAG1, FLAG2, FLAG3, FLAG4, FLAG5, FLAG6, FLAG7, FLAG8, FLAG9  FROM PERSON WHERE PER_NO = ?";

	private final String CHILDCOUNT = "SELECT COUNT(*) FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?";

	private int childCount;
	private PreparedStatement pst = null;

	private PreparedStatement pst1;

	public Person() {
	}

	public Person(Connection conn, int perNo) {
		DateFormat df = null;

		this.perNo = perNo;
		try {
			new ScopedPreferenceStore(InstanceScope.INSTANCE,
					"org.historyresearchenvironment.usergui");
			this.pst = conn.prepareStatement(
					"SELECT PER_NO, FATHER, MOTHER, LAST_EDIT, DSID, REF_ID, REFERENCE, SPOULAST, SCBUFF, PBIRTH, PDEATH, SEX, LIVING, BIRTHORDER, MULTIBIRTH, ADOPTED, ANCE_INT, DESC_INT, RELATE, RELATEFO, TT, FLAG1, FLAG2, FLAG3, FLAG4, FLAG5, FLAG6, FLAG7, FLAG8, FLAG9  FROM PERSON WHERE PER_NO = ?");

			this.pst.setInt(1, perNo);

			ResultSet rs = this.pst.executeQuery();

			if (rs.next()) {
				setFather(rs.getInt("FATHER"));
				setMother(rs.getInt("MOTHER"));
				df = new SimpleDateFormat("yyyy-MM-dd");
				setLastEdit(df.parse(rs.getString("LAST_EDIT")));
				setDsid(rs.getInt("DSID"));
				setSpoulast(rs.getInt("SPOULAST"));
				setPbirth(rs.getString("PBIRTH"));
				setPdeath(rs.getString("PDEATH"));
				setSex(rs.getString("SEX"));

				this.pst1 = conn.prepareStatement(
						"SELECT COUNT(*) FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?");
				this.pst1.setInt(1, perNo);
				ResultSet rs1 = this.pst1.executeQuery();

				if (rs1.next()) {
					setChildCount(rs1.getInt(1));
				} else {
					setChildCount(0);
				}
			}
			try {
				this.pst.close();
				this.pst1.close();
			} catch (Exception localException1) {
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}
	}

	public String getAdopted() {
		return this.adopted;
	}

	public String getAnceInt() {
		return this.anceInt;
	}

	public String getBirthorder() {
		return this.birthorder;
	}

	public int getChildCount() {
		return this.childCount;
	}

	public String getDescInt() {
		return this.descInt;
	}

	public int getDsid() {
		return this.dsid;
	}

	public int getFather() {
		return this.father;
	}

	public String getFlag1() {
		return this.flag1;
	}

	public String getFlag2() {
		return this.flag2;
	}

	public String getFlag3() {
		return this.flag3;
	}

	public String getFlag4() {
		return this.flag4;
	}

	public String getFlag5() {
		return this.flag5;
	}

	public String getFlag6() {
		return this.flag6;
	}

	public String getFlag7() {
		return this.flag7;
	}

	public String getFlag8() {
		return this.flag8;
	}

	public String getFlag9() {
		return this.flag9;
	}

	public Date getLastEdit() {
		return this.lastEdit;
	}

	public String getLiving() {
		return this.living;
	}

	public int getMother() {
		return this.mother;
	}

	public String getMultibirth() {
		return this.multibirth;
	}

	public String getPbirth() {
		return this.pbirth;
	}

	public String getPdeath() {
		return this.pdeath;
	}

	public int getPerNo() {
		return this.perNo;
	}

	public String getReference() {
		return this.reference;
	}

	public int getRefId() {
		return this.refId;
	}

	public int getRelate() {
		return this.relate;
	}

	public int getRelatefo() {
		return this.relatefo;
	}

	public String getScbuff() {
		return this.scbuff;
	}

	public String getSex() {
		return this.sex;
	}

	public int getSpoulast() {
		return this.spoulast;
	}

	public String getTt() {
		return this.tt;
	}

	public void setAdopted(String adopted) {
		this.adopted = adopted;
	}

	public void setAnceInt(String anceInt) {
		this.anceInt = anceInt;
	}

	public void setBirthorder(String birthorder) {
		this.birthorder = birthorder;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public void setDescInt(String descInt) {
		this.descInt = descInt;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

	public void setFather(int father) {
		this.father = father;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public void setFlag4(String flag4) {
		this.flag4 = flag4;
	}

	public void setFlag5(String flag5) {
		this.flag5 = flag5;
	}

	public void setFlag6(String flag6) {
		this.flag6 = flag6;
	}

	public void setFlag7(String flag7) {
		this.flag7 = flag7;
	}

	public void setFlag8(String flag8) {
		this.flag8 = flag8;
	}

	public void setFlag9(String flag9) {
		this.flag9 = flag9;
	}

	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}

	public void setLiving(String living) {
		this.living = living;
	}

	public void setMother(int mother) {
		this.mother = mother;
	}

	public void setMultibirth(String multibirth) {
		this.multibirth = multibirth;
	}

	public void setPbirth(String pbirth) {
		this.pbirth = pbirth;
	}

	public void setPdeath(String pdeath) {
		this.pdeath = pdeath;
	}

	public void setPerNo(int perNo) {
		this.perNo = perNo;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public void setRelate(int relate) {
		this.relate = relate;
	}

	public void setRelatefo(int relatefo) {
		this.relatefo = relatefo;
	}

	public void setScbuff(String scbuff) {
		this.scbuff = scbuff;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setSpoulast(int spoulast) {
		this.spoulast = spoulast;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Person.
 * class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */