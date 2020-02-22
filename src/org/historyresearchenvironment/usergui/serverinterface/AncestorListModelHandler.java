/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.providers.AncestorTreeProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AncestorListModelHandler
/*    */   implements HttpHandler
/*    */ {
/* 20 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void handle(HttpExchange t)
/*    */     throws IOException
/*    */   {
/* 30 */     AncestorTreeProvider provider = new AncestorTreeProvider();
/*    */     
/* 32 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "ancestorlist", provider);
/* 33 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 34 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 36 */     provider = (AncestorTreeProvider)response.getProvider();
/*    */     
/* 38 */     String s = provider.writeJson(provider.getClass().getName());
/*    */     
/* 40 */     LOGGER.fine("Server returning ancestorlist " + s);
/*    */     
/* 42 */     Headers headers = t.getResponseHeaders();
/* 43 */     headers.add("Content-Type", "application/json");
/* 44 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 46 */     OutputStream os = t.getResponseBody();
/* 47 */     os.write(s.getBytes());
/* 48 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\AncestorListModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */