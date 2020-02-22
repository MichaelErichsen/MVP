/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.providers.TMGPlaceProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocationListModelHandler
/*    */   implements HttpHandler
/*    */ {
/* 20 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void handle(HttpExchange t)
/*    */     throws IOException
/*    */   {
/* 32 */     TMGPlaceProvider provider = new TMGPlaceProvider();
/*    */     
/* 34 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "locationlist", provider);
/* 35 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 36 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 38 */     provider = (TMGPlaceProvider)response.getProvider();
/*    */     
/* 40 */     String s = provider.writeJson(provider.getClass().getName());
/*    */     
/* 42 */     LOGGER.fine("Server returning locationlist " + s);
/*    */     
/* 44 */     Headers headers = t.getResponseHeaders();
/* 45 */     headers.add("Content-Type", "application/json");
/* 46 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 48 */     OutputStream os = t.getResponseBody();
/* 49 */     os.write(s.getBytes());
/* 50 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\LocationListModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */