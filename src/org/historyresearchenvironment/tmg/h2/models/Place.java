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
/*     */ public class Place
/*     */ {
/*  16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   private int recno;
/*     */   private String comment;
/*     */   private int dsid;
/*     */   private String endyear;
/*     */   private String shortplace;
/*     */   private String startyear;
/*     */   private int styleid;
/*     */   private String tt;
/*  25 */   private final String SELECT = "SELECT RECNO, STYLEID, DSID, TT, STARTYEAR, ENDYEAR, COMMENT, SHORTPLACE FROM PLACE WHERE RECNO = ?";
/*     */   
/*  27 */   private PreparedStatement pst = null;
/*     */   
/*     */ 
/*     */   private Placepartvalue ppv;
/*     */   
/*     */ 
/*     */ 
/*     */   public Place(Connection conn, int i)
/*     */   {
/*  36 */     this.recno = i;
/*     */     try
/*     */     {
/*  39 */       this.pst = conn.prepareStatement("SELECT RECNO, STYLEID, DSID, TT, STARTYEAR, ENDYEAR, COMMENT, SHORTPLACE FROM PLACE WHERE RECNO = ?");
/*  40 */       this.pst.setInt(1, this.recno);
/*     */       
/*  42 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  44 */       if (rs.next()) {
/*  45 */         setComment(rs.getString("COMMENT"));
/*  46 */         setDsid(rs.getInt("DSID"));
/*  47 */         setEndyear(rs.getString("ENDYEAR"));
/*  48 */         setShortplace(rs.getString("SHORTPLACE"));
/*  49 */         setStartyear(rs.getString("STARTYEAR"));
/*  50 */         setStyleid(rs.getInt("STYLEID"));
/*  51 */         setPpv(new Placepartvalue(conn, this.recno));
/*     */       } else {
/*  53 */         setRecno(0);
/*     */       }
/*     */       try {
/*  56 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     }
/*     */     catch (Exception e) {
/*  62 */       e.printStackTrace();
/*  63 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getComment() {
/*  68 */     return this.comment;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  72 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getEndyear() {
/*  76 */     return this.endyear;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Placepartvalue getPpv()
/*     */   {
/*  83 */     return this.ppv;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/*  87 */     return this.recno;
/*     */   }
/*     */   
/*     */   public String getShortplace() {
/*  91 */     return this.shortplace;
/*     */   }
/*     */   
/*     */   public String getStartyear() {
/*  95 */     return this.startyear;
/*     */   }
/*     */   
/*     */   public int getStyleid() {
/*  99 */     return this.styleid;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 103 */     return this.tt;
/*     */   }
/*     */   
/*     */   public void setComment(String comment) {
/* 107 */     this.comment = comment;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 111 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setEndyear(String endyear) {
/* 115 */     this.endyear = endyear;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPpv(Placepartvalue ppv)
/*     */   {
/* 123 */     this.ppv = ppv;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 127 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setShortplace(String shortplace) {
/* 131 */     this.shortplace = shortplace;
/*     */   }
/*     */   
/*     */   public void setStartyear(String startyear) {
/* 135 */     this.startyear = startyear;
/*     */   }
/*     */   
/*     */   public void setStyleid(int styleid) {
/* 139 */     this.styleid = styleid;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 143 */     this.tt = tt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 153 */     return 
/*     */     
/*     */ 
/* 156 */       "Place [recno=" + this.recno + ", " + (this.comment != null ? "comment=" + this.comment + ", " : "") + "dsid=" + this.dsid + ", " + (this.endyear != null ? "endyear=" + this.endyear + ", " : "") + (this.shortplace != null ? "shortplace=" + this.shortplace + ", " : "") + (this.startyear != null ? "startyear=" + this.startyear + ", " : "") + "styleid=" + this.styleid + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Place.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */