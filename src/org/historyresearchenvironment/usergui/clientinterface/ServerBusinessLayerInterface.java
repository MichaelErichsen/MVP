/*    */ package org.historyresearchenvironment.usergui.clientinterface;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerBusinessLayerInterface
/*    */   implements BusinessLayerInterface
/*    */ {
/* 23 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ServerResponse callBusinessLayer(ServerRequest request)
/*    */   {
/* 35 */     ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 36 */       "org.historyresearchenvironment.usergui");
/*    */     
/* 38 */     String serverAddress = "http://" + store.getString("SERVERADDRESS") + "/hre/v1/";
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     String s = "";
/*    */     
/*    */     try
/*    */     {
/* 44 */       if (request.getOperation().equals("GET")) {
/* 45 */         LOGGER.fine("Calling " + serverAddress + request.getModelName() + "/" + request.getProvider().getKey());
/*    */         
/* 47 */         URL url = new URL(serverAddress + request.getModelName() + "/" + request.getProvider().getKey());
/* 48 */         BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
/*    */         
/* 50 */         while ((s = br.readLine()) != null) {
/* 51 */           sb.append(s);
/*    */         }
/*    */         
/* 54 */         br.close();
/*    */       }
/*    */     } catch (Exception e) {
/* 57 */       LOGGER.severe("Server call error: " + e.getClass() + " " + e.getMessage());
/* 58 */       return new ServerResponse(request.getProvider(), 999, e.getClass() + " " + e.getMessage());
/*    */     }
/*    */     
/* 61 */     AbstractHreProvider provider = request.getProvider();
/* 62 */     provider.readJson(sb.toString());
/* 63 */     return new ServerResponse(provider, 0, "OK");
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\clientinterface\ServerBusinessLayerInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */