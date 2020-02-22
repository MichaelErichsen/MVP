/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
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
/*     */ public class Style
/*     */ {
/*  16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   private int styleid;
/*     */   private int dsid;
/*     */   private String gvnamedisp;
/*     */   private String gvnamesort;
/*     */   private String otherdisp;
/*     */   private String srnamedisp;
/*     */   private String srnamesort;
/*     */   private String stDisplay;
/*     */   private String stOutput;
/*     */   private String stylename;
/*     */   private String tt;
/*     */   private String xgroup;
/*  29 */   private final String SELECT = "SELECT STYLEID, ST_DISPLAY, ST_OUTPUT, XGROUP, SRNAMESORT, SRNAMEDISP, GVNAMESORT, GVNAMEDISP, OTHERDISP, TT, DSID, STYLENAME FROM STYLE WHERE STYLEID = ?";
/*     */   
/*     */ 
/*  32 */   private PreparedStatement pst = null;
/*     */   
/*     */   public Style(Connection conn, int i) {
/*  35 */     this.styleid = i;
/*     */     try
/*     */     {
/*  38 */       this.pst = conn.prepareStatement("SELECT STYLEID, ST_DISPLAY, ST_OUTPUT, XGROUP, SRNAMESORT, SRNAMEDISP, GVNAMESORT, GVNAMEDISP, OTHERDISP, TT, DSID, STYLENAME FROM STYLE WHERE STYLEID = ?");
/*  39 */       this.pst.setInt(1, this.styleid);
/*     */       
/*  41 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  43 */       if (rs.next()) {
/*  44 */         setDsid(rs.getInt("DSID"));
/*  45 */         setGvnamedisp(rs.getString("GVNAMEDISP"));
/*  46 */         setGvnamesort(rs.getString("GVNAMESORT"));
/*  47 */         setOtherdisp(rs.getString("OTHERDISP"));
/*  48 */         setSrnamedisp(rs.getString("SRNAMEDISP"));
/*  49 */         setSrnamesort(rs.getString("SRNAMESORT"));
/*  50 */         setStDisplay(rs.getString("ST_DISPLAY"));
/*  51 */         setStOutput(rs.getString("ST_OUTPUT"));
/*  52 */         setStylename(rs.getString("STYLENAME"));
/*  53 */         setXgroup(rs.getString("XGROUP"));
/*     */       } else {
/*  55 */         setStyleid(0);
/*     */       }
/*     */       try {
/*  58 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     }
/*     */     catch (Exception e) {
/*  64 */       e.printStackTrace();
/*  65 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDsid()
/*     */   {
/*  71 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getGvnamedisp() {
/*  75 */     return this.gvnamedisp;
/*     */   }
/*     */   
/*     */   public String getGvnamesort() {
/*  79 */     return this.gvnamesort;
/*     */   }
/*     */   
/*     */   public String getOtherdisp() {
/*  83 */     return this.otherdisp;
/*     */   }
/*     */   
/*     */   public String getSrnamedisp() {
/*  87 */     return this.srnamedisp;
/*     */   }
/*     */   
/*     */   public String getSrnamesort() {
/*  91 */     return this.srnamesort;
/*     */   }
/*     */   
/*     */   public String getStDisplay() {
/*  95 */     return this.stDisplay;
/*     */   }
/*     */   
/*     */   public String getStOutput() {
/*  99 */     return this.stOutput;
/*     */   }
/*     */   
/*     */   public int getStyleid() {
/* 103 */     return this.styleid;
/*     */   }
/*     */   
/*     */   public String getStylename() {
/* 107 */     return this.stylename;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 111 */     return this.tt;
/*     */   }
/*     */   
/*     */   public String getXgroup() {
/* 115 */     return this.xgroup;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 119 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setGvnamedisp(String gvnamedisp) {
/* 123 */     this.gvnamedisp = gvnamedisp;
/*     */   }
/*     */   
/*     */   public void setGvnamesort(String gvnamesort) {
/* 127 */     this.gvnamesort = gvnamesort;
/*     */   }
/*     */   
/*     */   public void setOtherdisp(String otherdisp) {
/* 131 */     this.otherdisp = otherdisp;
/*     */   }
/*     */   
/*     */   public void setSrnamedisp(String srnamedisp) {
/* 135 */     this.srnamedisp = srnamedisp;
/*     */   }
/*     */   
/*     */   public void setSrnamesort(String srnamesort) {
/* 139 */     this.srnamesort = srnamesort;
/*     */   }
/*     */   
/*     */   public void setStDisplay(String stDisplay) {
/* 143 */     this.stDisplay = stDisplay;
/*     */   }
/*     */   
/*     */   public void setStOutput(String stOutput) {
/* 147 */     this.stOutput = stOutput;
/*     */   }
/*     */   
/*     */   public void setStyleid(int styleid) {
/* 151 */     this.styleid = styleid;
/*     */   }
/*     */   
/*     */   public void setStylename(String stylename) {
/* 155 */     this.stylename = stylename;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 159 */     this.tt = tt;
/*     */   }
/*     */   
/*     */   public void setXgroup(String xgroup) {
/* 163 */     this.xgroup = xgroup;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Style.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */