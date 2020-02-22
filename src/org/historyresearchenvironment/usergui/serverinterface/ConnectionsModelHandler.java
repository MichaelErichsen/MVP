/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import com.sun.net.httpserver.Headers;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import com.sun.net.httpserver.HttpHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.net.URI;
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.models.PersonalConnectionsModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ class ConnectionsModelHandler
/*    */   implements HttpHandler
/*    */ {
/*    */   public void handle(HttpExchange t)
/*    */     throws IOException
/*    */   {
/* 20 */     String s = t.getRequestURI().getPath();
/* 21 */     String[] parts = s.split("/");
/*    */     
/* 23 */     PersonalConnectionsModel model = new PersonalConnectionsModel();
/* 24 */     s = parts[(parts.length - 1)];
/* 25 */     model.setId(Integer.parseInt(s));
/*    */     
/* 27 */     ServerRequest request = new ServerRequest(t.getRequestMethod(), "personalrelatives", model);
/* 28 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 29 */     ServerResponse response = sbl.doSomethingWithRequest();
/*    */     
/* 31 */     model = (PersonalConnectionsModel)response.getProvider();
/*    */     
/* 33 */     s = model.writeJson(model.getClass().getName());
/*    */     
/* 35 */     Headers headers = t.getResponseHeaders();
/* 36 */     headers.add("Content-Type", "application/json");
/* 37 */     t.sendResponseHeaders(200, s.length());
/*    */     
/* 39 */     OutputStream os = t.getResponseBody();
/* 40 */     os.write(s.getBytes());
/* 41 */     os.close();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\ConnectionsModelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */