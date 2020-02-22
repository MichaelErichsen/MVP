/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.logging.Logger;

/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;

/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Eventwitness
/*     */ {
/*  21 */   private static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private int dsid;
/*     */   private int eper;
/*     */   private int gnum;
/*     */   private int namerec;
/*     */   private String role;
/*     */   private int sequence;
/*     */   private String tt;
/*     */   private String witmemo;
/*     */   private String wsentence;
/*     */   private String xprimary;
/*  33 */   private final String SELECT = "SELECT EPER, GNUM, XPRIMARY, WSENTENCE, ROLE, DSID, NAMEREC, WITMEMO, SEQUENCE FROM EVENTWITNESS WHERE EPER = ?";
/*     */   
/*  35 */   private PreparedStatement pst = null;
/*     */   private TmgEvent event;
/*     */   private Name name;
/*     */   
/*     */   public Eventwitness() {}
/*     */   
/*     */   public Eventwitness(Connection conn, int i)
/*     */   {
/*  43 */     this.eper = i;
/*     */     try
/*     */     {
/*  46 */       ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  47 */         "org.historyresearchenvironment.usergui");
/*  48 */       conn = DriverManager.getConnection("jdbc:h2:" + store.getString("DBNAME") + ";IFEXISTS=TRUE", 
/*  49 */         store.getString("H2USERID"), store.getString("H2PASSWORD"));
/*  50 */       this.pst = conn.prepareStatement("SELECT EPER, GNUM, XPRIMARY, WSENTENCE, ROLE, DSID, NAMEREC, WITMEMO, SEQUENCE FROM EVENTWITNESS WHERE EPER = ?");
/*     */       
/*  52 */       this.pst.setInt(1, this.eper);
/*     */       
/*  54 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  56 */       if (rs.next()) {
/*  57 */         setDsid(rs.getInt("DSID"));
/*  58 */         setGnum(rs.getInt("GNUM"));
/*  59 */         setEvent(new TmgEvent(conn, this.gnum));
/*  60 */         setNamerec(rs.getInt("NAMEREC"));
/*  61 */         setName(new Name(conn, this.namerec));
/*  62 */         setRole(rs.getString("ROLE"));
/*  63 */         setSequence(rs.getInt("SEQUENCE"));
/*  64 */         setWsentence(rs.getString("WSENTENCE"));
/*  65 */         setXprimary(rs.getString("XPRIMARY"));
/*     */       }
/*     */       try
/*     */       {
/*  69 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/*  74 */       e.printStackTrace();
/*  75 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  80 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public int getEper() {
/*  84 */     return this.eper;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TmgEvent getEvent()
/*     */   {
/*  91 */     return this.event;
/*     */   }
/*     */   
/*     */   public int getGnum() {
/*  95 */     return this.gnum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Name getName()
/*     */   {
/* 102 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getNamerec() {
/* 106 */     return this.namerec;
/*     */   }
/*     */   
/*     */   public String getRole() {
/* 110 */     return this.role;
/*     */   }
/*     */   
/*     */   public int getSequence() {
/* 114 */     return this.sequence;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 118 */     return this.tt;
/*     */   }
/*     */   
/*     */   public String getWitmemo() {
/* 122 */     return this.witmemo;
/*     */   }
/*     */   
/*     */   public String getWsentence() {
/* 126 */     return this.wsentence;
/*     */   }
/*     */   
/*     */   public String getXprimary() {
/* 130 */     return this.xprimary;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 134 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setEper(int eper) {
/* 138 */     this.eper = eper;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEvent(TmgEvent event)
/*     */   {
/* 146 */     this.event = event;
/*     */   }
/*     */   
/*     */   public void setGnum(int gnum) {
/* 150 */     this.gnum = gnum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(Name name)
/*     */   {
/* 158 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setNamerec(int namerec) {
/* 162 */     this.namerec = namerec;
/*     */   }
/*     */   
/*     */   public void setRole(String role) {
/* 166 */     this.role = role;
/*     */   }
/*     */   
/*     */   public void setSequence(int sequence) {
/* 170 */     this.sequence = sequence;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 174 */     this.tt = tt;
/*     */   }
/*     */   
/*     */   public void setWitmemo(String witmemo) {
/* 178 */     this.witmemo = witmemo;
/*     */   }
/*     */   
/*     */   public void setWsentence(String wsentence) {
/* 182 */     this.wsentence = wsentence;
/*     */   }
/*     */   
/*     */   public void setXprimary(String xprimary) {
/* 186 */     this.xprimary = xprimary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 196 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 201 */       "Eventwitness [dsid=" + this.dsid + ", eper=" + this.eper + ", gnum=" + this.gnum + ", namerec=" + this.namerec + ", " + (this.role != null ? "role=" + this.role + ", " : "") + "sequence=" + this.sequence + ", " + (this.tt != null ? "tt=" + this.tt + ", " : "") + (this.witmemo != null ? "witmemo=" + this.witmemo + ", " : "") + (this.wsentence != null ? "wsentence=" + this.wsentence + ", " : "") + (this.xprimary != null ? "xprimary=" + this.xprimary + ", " : "") + (this.event != null ? "event=" + this.event + ", " : "") + (this.name != null ? "name=" + this.name : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Eventwitness.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */