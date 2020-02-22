/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Person
/*     */ {
/*  21 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private int perNo;
/*     */   private String adopted;
/*     */   private String anceInt;
/*     */   private String birthorder;
/*     */   private String descInt;
/*     */   private int dsid;
/*     */   private int father;
/*     */   private String flag1;
/*     */   private String flag2;
/*     */   private String flag3;
/*     */   private String flag4;
/*     */   private String flag5;
/*     */   private String flag6;
/*     */   private String flag7;
/*     */   private String flag8;
/*     */   private String flag9;
/*     */   private Date lastEdit;
/*     */   private String living;
/*     */   private int mother;
/*     */   private String multibirth;
/*     */   private String pbirth;
/*     */   private String pdeath;
/*     */   private int refId;
/*     */   private String reference;
/*     */   private int relate;
/*     */   private int relatefo;
/*     */   private String scbuff;
/*     */   private String sex;
/*     */   private int spoulast;
/*     */   private String tt;
/*  53 */   private final String SELECT = "SELECT PER_NO, FATHER, MOTHER, LAST_EDIT, DSID, REF_ID, REFERENCE, SPOULAST, SCBUFF, PBIRTH, PDEATH, SEX, LIVING, BIRTHORDER, MULTIBIRTH, ADOPTED, ANCE_INT, DESC_INT, RELATE, RELATEFO, TT, FLAG1, FLAG2, FLAG3, FLAG4, FLAG5, FLAG6, FLAG7, FLAG8, FLAG9  FROM PERSON WHERE PER_NO = ?";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  58 */   private final String CHILDCOUNT = "SELECT COUNT(*) FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?";
/*     */   
/*     */   private int childCount;
/*  61 */   private PreparedStatement pst = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private PreparedStatement pst1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Person() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Person(Connection conn, int perNo)
/*     */   {
/*  80 */     DateFormat df = null;
/*     */     
/*  82 */     this.perNo = perNo;
/*     */     try
/*     */     {
/*  85 */       new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  86 */       this.pst = conn.prepareStatement("SELECT PER_NO, FATHER, MOTHER, LAST_EDIT, DSID, REF_ID, REFERENCE, SPOULAST, SCBUFF, PBIRTH, PDEATH, SEX, LIVING, BIRTHORDER, MULTIBIRTH, ADOPTED, ANCE_INT, DESC_INT, RELATE, RELATEFO, TT, FLAG1, FLAG2, FLAG3, FLAG4, FLAG5, FLAG6, FLAG7, FLAG8, FLAG9  FROM PERSON WHERE PER_NO = ?");
/*     */       
/*  88 */       this.pst.setInt(1, perNo);
/*     */       
/*  90 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  92 */       if (rs.next()) {
/*  93 */         setFather(rs.getInt("FATHER"));
/*  94 */         setMother(rs.getInt("MOTHER"));
/*  95 */         df = new SimpleDateFormat("yyyy-MM-dd");
/*  96 */         setLastEdit(df.parse(rs.getString("LAST_EDIT")));
/*  97 */         setDsid(rs.getInt("DSID"));
/*  98 */         setSpoulast(rs.getInt("SPOULAST"));
/*  99 */         setPbirth(rs.getString("PBIRTH"));
/* 100 */         setPdeath(rs.getString("PDEATH"));
/* 101 */         setSex(rs.getString("SEX"));
/*     */         
/* 103 */         this.pst1 = conn.prepareStatement("SELECT COUNT(*) FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?");
/* 104 */         this.pst1.setInt(1, perNo);
/* 105 */         ResultSet rs1 = this.pst1.executeQuery();
/*     */         
/*     */ 
/*     */ 
/* 109 */         if (rs1.next()) {
/* 110 */           setChildCount(rs1.getInt(1));
/*     */         } else {
/* 112 */           setChildCount(0);
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 117 */         this.pst.close();
/* 118 */         this.pst1.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/* 123 */       e.printStackTrace();
/* 124 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAdopted() {
/* 129 */     return this.adopted;
/*     */   }
/*     */   
/*     */   public String getAnceInt() {
/* 133 */     return this.anceInt;
/*     */   }
/*     */   
/*     */   public String getBirthorder() {
/* 137 */     return this.birthorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getChildCount()
/*     */   {
/* 144 */     return this.childCount;
/*     */   }
/*     */   
/*     */   public String getDescInt() {
/* 148 */     return this.descInt;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/* 152 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public int getFather() {
/* 156 */     return this.father;
/*     */   }
/*     */   
/*     */   public String getFlag1() {
/* 160 */     return this.flag1;
/*     */   }
/*     */   
/*     */   public String getFlag2() {
/* 164 */     return this.flag2;
/*     */   }
/*     */   
/*     */   public String getFlag3() {
/* 168 */     return this.flag3;
/*     */   }
/*     */   
/*     */   public String getFlag4() {
/* 172 */     return this.flag4;
/*     */   }
/*     */   
/*     */   public String getFlag5() {
/* 176 */     return this.flag5;
/*     */   }
/*     */   
/*     */   public String getFlag6() {
/* 180 */     return this.flag6;
/*     */   }
/*     */   
/*     */   public String getFlag7() {
/* 184 */     return this.flag7;
/*     */   }
/*     */   
/*     */   public String getFlag8() {
/* 188 */     return this.flag8;
/*     */   }
/*     */   
/*     */   public String getFlag9() {
/* 192 */     return this.flag9;
/*     */   }
/*     */   
/*     */   public Date getLastEdit() {
/* 196 */     return this.lastEdit;
/*     */   }
/*     */   
/*     */   public String getLiving() {
/* 200 */     return this.living;
/*     */   }
/*     */   
/*     */   public int getMother() {
/* 204 */     return this.mother;
/*     */   }
/*     */   
/*     */   public String getMultibirth() {
/* 208 */     return this.multibirth;
/*     */   }
/*     */   
/*     */   public String getPbirth() {
/* 212 */     return this.pbirth;
/*     */   }
/*     */   
/*     */   public String getPdeath() {
/* 216 */     return this.pdeath;
/*     */   }
/*     */   
/*     */   public int getPerNo() {
/* 220 */     return this.perNo;
/*     */   }
/*     */   
/*     */   public String getReference() {
/* 224 */     return this.reference;
/*     */   }
/*     */   
/*     */   public int getRefId() {
/* 228 */     return this.refId;
/*     */   }
/*     */   
/*     */   public int getRelate() {
/* 232 */     return this.relate;
/*     */   }
/*     */   
/*     */   public int getRelatefo() {
/* 236 */     return this.relatefo;
/*     */   }
/*     */   
/*     */   public String getScbuff() {
/* 240 */     return this.scbuff;
/*     */   }
/*     */   
/*     */   public String getSex() {
/* 244 */     return this.sex;
/*     */   }
/*     */   
/*     */   public int getSpoulast() {
/* 248 */     return this.spoulast;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 252 */     return this.tt;
/*     */   }
/*     */   
/*     */   public void setAdopted(String adopted) {
/* 256 */     this.adopted = adopted;
/*     */   }
/*     */   
/*     */   public void setAnceInt(String anceInt) {
/* 260 */     this.anceInt = anceInt;
/*     */   }
/*     */   
/*     */   public void setBirthorder(String birthorder) {
/* 264 */     this.birthorder = birthorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChildCount(int childCount)
/*     */   {
/* 272 */     this.childCount = childCount;
/*     */   }
/*     */   
/*     */   public void setDescInt(String descInt) {
/* 276 */     this.descInt = descInt;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 280 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setFather(int father) {
/* 284 */     this.father = father;
/*     */   }
/*     */   
/*     */   public void setFlag1(String flag1) {
/* 288 */     this.flag1 = flag1;
/*     */   }
/*     */   
/*     */   public void setFlag2(String flag2) {
/* 292 */     this.flag2 = flag2;
/*     */   }
/*     */   
/*     */   public void setFlag3(String flag3) {
/* 296 */     this.flag3 = flag3;
/*     */   }
/*     */   
/*     */   public void setFlag4(String flag4) {
/* 300 */     this.flag4 = flag4;
/*     */   }
/*     */   
/*     */   public void setFlag5(String flag5) {
/* 304 */     this.flag5 = flag5;
/*     */   }
/*     */   
/*     */   public void setFlag6(String flag6) {
/* 308 */     this.flag6 = flag6;
/*     */   }
/*     */   
/*     */   public void setFlag7(String flag7) {
/* 312 */     this.flag7 = flag7;
/*     */   }
/*     */   
/*     */   public void setFlag8(String flag8) {
/* 316 */     this.flag8 = flag8;
/*     */   }
/*     */   
/*     */   public void setFlag9(String flag9) {
/* 320 */     this.flag9 = flag9;
/*     */   }
/*     */   
/*     */   public void setLastEdit(Date lastEdit) {
/* 324 */     this.lastEdit = lastEdit;
/*     */   }
/*     */   
/*     */   public void setLiving(String living) {
/* 328 */     this.living = living;
/*     */   }
/*     */   
/*     */   public void setMother(int mother) {
/* 332 */     this.mother = mother;
/*     */   }
/*     */   
/*     */   public void setMultibirth(String multibirth) {
/* 336 */     this.multibirth = multibirth;
/*     */   }
/*     */   
/*     */   public void setPbirth(String pbirth) {
/* 340 */     this.pbirth = pbirth;
/*     */   }
/*     */   
/*     */   public void setPdeath(String pdeath) {
/* 344 */     this.pdeath = pdeath;
/*     */   }
/*     */   
/*     */   public void setPerNo(int perNo) {
/* 348 */     this.perNo = perNo;
/*     */   }
/*     */   
/*     */   public void setReference(String reference) {
/* 352 */     this.reference = reference;
/*     */   }
/*     */   
/*     */   public void setRefId(int refId) {
/* 356 */     this.refId = refId;
/*     */   }
/*     */   
/*     */   public void setRelate(int relate) {
/* 360 */     this.relate = relate;
/*     */   }
/*     */   
/*     */   public void setRelatefo(int relatefo) {
/* 364 */     this.relatefo = relatefo;
/*     */   }
/*     */   
/*     */   public void setScbuff(String scbuff) {
/* 368 */     this.scbuff = scbuff;
/*     */   }
/*     */   
/*     */   public void setSex(String sex) {
/* 372 */     this.sex = sex;
/*     */   }
/*     */   
/*     */   public void setSpoulast(int spoulast) {
/* 376 */     this.spoulast = spoulast;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 380 */     this.tt = tt;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Person.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */