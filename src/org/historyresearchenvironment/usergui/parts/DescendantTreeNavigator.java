/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Event;
/*     */ import org.eclipse.swt.widgets.Listener;
/*     */ import org.eclipse.swt.widgets.Tree;
/*     */ import org.eclipse.swt.widgets.TreeItem;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.models.DescendantModel;
/*     */ import org.historyresearchenvironment.usergui.models.DescendantVector;
/*     */ import org.historyresearchenvironment.usergui.providers.DescendantTreeProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescendantTreeNavigator
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   private Tree tree;
/*  36 */   private DescendantTreeProvider provider = new DescendantTreeProvider();
/*  37 */   private int perNo = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   private Vector<Integer> children;
/*     */   
/*     */ 
/*     */ 
/*     */   private void addBranch(TreeItem parent, int perNo)
/*     */   {
/*  47 */     this.provider.setPerNo(perNo);
/*     */     
/*  49 */     TreeItem currentTreeItem = new TreeItem(parent, 0);
/*  50 */     currentTreeItem.setText(((DescendantModel)this.provider.getDescendantItems().getVector().get(0)).getName());
/*     */     
/*  52 */     for (Integer child : ((DescendantModel)this.provider.getDescendantItems().getVector().get(0)).getChildren()) {
/*  53 */       addBranch(currentTreeItem, child.intValue());
/*     */     }
/*     */     
/*  56 */     currentTreeItem.setExpanded(true);
/*     */   }
/*     */   
/*     */   protected void callBusinessLayer(int perNo)
/*     */   {
/*  61 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  62 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*  63 */     this.provider = new DescendantTreeProvider(perNo);
/*     */     
/*  65 */     this.req = new ServerRequest("GET", "descendantlist", this.provider);
/*     */     
/*  67 */     long before = System.nanoTime();
/*     */     
/*  69 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     
/*  71 */     long after = System.nanoTime();
/*  72 */     LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */     
/*  74 */     if (this.resp == null) {
/*  75 */       this.eventBroker.post("MESSAGE", "Call not successful");
/*  76 */       LOGGER.severe("Call not successful");
/*  77 */     } else if (this.resp.getReturnCode() != 0) {
/*  78 */       this.eventBroker.post("MESSAGE", this.resp.getReturnMessage());
/*  79 */       LOGGER.severe(this.resp.getReturnMessage());
/*     */     } else {
/*  81 */       this.provider = ((DescendantTreeProvider)this.resp.getProvider());
/*     */       try
/*     */       {
/*  84 */         if (this.tree != null) {
/*  85 */           this.perNo = perNo;
/*  86 */           updateGui();
/*     */         }
/*     */       } catch (Exception e2) {
/*  89 */         this.eventBroker.post("MESSAGE", e2.getMessage());
/*  90 */         LOGGER.severe(
/*  91 */           "Error in request " + this.req.getOperation() + " " + this.req.getModelName() + ", " + e2.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/* 101 */     parent.setLayout(new GridLayout(1, false));
/* 102 */     this.tree = new Tree(parent, 2048);
/* 103 */     this.tree.setFont(getHreFont(parent));
/* 104 */     this.tree.setLayoutData(new GridData(4, 4, true, true, 1, 1));
/* 105 */     this.tree.addListener(13, new Listener()
/*     */     {
/*     */       public void handleEvent(Event e) {
/* 108 */         TreeItem[] selection = DescendantTreeNavigator.this.tree.getSelection();
/* 109 */         TreeItem ti = selection[0];
/* 110 */         String string = ti.getText();
/* 111 */         String[] sa = string.split(",");
/* 112 */         DescendantTreeNavigator.this.perNo = Integer.parseInt(sa[0]);
/*     */       }
/*     */       
/* 115 */     });
/* 116 */     this.tree.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDoubleClick(MouseEvent e) {
/* 119 */         DescendantTreeNavigator.this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(DescendantTreeNavigator.this.perNo));
/*     */       }
/* 121 */     });
/* 122 */     callBusinessLayer(this.perNo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose()
/*     */   {
/* 130 */     this.provider.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*     */   {
/* 140 */     callBusinessLayer(perNo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateGui()
/*     */   {
/* 151 */     this.tree.removeAll();
/*     */     
/* 153 */     TreeItem root = new TreeItem(this.tree, 0);
/*     */     
/* 155 */     this.provider = new DescendantTreeProvider(this.perNo);
/* 156 */     root.setText(((DescendantModel)this.provider.getDescendantItems().getVector().get(0)).getName());
/* 157 */     this.children = ((DescendantModel)this.provider.getDescendantItems().getVector().get(0)).getChildren();
/*     */     
/* 159 */     if (this.children != null) {
/* 160 */       for (Integer child : this.children) {
/* 161 */         addBranch(root, child.intValue());
/*     */       }
/*     */     }
/* 164 */     root.setExpanded(true);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\DescendantTreeNavigator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */