/*      */ package org.apache.commons.logging.impl;
/*      */ 
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogConfigurationException;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LogFactoryImpl
/*      */   extends LogFactory
/*      */ {
/*      */   private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
/*      */   private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
/*      */   private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
/*      */   private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
/*      */   private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
/*   78 */   private static final int PKG_LEN = "org.apache.commons.logging.impl.".length();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  145 */   private static final String[] classesToDiscover = { "org.apache.commons.logging.impl.Log4JLogger", 
/*  146 */     "org.apache.commons.logging.impl.Jdk14Logger", "org.apache.commons.logging.impl.Jdk13LumberjackLogger", 
/*  147 */     "org.apache.commons.logging.impl.SimpleLog" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ClassLoader getClassLoader(Class clazz)
/*      */   {
/*  156 */     return LogFactory.getClassLoader(clazz);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ClassLoader getContextClassLoader()
/*      */     throws LogConfigurationException
/*      */   {
/*  168 */     return LogFactory.getContextClassLoader();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ClassLoader getContextClassLoaderInternal()
/*      */     throws LogConfigurationException
/*      */   {
/*  191 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*  194 */         return LogFactoryImpl.access$0();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getSystemProperty(String key, final String def)
/*      */     throws SecurityException
/*      */   {
/*  209 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*  212 */         return System.getProperty(LogFactoryImpl.this, def);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static boolean isDiagnosticsEnabled()
/*      */   {
/*  222 */     return LogFactory.isDiagnosticsEnabled();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  229 */   private boolean useTCCL = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String diagnosticPrefix;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  239 */   protected Hashtable<String, Object> attributes = new Hashtable();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */   protected Hashtable instances = new Hashtable();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String logClassName;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */   protected Constructor logConstructor = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  263 */   protected Class[] logConstructorSignature = { String.class };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  269 */   protected Method logMethod = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  276 */   protected Class[] logMethodSignature = { LogFactory.class };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean allowFlawedContext;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean allowFlawedDiscovery;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean allowFlawedHierarchy;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public LogFactoryImpl()
/*      */   {
/*  298 */     initDiagnostics();
/*  299 */     if (isDiagnosticsEnabled()) {
/*  300 */       logDiagnostic("Instance created.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Log createLogFromClass(String logAdapterClassName, String logCategory, boolean affectState)
/*      */     throws LogConfigurationException
/*      */   {
/*  324 */     if (isDiagnosticsEnabled()) {
/*  325 */       logDiagnostic("Attempting to instantiate '" + logAdapterClassName + "'");
/*      */     }
/*      */     
/*  328 */     Object[] params = { logCategory };
/*  329 */     Log logAdapter = null;
/*  330 */     Constructor constructor = null;
/*      */     
/*  332 */     Class logAdapterClass = null;
/*  333 */     ClassLoader currentCL = getBaseClassLoader();
/*      */     
/*      */ 
/*      */     for (;;)
/*      */     {
/*  338 */       logDiagnostic("Trying to load '" + logAdapterClassName + "' from classloader " + objectId(currentCL));
/*      */       try {
/*  340 */         if (isDiagnosticsEnabled())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  346 */           String resourceName = logAdapterClassName.replace('.', '/') + ".class";
/*  347 */           URL url; URL url; if (currentCL != null) {
/*  348 */             url = currentCL.getResource(resourceName);
/*      */           } else {
/*  350 */             url = ClassLoader.getSystemResource(resourceName + ".class");
/*      */           }
/*      */           
/*  353 */           if (url == null) {
/*  354 */             logDiagnostic("Class '" + logAdapterClassName + "' [" + resourceName + "] cannot be found.");
/*      */           } else {
/*  356 */             logDiagnostic("Class '" + logAdapterClassName + "' was found at '" + url + "'");
/*      */           }
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  362 */           c = Class.forName(logAdapterClassName, true, currentCL);
/*      */         }
/*      */         catch (ClassNotFoundException originalClassNotFoundException)
/*      */         {
/*      */           Class c;
/*  367 */           String msg = originalClassNotFoundException.getMessage();
/*  368 */           logDiagnostic("The log adapter '" + logAdapterClassName + "' is not available via classloader " + 
/*  369 */             objectId(currentCL) + ": " + msg.trim());
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/*  378 */             c = Class.forName(logAdapterClassName);
/*      */           } catch (ClassNotFoundException secondaryClassNotFoundException) {
/*      */             Class c;
/*  381 */             msg = secondaryClassNotFoundException.getMessage();
/*  382 */             logDiagnostic("The log adapter '" + logAdapterClassName + 
/*  383 */               "' is not available via the LogFactoryImpl class classloader: " + msg.trim());
/*  384 */             break;
/*      */           }
/*      */         }
/*      */         Class c;
/*  388 */         constructor = c.getConstructor(this.logConstructorSignature);
/*  389 */         Object o = constructor.newInstance(params);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  395 */         if ((o instanceof Log)) {
/*  396 */           logAdapterClass = c;
/*  397 */           logAdapter = (Log)o;
/*  398 */           break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  411 */         handleFlawedHierarchy(currentCL, c);
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (NoClassDefFoundError e)
/*      */       {
/*      */ 
/*  418 */         String msg = e.getMessage();
/*  419 */         logDiagnostic("The log adapter '" + logAdapterClassName + 
/*  420 */           "' is missing dependencies when loaded via classloader " + objectId(currentCL) + ": " + 
/*  421 */           msg.trim());
/*  422 */         break;
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (ExceptionInInitializerError e)
/*      */       {
/*      */ 
/*      */ 
/*  430 */         String msg = e.getMessage();
/*  431 */         logDiagnostic("The log adapter '" + logAdapterClassName + 
/*  432 */           "' is unable to initialize itself when loaded via classloader " + objectId(currentCL) + ": " + 
/*  433 */           msg.trim());
/*  434 */         break;
/*      */       }
/*      */       catch (LogConfigurationException e)
/*      */       {
/*  438 */         throw e;
/*      */       } catch (Throwable t) {
/*  440 */         handleThrowable(t);
/*      */         
/*      */ 
/*      */ 
/*  444 */         handleFlawedDiscovery(logAdapterClassName, currentCL, t);
/*      */       }
/*      */       
/*  447 */       if (currentCL == null) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  453 */       currentCL = getParentClassLoader(currentCL);
/*      */     }
/*      */     
/*  456 */     if ((logAdapterClass != null) && (affectState))
/*      */     {
/*  458 */       this.logClassName = logAdapterClassName;
/*  459 */       this.logConstructor = constructor;
/*      */       
/*      */       try
/*      */       {
/*  463 */         this.logMethod = logAdapterClass.getMethod("setLogFactory", this.logMethodSignature);
/*  464 */         logDiagnostic("Found method setLogFactory(LogFactory) in '" + logAdapterClassName + "'");
/*      */       } catch (Throwable t) {
/*  466 */         handleThrowable(t);
/*  467 */         this.logMethod = null;
/*  468 */         logDiagnostic("[INFO] '" + logAdapterClassName + "' from classloader " + objectId(currentCL) + 
/*  469 */           " does not declare optional method " + "setLogFactory(LogFactory)");
/*      */       }
/*      */       
/*  472 */       logDiagnostic("Log adapter '" + logAdapterClassName + "' from classloader " + 
/*  473 */         objectId(logAdapterClass.getClassLoader()) + " has been selected for use.");
/*      */     }
/*      */     
/*  476 */     return logAdapter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Log discoverLogImplementation(String logCategory)
/*      */     throws LogConfigurationException
/*      */   {
/*  491 */     if (isDiagnosticsEnabled()) {
/*  492 */       logDiagnostic("Discovering a Log implementation...");
/*      */     }
/*      */     
/*  495 */     initConfiguration();
/*      */     
/*  497 */     Log result = null;
/*      */     
/*      */ 
/*  500 */     String specifiedLogClassName = findUserSpecifiedLogClassName();
/*      */     
/*  502 */     if (specifiedLogClassName != null) {
/*  503 */       if (isDiagnosticsEnabled()) {
/*  504 */         logDiagnostic("Attempting to load user-specified log class '" + specifiedLogClassName + "'...");
/*      */       }
/*      */       
/*  507 */       result = createLogFromClass(specifiedLogClassName, logCategory, true);
/*  508 */       if (result == null) {
/*  509 */         StringBuffer messageBuffer = new StringBuffer("User-specified log class '");
/*  510 */         messageBuffer.append(specifiedLogClassName);
/*  511 */         messageBuffer.append("' cannot be found or is not useable.");
/*      */         
/*      */ 
/*      */ 
/*  515 */         informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Log4JLogger");
/*  516 */         informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Jdk14Logger");
/*  517 */         informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
/*  518 */         informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.SimpleLog");
/*  519 */         throw new LogConfigurationException(messageBuffer.toString());
/*      */       }
/*      */       
/*  522 */       return result;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  553 */     if (isDiagnosticsEnabled()) {
/*  554 */       logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
/*      */     }
/*      */     
/*  557 */     for (int i = 0; (i < classesToDiscover.length) && (result == null); i++) {
/*  558 */       result = createLogFromClass(classesToDiscover[i], logCategory, true);
/*      */     }
/*      */     
/*  561 */     if (result == null) {
/*  562 */       throw new LogConfigurationException("No suitable Log implementation");
/*      */     }
/*      */     
/*  565 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findUserSpecifiedLogClassName()
/*      */   {
/*  583 */     if (isDiagnosticsEnabled()) {
/*  584 */       logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
/*      */     }
/*  586 */     String specifiedClass = (String)getAttribute("org.apache.commons.logging.Log");
/*      */     
/*  588 */     if (specifiedClass == null) {
/*  589 */       if (isDiagnosticsEnabled()) {
/*  590 */         logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
/*      */       }
/*  592 */       specifiedClass = (String)getAttribute("org.apache.commons.logging.log");
/*      */     }
/*      */     
/*  595 */     if (specifiedClass == null) {
/*  596 */       if (isDiagnosticsEnabled()) {
/*  597 */         logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
/*      */       }
/*      */       try {
/*  600 */         specifiedClass = getSystemProperty("org.apache.commons.logging.Log", null);
/*      */       } catch (SecurityException e) {
/*  602 */         if (isDiagnosticsEnabled()) {
/*  603 */           logDiagnostic("No access allowed to system property 'org.apache.commons.logging.Log' - " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  608 */     if (specifiedClass == null) {
/*  609 */       if (isDiagnosticsEnabled()) {
/*  610 */         logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
/*      */       }
/*      */       try {
/*  613 */         specifiedClass = getSystemProperty("org.apache.commons.logging.log", null);
/*      */       } catch (SecurityException e) {
/*  615 */         if (isDiagnosticsEnabled()) {
/*  616 */           logDiagnostic(
/*  617 */             "No access allowed to system property 'org.apache.commons.logging.log' - " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  625 */     if (specifiedClass != null) {
/*  626 */       specifiedClass = specifiedClass.trim();
/*      */     }
/*      */     
/*  629 */     return specifiedClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getAttribute(String name)
/*      */   {
/*  641 */     return this.attributes.get(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getAttributeNames()
/*      */   {
/*  650 */     return (String[])this.attributes.keySet().toArray(new String[this.attributes.size()]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ClassLoader getBaseClassLoader()
/*      */     throws LogConfigurationException
/*      */   {
/*  673 */     ClassLoader thisClassLoader = getClassLoader(LogFactoryImpl.class);
/*      */     
/*  675 */     if (!this.useTCCL) {
/*  676 */       return thisClassLoader;
/*      */     }
/*      */     
/*  679 */     ClassLoader contextClassLoader = getContextClassLoaderInternal();
/*      */     
/*  681 */     ClassLoader baseClassLoader = getLowestClassLoader(contextClassLoader, thisClassLoader);
/*      */     
/*  683 */     if (baseClassLoader == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  688 */       if (this.allowFlawedContext) {
/*  689 */         if (isDiagnosticsEnabled()) {
/*  690 */           logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  696 */         return contextClassLoader;
/*      */       }
/*  698 */       throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  703 */     if (baseClassLoader != contextClassLoader)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  709 */       if (this.allowFlawedContext) {
/*  710 */         if (isDiagnosticsEnabled()) {
/*  711 */           logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else {
/*  717 */         throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  722 */     return baseClassLoader;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean getBooleanConfiguration(String key, boolean dflt)
/*      */   {
/*  730 */     String val = getConfigurationValue(key);
/*  731 */     if (val == null) {
/*  732 */       return dflt;
/*      */     }
/*  734 */     return Boolean.valueOf(val).booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getConfigurationValue(String property)
/*      */   {
/*  748 */     if (isDiagnosticsEnabled()) {
/*  749 */       logDiagnostic("[ENV] Trying to get configuration for item " + property);
/*      */     }
/*      */     
/*  752 */     Object valueObj = getAttribute(property);
/*  753 */     if (valueObj != null) {
/*  754 */       if (isDiagnosticsEnabled()) {
/*  755 */         logDiagnostic("[ENV] Found LogFactory attribute [" + valueObj + "] for " + property);
/*      */       }
/*  757 */       return valueObj.toString();
/*      */     }
/*      */     
/*  760 */     if (isDiagnosticsEnabled()) {
/*  761 */       logDiagnostic("[ENV] No LogFactory attribute found for " + property);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  769 */       String value = getSystemProperty(property, null);
/*  770 */       if (value != null) {
/*  771 */         if (isDiagnosticsEnabled()) {
/*  772 */           logDiagnostic("[ENV] Found system property [" + value + "] for " + property);
/*      */         }
/*  774 */         return value;
/*      */       }
/*      */       
/*  777 */       if (isDiagnosticsEnabled()) {
/*  778 */         logDiagnostic("[ENV] No system property found for property " + property);
/*      */       }
/*      */     } catch (SecurityException localSecurityException) {
/*  781 */       if (isDiagnosticsEnabled()) {
/*  782 */         logDiagnostic("[ENV] Security prevented reading system property " + property);
/*      */       }
/*      */     }
/*      */     
/*  786 */     if (isDiagnosticsEnabled()) {
/*  787 */       logDiagnostic("[ENV] No configuration defined for item " + property);
/*      */     }
/*      */     
/*  790 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Log getInstance(Class clazz)
/*      */     throws LogConfigurationException
/*      */   {
/*  805 */     return getInstance(clazz.getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Log getInstance(String name)
/*      */     throws LogConfigurationException
/*      */   {
/*  831 */     Log instance = (Log)this.instances.get(name);
/*  832 */     if (instance == null) {
/*  833 */       instance = newInstance(name);
/*  834 */       this.instances.put(name, instance);
/*      */     }
/*  836 */     return instance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected String getLogClassName()
/*      */   {
/*  848 */     if (this.logClassName == null) {
/*  849 */       discoverLogImplementation(getClass().getName());
/*      */     }
/*      */     
/*  852 */     return this.logClassName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected Constructor getLogConstructor()
/*      */     throws LogConfigurationException
/*      */   {
/*  878 */     if (this.logConstructor == null) {
/*  879 */       discoverLogImplementation(getClass().getName());
/*      */     }
/*      */     
/*  882 */     return this.logConstructor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ClassLoader getLowestClassLoader(ClassLoader c1, ClassLoader c2)
/*      */   {
/*  899 */     if (c1 == null) {
/*  900 */       return c2;
/*      */     }
/*      */     
/*  903 */     if (c2 == null) {
/*  904 */       return c1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  910 */     ClassLoader current = c1;
/*  911 */     while (current != null) {
/*  912 */       if (current == c2) {
/*  913 */         return c1;
/*      */       }
/*      */       
/*  916 */       current = getParentClassLoader(current);
/*      */     }
/*      */     
/*      */ 
/*  920 */     current = c2;
/*  921 */     while (current != null) {
/*  922 */       if (current == c1) {
/*  923 */         return c2;
/*      */       }
/*      */       
/*  926 */       current = getParentClassLoader(current);
/*      */     }
/*      */     
/*  929 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ClassLoader getParentClassLoader(final ClassLoader cl)
/*      */   {
/*      */     try
/*      */     {
/*  943 */       (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run() {
/*  946 */           return cl.getParent();
/*      */         }
/*      */       });
/*      */     } catch (SecurityException localSecurityException) {
/*  950 */       logDiagnostic("[SECURITY] Unable to obtain parent classloader"); }
/*  951 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void handleFlawedDiscovery(String logAdapterClassName, ClassLoader classLoader, Throwable discoveryFlaw)
/*      */   {
/*  978 */     if (isDiagnosticsEnabled()) {
/*  979 */       logDiagnostic("Could not instantiate Log '" + logAdapterClassName + "' -- " + 
/*  980 */         discoveryFlaw.getClass().getName() + ": " + discoveryFlaw.getLocalizedMessage());
/*      */       
/*  982 */       if ((discoveryFlaw instanceof InvocationTargetException))
/*      */       {
/*      */ 
/*      */ 
/*  986 */         InvocationTargetException ite = (InvocationTargetException)discoveryFlaw;
/*  987 */         Throwable cause = ite.getTargetException();
/*  988 */         if (cause != null) {
/*  989 */           logDiagnostic("... InvocationTargetException: " + cause.getClass().getName() + ": " + 
/*  990 */             cause.getLocalizedMessage());
/*      */           
/*  992 */           if ((cause instanceof ExceptionInInitializerError)) {
/*  993 */             ExceptionInInitializerError eiie = (ExceptionInInitializerError)cause;
/*  994 */             Throwable cause2 = eiie.getException();
/*  995 */             if (cause2 != null) {
/*  996 */               StringWriter sw = new StringWriter();
/*  997 */               cause2.printStackTrace(new PrintWriter(sw, true));
/*  998 */               logDiagnostic("... ExceptionInInitializerError: " + sw.toString());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1005 */     if (!this.allowFlawedDiscovery) {
/* 1006 */       throw new LogConfigurationException(discoveryFlaw);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void handleFlawedHierarchy(ClassLoader badClassLoader, Class badClass)
/*      */     throws LogConfigurationException
/*      */   {
/* 1039 */     boolean implementsLog = false;
/* 1040 */     String logInterfaceName = Log.class.getName();
/* 1041 */     Class[] interfaces = badClass.getInterfaces();
/* 1042 */     Class[] arrayOfClass1; int j = (arrayOfClass1 = interfaces).length; for (int i = 0; i < j; i++) { Class interface1 = arrayOfClass1[i];
/* 1043 */       if (logInterfaceName.equals(interface1.getName())) {
/* 1044 */         implementsLog = true;
/* 1045 */         break;
/*      */       }
/*      */     }
/*      */     
/* 1049 */     if (implementsLog)
/*      */     {
/*      */ 
/* 1052 */       if (isDiagnosticsEnabled()) {
/*      */         try {
/* 1054 */           ClassLoader logInterfaceClassLoader = getClassLoader(Log.class);
/* 1055 */           logDiagnostic("Class '" + badClass.getName() + "' was found in classloader " + 
/* 1056 */             objectId(badClassLoader) + ". It is bound to a Log interface which is not" + 
/* 1057 */             " the one loaded from classloader " + objectId(logInterfaceClassLoader));
/*      */         } catch (Throwable t) {
/* 1059 */           handleThrowable(t);
/* 1060 */           logDiagnostic("Error while trying to output diagnostics about bad class '" + badClass + "'");
/*      */         }
/*      */       }
/*      */       
/* 1064 */       if (!this.allowFlawedHierarchy) {
/* 1065 */         StringBuffer msg = new StringBuffer();
/* 1066 */         msg.append("Terminating logging for this context ");
/* 1067 */         msg.append("due to bad log hierarchy. ");
/* 1068 */         msg.append("You have more than one version of '");
/* 1069 */         msg.append(Log.class.getName());
/* 1070 */         msg.append("' visible.");
/* 1071 */         if (isDiagnosticsEnabled()) {
/* 1072 */           logDiagnostic(msg.toString());
/*      */         }
/* 1074 */         throw new LogConfigurationException(msg.toString());
/*      */       }
/*      */       
/* 1077 */       if (isDiagnosticsEnabled()) {
/* 1078 */         StringBuffer msg = new StringBuffer();
/* 1079 */         msg.append("Warning: bad log hierarchy. ");
/* 1080 */         msg.append("You have more than one version of '");
/* 1081 */         msg.append(Log.class.getName());
/* 1082 */         msg.append("' visible.");
/* 1083 */         logDiagnostic(msg.toString());
/*      */       }
/*      */     }
/*      */     else {
/* 1087 */       if (!this.allowFlawedDiscovery) {
/* 1088 */         StringBuffer msg = new StringBuffer();
/* 1089 */         msg.append("Terminating logging for this context. ");
/* 1090 */         msg.append("Log class '");
/* 1091 */         msg.append(badClass.getName());
/* 1092 */         msg.append("' does not implement the Log interface.");
/* 1093 */         if (isDiagnosticsEnabled()) {
/* 1094 */           logDiagnostic(msg.toString());
/*      */         }
/*      */         
/* 1097 */         throw new LogConfigurationException(msg.toString());
/*      */       }
/*      */       
/* 1100 */       if (isDiagnosticsEnabled()) {
/* 1101 */         StringBuffer msg = new StringBuffer();
/* 1102 */         msg.append("[WARNING] Log class '");
/* 1103 */         msg.append(badClass.getName());
/* 1104 */         msg.append("' does not implement the Log interface.");
/* 1105 */         logDiagnostic(msg.toString());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void informUponSimilarName(StringBuffer messageBuffer, String name, String candidate)
/*      */   {
/* 1122 */     if (name.equals(candidate))
/*      */     {
/*      */ 
/* 1125 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1131 */     if (name.regionMatches(true, 0, candidate, 0, PKG_LEN + 5)) {
/* 1132 */       messageBuffer.append(" Did you mean '");
/* 1133 */       messageBuffer.append(candidate);
/* 1134 */       messageBuffer.append("'?");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initConfiguration()
/*      */   {
/* 1145 */     this.allowFlawedContext = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedContext", true);
/* 1146 */     this.allowFlawedDiscovery = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedDiscovery", true);
/* 1147 */     this.allowFlawedHierarchy = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedHierarchy", true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initDiagnostics()
/*      */   {
/* 1172 */     Class clazz = getClass();
/* 1173 */     ClassLoader classLoader = getClassLoader(clazz);
/*      */     String classLoaderName;
/*      */     try { String classLoaderName;
/* 1176 */       if (classLoader == null) {
/* 1177 */         classLoaderName = "BOOTLOADER";
/*      */       } else
/* 1179 */         classLoaderName = objectId(classLoader);
/*      */     } catch (SecurityException localSecurityException) {
/*      */       String classLoaderName;
/* 1182 */       classLoaderName = "UNKNOWN";
/*      */     }
/* 1184 */     this.diagnosticPrefix = ("[LogFactoryImpl@" + System.identityHashCode(this) + " from " + classLoaderName + "] ");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected boolean isJdk13LumberjackAvailable()
/*      */   {
/* 1195 */     return isLogLibraryAvailable("Jdk13Lumberjack", "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected boolean isJdk14Available()
/*      */   {
/* 1208 */     return isLogLibraryAvailable("Jdk14", "org.apache.commons.logging.impl.Jdk14Logger");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected boolean isLog4JAvailable()
/*      */   {
/* 1219 */     return isLogLibraryAvailable("Log4J", "org.apache.commons.logging.impl.Log4JLogger");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isLogLibraryAvailable(String name, String classname)
/*      */   {
/* 1228 */     if (isDiagnosticsEnabled()) {
/* 1229 */       logDiagnostic("Checking for '" + name + "'.");
/*      */     }
/*      */     try {
/* 1232 */       Log log = createLogFromClass(classname, getClass().getName(), 
/* 1233 */         false);
/*      */       
/* 1235 */       if (log == null) {
/* 1236 */         if (isDiagnosticsEnabled()) {
/* 1237 */           logDiagnostic("Did not find '" + name + "'.");
/*      */         }
/* 1239 */         return false;
/*      */       }
/* 1241 */       if (isDiagnosticsEnabled()) {
/* 1242 */         logDiagnostic("Found '" + name + "'.");
/*      */       }
/* 1244 */       return true;
/*      */     }
/*      */     catch (LogConfigurationException localLogConfigurationException) {
/* 1247 */       if (isDiagnosticsEnabled())
/* 1248 */         logDiagnostic("Logging system '" + name + "' is available but not useable.");
/*      */     }
/* 1250 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void logDiagnostic(String msg)
/*      */   {
/* 1263 */     if (isDiagnosticsEnabled()) {
/* 1264 */       logRawDiagnostic(this.diagnosticPrefix + msg);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Log newInstance(String name)
/*      */     throws LogConfigurationException
/*      */   {
/*      */     try
/*      */     {
/*      */       Log instance;
/*      */       
/*      */ 
/*      */       Log instance;
/*      */       
/*      */ 
/* 1281 */       if (this.logConstructor == null) {
/* 1282 */         instance = discoverLogImplementation(name);
/*      */       } else {
/* 1284 */         Object[] params = { name };
/* 1285 */         instance = (Log)this.logConstructor.newInstance(params);
/*      */       }
/*      */       
/* 1288 */       if (this.logMethod != null) {
/* 1289 */         Object[] params = { this };
/* 1290 */         this.logMethod.invoke(instance, params);
/*      */       }
/*      */       
/* 1293 */       return instance;
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (LogConfigurationException lce)
/*      */     {
/*      */ 
/* 1300 */       throw lce;
/*      */ 
/*      */     }
/*      */     catch (InvocationTargetException e)
/*      */     {
/* 1305 */       Throwable c = e.getTargetException();
/* 1306 */       throw new LogConfigurationException(c == null ? e : c);
/*      */     } catch (Throwable t) {
/* 1308 */       handleThrowable(t);
/*      */       
/*      */ 
/* 1311 */       throw new LogConfigurationException(t);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void release()
/*      */   {
/* 1325 */     logDiagnostic("Releasing all known loggers");
/* 1326 */     this.instances.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAttribute(String name)
/*      */   {
/* 1338 */     this.attributes.remove(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAttribute(String name, Object value)
/*      */   {
/* 1371 */     if (this.logConstructor != null) {
/* 1372 */       logDiagnostic("setAttribute: call too late; configuration already performed.");
/*      */     }
/*      */     
/* 1375 */     if (value == null) {
/* 1376 */       this.attributes.remove(name);
/*      */     } else {
/* 1378 */       this.attributes.put(name, value);
/*      */     }
/*      */     
/* 1381 */     if (name.equals("use_tccl")) {
/* 1382 */       this.useTCCL = ((value != null) && (Boolean.valueOf(value.toString()).booleanValue()));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\LogFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */