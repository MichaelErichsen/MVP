/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.core.di.annotations.Optional;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ import org.eclipse.e4.ui.di.UIEventTopic;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ import org.eclipse.wb.swt.SWTResourceManager;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonalEventProvider;
/*    */ 
/*    */ 
/*    */ public class StatusToolControl
/*    */ {
/*    */   @Inject
/*    */   IEventBroker eventBroker;
/*    */   private Text textStatus;
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 23 */     this.textStatus = new Text(parent, 0);
/* 24 */     this.textStatus.setBackground(SWTResourceManager.getColor(1));
/* 25 */     this.textStatus.setEditable(false);
/* 26 */     this.textStatus.setSize(1000, 200);
/* 27 */     this.textStatus.setText("                                                                                                                                                                        ");
/*    */   }
/*    */   
/*    */   @Inject
/*    */   @Optional
/*    */   public void messageHandler(@UIEventTopic("PEM") PersonalEventProvider pem)
/*    */   {
/* 34 */     setMessage("PEM Identity: " + pem.getIdentity());
/*    */   }
/*    */   
/*    */   @Inject
/*    */   @Optional
/*    */   public void messageHandler(@UIEventTopic("MESSAGE") String s) {
/* 40 */     setMessage(s);
/*    */   }
/*    */   
/*    */   public void setMessage(String s) {
/* 44 */     if (s != null) {
/* 45 */       this.textStatus.setText(s);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\StatusToolControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */