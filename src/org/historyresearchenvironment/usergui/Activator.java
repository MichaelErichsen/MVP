/*     */ package org.historyresearchenvironment.usergui;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.osgi.service.environment.EnvironmentInfo;
/*     */ import org.osgi.framework.BundleActivator;
/*     */ import org.osgi.framework.BundleContext;
/*     */ import org.osgi.framework.ServiceReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Activator
/*     */   implements BundleActivator
/*     */ {
/*  24 */   private final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private static BundleContext context;
/*     */   
/*     */ 
/*     */   static BundleContext getContext()
/*     */   {
/*  31 */     return context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start(BundleContext bundleContext)
/*     */     throws Exception
/*     */   {
/*  42 */     context = bundleContext;
/*     */     
/*  44 */     HRELogger.setup();
/*     */     
/*  46 */     ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  47 */       "org.historyresearchenvironment.usergui");
/*     */     
/*  49 */     String levelName = store.getString("LOGLEVEL");
/*     */     
/*  51 */     if (levelName.equals("OFF")) {
/*  52 */       this.LOGGER.setLevel(Level.OFF);
/*  53 */     } else if (levelName.equals("SEVERE")) {
/*  54 */       this.LOGGER.setLevel(Level.SEVERE);
/*  55 */     } else if (levelName.equals("WARNING")) {
/*  56 */       this.LOGGER.setLevel(Level.WARNING);
/*  57 */     } else if (levelName.equals("CONFIG")) {
/*  58 */       this.LOGGER.setLevel(Level.CONFIG);
/*  59 */     } else if (levelName.equals("FINE")) {
/*  60 */       this.LOGGER.setLevel(Level.FINE);
/*  61 */     } else if (levelName.equals("FINER")) {
/*  62 */       this.LOGGER.setLevel(Level.FINER);
/*  63 */     } else if (levelName.equals("FINEST")) {
/*  64 */       this.LOGGER.setLevel(Level.FINEST);
/*  65 */     } else if (levelName.equals("ALL")) {
/*  66 */       this.LOGGER.setLevel(Level.ALL);
/*     */     } else {
/*  68 */       this.LOGGER.setLevel(Level.INFO);
/*     */     }
/*     */     
/*  71 */     this.LOGGER.info("Command line arguments:");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */     ServiceReference<EnvironmentInfo> envRef = context.getServiceReference(EnvironmentInfo.class);
/*  82 */     EnvironmentInfo envInfo = (EnvironmentInfo)context.getService(envRef);
/*  83 */     String[] args = envInfo.getCommandLineArgs();
/*  84 */     for (int i = 0; i < args.length; i++) {
/*  85 */       System.out.println("CLI " + i + ": " + args[i]);
/*     */     }
/*     */     
/*  88 */     String csMode = store.getString("CSMODE");
/*  89 */     this.LOGGER.info("Client/server mode " + csMode);
/*  90 */     this.LOGGER.info("HRE Absolute path: " + new File(".").getAbsolutePath());
/*  91 */     this.LOGGER.info("HRE Font: " + store.getString("HREFONT"));
/*     */     
/*  93 */     String command = "java -classpath plugins\\org.eclipse.help.base_4.2.102.v20171130-0510.jar org.eclipse.help.standalone.Infocenter -command start -port " + 
/*  94 */       store.getInt("HELPSYSTEMPORT") + " -product org.historyresearchenvironment.helpsystem -clean";
/*     */     try
/*     */     {
/*  97 */       this.LOGGER.info("Help System is being started at port " + store.getInt("HELPSYSTEMPORT"));
/*  98 */       Runtime.getRuntime().exec(command);
/*  99 */       this.LOGGER.info("Has started " + command);
/*     */     } catch (Exception e) {
/* 101 */       this.LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop(BundleContext bundleContext)
/*     */     throws Exception
/*     */   {
/* 113 */     context = null;
/*     */     
/*     */     try
/*     */     {
/* 117 */       Runtime.getRuntime().exec("java -classpath plugins\\org.eclipse.help.base_4.2.102.v20171130-0510.jar org.eclipse.help.standalone.Infocenter -command shutdown");
/* 118 */       this.LOGGER.info("Help System is being stopped");
/* 119 */       HreH2ConnectionPool.dispose();
/*     */     } catch (Exception e) {
/* 121 */       this.LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\Activator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */