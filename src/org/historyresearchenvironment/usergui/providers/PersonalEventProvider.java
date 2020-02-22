/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.historyresearchenvironment.tmg.h2.models.Name;
/*     */ import org.historyresearchenvironment.tmg.h2.models.Person;
/*     */ import org.historyresearchenvironment.usergui.models.EventModel;
/*     */ import org.historyresearchenvironment.usergui.models.EventVector;
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
/*     */ 
/*     */ 
/*     */ public class PersonalEventProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*     */   protected int Id;
/*     */   protected String identity;
/*     */   protected String father;
/*     */   protected String mother;
/*     */   protected int noChildren;
/*     */   protected Vector<EventModel> events;
/*     */   
/*     */   public PersonalEventProvider() {}
/*     */   
/*     */   public PersonalEventProvider(String identity, String father, String mother, int noChildren)
/*     */   {
/*  40 */     this.identity = identity;
/*  41 */     this.father = father;
/*  42 */     this.mother = mother;
/*  43 */     this.noChildren = noChildren;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addEvent(String eventTag, String role, String date, String summary)
/*     */   {
/*  53 */     EventModel e = new EventModel(eventTag, role, date, summary);
/*  54 */     if (this.events == null) {
/*  55 */       this.events = new Vector(this.Id);
/*     */     }
/*  57 */     this.events.add(e);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */     try
/*     */     {
/*  65 */       this.conn.close();
/*     */     }
/*     */     catch (SQLException localSQLException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<EventModel> getEvents()
/*     */   {
/*  74 */     return this.events;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getFather()
/*     */   {
/*  81 */     return this.father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  88 */     return this.Id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getIdentity()
/*     */   {
/*  95 */     return this.identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getMother()
/*     */   {
/* 102 */     return this.mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNoChildren()
/*     */   {
/* 109 */     return this.noChildren;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromH2(int i)
/*     */   {
/* 117 */     StringBuilder sb = new StringBuilder();
/* 118 */     Person p = null;
/* 119 */     Person p1 = null;
/* 120 */     int p1PerNo = 0;
/*     */     
/* 122 */     setId(i);
/*     */     try
/*     */     {
/* 125 */       p = new Person(this.conn, i);
/*     */       
/* 127 */       if (p.getPerNo() == 0) {
/* 128 */         eventBroker.post("MESSAGE", "No person found");
/* 129 */         LOGGER.info("No person found");
/*     */       }
/*     */       
/* 132 */       Name n = new Name(this.conn, i);
/*     */       
/* 134 */       sb = new StringBuilder(n.getSrnamedisp());
/* 135 */       sb.append(" (" + i + ") (");
/*     */       try {
/* 137 */         sb.append(p.getPbirth().substring(1, 5));
/*     */       } catch (Exception localException1) {
/* 139 */         sb.append("    ");
/*     */       }
/* 141 */       sb.append("-");
/*     */       try {
/* 143 */         sb.append(p.getPdeath().substring(1, 5));
/*     */       } catch (Exception localException2) {
/* 145 */         sb.append("    ");
/*     */       }
/* 147 */       sb.append(")");
/* 148 */       setIdentity(sb.toString());
/*     */       
/* 150 */       p1PerNo = p.getFather();
/* 151 */       p1 = new Person(this.conn, p1PerNo);
/* 152 */       n = new Name(this.conn, p1PerNo);
/*     */       
/* 154 */       sb = new StringBuilder(n.getSrnamedisp());
/* 155 */       sb.append(" (" + p1PerNo + ") (");
/*     */       try {
/* 157 */         sb.append(p1.getPbirth().substring(1, 5));
/*     */       } catch (Exception localException3) {
/* 159 */         sb.append("    ");
/*     */       }
/* 161 */       sb.append("-");
/*     */       try {
/* 163 */         sb.append(p1.getPdeath().substring(1, 5));
/*     */       } catch (Exception localException4) {
/* 165 */         sb.append("    ");
/*     */       }
/* 167 */       sb.append(")");
/* 168 */       setFather(sb.toString());
/*     */       
/* 170 */       p1PerNo = p.getMother();
/* 171 */       p1 = new Person(this.conn, p1PerNo);
/* 172 */       n = new Name(this.conn, p1PerNo);
/*     */       
/* 174 */       sb = new StringBuilder(n.getSrnamedisp());
/* 175 */       sb.append(" (" + p1PerNo + ") (");
/*     */       try {
/* 177 */         sb.append(p1.getPbirth().substring(1, 5));
/*     */       } catch (Exception localException5) {
/* 179 */         sb.append("    ");
/*     */       }
/* 181 */       sb.append("-");
/*     */       try {
/* 183 */         sb.append(p1.getPdeath().substring(1, 5));
/*     */       } catch (Exception localException6) {
/* 185 */         sb.append("    ");
/*     */       }
/* 187 */       sb.append(")");
/* 188 */       setMother(sb.toString());
/*     */       
/* 190 */       setNoChildren(p.getChildCount());
/*     */       
/* 192 */       this.events = new EventVector(this.conn, i).getVector();
/*     */     }
/*     */     catch (Exception e) {
/* 195 */       eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 196 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFather(String father)
/*     */   {
/* 205 */     this.father = father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 213 */     this.Id = id;
/* 214 */     setKey(Integer.toString(this.Id));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIdentity(String identity)
/*     */   {
/* 222 */     this.identity = identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMother(String mother)
/*     */   {
/* 230 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNoChildren(int noChildren)
/*     */   {
/* 238 */     this.noChildren = noChildren;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\PersonalEventProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */