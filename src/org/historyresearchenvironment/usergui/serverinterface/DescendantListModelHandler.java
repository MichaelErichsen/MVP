/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.providers.DescendantTreeProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DescendantListModelHandler
/*    */   implements HttpHandler
/*    */ {
/* 20 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */   public void handle(HttpExchange t)
/*    */     throws IOException
/*    */   {
/* 26 */     DescendantTreeProvider model = new DescendantTreeProvider();
/*    */     
/* 28 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "descendantlist", model);
/* 29 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 30 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 32 */     model = (DescendantTreeProvider)response.getProvider();
/*    */     
/* 34 */     String s = model.writeJson(model.getClass().getName());
/*    */     
/* 36 */     LOGGER.info("Server returning descendantlist " + s);
/*    */     
/* 38 */     Headers headers = t.getResponseHeaders();
/* 39 */     headers.add("Content-Type", "application/json");
/* 40 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 42 */     OutputStream os = t.getResponseBody();
/* 43 */     os.write(s.getBytes());
/* 44 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\DescendantListModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */