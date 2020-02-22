/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Logger;
/*     */ import org.historyresearchenvironment.usergui.models.TMGPlaceVector;
/*     */ 
/*     */ 
/*     */ public class TMGPlaceProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*  14 */   private final String SELECT1 = "SELECT TYPE, XVALUE FROM PLACEPARTTYPE ORDER BY TYPE";
/*  15 */   private final String SELECT2 = "SELECT RECNO FROM PLACE ORDER BY RECNO";
/*  16 */   private String[] labels = { "Record Number", "", "", "", "", "", "", "", "", "", "", "" };
/*  17 */   private TMGPlaceVector vector = new TMGPlaceVector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TMGPlaceProvider()
/*     */   {
/*     */     try
/*     */     {
/*  29 */       this.pst = this.conn.prepareStatement("SELECT TYPE, XVALUE FROM PLACEPARTTYPE ORDER BY TYPE");
/*  30 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  32 */       while (rs.next()) {
/*  33 */         int type = rs.getInt("TYPE");
/*  34 */         this.labels[type] = rs.getString("XVALUE");
/*     */       }
/*  36 */       this.pst.close();
/*     */     } catch (Exception e) {
/*  38 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */     try
/*     */     {
/*  47 */       this.conn.close();
/*     */     } catch (SQLException e) {
/*  49 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLabel(int i)
/*     */   {
/*  58 */     return this.labels[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] getLabels()
/*     */   {
/*  65 */     return this.labels;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TMGPlaceVector getVector()
/*     */   {
/*  72 */     return this.vector;
/*     */   }
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
/*     */   public void readFromH2(int i)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       this.pst = this.conn.prepareStatement("SELECT RECNO FROM PLACE ORDER BY RECNO");
/*  90 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  92 */       while (rs.next()) {
/*  93 */         int recNo = rs.getInt("RECNO");
/*  94 */         this.vector.add(this.conn, recNo);
/*     */       }
/*  96 */       this.pst.close();
/*     */     } catch (Exception e) {
/*  98 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLabels(String[] labels)
/*     */   {
/* 107 */     this.labels = labels;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVector(TMGPlaceVector vector)
/*     */   {
/* 115 */     this.vector = vector;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\TMGPlaceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */