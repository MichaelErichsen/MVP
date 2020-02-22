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
/*    */ public class PersonVector
/*    */   extends AbstractHreVector
/*    */ {
/*    */   private Vector<PersonNavigatorModel> vector;
/*    */   private PersonNavigatorModel item;
/* 16 */   private final String SELECT = "SELECT NAME.SRNAMEDISP, PERSON.PER_NO, PERSON.PBIRTH, PERSON.PDEATH, PERSON.FATHER, PERSON.MOTHER FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO ORDER BY NAME.SRNAMEDISP";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PersonVector(Connection conn)
/*    */   {
/* 26 */     this.vector = new Vector();
/*    */     
/*    */     try
/*    */     {
/* 30 */       this.pst = conn.prepareStatement("SELECT NAME.SRNAMEDISP, PERSON.PER_NO, PERSON.PBIRTH, PERSON.PDEATH, PERSON.FATHER, PERSON.MOTHER FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO ORDER BY NAME.SRNAMEDISP");
/*    */       
/* 32 */       ResultSet rs = this.pst.executeQuery();
/*    */       
/* 34 */       while (rs.next()) {
/* 35 */         this.item = new PersonNavigatorModel();
/* 36 */         this.item.setName(rs.getString("SRNAMEDISP"));
/* 37 */         this.item.setPerNo(rs.getInt("PER_NO"));
/* 38 */         String date = rs.getString("PBIRTH");
/*    */         try
/*    */         {
/* 41 */           this.item.setPbirth(date.substring(1, 5) + "-" + date.substring(5, 7) + "-" + date.substring(7, 9));
/*    */         } catch (Exception localException1) {
/* 43 */           this.item.setPbirth("");
/*    */         }
/*    */         
/* 46 */         date = rs.getString("PDEATH");
/*    */         try
/*    */         {
/* 49 */           this.item.setPdeath(date.substring(1, 5) + "-" + date.substring(5, 7) + "-" + date.substring(7, 9));
/*    */         } catch (Exception localException2) {
/* 51 */           this.item.setPdeath("");
/*    */         }
/*    */         
/* 54 */         this.item.setFather(rs.getInt("FATHER"));
/* 55 */         this.item.setMother(rs.getInt("MOTHER"));
/*    */         
/* 57 */         this.vector.addElement(this.item);
/*    */       }
/*    */       try {
/* 60 */         this.pst.close();
/*    */       } catch (Exception e) {
/* 62 */         e.printStackTrace();
/* 63 */         LOGGER.severe(
/* 64 */           e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */       }
/*    */       return;
/* 67 */     } catch (Exception e) { e.printStackTrace();
/* 68 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(PersonNavigatorModel item)
/*    */   {
/* 77 */     this.vector.addElement(item);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<PersonNavigatorModel> getVector()
/*    */   {
/* 84 */     return this.vector;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setVector(Vector<PersonNavigatorModel> vector)
/*    */   {
/* 92 */     this.vector = vector;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */