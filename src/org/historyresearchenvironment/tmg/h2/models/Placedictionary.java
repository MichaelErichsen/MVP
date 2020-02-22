/*    */ package org.historyresearchenvironment.tmg.h2.models;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Placedictionary
/*    */ {
/* 16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*    */   private int uid;
/*    */   private String soundex;
/*    */   private String tt;
/*    */   private String xvalue;
/* 21 */   private final String SELECT = "SELECT XVALUE, SDX FROM PLACEDICTIONARY WHERE UID = ?";
/* 22 */   private PreparedStatement pst = null;
/*    */   
/*    */   public Placedictionary(Connection conn, int i) {
/* 25 */     this.uid = i;
/*    */     try
/*    */     {
/* 28 */       this.pst = conn.prepareStatement("SELECT XVALUE, SDX FROM PLACEDICTIONARY WHERE UID = ?");
/* 29 */       this.pst.setInt(1, this.uid);
/*    */       
/* 31 */       ResultSet rs = this.pst.executeQuery();
/*    */       
/* 33 */       if (rs.next()) {
/* 34 */         setSoundex(rs.getString("SDX"));
/* 35 */         setXvalue(rs.getString("XVALUE"));
/*    */       } else {
/* 37 */         setUid(0);
/*    */       }
/*    */       try {
/* 40 */         this.pst.close();
/*    */       }
/*    */       catch (Exception localException1) {}
/*    */       return;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 47 */       e.printStackTrace();
/* 48 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */   
/*    */   public String getSoundex() {
/* 53 */     return this.soundex;
/*    */   }
/*    */   
/*    */   public String getTt() {
/* 57 */     return this.tt;
/*    */   }
/*    */   
/*    */   public int getUid() {
/* 61 */     return this.uid;
/*    */   }
/*    */   
/*    */   public String getXvalue() {
/* 65 */     return this.xvalue;
/*    */   }
/*    */   
/*    */   public void setSoundex(String sdx) {
/* 69 */     this.soundex = sdx;
/*    */   }
/*    */   
/*    */   public void setTt(String tt) {
/* 73 */     this.tt = tt;
/*    */   }
/*    */   
/*    */   public void setUid(int uid) {
/* 77 */     this.uid = uid;
/*    */   }
/*    */   
/*    */   public void setXvalue(String xvalue) {
/* 81 */     this.xvalue = xvalue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 91 */     return 
/* 92 */       "Placedictionary [uid=" + this.uid + ", " + (this.soundex != null ? "soundex=" + this.soundex + ", " : "") + (this.xvalue != null ? "xvalue=" + this.xvalue : "") + "]";
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Placedictionary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */