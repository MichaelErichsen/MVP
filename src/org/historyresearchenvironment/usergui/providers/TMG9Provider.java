/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.IProgressMonitor;
/*     */ import org.eclipse.core.runtime.SubMonitor;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.jface.operation.IRunnableWithProgress;
/*     */ import org.historyresearchenvironment.tmg.h2.Dbf2H2.CreateH2Database;
/*     */ import org.historyresearchenvironment.tmg.h2.Dbf2H2.TableLoader;
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
/*     */ public class TMG9Provider
/*     */   implements IRunnableWithProgress
/*     */ {
/*  28 */   private static final Logger LOGGER = Logger.getLogger("global");
/*     */   @Inject
/*     */   private IEventBroker eventBroker;
/*  31 */   private final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  32 */     "org.historyresearchenvironment.usergui");
/*     */   
/*     */ 
/*     */   private String dbName;
/*     */   
/*     */   private String project;
/*     */   
/*     */ 
/*     */   public TMG9Provider(String dbName, String project)
/*     */   {
/*  42 */     this.dbName = dbName;
/*  43 */     this.project = project;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDbName()
/*     */   {
/*  50 */     return this.dbName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getProject()
/*     */   {
/*  57 */     return this.project;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run(IProgressMonitor monitor)
/*     */     throws InvocationTargetException, InterruptedException
/*     */   {
/*  68 */     LOGGER.info("Run");
/*  69 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 30);
/*     */     
/*     */     try
/*     */     {
/*  73 */       subMonitor.beginTask("Converting TMG9 Project " + this.project + " to H2", 30);
/*  74 */       subMonitor.subTask("Creating H2 Database " + this.dbName);
/*     */       
/*  76 */       this.store.putValue("DBNAME", this.dbName);
/*  77 */       CreateH2Database createH2Database = new CreateH2Database(this.dbName, 
/*  78 */         subMonitor.split(2, 0));
/*  79 */       createH2Database.run();
/*  80 */       subMonitor.worked(2);
/*  81 */       LOGGER.info("DB " + this.dbName + " created");
/*     */       
/*  83 */       TableLoader ld = new TableLoader(this.project, "D", "DATASET", this.dbName, 
/*  84 */         subMonitor.split(1, 0));
/*  85 */       ld.run();
/*  86 */       subMonitor.worked(1);
/*     */       
/*  88 */       ld = new TableLoader(this.project, "DNA", "DNA", this.dbName, subMonitor.split(1, 0));
/*  89 */       ld.run();
/*  90 */       subMonitor.worked(1);
/*     */       
/*  92 */       ld = new TableLoader(this.project, "G", "EVENT", this.dbName, 
/*  93 */         subMonitor.split(1, 0));
/*  94 */       ld.run();
/*  95 */       subMonitor.worked(1);
/*     */       
/*  97 */       ld = new TableLoader(this.project, "E", "EVENTWITNESS", this.dbName, 
/*  98 */         subMonitor.split(1, 0));
/*  99 */       ld.run();
/* 100 */       subMonitor.worked(1);
/*     */       
/* 102 */       ld = new TableLoader(this.project, "XD", "EXCLUDEDPAIR", this.dbName, 
/* 103 */         subMonitor.split(1, 0));
/* 104 */       ld.run();
/* 105 */       subMonitor.worked(1);
/*     */       
/* 107 */       ld = new TableLoader(this.project, "I", "EXHIBIT", this.dbName, 
/* 108 */         subMonitor.split(1, 0));
/* 109 */       ld.run();
/* 110 */       subMonitor.worked(1);
/*     */       
/* 112 */       ld = new TableLoader(this.project, "C", "FLAG", this.dbName, subMonitor.split(1, 0));
/* 113 */       ld.run();
/* 114 */       subMonitor.worked(1);
/*     */       
/* 116 */       ld = new TableLoader(this.project, "O", "FOCUSGROUP", this.dbName, 
/* 117 */         subMonitor.split(1, 0));
/* 118 */       ld.run();
/* 119 */       subMonitor.worked(1);
/*     */       
/* 121 */       ld = new TableLoader(this.project, "B", "FOCUSGROUPMEMBER", this.dbName, 
/* 122 */         subMonitor.split(1, 0));
/* 123 */       ld.run();
/* 124 */       subMonitor.worked(1);
/*     */       
/* 126 */       ld = new TableLoader(this.project, "N", "NAME", this.dbName, subMonitor.split(1, 0));
/* 127 */       ld.run();
/* 128 */       subMonitor.worked(1);
/*     */       
/* 130 */       ld = new TableLoader(this.project, "ND", "NAMEDICTIONARY", this.dbName, 
/* 131 */         subMonitor.split(1, 0));
/* 132 */       ld.run();
/* 133 */       subMonitor.worked(1);
/*     */       
/* 135 */       ld = new TableLoader(this.project, "NPT", "NAMEPARTTYPE", this.dbName, 
/* 136 */         subMonitor.split(1, 0));
/* 137 */       ld.run();
/* 138 */       subMonitor.worked(1);
/*     */       
/* 140 */       ld = new TableLoader(this.project, "NPV", "NAMEPARTVALUE", this.dbName, 
/* 141 */         subMonitor.split(1, 0));
/* 142 */       ld.run();
/* 143 */       subMonitor.worked(1);
/*     */       
/* 145 */       ld = new TableLoader(this.project, "F", "PARENTCHILDRELATIONSHIP", this.dbName, 
/* 146 */         subMonitor.split(1, 0));
/* 147 */       ld.run();
/* 148 */       subMonitor.worked(1);
/*     */       
/* 150 */       ld = new TableLoader(this.project, "$", "PERSON", this.dbName, 
/* 151 */         subMonitor.split(1, 0));
/* 152 */       ld.run();
/* 153 */       subMonitor.worked(1);
/*     */       
/* 155 */       ld = new TableLoader(this.project, "P", "PLACE", this.dbName, 
/* 156 */         subMonitor.split(1, 0));
/* 157 */       ld.run();
/* 158 */       subMonitor.worked(1);
/*     */       
/* 160 */       ld = new TableLoader(this.project, "PD", "PLACEDICTIONARY", this.dbName, 
/* 161 */         subMonitor.split(1, 0));
/* 162 */       ld.run();
/* 163 */       subMonitor.worked(1);
/*     */       
/* 165 */       ld = new TableLoader(this.project, "PPT", "PLACEPARTTYPE", this.dbName, 
/* 166 */         subMonitor.split(1, 0));
/* 167 */       ld.run();
/* 168 */       subMonitor.worked(1);
/*     */       
/* 170 */       ld = new TableLoader(this.project, "PPV", "PLACEPARTVALUE", this.dbName, 
/* 171 */         subMonitor.split(1, 0));
/* 172 */       ld.run();
/* 173 */       subMonitor.worked(1);
/*     */       
/* 175 */       ld = new TableLoader(this.project, "R", "REPOSITORY", this.dbName, 
/* 176 */         subMonitor.split(1, 0));
/* 177 */       ld.run();
/* 178 */       subMonitor.worked(1);
/*     */       
/* 180 */       ld = new TableLoader(this.project, "W", "REPOSITORYLINK", this.dbName, 
/* 181 */         subMonitor.split(1, 0));
/* 182 */       ld.run();
/* 183 */       subMonitor.worked(1);
/*     */       
/* 185 */       ld = new TableLoader(this.project, "L", "RESEARCHLOG", this.dbName, 
/* 186 */         subMonitor.split(1, 0));
/* 187 */       ld.run();
/* 188 */       subMonitor.worked(1);
/*     */       
/* 190 */       ld = new TableLoader(this.project, "M", "SOURCE", this.dbName, 
/* 191 */         subMonitor.split(1, 0));
/* 192 */       ld.run();
/* 193 */       subMonitor.worked(1);
/*     */       
/* 195 */       ld = new TableLoader(this.project, "S", "SOURCECITATION", this.dbName, 
/* 196 */         subMonitor.split(1, 0));
/* 197 */       ld.run();
/* 198 */       subMonitor.worked(1);
/*     */       
/* 200 */       ld = new TableLoader(this.project, "U", "SOURCEELEMENT", this.dbName, 
/* 201 */         subMonitor.split(1, 0));
/* 202 */       ld.run();
/* 203 */       subMonitor.worked(1);
/*     */       
/* 205 */       ld = new TableLoader(this.project, "A", "SOURCETYPE", this.dbName, 
/* 206 */         subMonitor.split(1, 0));
/* 207 */       ld.run();
/* 208 */       subMonitor.worked(1);
/*     */       
/* 210 */       ld = new TableLoader(this.project, "ST", "STYLE", this.dbName, 
/* 211 */         subMonitor.split(1, 0));
/* 212 */       ld.run();
/* 213 */       subMonitor.worked(1);
/*     */       
/* 215 */       ld = new TableLoader(this.project, "T", "TAGTYPE", this.dbName, 
/* 216 */         subMonitor.split(1, 0));
/* 217 */       ld.run();
/* 218 */       subMonitor.worked(1);
/*     */       
/* 220 */       ld = new TableLoader(this.project, "K", "TIMELINELOCK", this.dbName, 
/* 221 */         subMonitor.split(1, 0));
/* 222 */       ld.run();
/* 223 */       subMonitor.worked(1);
/*     */       
/* 225 */       this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(1));
/*     */     } catch (Exception e) {
/* 227 */       StringBuilder sb = new StringBuilder(e.getClass() + " " + e.getMessage());
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 229 */       int j = (arrayOfStackTraceElement = e.getStackTrace()).length; for (int i = 0; i < j; i++) { StackTraceElement element = arrayOfStackTraceElement[i];
/* 230 */         sb.append("\n" + element.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDbName(String dbName)
/*     */   {
/* 241 */     this.dbName = dbName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProject(String project)
/*     */   {
/* 249 */     this.project = project;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\TMG9Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */