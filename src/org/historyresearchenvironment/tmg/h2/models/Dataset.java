/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
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
/*     */ public class Dataset
/*     */ {
/*     */   private static final String SELECT = "SELECT DSID, DSNAME, DSLOCATION, DSTYPE, DSLOCKED, DSENABLED, PROPERTY, DSP, DSP2, DCOMMENT, HOST, NAMESTYLE, PLACESTYLE FROM DATASET WHERE DSID = ?";
/*     */   private int dsid;
/*     */   private String dcomment;
/*     */   private String dsenabled;
/*     */   private String dslocation;
/*     */   private String dslocked;
/*     */   private String dsname;
/*     */   private String dsp;
/*     */   private String dsp2;
/*     */   private BigDecimal dstype;
/*     */   private String host;
/*     */   private int namestyle;
/*     */   private int placestyle;
/*     */   private String property;
/*     */   private String tt;
/*  36 */   private PreparedStatement pst = null;
/*     */   
/*     */   public static void main(String[] args) {}
/*     */   
/*  40 */   public Dataset(Connection conn, int dsid) { this.dsid = dsid;
/*     */     try
/*     */     {
/*  43 */       this.pst = conn.prepareStatement("SELECT DSID, DSNAME, DSLOCATION, DSTYPE, DSLOCKED, DSENABLED, PROPERTY, DSP, DSP2, DCOMMENT, HOST, NAMESTYLE, PLACESTYLE FROM DATASET WHERE DSID = ?");
/*     */       
/*  45 */       this.pst.setInt(1, dsid);
/*     */       
/*  47 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  49 */       if (rs.next()) {
/*  50 */         setDcomment(rs.getString("DCOMMENT"));
/*  51 */         setDsenabled(rs.getString("DSENABLED"));
/*  52 */         setDslocation(rs.getString("DSLOCATION"));
/*  53 */         setDslocked(rs.getString("DSLOCKED"));
/*  54 */         setDsname(rs.getString("DSNAME"));
/*  55 */         setDsp(rs.getString("DSP"));
/*  56 */         setDsp2(rs.getString("DSP2"));
/*  57 */         setDstype(rs.getBigDecimal("DSTYPE"));
/*  58 */         setHost(rs.getString("HOST"));
/*  59 */         setNamestyle(rs.getInt("NAMESTYLE"));
/*  60 */         setPlacestyle(rs.getInt("PLACESTYLE"));
/*  61 */         setProperty(rs.getString("PROPERTY"));
/*     */       }
/*     */       try
/*     */       {
/*  65 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/*  70 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDcomment()
/*     */   {
/*  78 */     return this.dcomment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDsenabled()
/*     */   {
/*  85 */     return this.dsenabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getDsid()
/*     */   {
/*  92 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getDslocation() {
/*  96 */     return this.dslocation;
/*     */   }
/*     */   
/*     */   public String getDslocked() {
/* 100 */     return this.dslocked;
/*     */   }
/*     */   
/*     */   public String getDsname() {
/* 104 */     return this.dsname;
/*     */   }
/*     */   
/*     */   public String getDsp() {
/* 108 */     return this.dsp;
/*     */   }
/*     */   
/*     */   public String getDsp2() {
/* 112 */     return this.dsp2;
/*     */   }
/*     */   
/*     */   public BigDecimal getDstype() {
/* 116 */     return this.dstype;
/*     */   }
/*     */   
/*     */   public String getHost() {
/* 120 */     return this.host;
/*     */   }
/*     */   
/*     */   public int getNamestyle() {
/* 124 */     return this.namestyle;
/*     */   }
/*     */   
/*     */   public int getPlacestyle() {
/* 128 */     return this.placestyle;
/*     */   }
/*     */   
/*     */   public String getProperty() {
/* 132 */     return this.property;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 136 */     return this.tt;
/*     */   }
/*     */   
/*     */   public void setDcomment(String dcomment) {
/* 140 */     this.dcomment = dcomment;
/*     */   }
/*     */   
/*     */   public void setDsenabled(String dsenabled) {
/* 144 */     this.dsenabled = dsenabled;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 148 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setDslocation(String dslocation) {
/* 152 */     this.dslocation = dslocation;
/*     */   }
/*     */   
/*     */   public void setDslocked(String dslocked) {
/* 156 */     this.dslocked = dslocked;
/*     */   }
/*     */   
/*     */   public void setDsname(String dsname) {
/* 160 */     this.dsname = dsname;
/*     */   }
/*     */   
/*     */   public void setDsp(String dsp) {
/* 164 */     this.dsp = dsp;
/*     */   }
/*     */   
/*     */   public void setDsp2(String dsp2) {
/* 168 */     this.dsp2 = dsp2;
/*     */   }
/*     */   
/*     */   public void setDstype(BigDecimal dstype) {
/* 172 */     this.dstype = dstype;
/*     */   }
/*     */   
/*     */   public void setHost(String host) {
/* 176 */     this.host = host;
/*     */   }
/*     */   
/*     */   public void setNamestyle(int namestyle) {
/* 180 */     this.namestyle = namestyle;
/*     */   }
/*     */   
/*     */   public void setPlacestyle(int placestyle) {
/* 184 */     this.placestyle = placestyle;
/*     */   }
/*     */   
/*     */   public void setProperty(String property) {
/* 188 */     this.property = property;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 192 */     this.tt = tt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 202 */     return 
/*     */     
/*     */ 
/* 205 */       "Dataset [dsid=" + this.dsid + ", dcomment=" + this.dcomment + ", dsenabled=" + this.dsenabled + ", dslocation=" + this.dslocation + ", dslocked=" + this.dslocked + ", dsname=" + this.dsname + ", dsp=" + this.dsp + ", dsp2=" + this.dsp2 + ", dstype=" + this.dstype + ", host=" + this.host + ", namestyle=" + this.namestyle + ", placestyle=" + this.placestyle + ", property=" + this.property + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Dataset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */