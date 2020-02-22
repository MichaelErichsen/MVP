/*    */ package org.historyresearchenvironment.usergui.businesslogic;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.models.PersonalRelativesProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.AncestorTreeProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.DescendantTreeProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonNavigatorProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonalEventProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.TMGPlaceProvider;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SomeBusinessLogic
/*    */ {
/* 20 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */   private final ServerRequest serverRequest;
/*    */   
/*    */ 
/*    */   public SomeBusinessLogic(ServerRequest serverRequest)
/*    */   {
/* 28 */     this.serverRequest = serverRequest;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ServerResponse doSomethingWithRequest()
/*    */   {
/* 35 */     ServerResponse resp = null;
/*    */     
/* 37 */     if (this.serverRequest.getModelName().equals("personalevents")) {
/* 38 */       PersonalEventProvider provider = (PersonalEventProvider)this.serverRequest.getProvider();
/* 39 */       int perNo = provider.getId();
/* 40 */       provider.readFromH2(perNo);
/* 41 */       resp = new ServerResponse(provider, 0, "OK");
/* 42 */       LOGGER.finest(provider.getIdentity());
/* 43 */     } else if (this.serverRequest.getModelName().equals("personalrelatives")) {
/* 44 */       PersonalRelativesProvider provider = (PersonalRelativesProvider)this.serverRequest.getProvider();
/* 45 */       int perNo = provider.getId();
/* 46 */       provider.readFromH2(perNo);
/* 47 */       resp = new ServerResponse(provider, 0, "OK");
/* 48 */       LOGGER.finest(provider.getIdentity());
/* 49 */     } else if (this.serverRequest.getModelName().equals("personlist")) {
/* 50 */       PersonNavigatorProvider provider = (PersonNavigatorProvider)this.serverRequest.getProvider();
/* 51 */       provider.readFromH2(0);
/* 52 */       resp = new ServerResponse(provider, 0, "OK");
/* 53 */       LOGGER.finest(provider.toString());
/* 54 */     } else if (this.serverRequest.getModelName().equals("descendantlist")) {
/* 55 */       DescendantTreeProvider provider = (DescendantTreeProvider)this.serverRequest.getProvider();
/* 56 */       provider.readFromH2(0);
/* 57 */       resp = new ServerResponse(provider, 0, "OK");
/* 58 */       LOGGER.finest(provider.getKey());
/* 59 */     } else if (this.serverRequest.getModelName().equals("ancestorlist")) {
/* 60 */       AncestorTreeProvider provider = (AncestorTreeProvider)this.serverRequest.getProvider();
/* 61 */       provider.readFromH2(0);
/* 62 */       resp = new ServerResponse(provider, 0, "OK");
/* 63 */       LOGGER.finest(provider.getKey());
/* 64 */     } else if (this.serverRequest.getModelName().equals("locationlist")) {
/* 65 */       TMGPlaceProvider provider = (TMGPlaceProvider)this.serverRequest.getProvider();
/* 66 */       provider.readFromH2(0);
/* 67 */       resp = new ServerResponse(provider, 0, "OK");
/* 68 */       LOGGER.finest(provider.getKey());
/*    */     }
/* 70 */     return resp;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\businesslogic\SomeBusinessLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */