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
/*     */ public class Placepartvalue
/*     */ {
/*  16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   private int dsid;
/*     */   private int id;
/*     */   private int recno;
/*     */   private int type;
/*     */   private int uid;
/*     */   private Placedictionary pd;
/*  23 */   private final String SELECT = "SELECT RECNO, UID, TYPE, ID, DSID FROM PLACEPARTVALUE WHERE RECNO = ?";
/*  24 */   private PreparedStatement pst = null;
/*     */   
/*     */   public Placepartvalue(Connection conn, int i) {
/*  27 */     this.recno = i;
/*     */     try
/*     */     {
/*  30 */       this.pst = conn.prepareStatement("SELECT RECNO, UID, TYPE, ID, DSID FROM PLACEPARTVALUE WHERE RECNO = ?");
/*  31 */       this.pst.setInt(1, this.recno);
/*     */       
/*  33 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  35 */       if (rs.next()) {
/*  36 */         setDsid(rs.getInt("DSID"));
/*  37 */         setId(rs.getInt("ID"));
/*  38 */         setType(rs.getInt("TYPE"));
/*  39 */         setUid(rs.getInt("UID"));
/*  40 */         setPd(new Placedictionary(conn, this.uid));
/*     */       } else {
/*  42 */         setRecno(0);
/*     */       }
/*     */       try
/*     */       {
/*  46 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     }
/*     */     catch (Exception e) {
/*  52 */       e.printStackTrace();
/*  53 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  58 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  62 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Placedictionary getPd()
/*     */   {
/*  69 */     return this.pd;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/*  73 */     return this.recno;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  77 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getUid() {
/*  81 */     return this.uid;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/*  85 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  89 */     this.id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPd(Placedictionary pd)
/*     */   {
/*  97 */     this.pd = pd;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 101 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 105 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setUid(int uid) {
/* 109 */     this.uid = uid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 119 */     return 
/* 120 */       "Placepartvalue [dsid=" + this.dsid + ", id=" + this.id + ", recno=" + this.recno + ", type=" + this.type + ", uid=" + this.uid + ", " + (this.pd != null ? "pd=" + this.pd : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Placepartvalue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */