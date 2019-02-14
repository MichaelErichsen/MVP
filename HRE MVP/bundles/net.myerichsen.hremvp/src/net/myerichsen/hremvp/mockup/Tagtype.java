package net.myerichsen.hremvp.mockup;
 
 import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
 
 
 
 
 
 
 
 public class Tagtype
 {
   protected static final Logger LOGGER = Logger.getLogger("global");
   
   private static final String SELECT = "SELECT ISPICKED, DSID, ACTIVE, ETYPENUM, ORIGETYPE, ADMIN, LDSONLY, ETYPENAME, GEDCOM_TAG, ISREPORT, TSENTENCE, ABBREV, WITDISP, PASTTENSE, WITROLE, MAXYEAR, MINYEAR, REMINDERS, PROPERTIES FROM TAGTYPE WHERE ETYPENUM = ?";
   
   private int etypenum;
   
   private String abbrev;
   
   private String active;
   private BigDecimal admin;
   private int dsid;
   private String etypename;
   private String gedcomTag;
   private String ispicked;
   private String isreport;
   private String ldsonly;
   private BigDecimal maxyear;
   private BigDecimal minyear;
   private int origetype;
   private String pasttense;
   private String place4;
   private String prinrole;
   private String properties;
   private String reminders;
   private String tsentence;
   private String tt;
   private String witdisp;
   private String witrole;
   private PreparedStatement pst = null;
   
   public Tagtype(Connection conn, int i) {
     this.etypenum = i;
     try
     {
       this.pst = conn.prepareStatement("SELECT ISPICKED, DSID, ACTIVE, ETYPENUM, ORIGETYPE, ADMIN, LDSONLY, ETYPENAME, GEDCOM_TAG, ISREPORT, TSENTENCE, ABBREV, WITDISP, PASTTENSE, WITROLE, MAXYEAR, MINYEAR, REMINDERS, PROPERTIES FROM TAGTYPE WHERE ETYPENUM = ?");
       
       this.pst.setInt(1, this.etypenum);
       
       ResultSet rs = this.pst.executeQuery();
       
       if (rs.next()) {
         setDsid(rs.getInt("DSID"));
         setEtypename(rs.getString("ETYPENAME").trim());
       }
       try
       {
         this.pst.close();
       }
       catch (Exception localException1) {}
       return;
     } catch (Exception e) {
       e.printStackTrace();
       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
     }
   }
   
   public String getAbbrev() {
     return this.abbrev;
   }
   
   public String getActive() {
     return this.active;
   }
   
   public BigDecimal getAdmin() {
     return this.admin;
   }
   
   public int getDsid() {
     return this.dsid;
   }
   
   public String getEtypename() {
     return this.etypename;
   }
   
   public int getEtypenum() {
     return this.etypenum;
   }
   
   public String getGedcomTag() {
     return this.gedcomTag;
   }
   
   public String getIspicked() {
     return this.ispicked;
   }
   
   public String getIsreport() {
     return this.isreport;
   }
   
   public String getLdsonly() {
     return this.ldsonly;
   }
   
   public BigDecimal getMaxyear() {
     return this.maxyear;
   }
   
   public BigDecimal getMinyear() {
     return this.minyear;
   }
   
   public int getOrigetype() {
     return this.origetype;
   }
   
   public String getPasttense() {
     return this.pasttense;
   }
   
   public String getPlace4() {
     return this.place4;
   }
   
   public String getPrinrole() {
     return this.prinrole;
   }
   
   public String getProperties() {
     return this.properties;
   }
   
   public String getReminders() {
     return this.reminders;
   }
   
   public String getTsentence() {
     return this.tsentence;
   }
   
   public String getTt() {
     return this.tt;
   }
   
   public String getWitdisp() {
     return this.witdisp;
   }
   
   public String getWitrole() {
     return this.witrole;
   }
   
   public void setAbbrev(String abbrev) {
     this.abbrev = abbrev;
   }
   
   public void setActive(String active) {
     this.active = active;
   }
   
   public void setAdmin(BigDecimal admin) {
     this.admin = admin;
   }
   
   public void setDsid(int dsid) {
     this.dsid = dsid;
   }
   
   public void setEtypename(String etypename) {
     this.etypename = etypename;
   }
   
   public void setEtypenum(int etypenum) {
     this.etypenum = etypenum;
   }
   
   public void setGedcomTag(String gedcomTag) {
     this.gedcomTag = gedcomTag;
   }
   
   public void setIspicked(String ispicked) {
     this.ispicked = ispicked;
   }
   
   public void setIsreport(String isreport) {
     this.isreport = isreport;
   }
   
   public void setLdsonly(String ldsonly) {
     this.ldsonly = ldsonly;
   }
   
   public void setMaxyear(BigDecimal maxyear) {
     this.maxyear = maxyear;
   }
   
   public void setMinyear(BigDecimal minyear) {
     this.minyear = minyear;
   }
   
   public void setOrigetype(int origetype) {
     this.origetype = origetype;
   }
   
   public void setPasttense(String pasttense) {
     this.pasttense = pasttense;
   }
   
   public void setPlace4(String place4) {
     this.place4 = place4;
   }
   
   public void setPrinrole(String prinrole) {
     this.prinrole = prinrole;
   }
   
   public void setProperties(String properties) {
     this.properties = properties;
   }
   
   public void setReminders(String reminders) {
     this.reminders = reminders;
   }
   
   public void setTsentence(String tsentence) {
     this.tsentence = tsentence;
   }
   
   public void setTt(String tt) {
     this.tt = tt;
   }
   
   public void setWitdisp(String witdisp) {
     this.witdisp = witdisp;
   }
   
   public void setWitrole(String witrole) {
     this.witrole = witrole;
   }
   
 
 
 
 
 
   public String toString()
   {
     return 
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       "Tagtype [etypenum=" + this.etypenum + ", " + (this.abbrev != null ? "abbrev=" + this.abbrev + ", " : "") + (this.active != null ? "active=" + this.active + ", " : "") + (this.admin != null ? "admin=" + this.admin + ", " : "") + "dsid=" + this.dsid + ", " + (this.etypename != null ? "etypename=" + this.etypename + ", " : "") + (this.gedcomTag != null ? "gedcomTag=" + this.gedcomTag + ", " : "") + (this.ispicked != null ? "ispicked=" + this.ispicked + ", " : "") + (this.isreport != null ? "isreport=" + this.isreport + ", " : "") + (this.ldsonly != null ? "ldsonly=" + this.ldsonly + ", " : "") + (this.maxyear != null ? "maxyear=" + this.maxyear + ", " : "") + (this.minyear != null ? "minyear=" + this.minyear + ", " : "") + "origetype=" + this.origetype + ", " + (this.pasttense != null ? "pasttense=" + this.pasttense + ", " : "") + (this.place4 != null ? "place4=" + this.place4 + ", " : "") + (this.prinrole != null ? "prinrole=" + this.prinrole + ", " : "") + (this.properties != null ? "properties=" + this.properties + ", " : "") + (this.reminders != null ? "reminders=" + this.reminders + ", " : "") + (this.tsentence != null ? "tsentence=" + this.tsentence + ", " : "") + (this.witdisp != null ? "witdisp=" + this.witdisp + ", " : "") + (this.witrole != null ? "witrole=" + this.witrole : "") + "]";
   }
 }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Tagtype.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */