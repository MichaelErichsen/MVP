/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tagtype
/*     */ {
/*  17 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private static final String SELECT = "SELECT ISPICKED, DSID, ACTIVE, ETYPENUM, ORIGETYPE, ADMIN, LDSONLY, ETYPENAME, GEDCOM_TAG, ISREPORT, TSENTENCE, ABBREV, WITDISP, PASTTENSE, WITROLE, MAXYEAR, MINYEAR, REMINDERS, PROPERTIES FROM TAGTYPE WHERE ETYPENUM = ?";
/*     */   
/*     */   private int etypenum;
/*     */   
/*     */   private String abbrev;
/*     */   
/*     */   private String active;
/*     */   private BigDecimal admin;
/*     */   private int dsid;
/*     */   private String etypename;
/*     */   private String gedcomTag;
/*     */   private String ispicked;
/*     */   private String isreport;
/*     */   private String ldsonly;
/*     */   private BigDecimal maxyear;
/*     */   private BigDecimal minyear;
/*     */   private int origetype;
/*     */   private String pasttense;
/*     */   private String place4;
/*     */   private String prinrole;
/*     */   private String properties;
/*     */   private String reminders;
/*     */   private String tsentence;
/*     */   private String tt;
/*     */   private String witdisp;
/*     */   private String witrole;
/*  45 */   private PreparedStatement pst = null;
/*     */   
/*     */   public Tagtype(Connection conn, int i) {
/*  48 */     this.etypenum = i;
/*     */     try
/*     */     {
/*  51 */       this.pst = conn.prepareStatement("SELECT ISPICKED, DSID, ACTIVE, ETYPENUM, ORIGETYPE, ADMIN, LDSONLY, ETYPENAME, GEDCOM_TAG, ISREPORT, TSENTENCE, ABBREV, WITDISP, PASTTENSE, WITROLE, MAXYEAR, MINYEAR, REMINDERS, PROPERTIES FROM TAGTYPE WHERE ETYPENUM = ?");
/*     */       
/*  53 */       this.pst.setInt(1, this.etypenum);
/*     */       
/*  55 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  57 */       if (rs.next()) {
/*  58 */         setDsid(rs.getInt("DSID"));
/*  59 */         setEtypename(rs.getString("ETYPENAME").trim());
/*     */       }
/*     */       try
/*     */       {
/*  63 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*  69 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAbbrev() {
/*  74 */     return this.abbrev;
/*     */   }
/*     */   
/*     */   public String getActive() {
/*  78 */     return this.active;
/*     */   }
/*     */   
/*     */   public BigDecimal getAdmin() {
/*  82 */     return this.admin;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  86 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getEtypename() {
/*  90 */     return this.etypename;
/*     */   }
/*     */   
/*     */   public int getEtypenum() {
/*  94 */     return this.etypenum;
/*     */   }
/*     */   
/*     */   public String getGedcomTag() {
/*  98 */     return this.gedcomTag;
/*     */   }
/*     */   
/*     */   public String getIspicked() {
/* 102 */     return this.ispicked;
/*     */   }
/*     */   
/*     */   public String getIsreport() {
/* 106 */     return this.isreport;
/*     */   }
/*     */   
/*     */   public String getLdsonly() {
/* 110 */     return this.ldsonly;
/*     */   }
/*     */   
/*     */   public BigDecimal getMaxyear() {
/* 114 */     return this.maxyear;
/*     */   }
/*     */   
/*     */   public BigDecimal getMinyear() {
/* 118 */     return this.minyear;
/*     */   }
/*     */   
/*     */   public int getOrigetype() {
/* 122 */     return this.origetype;
/*     */   }
/*     */   
/*     */   public String getPasttense() {
/* 126 */     return this.pasttense;
/*     */   }
/*     */   
/*     */   public String getPlace4() {
/* 130 */     return this.place4;
/*     */   }
/*     */   
/*     */   public String getPrinrole() {
/* 134 */     return this.prinrole;
/*     */   }
/*     */   
/*     */   public String getProperties() {
/* 138 */     return this.properties;
/*     */   }
/*     */   
/*     */   public String getReminders() {
/* 142 */     return this.reminders;
/*     */   }
/*     */   
/*     */   public String getTsentence() {
/* 146 */     return this.tsentence;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 150 */     return this.tt;
/*     */   }
/*     */   
/*     */   public String getWitdisp() {
/* 154 */     return this.witdisp;
/*     */   }
/*     */   
/*     */   public String getWitrole() {
/* 158 */     return this.witrole;
/*     */   }
/*     */   
/*     */   public void setAbbrev(String abbrev) {
/* 162 */     this.abbrev = abbrev;
/*     */   }
/*     */   
/*     */   public void setActive(String active) {
/* 166 */     this.active = active;
/*     */   }
/*     */   
/*     */   public void setAdmin(BigDecimal admin) {
/* 170 */     this.admin = admin;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 174 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setEtypename(String etypename) {
/* 178 */     this.etypename = etypename;
/*     */   }
/*     */   
/*     */   public void setEtypenum(int etypenum) {
/* 182 */     this.etypenum = etypenum;
/*     */   }
/*     */   
/*     */   public void setGedcomTag(String gedcomTag) {
/* 186 */     this.gedcomTag = gedcomTag;
/*     */   }
/*     */   
/*     */   public void setIspicked(String ispicked) {
/* 190 */     this.ispicked = ispicked;
/*     */   }
/*     */   
/*     */   public void setIsreport(String isreport) {
/* 194 */     this.isreport = isreport;
/*     */   }
/*     */   
/*     */   public void setLdsonly(String ldsonly) {
/* 198 */     this.ldsonly = ldsonly;
/*     */   }
/*     */   
/*     */   public void setMaxyear(BigDecimal maxyear) {
/* 202 */     this.maxyear = maxyear;
/*     */   }
/*     */   
/*     */   public void setMinyear(BigDecimal minyear) {
/* 206 */     this.minyear = minyear;
/*     */   }
/*     */   
/*     */   public void setOrigetype(int origetype) {
/* 210 */     this.origetype = origetype;
/*     */   }
/*     */   
/*     */   public void setPasttense(String pasttense) {
/* 214 */     this.pasttense = pasttense;
/*     */   }
/*     */   
/*     */   public void setPlace4(String place4) {
/* 218 */     this.place4 = place4;
/*     */   }
/*     */   
/*     */   public void setPrinrole(String prinrole) {
/* 222 */     this.prinrole = prinrole;
/*     */   }
/*     */   
/*     */   public void setProperties(String properties) {
/* 226 */     this.properties = properties;
/*     */   }
/*     */   
/*     */   public void setReminders(String reminders) {
/* 230 */     this.reminders = reminders;
/*     */   }
/*     */   
/*     */   public void setTsentence(String tsentence) {
/* 234 */     this.tsentence = tsentence;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 238 */     this.tt = tt;
/*     */   }
/*     */   
/*     */   public void setWitdisp(String witdisp) {
/* 242 */     this.witdisp = witdisp;
/*     */   }
/*     */   
/*     */   public void setWitrole(String witrole) {
/* 246 */     this.witrole = witrole;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 256 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 272 */       "Tagtype [etypenum=" + this.etypenum + ", " + (this.abbrev != null ? "abbrev=" + this.abbrev + ", " : "") + (this.active != null ? "active=" + this.active + ", " : "") + (this.admin != null ? "admin=" + this.admin + ", " : "") + "dsid=" + this.dsid + ", " + (this.etypename != null ? "etypename=" + this.etypename + ", " : "") + (this.gedcomTag != null ? "gedcomTag=" + this.gedcomTag + ", " : "") + (this.ispicked != null ? "ispicked=" + this.ispicked + ", " : "") + (this.isreport != null ? "isreport=" + this.isreport + ", " : "") + (this.ldsonly != null ? "ldsonly=" + this.ldsonly + ", " : "") + (this.maxyear != null ? "maxyear=" + this.maxyear + ", " : "") + (this.minyear != null ? "minyear=" + this.minyear + ", " : "") + "origetype=" + this.origetype + ", " + (this.pasttense != null ? "pasttense=" + this.pasttense + ", " : "") + (this.place4 != null ? "place4=" + this.place4 + ", " : "") + (this.prinrole != null ? "prinrole=" + this.prinrole + ", " : "") + (this.properties != null ? "properties=" + this.properties + ", " : "") + (this.reminders != null ? "reminders=" + this.reminders + ", " : "") + (this.tsentence != null ? "tsentence=" + this.tsentence + ", " : "") + (this.witdisp != null ? "witdisp=" + this.witdisp + ", " : "") + (this.witrole != null ? "witrole=" + this.witrole : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Tagtype.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */