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
/*     */ public class TmgEvent
/*     */ {
/*  16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private static final String SELECT = "SELECT ETYPE, DSID, PER1SHOW, PER2SHOW, PER1, PER2, EDATE, PLACENUM, EFOOT, ENSURE, ESSURE, EDSURE, EPSURE, EFSURE, RECNO, SENTENCE, SRTDATE, TT, REF_ID FROM EVENT WHERE RECNO = ?";
/*     */   
/*     */   private int recno;
/*     */   
/*     */   private int dsid;
/*     */   
/*     */   private String edate;
/*     */   private String edsure;
/*     */   private String efoot;
/*     */   private String efsure;
/*     */   private String ensure;
/*     */   private String epsure;
/*     */   private String essure;
/*     */   private int etype;
/*     */   private int per1;
/*     */   private String per1show;
/*     */   private int per2;
/*     */   private String per2show;
/*     */   private int placenum;
/*     */   private int refId;
/*     */   private String sentence;
/*     */   private String srtdate;
/*     */   private Placepartvalue ppv;
/*  41 */   private String placestring = "";
/*     */   private Tagtype tagtype;
/*  43 */   private PreparedStatement pst = null;
/*     */   
/*     */   public TmgEvent() {}
/*     */   
/*     */   public TmgEvent(Connection conn, int i)
/*     */   {
/*  49 */     this.recno = i;
/*     */     try {
/*  51 */       this.pst = conn.prepareStatement("SELECT ETYPE, DSID, PER1SHOW, PER2SHOW, PER1, PER2, EDATE, PLACENUM, EFOOT, ENSURE, ESSURE, EDSURE, EPSURE, EFSURE, RECNO, SENTENCE, SRTDATE, TT, REF_ID FROM EVENT WHERE RECNO = ?");
/*     */       
/*  53 */       this.pst.setInt(1, this.recno);
/*     */       
/*  55 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  57 */       if (rs.next()) {
/*  58 */         setDsid(rs.getInt("DSID"));
/*  59 */         setEdate(rs.getString("EDATE").trim());
/*  60 */         setEdsure(rs.getString("EDSURE"));
/*  61 */         setEfoot(rs.getString("EFOOT"));
/*  62 */         setEnsure(rs.getString("ENSURE"));
/*  63 */         setEpsure(rs.getString("EPSURE"));
/*  64 */         setEssure(rs.getString("ESSURE"));
/*  65 */         setEtype(rs.getInt("ETYPE"));
/*  66 */         setTagtype(new Tagtype(conn, this.etype));
/*  67 */         setPer1(rs.getInt("PER1"));
/*  68 */         setPer1show(rs.getString("PER1SHOW"));
/*  69 */         setPer2(rs.getInt("PER2"));
/*  70 */         setPer2show(rs.getString("PER2SHOW"));
/*  71 */         setPlacenum(rs.getInt("PLACENUM"));
/*  72 */         setPpv(new Placepartvalue(conn, this.placenum));
/*  73 */         setRefId(rs.getInt("REF_ID"));
/*  74 */         setSentence(rs.getString("SENTENCE"));
/*  75 */         setSrtdate(rs.getString("SRTDATE").trim());
/*     */       }
/*     */       try
/*     */       {
/*  79 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/*  84 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  89 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getEdate() {
/*  93 */     return this.edate;
/*     */   }
/*     */   
/*     */   public String getEdsure() {
/*  97 */     return this.edsure;
/*     */   }
/*     */   
/*     */   public String getEfoot() {
/* 101 */     return this.efoot;
/*     */   }
/*     */   
/*     */   public String getEfsure() {
/* 105 */     return this.efsure;
/*     */   }
/*     */   
/*     */   public String getEnsure() {
/* 109 */     return this.ensure;
/*     */   }
/*     */   
/*     */   public String getEpsure() {
/* 113 */     return this.epsure;
/*     */   }
/*     */   
/*     */   public String getEssure() {
/* 117 */     return this.essure;
/*     */   }
/*     */   
/*     */   public int getEtype() {
/* 121 */     return this.etype;
/*     */   }
/*     */   
/*     */   public int getPer1() {
/* 125 */     return this.per1;
/*     */   }
/*     */   
/*     */   public String getPer1show() {
/* 129 */     return this.per1show;
/*     */   }
/*     */   
/*     */   public int getPer2() {
/* 133 */     return this.per2;
/*     */   }
/*     */   
/*     */   public String getPer2show() {
/* 137 */     return this.per2show;
/*     */   }
/*     */   
/*     */   public int getPlacenum() {
/* 141 */     return this.placenum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPlacestring()
/*     */   {
/* 148 */     return this.placestring;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Placepartvalue getPpv()
/*     */   {
/* 155 */     return this.ppv;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/* 159 */     return this.recno;
/*     */   }
/*     */   
/*     */   public int getRefId() {
/* 163 */     return this.refId;
/*     */   }
/*     */   
/*     */   public String getSentence() {
/* 167 */     return this.sentence;
/*     */   }
/*     */   
/*     */   public String getSrtdate() {
/* 171 */     return this.srtdate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Tagtype getTagtype()
/*     */   {
/* 178 */     return this.tagtype;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 182 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setEdate(String string) {
/* 186 */     this.edate = string;
/*     */   }
/*     */   
/*     */   public void setEdsure(String edsure) {
/* 190 */     this.edsure = edsure;
/*     */   }
/*     */   
/*     */   public void setEfoot(String efoot) {
/* 194 */     this.efoot = efoot;
/*     */   }
/*     */   
/*     */   public void setEfsure(String efsure) {
/* 198 */     this.efsure = efsure;
/*     */   }
/*     */   
/*     */   public void setEnsure(String ensure) {
/* 202 */     this.ensure = ensure;
/*     */   }
/*     */   
/*     */   public void setEpsure(String epsure) {
/* 206 */     this.epsure = epsure;
/*     */   }
/*     */   
/*     */   public void setEssure(String essure) {
/* 210 */     this.essure = essure;
/*     */   }
/*     */   
/*     */   public void setEtype(int etype) {
/* 214 */     this.etype = etype;
/*     */   }
/*     */   
/*     */   public void setPer1(int per1) {
/* 218 */     this.per1 = per1;
/*     */   }
/*     */   
/*     */   public void setPer1show(String per1show) {
/* 222 */     this.per1show = per1show;
/*     */   }
/*     */   
/*     */   public void setPer2(int per2) {
/* 226 */     this.per2 = per2;
/*     */   }
/*     */   
/*     */   public void setPer2show(String per2show) {
/* 230 */     this.per2show = per2show;
/*     */   }
/*     */   
/*     */   public void setPlacenum(int int1) {
/* 234 */     this.placenum = int1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlacestring(String placestring)
/*     */   {
/* 242 */     this.placestring = placestring;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPpv(Placepartvalue ppv)
/*     */   {
/* 250 */     this.ppv = ppv;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 254 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setRefId(int refId) {
/* 258 */     this.refId = refId;
/*     */   }
/*     */   
/*     */   public void setSentence(String sentence) {
/* 262 */     this.sentence = sentence;
/*     */   }
/*     */   
/*     */   public void setSrtdate(String srtdate) {
/* 266 */     this.srtdate = srtdate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTagtype(Tagtype tagtype)
/*     */   {
/* 274 */     this.tagtype = tagtype;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 284 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */       "TmgEvent [recno=" + this.recno + ", dsid=" + this.dsid + ", " + (this.edate != null ? "edate=" + this.edate + ", " : "") + (this.edsure != null ? "edsure=" + this.edsure + ", " : "") + (this.efoot != null ? "efoot=" + this.efoot + ", " : "") + (this.efsure != null ? "efsure=" + this.efsure + ", " : "") + (this.ensure != null ? "ensure=" + this.ensure + ", " : "") + (this.epsure != null ? "epsure=" + this.epsure + ", " : "") + (this.essure != null ? "essure=" + this.essure + ", " : "") + "etype=" + this.etype + ", per1=" + this.per1 + ", " + (this.per1show != null ? "per1show=" + this.per1show + ", " : "") + "per2=" + this.per2 + ", " + (this.per2show != null ? "per2show=" + this.per2show + ", " : "") + "placenum=" + this.placenum + ", refId=" + this.refId + ", " + (this.sentence != null ? "sentence=" + this.sentence + ", " : "") + (this.srtdate != null ? "srtdate=" + this.srtdate + ", " : "") + (this.ppv != null ? "ppv=" + this.ppv + ", " : "") + (this.tagtype != null ? "tagtype=" + this.tagtype : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\TmgEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */