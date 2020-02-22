/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
/*    */ import org.eclipse.core.internal.runtime.InternalPlatform;
/*    */ import org.eclipse.e4.core.contexts.EclipseContextFactory;
/*    */ import org.eclipse.e4.core.contexts.IEclipseContext;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ import org.osgi.framework.BundleContext;
/*    */ 
/*    */ 
/*    */ public class HRELoggerHandler
/*    */   extends Handler
/*    */ {
/*    */   private static IEventBroker eventBroker;
/*    */   
/*    */   public HRELoggerHandler()
/*    */   {
/* 20 */     BundleContext context = InternalPlatform.getDefault().getBundleContext();
/* 21 */     IEclipseContext ecf = EclipseContextFactory.getServiceContext(context);
/* 22 */     eventBroker = (IEventBroker)ecf.get(IEventBroker.class.getName());
/* 23 */     System.out.println("Constructed logger handler");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void close()
/*    */     throws SecurityException
/*    */   {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void flush() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void publish(LogRecord record)
/*    */   {
/* 51 */     BundleContext context = InternalPlatform.getDefault().getBundleContext();
/* 52 */     IEclipseContext ecf = EclipseContextFactory.getServiceContext(context);
/* 53 */     eventBroker = (IEventBroker)ecf.get(IEventBroker.class.getName());
/* 54 */     eventBroker.post("LOG", record);
/* 55 */     eventBroker.post("MESSAGE", record);
/* 56 */     System.out.println("Called console for logger");
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\HRELoggerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */