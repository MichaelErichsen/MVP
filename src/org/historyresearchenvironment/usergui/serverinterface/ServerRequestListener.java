/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import com.sun.net.httpserver.HttpServer;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.logging.Logger;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerRequestListener
/*    */ {
/* 22 */   private static final Logger LOGGER = Logger.getLogger("global");
/* 23 */   final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 24 */     "org.historyresearchenvironment.usergui");
/*    */   
/*    */   @Inject
/*    */   private IEventBroker eventBroker;
/*    */   
/*    */   @Execute
/*    */   public void execute()
/*    */   {
/*    */     try
/*    */     {
/* 34 */       HttpServer server = HttpServer.create(new InetSocketAddress(this.store.getInt("SERVERPORT")), 0);
/* 35 */       server.createContext("/hre/v1/personalevents", new EventModelHandler());
/* 36 */       server.createContext("/hre/v1/personalrelatives", new RelativesModelHandler());
/* 37 */       server.createContext("/hre/v1/personalconnections", new ConnectionsModelHandler());
/* 38 */       server.createContext("/hre/v1/eventassociates", new AssociatesModelHandler());
/* 39 */       server.createContext("/hre/v1/personlist", new PersonListModelHandler());
/* 40 */       server.createContext("/hre/v1/descendantlist", new DescendantListModelHandler());
/* 41 */       server.createContext("/hre/v1/ancestorlist", new AncestorListModelHandler());
/* 42 */       server.createContext("/hre/v1/locationlist", new LocationListModelHandler());
/* 43 */       server.setExecutor(null);
/* 44 */       server.start();
/*    */       
/* 46 */       LOGGER.info("The server is running at " + server.getAddress());
/* 47 */       this.eventBroker.post("MESSAGE", "The server is running at " + server.getAddress());
/*    */     }
/*    */     catch (IOException e) {
/* 50 */       e.printStackTrace();
/* 51 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\ServerRequestListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */