package net.myerichsen.hremvp.mockup;
 
 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
 
 
 
 
 
 
 
 public class TmgEvent
 {
   protected static final Logger LOGGER = Logger.getLogger("global");
   
   private static final String SELECT = "SELECT ETYPE, DSID, PER1SHOW, PER2SHOW, PER1, PER2, EDATE, PLACENUM, EFOOT, ENSURE, ESSURE, EDSURE, EPSURE, EFSURE, RECNO, SENTENCE, SRTDATE, TT, REF_ID FROM EVENT WHERE RECNO = ?";
   
   private int recno;
   
   private int dsid;
   
   private String edate;
   private String edsure;
   private String efoot;
   private String efsure;
   private String ensure;
   private String epsure;
   private String essure;
   private int etype;
   private int per1;
   private String per1show;
   private int per2;
   private String per2show;
   private int placenum;
   private int refId;
   private String sentence;
   private String srtdate;
   private Placepartvalue ppv;
   private String placestring = "";
   private Tagtype tagtype;
   private PreparedStatement pst = null;
   
   public TmgEvent() {}
   
   public TmgEvent(Connection conn, int i)
   {
     this.recno = i;
     try {
       this.pst = conn.prepareStatement("SELECT ETYPE, DSID, PER1SHOW, PER2SHOW, PER1, PER2, EDATE, PLACENUM, EFOOT, ENSURE, ESSURE, EDSURE, EPSURE, EFSURE, RECNO, SENTENCE, SRTDATE, TT, REF_ID FROM EVENT WHERE RECNO = ?");
       
       this.pst.setInt(1, this.recno);
       
       ResultSet rs = this.pst.executeQuery();
       
       if (rs.next()) {
         setDsid(rs.getInt("DSID"));
         setEdate(rs.getString("EDATE").trim());
         setEdsure(rs.getString("EDSURE"));
         setEfoot(rs.getString("EFOOT"));
         setEnsure(rs.getString("ENSURE"));
         setEpsure(rs.getString("EPSURE"));
         setEssure(rs.getString("ESSURE"));
         setEtype(rs.getInt("ETYPE"));
         setTagtype(new Tagtype(conn, this.etype));
         setPer1(rs.getInt("PER1"));
         setPer1show(rs.getString("PER1SHOW"));
         setPer2(rs.getInt("PER2"));
         setPer2show(rs.getString("PER2SHOW"));
         setPlacenum(rs.getInt("PLACENUM"));
         setPpv(new Placepartvalue(conn, this.placenum));
         setRefId(rs.getInt("REF_ID"));
         setSentence(rs.getString("SENTENCE"));
         setSrtdate(rs.getString("SRTDATE").trim());
       }
       try
       {
         this.pst.close();
       }
       catch (Exception localException1) {}
       return;
     } catch (Exception e) {
       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
     }
   }
   
   public int getDsid() {
     return this.dsid;
   }
   
   public String getEdate() {
     return this.edate;
   }
   
   public String getEdsure() {
     return this.edsure;
   }
   
   public String getEfoot() {
     return this.efoot;
   }
   
   public String getEfsure() {
     return this.efsure;
   }
   
   public String getEnsure() {
     return this.ensure;
   }
   
   public String getEpsure() {
     return this.epsure;
   }
   
   public String getEssure() {
     return this.essure;
   }
   
   public int getEtype() {
     return this.etype;
   }
   
   public int getPer1() {
     return this.per1;
   }
   
   public String getPer1show() {
     return this.per1show;
   }
   
   public int getPer2() {
     return this.per2;
   }
   
   public String getPer2show() {
     return this.per2show;
   }
   
   public int getPlacenum() {
     return this.placenum;
   }
   
 
 
   public String getPlacestring()
   {
     return this.placestring;
   }
   
 
 
   public Placepartvalue getPpv()
   {
     return this.ppv;
   }
   
   public int getRecno() {
     return this.recno;
   }
   
   public int getRefId() {
     return this.refId;
   }
   
   public String getSentence() {
     return this.sentence;
   }
   
   public String getSrtdate() {
     return this.srtdate;
   }
   
 
 
   public Tagtype getTagtype()
   {
     return this.tagtype;
   }
   
   public void setDsid(int dsid) {
     this.dsid = dsid;
   }
   
   public void setEdate(String string) {
     this.edate = string;
   }
   
   public void setEdsure(String edsure) {
     this.edsure = edsure;
   }
   
   public void setEfoot(String efoot) {
     this.efoot = efoot;
   }
   
   public void setEfsure(String efsure) {
     this.efsure = efsure;
   }
   
   public void setEnsure(String ensure) {
     this.ensure = ensure;
   }
   
   public void setEpsure(String epsure) {
     this.epsure = epsure;
   }
   
   public void setEssure(String essure) {
     this.essure = essure;
   }
   
   public void setEtype(int etype) {
     this.etype = etype;
   }
   
   public void setPer1(int per1) {
     this.per1 = per1;
   }
   
   public void setPer1show(String per1show) {
     this.per1show = per1show;
   }
   
   public void setPer2(int per2) {
     this.per2 = per2;
   }
   
   public void setPer2show(String per2show) {
     this.per2show = per2show;
   }
   
   public void setPlacenum(int int1) {
     this.placenum = int1;
   }
   
 
 
 
   public void setPlacestring(String placestring)
   {
     this.placestring = placestring;
   }
   
 
 
 
   public void setPpv(Placepartvalue ppv)
   {
     this.ppv = ppv;
   }
   
   public void setRecno(int recno) {
     this.recno = recno;
   }
   
   public void setRefId(int refId) {
     this.refId = refId;
   }
   
   public void setSentence(String sentence) {
     this.sentence = sentence;
   }
   
   public void setSrtdate(String srtdate) {
     this.srtdate = srtdate;
   }
   
 
 
 
   public void setTagtype(Tagtype tagtype)
   {
     this.tagtype = tagtype;
   }
   
 
 
 
 
 
   public String toString()
   {
     return 
     
 
 
 
 
 
 
       "TmgEvent [recno=" + this.recno + ", dsid=" + this.dsid + ", " + (this.edate != null ? "edate=" + this.edate + ", " : "") + (this.edsure != null ? "edsure=" + this.edsure + ", " : "") + (this.efoot != null ? "efoot=" + this.efoot + ", " : "") + (this.efsure != null ? "efsure=" + this.efsure + ", " : "") + (this.ensure != null ? "ensure=" + this.ensure + ", " : "") + (this.epsure != null ? "epsure=" + this.epsure + ", " : "") + (this.essure != null ? "essure=" + this.essure + ", " : "") + "etype=" + this.etype + ", per1=" + this.per1 + ", " + (this.per1show != null ? "per1show=" + this.per1show + ", " : "") + "per2=" + this.per2 + ", " + (this.per2show != null ? "per2show=" + this.per2show + ", " : "") + "placenum=" + this.placenum + ", refId=" + this.refId + ", " + (this.sentence != null ? "sentence=" + this.sentence + ", " : "") + (this.srtdate != null ? "srtdate=" + this.srtdate + ", " : "") + (this.ppv != null ? "ppv=" + this.ppv + ", " : "") + (this.tagtype != null ? "tagtype=" + this.tagtype : "") + "]";
   }
 }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\TmgEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */