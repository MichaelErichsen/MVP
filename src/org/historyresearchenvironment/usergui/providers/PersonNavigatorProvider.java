/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.models.PersonNavigatorModel;
/*    */ import org.historyresearchenvironment.usergui.models.PersonVector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersonNavigatorProvider
/*    */   extends AbstractHreProvider
/*    */ {
/*    */   protected Vector<PersonNavigatorModel> persons;
/*    */   
/*    */   public PersonNavigatorProvider()
/*    */   {
/* 22 */     this.persons = new Vector();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addPerson(String name, int perNo, String pBirth, String pDeath, int father, int mother)
/*    */   {
/* 34 */     PersonNavigatorModel pi = new PersonNavigatorModel(name, perNo, pBirth, pDeath, father, mother);
/*    */     
/* 36 */     if (this.persons == null) {
/* 37 */       this.persons = new Vector();
/*    */     }
/* 39 */     this.persons.add(pi);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<PersonNavigatorModel> getPersons()
/*    */   {
/* 46 */     return this.persons;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void readFromH2(int i)
/*    */   {
/* 59 */     this.persons = new PersonVector(this.conn).getVector();
/*    */   }
/*    */   
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */     try
/*    */     {
/* 67 */       this.conn.close();
/*    */     } catch (SQLException e) {
/* 69 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\PersonNavigatorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */