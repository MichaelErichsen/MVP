/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
/*     */ import org.eclipse.jface.viewers.TreeViewer;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.events.SelectionListener;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Tree;
/*     */ import org.eclipse.swt.widgets.TreeItem;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.providers.AncestorTreeContentProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.AncestorTreeLabelProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.AncestorTreeProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AncestorTreeNavigator
/*     */   extends AbstractHREGuiPart
/*     */ {
/*  34 */   private int perNo = 1;
/*     */   private TreeViewer viewer;
/*     */   private Tree tree;
/*  37 */   private AncestorTreeProvider provider = new AncestorTreeProvider();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void callBusinessLayer(int perNo)
/*     */   {
/*  47 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  48 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*  49 */     this.provider.setPerNo(perNo);
/*  50 */     this.req = new ServerRequest("GET", "ancestorlist", this.provider);
/*     */     
/*  52 */     long before = System.nanoTime();
/*     */     
/*  54 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     
/*  56 */     long after = System.nanoTime();
/*  57 */     LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */     
/*  59 */     if (this.resp == null) {
/*  60 */       this.eventBroker.post("MESSAGE", "Call not successful");
/*  61 */       LOGGER.severe("Call not successful");
/*  62 */     } else if (this.resp.getReturnCode() != 0) {
/*  63 */       this.eventBroker.post("MESSAGE", this.resp.getReturnMessage());
/*  64 */       LOGGER.severe(this.resp.getReturnMessage());
/*     */     } else {
/*  66 */       this.provider = ((AncestorTreeProvider)this.resp.getProvider());
/*     */       try
/*     */       {
/*  69 */         if (this.tree != null) {
/*  70 */           this.perNo = perNo;
/*     */         }
/*     */       }
/*     */       catch (Exception e2) {
/*  74 */         this.eventBroker.post("MESSAGE", e2.getMessage());
/*  75 */         LOGGER.severe(
/*  76 */           "Error in request " + this.req.getOperation() + " " + this.req.getModelName() + ", " + e2.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  86 */     this.viewer = new TreeViewer(parent, 2048);
/*  87 */     this.tree = this.viewer.getTree();
/*  88 */     this.tree.setFont(getHreFont(parent));
/*     */     
/*  90 */     this.tree.addSelectionListener(new SelectionListener()
/*     */     {
/*     */       public void widgetDefaultSelected(SelectionEvent e) {}
/*     */       
/*     */ 
/*     */       public void widgetSelected(SelectionEvent e)
/*     */       {
/*  97 */         AncestorTreeNavigator.LOGGER.info(e.toString());
/*     */         
/*  99 */         if ((e.item instanceof TreeItem)) {
/* 100 */           TreeItem item = (TreeItem)e.item;
/* 101 */           String string = item.getText();
/* 102 */           String[] sa = string.split(",");
/* 103 */           AncestorTreeNavigator.this.perNo = Integer.parseInt(sa[0]);
/*     */         }
/*     */       }
/* 106 */     });
/* 107 */     this.tree.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDoubleClick(MouseEvent e) {
/* 110 */         AncestorTreeNavigator.LOGGER.info("Double clicking " + AncestorTreeNavigator.this.perNo);
/* 111 */         AncestorTreeNavigator.this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(AncestorTreeNavigator.this.perNo));
/*     */       }
/*     */       
/* 114 */     });
/* 115 */     this.viewer.setLabelProvider(new AncestorTreeLabelProvider());
/* 116 */     this.viewer.setContentProvider(new AncestorTreeContentProvider());
/* 117 */     callBusinessLayer(this.perNo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose()
/*     */   {
/* 134 */     this.provider.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*     */   {
/* 144 */     LOGGER.info("Received " + perNo);
/* 145 */     if (perNo != this.perNo) {
/* 146 */       this.tree.removeAll();
/* 147 */       callBusinessLayer(perNo);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateGui()
/*     */   {
/* 162 */     LOGGER.info("Refresh viewer");
/* 163 */     this.tree.removeAll();
/* 164 */     this.viewer.refresh();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\AncestorTreeNavigator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */