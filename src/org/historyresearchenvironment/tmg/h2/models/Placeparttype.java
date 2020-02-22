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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Placeparttype
/*     */ {
/*  21 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   private int id;
/*     */   private int dsid;
/*     */   private String shortvalue;
/*     */   private String system;
/*     */   private String tt;
/*     */   private int type;
/*     */   private String xvalue;
/*  29 */   private final String SELECT = "SELECT ID, TYPE, VALUE, SYSTEM, SHORTVALUE, TT, DSID FROM PLACEPARTTYPE WHERE ID = ?";
/*     */   
/*  31 */   private PreparedStatement pst = null;
/*     */   
/*     */   public Placeparttype(Connection conn, int i) {
/*  34 */     this.id = i;
/*     */     try
/*     */     {
/*  37 */       this.pst = conn.prepareStatement("SELECT ID, TYPE, VALUE, SYSTEM, SHORTVALUE, TT, DSID FROM PLACEPARTTYPE WHERE ID = ?");
/*  38 */       this.pst.setInt(1, this.id);
/*     */       
/*  40 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  42 */       if (rs.next()) {
/*  43 */         setDsid(rs.getInt("DSID"));
/*  44 */         setShortvalue(rs.getString("SHORTVALUE"));
/*  45 */         setSystem(rs.getString("SYSTEM"));
/*  46 */         setType(rs.getInt("TYPE"));
/*  47 */         setXvalue(rs.getString("VALUE"));
/*     */       } else {
/*  49 */         setId(0);
/*     */       }
/*     */       try
/*     */       {
/*  53 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  60 */       e.printStackTrace();
/*  61 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  66 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  70 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getShortvalue() {
/*  74 */     return this.shortvalue;
/*     */   }
/*     */   
/*     */   public String getSystem() {
/*  78 */     return this.system;
/*     */   }
/*     */   
/*     */   public String getTt() {
/*  82 */     return this.tt;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  86 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getXvalue() {
/*  90 */     return this.xvalue;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/*  94 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  98 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setShortvalue(String shortvalue) {
/* 102 */     this.shortvalue = shortvalue;
/*     */   }
/*     */   
/*     */   public void setSystem(String system) {
/* 106 */     this.system = system;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 110 */     this.tt = tt;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 114 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setXvalue(String xvalue) {
/* 118 */     this.xvalue = xvalue;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Placeparttype.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */