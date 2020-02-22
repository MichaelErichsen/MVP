/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.net.URI;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonalEventProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class EventModelHandler
/*    */   implements HttpHandler
/*    */ {
/* 19 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */   public void handle(HttpExchange t) throws IOException
/*    */   {
/* 23 */     String s = t.getRequestURI().getPath();
/* 24 */     String[] parts = s.split("/");
/*    */     
/* 26 */     PersonalEventProvider model = new PersonalEventProvider();
/* 27 */     s = parts[(parts.length - 1)];
/* 28 */     model.setId(Integer.parseInt(s));
/*    */     
/* 30 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "personalevents", model);
/* 31 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 32 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 34 */     model = (PersonalEventProvider)response.getProvider();
/*    */     
/* 36 */     s = model.writeJson(model.getClass().getName());
/*    */     
/* 38 */     LOGGER.info("Server returning personalevents " + s);
/*    */     
/* 40 */     Headers headers = t.getResponseHeaders();
/* 41 */     headers.add("Content-Type", "application/json");
/* 42 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 44 */     OutputStream os = t.getResponseBody();
/* 45 */     os.write(s.getBytes());
/* 46 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\EventModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */