/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
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
/*     */ public class Name
/*     */ {
/*  19 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private int recno;
/*     */   
/*     */   private int altype;
/*     */   
/*     */   private int dsid;
/*     */   
/*     */   private String dsure;
/*     */   private String fsure;
/*     */   private int givid;
/*     */   private String gvnamesort;
/*     */   private String infg;
/*     */   private String infs;
/*     */   private String ispicked;
/*     */   private Date lastEdit;
/*     */   private String ndate;
/*     */   private String nnote;
/*     */   private int nper;
/*     */   private String nsure;
/*     */   private String pbirth;
/*     */   private String pdeath;
/*     */   private int prefId;
/*     */   private String refer;
/*     */   private String sentence;
/*     */   private String sndxgvn;
/*     */   private String sndxsurn;
/*     */   private String srnamedisp;
/*     */   private String srnamesort;
/*     */   private String srtdate;
/*     */   private int styleid;
/*     */   private int surid;
/*     */   private String xprimary;
/*     */   
/*     */   public Name(Connection conn, int nper)
/*     */   {
/*  55 */     PreparedStatement pst = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */     this.nper = nper;
/*     */     try
/*     */     {
/*  64 */       new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  65 */       pst = conn.prepareStatement("SELECT NPER, ALTYPE, ISPICKED, INFS, INFG, XPRIMARY, NSURE, FSURE, NNOTE, RECNO, SENTENCE, NDATE, SRTDATE, DSURE, DSID, TT, SRNAMESORT, GVNAMESORT, STYLEID, SURID, GIVID, SRNAMEDISP, SNDXSURN, SNDXGVN, PBIRTH, PDEATH, REFER, PREF_ID, LAST_EDIT FROM NAME WHERE NPER = ?");
/*  66 */       pst.setInt(1, nper);
/*     */       
/*     */ 
/*  69 */       ResultSet rs = pst.executeQuery();
/*     */       
/*  71 */       if (rs.next()) {
/*  72 */         setSrnamedisp(rs.getString("SRNAMEDISP"));
/*     */       } else {
/*  74 */         setSrnamedisp("");
/*     */       }
/*     */       try
/*     */       {
/*  78 */         pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/*  83 */       e.printStackTrace();
/*  84 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getAltype() {
/*  89 */     return this.altype;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  93 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public String getDsure() {
/*  97 */     return this.dsure;
/*     */   }
/*     */   
/*     */   public String getFsure() {
/* 101 */     return this.fsure;
/*     */   }
/*     */   
/*     */   public int getGivid() {
/* 105 */     return this.givid;
/*     */   }
/*     */   
/*     */   public String getGvnamesort() {
/* 109 */     return this.gvnamesort;
/*     */   }
/*     */   
/*     */   public String getInfg() {
/* 113 */     return this.infg;
/*     */   }
/*     */   
/*     */   public String getInfs() {
/* 117 */     return this.infs;
/*     */   }
/*     */   
/*     */   public String getIspicked() {
/* 121 */     return this.ispicked;
/*     */   }
/*     */   
/*     */   public Date getLastEdit() {
/* 125 */     return this.lastEdit;
/*     */   }
/*     */   
/*     */   public String getNdate() {
/* 129 */     return this.ndate;
/*     */   }
/*     */   
/*     */   public String getNnote() {
/* 133 */     return this.nnote;
/*     */   }
/*     */   
/*     */   public int getNper() {
/* 137 */     return this.nper;
/*     */   }
/*     */   
/*     */   public String getNsure() {
/* 141 */     return this.nsure;
/*     */   }
/*     */   
/*     */   public String getPbirth() {
/* 145 */     return this.pbirth;
/*     */   }
/*     */   
/*     */   public String getPdeath() {
/* 149 */     return this.pdeath;
/*     */   }
/*     */   
/*     */   public int getPrefId() {
/* 153 */     return this.prefId;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/* 157 */     return this.recno;
/*     */   }
/*     */   
/*     */   public String getRefer() {
/* 161 */     return this.refer;
/*     */   }
/*     */   
/*     */   public String getSentence() {
/* 165 */     return this.sentence;
/*     */   }
/*     */   
/*     */   public String getSndxgvn() {
/* 169 */     return this.sndxgvn;
/*     */   }
/*     */   
/*     */   public String getSndxsurn() {
/* 173 */     return this.sndxsurn;
/*     */   }
/*     */   
/*     */   public String getSrnamedisp() {
/* 177 */     return this.srnamedisp;
/*     */   }
/*     */   
/*     */   public String getSrnamesort() {
/* 181 */     return this.srnamesort;
/*     */   }
/*     */   
/*     */   public String getSrtdate() {
/* 185 */     return this.srtdate;
/*     */   }
/*     */   
/*     */   public int getStyleid() {
/* 189 */     return this.styleid;
/*     */   }
/*     */   
/*     */   public int getSurid() {
/* 193 */     return this.surid;
/*     */   }
/*     */   
/*     */   public String getXprimary() {
/* 197 */     return this.xprimary;
/*     */   }
/*     */   
/*     */   public void setAltype(int altype) {
/* 201 */     this.altype = altype;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 205 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setDsure(String dsure) {
/* 209 */     this.dsure = dsure;
/*     */   }
/*     */   
/*     */   public void setFsure(String fsure) {
/* 213 */     this.fsure = fsure;
/*     */   }
/*     */   
/*     */   public void setGivid(int givid) {
/* 217 */     this.givid = givid;
/*     */   }
/*     */   
/*     */   public void setGvnamesort(String gvnamesort) {
/* 221 */     this.gvnamesort = gvnamesort;
/*     */   }
/*     */   
/*     */   public void setInfg(String infg) {
/* 225 */     this.infg = infg;
/*     */   }
/*     */   
/*     */   public void setInfs(String infs) {
/* 229 */     this.infs = infs;
/*     */   }
/*     */   
/*     */   public void setIspicked(String ispicked) {
/* 233 */     this.ispicked = ispicked;
/*     */   }
/*     */   
/*     */   public void setLastEdit(Date lastEdit) {
/* 237 */     this.lastEdit = lastEdit;
/*     */   }
/*     */   
/*     */   public void setNdate(String ndate) {
/* 241 */     this.ndate = ndate;
/*     */   }
/*     */   
/*     */   public void setNnote(String nnote) {
/* 245 */     this.nnote = nnote;
/*     */   }
/*     */   
/*     */   public void setNper(int nper) {
/* 249 */     this.nper = nper;
/*     */   }
/*     */   
/*     */   public void setNsure(String nsure) {
/* 253 */     this.nsure = nsure;
/*     */   }
/*     */   
/*     */   public void setPbirth(String pbirth) {
/* 257 */     this.pbirth = pbirth;
/*     */   }
/*     */   
/*     */   public void setPdeath(String pdeath) {
/* 261 */     this.pdeath = pdeath;
/*     */   }
/*     */   
/*     */   public void setPrefId(int prefId) {
/* 265 */     this.prefId = prefId;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 269 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setRefer(String refer) {
/* 273 */     this.refer = refer;
/*     */   }
/*     */   
/*     */   public void setSentence(String sentence) {
/* 277 */     this.sentence = sentence;
/*     */   }
/*     */   
/*     */   public void setSndxgvn(String sndxgvn) {
/* 281 */     this.sndxgvn = sndxgvn;
/*     */   }
/*     */   
/*     */   public void setSndxsurn(String sndxsurn) {
/* 285 */     this.sndxsurn = sndxsurn;
/*     */   }
/*     */   
/*     */   public void setSrnamedisp(String srnamedisp) {
/* 289 */     this.srnamedisp = srnamedisp;
/*     */   }
/*     */   
/*     */   public void setSrnamesort(String srnamesort) {
/* 293 */     this.srnamesort = srnamesort;
/*     */   }
/*     */   
/*     */   public void setSrtdate(String srtdate) {
/* 297 */     this.srtdate = srtdate;
/*     */   }
/*     */   
/*     */   public void setStyleid(int styleid) {
/* 301 */     this.styleid = styleid;
/*     */   }
/*     */   
/*     */   public void setSurid(int surid) {
/* 305 */     this.surid = surid;
/*     */   }
/*     */   
/*     */   public void setXprimary(String xprimary) {
/* 309 */     this.xprimary = xprimary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 319 */     return 
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
/*     */ 
/* 335 */       "Name [recno=" + this.recno + ", altype=" + this.altype + ", dsid=" + this.dsid + ", " + (this.dsure != null ? "dsure=" + this.dsure + ", " : "") + (this.fsure != null ? "fsure=" + this.fsure + ", " : "") + "givid=" + this.givid + ", " + (this.gvnamesort != null ? "gvnamesort=" + this.gvnamesort + ", " : "") + (this.infg != null ? "infg=" + this.infg + ", " : "") + (this.infs != null ? "infs=" + this.infs + ", " : "") + (this.ispicked != null ? "ispicked=" + this.ispicked + ", " : "") + (this.lastEdit != null ? "lastEdit=" + this.lastEdit + ", " : "") + (this.ndate != null ? "ndate=" + this.ndate + ", " : "") + (this.nnote != null ? "nnote=" + this.nnote + ", " : "") + "nper=" + this.nper + ", " + (this.nsure != null ? "nsure=" + this.nsure + ", " : "") + (this.pbirth != null ? "pbirth=" + this.pbirth + ", " : "") + (this.pdeath != null ? "pdeath=" + this.pdeath + ", " : "") + "prefId=" + this.prefId + ", " + (this.refer != null ? "refer=" + this.refer + ", " : "") + (this.sentence != null ? "sentence=" + this.sentence + ", " : "") + (this.sndxgvn != null ? "sndxgvn=" + this.sndxgvn + ", " : "") + (this.sndxsurn != null ? "sndxsurn=" + this.sndxsurn + ", " : "") + (this.srnamedisp != null ? "srnamedisp=" + this.srnamedisp + ", " : "") + (this.srnamesort != null ? "srnamesort=" + this.srnamesort + ", " : "") + (this.srtdate != null ? "srtdate=" + this.srtdate + ", " : "") + "styleid=" + this.styleid + ", surid=" + this.surid + ", " + (this.xprimary != null ? "xprimary=" + this.xprimary : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Name.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */