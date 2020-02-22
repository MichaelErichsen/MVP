/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AncestorVector
/*    */   extends AbstractHreVector
/*    */ {
/* 14 */   private final String SELECT = "SELECT NAME.SRNAMEDISP, PERSON.PER_NO, PERSON.FATHER, PERSON.MOTHER, PERSON.SEX FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO AND PERSON.PER_NO = ? ORDER BY PERSON.PER_NO";
/*    */   
/*    */ 
/*    */   private Vector<AncestorModel> vector;
/*    */   
/*    */   private AncestorModel item;
/*    */   
/*    */ 
/*    */   public AncestorVector(Connection conn, int perNo)
/*    */   {
/* 24 */     this.vector = new Vector();
/*    */     try
/*    */     {
/* 27 */       this.pst = conn.prepareStatement("SELECT NAME.SRNAMEDISP, PERSON.PER_NO, PERSON.FATHER, PERSON.MOTHER, PERSON.SEX FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO AND PERSON.PER_NO = ? ORDER BY PERSON.PER_NO");
/* 28 */       this.pst.setInt(1, perNo);
/*    */       
/* 30 */       ResultSet rs = this.pst.executeQuery();
/*    */       
/* 32 */       if (rs.next()) {
/* 33 */         this.item = new AncestorModel();
/* 34 */         this.item.setPerNo(perNo);
/* 35 */         this.item.setName(rs.getString("SRNAMEDISP"));
/* 36 */         this.item.setFather(rs.getInt("FATHER"));
/* 37 */         this.item.setMother(rs.getInt("MOTHER"));
/* 38 */         this.item.setSex(rs.getString("SEX"));
/*    */       }
/*    */       
/* 41 */       this.vector.addElement(this.item);
/*    */       try
/*    */       {
/* 44 */         this.pst.close();
/*    */       } catch (Exception e) {
/* 46 */         LOGGER.severe(
/* 47 */           e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */       }
/*    */       return;
/*    */     }
/*    */     catch (Exception e) {
/* 52 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(AncestorModel item)
/*    */   {
/* 60 */     this.vector.addElement(item);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<AncestorModel> getVector()
/*    */   {
/* 67 */     return this.vector;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setVector(Vector<AncestorModel> vector)
/*    */   {
/* 75 */     this.vector = vector;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\AncestorVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */