/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Exhibit
/*     */ {
/*  22 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private static final String SELECT = "SELECT IDEXHIBIT, IDREF, RLTYPE, RLNUM, XNAME, VFILENAME, IFILENAME, AFILENAME, TFILENAME, REFERENCE, TEXT, IMAGE, AUDIO, DESCRIPT, RLPER1, RLPER2, RLGTYPE, OLE_OBJECT, XPRIMARY, VIDEO, PROPERTY, DSID, TT, ID_PERSON, RECNO, ID_EVENT, ID_SOURCE, ID_REPOS, THUMB, ID_CIT, ID_PLACE, CAPTION, SORTEXH, IMAGEFORE, IMAGEBACK, TRANSPAR FROM EXHIBIT WHERE IDEXHIBIT = ?";
/*     */   
/*     */   private int idexhibit;
/*     */   
/*     */   private String afilename;
/*     */   
/*     */   private String audio;
/*     */   
/*     */   private String caption;
/*     */   
/*     */   private String descript;
/*     */   
/*     */   private int dsid;
/*     */   
/*     */   private int idCit;
/*     */   private int idEvent;
/*     */   private int idPerson;
/*     */   private int idPlace;
/*     */   private int idRepos;
/*     */   private int idSource;
/*     */   private int idref;
/*     */   private String ifilename;
/*     */   private String image;
/*     */   private String imageback;
/*     */   private String imagefore;
/*     */   private String oleObject;
/*     */   private String property;
/*     */   private int recno;
/*     */   private String reference;
/*     */   private int rlgtype;
/*     */   private int rlnum;
/*     */   private int rlper1;
/*     */   private int rlper2;
/*     */   private String rltype;
/*     */   private int sortexh;
/*     */   private String text;
/*     */   private String tfilename;
/*     */   private String thumb;
/*     */   private BigDecimal transpar;
/*     */   private String vfilename;
/*     */   private String video;
/*     */   private String xname;
/*     */   private String xprimary;
/*     */   private Person person;
/*     */   private TmgEvent event;
/*  69 */   private PreparedStatement pst = null;
/*     */   
/*     */   public Exhibit(Connection conn, int i) {
/*  72 */     this.idexhibit = i;
/*     */     try
/*     */     {
/*  75 */       ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  76 */         "org.historyresearchenvironment.usergui");
/*  77 */       conn = DriverManager.getConnection("jdbc:h2:" + store.getString("DBNAME") + ";IFEXISTS=TRUE", 
/*  78 */         store.getString("H2USERID"), store.getString("H2PASSWORD"));
/*  79 */       this.pst = conn.prepareStatement("SELECT IDEXHIBIT, IDREF, RLTYPE, RLNUM, XNAME, VFILENAME, IFILENAME, AFILENAME, TFILENAME, REFERENCE, TEXT, IMAGE, AUDIO, DESCRIPT, RLPER1, RLPER2, RLGTYPE, OLE_OBJECT, XPRIMARY, VIDEO, PROPERTY, DSID, TT, ID_PERSON, RECNO, ID_EVENT, ID_SOURCE, ID_REPOS, THUMB, ID_CIT, ID_PLACE, CAPTION, SORTEXH, IMAGEFORE, IMAGEBACK, TRANSPAR FROM EXHIBIT WHERE IDEXHIBIT = ?");
/*     */       
/*  81 */       this.pst.setInt(1, this.idexhibit);
/*     */       
/*  83 */       ResultSet rs = this.pst.executeQuery();
/*     */       
/*  85 */       if (rs.next()) {
/*  86 */         setXname(rs.getString("XNAME"));
/*  87 */         setIdPerson(rs.getInt("ID_PERSON"));
/*  88 */         if (this.idPerson > 0) {
/*  89 */           this.person = new Person(conn, this.idPerson);
/*     */         }
/*  91 */         setIdEvent(rs.getInt("ID_EVENT"));
/*  92 */         if (this.idEvent > 0) {
/*  93 */           this.event = new TmgEvent(conn, this.idEvent);
/*     */         }
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  99 */         this.pst.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       return;
/*     */     } catch (Exception e) {
/* 104 */       e.printStackTrace();
/* 105 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAfilename() {
/* 110 */     return this.afilename;
/*     */   }
/*     */   
/*     */   public String getAudio() {
/* 114 */     return this.audio;
/*     */   }
/*     */   
/*     */   public String getCaption() {
/* 118 */     return this.caption;
/*     */   }
/*     */   
/*     */   public String getDescript() {
/* 122 */     return this.descript;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/* 126 */     return this.dsid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TmgEvent getEvent()
/*     */   {
/* 133 */     return this.event;
/*     */   }
/*     */   
/*     */   public int getIdCit() {
/* 137 */     return this.idCit;
/*     */   }
/*     */   
/*     */   public int getIdEvent() {
/* 141 */     return this.idEvent;
/*     */   }
/*     */   
/*     */   public int getIdexhibit() {
/* 145 */     return this.idexhibit;
/*     */   }
/*     */   
/*     */   public int getIdPerson() {
/* 149 */     return this.idPerson;
/*     */   }
/*     */   
/*     */   public int getIdPlace() {
/* 153 */     return this.idPlace;
/*     */   }
/*     */   
/*     */   public int getIdref() {
/* 157 */     return this.idref;
/*     */   }
/*     */   
/*     */   public int getIdRepos() {
/* 161 */     return this.idRepos;
/*     */   }
/*     */   
/*     */   public int getIdSource() {
/* 165 */     return this.idSource;
/*     */   }
/*     */   
/*     */   public String getIfilename() {
/* 169 */     return this.ifilename;
/*     */   }
/*     */   
/*     */   public String getImage() {
/* 173 */     return this.image;
/*     */   }
/*     */   
/*     */   public String getImageback() {
/* 177 */     return this.imageback;
/*     */   }
/*     */   
/*     */   public String getImagefore() {
/* 181 */     return this.imagefore;
/*     */   }
/*     */   
/*     */   public String getOleObject() {
/* 185 */     return this.oleObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Person getPerson()
/*     */   {
/* 192 */     return this.person;
/*     */   }
/*     */   
/*     */   public String getProperty() {
/* 196 */     return this.property;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/* 200 */     return this.recno;
/*     */   }
/*     */   
/*     */   public String getReference() {
/* 204 */     return this.reference;
/*     */   }
/*     */   
/*     */   public int getRlgtype() {
/* 208 */     return this.rlgtype;
/*     */   }
/*     */   
/*     */   public int getRlnum() {
/* 212 */     return this.rlnum;
/*     */   }
/*     */   
/*     */   public int getRlper1() {
/* 216 */     return this.rlper1;
/*     */   }
/*     */   
/*     */   public int getRlper2() {
/* 220 */     return this.rlper2;
/*     */   }
/*     */   
/*     */   public String getRltype() {
/* 224 */     return this.rltype;
/*     */   }
/*     */   
/*     */   public int getSortexh() {
/* 228 */     return this.sortexh;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 232 */     return this.text;
/*     */   }
/*     */   
/*     */   public String getTfilename() {
/* 236 */     return this.tfilename;
/*     */   }
/*     */   
/*     */   public String getThumb() {
/* 240 */     return this.thumb;
/*     */   }
/*     */   
/*     */   public BigDecimal getTranspar() {
/* 244 */     return this.transpar;
/*     */   }
/*     */   
/*     */   public String getVfilename() {
/* 248 */     return this.vfilename;
/*     */   }
/*     */   
/*     */   public String getVideo() {
/* 252 */     return this.video;
/*     */   }
/*     */   
/*     */   public String getXname() {
/* 256 */     return this.xname;
/*     */   }
/*     */   
/*     */   public String getXprimary() {
/* 260 */     return this.xprimary;
/*     */   }
/*     */   
/*     */   public void setAfilename(String afilename) {
/* 264 */     this.afilename = afilename;
/*     */   }
/*     */   
/*     */   public void setAudio(String audio) {
/* 268 */     this.audio = audio;
/*     */   }
/*     */   
/*     */   public void setCaption(String caption) {
/* 272 */     this.caption = caption;
/*     */   }
/*     */   
/*     */   public void setDescript(String descript) {
/* 276 */     this.descript = descript;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 280 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEvent(TmgEvent event)
/*     */   {
/* 288 */     this.event = event;
/*     */   }
/*     */   
/*     */   public void setIdCit(int idCit) {
/* 292 */     this.idCit = idCit;
/*     */   }
/*     */   
/*     */   public void setIdEvent(int idEvent) {
/* 296 */     this.idEvent = idEvent;
/*     */   }
/*     */   
/*     */   public void setIdexhibit(int idexhibit) {
/* 300 */     this.idexhibit = idexhibit;
/*     */   }
/*     */   
/*     */   public void setIdPerson(int idPerson) {
/* 304 */     this.idPerson = idPerson;
/*     */   }
/*     */   
/*     */   public void setIdPlace(int idPlace) {
/* 308 */     this.idPlace = idPlace;
/*     */   }
/*     */   
/*     */   public void setIdref(int idref) {
/* 312 */     this.idref = idref;
/*     */   }
/*     */   
/*     */   public void setIdRepos(int idRepos) {
/* 316 */     this.idRepos = idRepos;
/*     */   }
/*     */   
/*     */   public void setIdSource(int idSource) {
/* 320 */     this.idSource = idSource;
/*     */   }
/*     */   
/*     */   public void setIfilename(String ifilename) {
/* 324 */     this.ifilename = ifilename;
/*     */   }
/*     */   
/*     */   public void setImage(String image) {
/* 328 */     this.image = image;
/*     */   }
/*     */   
/*     */   public void setImageback(String imageback) {
/* 332 */     this.imageback = imageback;
/*     */   }
/*     */   
/*     */   public void setImagefore(String imagefore) {
/* 336 */     this.imagefore = imagefore;
/*     */   }
/*     */   
/*     */   public void setOleObject(String oleObject) {
/* 340 */     this.oleObject = oleObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPerson(Person person)
/*     */   {
/* 348 */     this.person = person;
/*     */   }
/*     */   
/*     */   public void setProperty(String property) {
/* 352 */     this.property = property;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 356 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setReference(String reference) {
/* 360 */     this.reference = reference;
/*     */   }
/*     */   
/*     */   public void setRlgtype(int rlgtype) {
/* 364 */     this.rlgtype = rlgtype;
/*     */   }
/*     */   
/*     */   public void setRlnum(int rlnum) {
/* 368 */     this.rlnum = rlnum;
/*     */   }
/*     */   
/*     */   public void setRlper1(int rlper1) {
/* 372 */     this.rlper1 = rlper1;
/*     */   }
/*     */   
/*     */   public void setRlper2(int rlper2) {
/* 376 */     this.rlper2 = rlper2;
/*     */   }
/*     */   
/*     */   public void setRltype(String rltype) {
/* 380 */     this.rltype = rltype;
/*     */   }
/*     */   
/*     */   public void setSortexh(int sortexh) {
/* 384 */     this.sortexh = sortexh;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/* 388 */     this.text = text;
/*     */   }
/*     */   
/*     */   public void setTfilename(String tfilename) {
/* 392 */     this.tfilename = tfilename;
/*     */   }
/*     */   
/*     */   public void setThumb(String thumb) {
/* 396 */     this.thumb = thumb;
/*     */   }
/*     */   
/*     */   public void setTranspar(BigDecimal transpar) {
/* 400 */     this.transpar = transpar;
/*     */   }
/*     */   
/*     */   public void setVfilename(String vfilename) {
/* 404 */     this.vfilename = vfilename;
/*     */   }
/*     */   
/*     */   public void setVideo(String video) {
/* 408 */     this.video = video;
/*     */   }
/*     */   
/*     */   public void setXname(String xname) {
/* 412 */     this.xname = xname;
/*     */   }
/*     */   
/*     */   public void setXprimary(String xprimary) {
/* 416 */     this.xprimary = xprimary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 426 */     return 
/* 427 */       "Exhibit [idexhibit=" + this.idexhibit + ", " + (this.person != null ? "person=" + this.person + ", " : "") + (this.event != null ? "event=" + this.event : "") + "]";
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Exhibit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */