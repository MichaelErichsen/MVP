/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonNavigatorProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PersonListModelHandler
/*    */   implements HttpHandler
/*    */ {
/* 19 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */   public void handle(HttpExchange t)
/*    */     throws IOException
/*    */   {
/* 25 */     PersonNavigatorProvider model = new PersonNavigatorProvider();
/*    */     
/* 27 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "personlist", model);
/* 28 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 29 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 31 */     model = (PersonNavigatorProvider)response.getProvider();
/*    */     
/* 33 */     String s = model.writeJson(model.getClass().getName());
/*    */     
/* 35 */     LOGGER.info("Server returning personlist " + s);
/*    */     
/* 37 */     Headers headers = t.getResponseHeaders();
/* 38 */     headers.add("Content-Type", "application/json");
/* 39 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 41 */     OutputStream os = t.getResponseBody();
/* 42 */     os.write(s.getBytes());
/* 43 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\PersonListModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */