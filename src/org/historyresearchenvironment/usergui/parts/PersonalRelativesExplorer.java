/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.Focus;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Combo;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableItem;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.eclipse.wb.swt.ResourceManager;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.dialogs.PersonSelectionDialog;
/*     */ import org.historyresearchenvironment.usergui.models.PersonalRelativesItem;
/*     */ import org.historyresearchenvironment.usergui.models.PersonalRelativesProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
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
/*     */ 
/*     */ public class PersonalRelativesExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   @Inject
/*     */   private IEventBroker eventBroker;
/*     */   private ScopedPreferenceStore store;
/*     */   private Text textIdentity;
/*     */   private PersonalRelativesExplorerComposite personalRelativesExplorerCompositePrev;
/*     */   private PersonalRelativesExplorerComposite personalRelativesExplorerCompositeThis;
/*     */   private PersonalRelativesExplorerComposite personalRelativesExplorerCompositeNext;
/*     */   private PersonalRelativesProvider prm;
/*     */   private BusinessLayerInterface bli;
/*     */   private ServerRequest req;
/*     */   private ServerResponse resp;
/*     */   
/*     */   protected void callBusinessLayer(int i) {}
/*     */   
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  67 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*  68 */     this.prm = new PersonalRelativesProvider();
/*  69 */     this.prm.setId(1);
/*     */     
/*  71 */     this.req = new ServerRequest("GET", "personalrelatives", this.prm);
/*     */     
/*  73 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     try {
/*  75 */       this.prm = ((PersonalRelativesProvider)this.resp.getProvider());
/*     */     } catch (Exception e1) {
/*  77 */       this.eventBroker.post("MESSAGE", e1.getMessage());
/*  78 */       LOGGER.info(e1.getMessage());
/*     */     }
/*     */     
/*  81 */     parent.setLayout(new GridLayout(7, false));
/*  82 */     parent.setFont(getHreFont(parent));
/*     */     
/*  84 */     Label lblIdentity = new Label(parent, 0);
/*  85 */     lblIdentity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  86 */     lblIdentity.setText("Identity:");
/*     */     
/*  88 */     this.textIdentity = new Text(parent, 2048);
/*  89 */     this.textIdentity.setLayoutData(new GridData(4, 16777216, true, false, 3, 1));
/*     */     
/*  91 */     Button btnSearch = new Button(parent, 0);
/*  92 */     btnSearch.setToolTipText("Select a person by ID");
/*  93 */     btnSearch.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDown(MouseEvent e) {
/*  96 */         Display display = Display.getDefault();
/*  97 */         Shell shell = new Shell(display);
/*  98 */         PersonSelectionDialog dialog = new PersonSelectionDialog(shell);
/*  99 */         dialog.open();
/*     */         
/* 101 */         PersonalRelativesExplorer.this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 102 */         PersonalRelativesExplorer.this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/* 103 */         PersonalRelativesExplorer.this.prm = new PersonalRelativesProvider();
/* 104 */         PersonalRelativesExplorer.this.prm.setId(Integer.parseInt(PersonalRelativesExplorer.this.store.getString("PERNO")));
/*     */         
/* 106 */         PersonalRelativesExplorer.this.req = new ServerRequest("GET", "personalrelatives", PersonalRelativesExplorer.this.prm);
/*     */         
/* 108 */         long before = System.nanoTime();
/*     */         
/* 110 */         PersonalRelativesExplorer.this.resp = PersonalRelativesExplorer.this.bli.callBusinessLayer(PersonalRelativesExplorer.this.req);
/*     */         
/* 112 */         long after = System.nanoTime();
/*     */         
/* 114 */         PersonalRelativesExplorer.LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */         
/* 116 */         if (PersonalRelativesExplorer.this.resp == null) {
/* 117 */           PersonalRelativesExplorer.this.eventBroker.post("MESSAGE", "Call not successful");
/* 118 */         } else if (PersonalRelativesExplorer.this.resp.getReturnCode() != 0) {
/* 119 */           PersonalRelativesExplorer.this.eventBroker.post("MESSAGE", PersonalRelativesExplorer.this.resp.getReturnMessage());
/*     */         } else {
/* 121 */           PersonalRelativesExplorer.this.prm = ((PersonalRelativesProvider)PersonalRelativesExplorer.this.resp.getProvider());
/*     */           try
/*     */           {
/* 124 */             PersonalRelativesExplorer.this.populateExplorer();
/*     */           } catch (Exception e2) {
/* 126 */             PersonalRelativesExplorer.LOGGER.info("PRE Error in request " + PersonalRelativesExplorer.this.req.getOperation() + " " + PersonalRelativesExplorer.this.req.getModelName() + ", " + 
/* 127 */               e2.getMessage());
/* 128 */             PersonalRelativesExplorer.this.eventBroker.post("MESSAGE", e2.getMessage());
/*     */           }
/*     */         }
/*     */       }
/* 132 */     });
/* 133 */     btnSearch.setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 135 */     Button btnNewButton_1 = new Button(parent, 0);
/* 136 */     btnNewButton_1.setToolTipText("A nice star picture");
/* 137 */     btnNewButton_1
/* 138 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon2.png"));
/*     */     
/* 140 */     Button btnNewButton_2 = new Button(parent, 0);
/* 141 */     btnNewButton_2.setToolTipText("Probably meant to squeeze something?");
/* 142 */     btnNewButton_2
/* 143 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon3.png"));
/*     */     
/* 145 */     Button btnNewButton_3 = new Button(parent, 0);
/* 146 */     btnNewButton_3.setToolTipText("Does nothing");
/* 147 */     btnNewButton_3
/* 148 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 150 */     Label lblShowRelativesBy = new Label(parent, 0);
/* 151 */     lblShowRelativesBy.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 152 */     lblShowRelativesBy.setText("Show Relatives by:");
/*     */     
/* 154 */     Combo comboRelativesBy = new Combo(parent, 0);
/* 155 */     comboRelativesBy.setItems(new String[] { "Generation", "Common Ancestor", "Birth Date" });
/* 156 */     comboRelativesBy.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/* 157 */     comboRelativesBy.select(0);
/*     */     
/* 159 */     Button btnOptions = new Button(parent, 0);
/* 160 */     btnOptions.setText("Options");
/* 161 */     new Label(parent, 0);
/* 162 */     new Label(parent, 0);
/* 163 */     new Label(parent, 0);
/*     */     
/* 165 */     this.personalRelativesExplorerCompositePrev = new PersonalRelativesExplorerComposite(parent, 0);
/* 166 */     this.personalRelativesExplorerCompositePrev.setLayoutData(new GridData(4, 4, true, true, 7, 1));
/*     */     
/* 168 */     Label label = new Label(parent, 258);
/* 169 */     label.setLayoutData(new GridData(16384, 16777216, false, false, 7, 1));
/*     */     
/* 171 */     this.personalRelativesExplorerCompositeThis = new PersonalRelativesExplorerComposite(parent, 0);
/* 172 */     this.personalRelativesExplorerCompositeThis.setLayoutData(new GridData(4, 4, true, true, 7, 1));
/*     */     
/* 174 */     Label label_1 = new Label(parent, 258);
/* 175 */     label_1.setLayoutData(new GridData(16384, 16777216, false, false, 7, 1));
/*     */     
/* 177 */     this.personalRelativesExplorerCompositeNext = new PersonalRelativesExplorerComposite(parent, 0);
/* 178 */     this.personalRelativesExplorerCompositeNext.setLayoutData(new GridData(4, 4, true, true, 7, 1));
/*     */     
/* 180 */     populateExplorer();
/*     */   }
/*     */   
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose() {}
/*     */   
/*     */ 
/*     */   protected void populateExplorer()
/*     */   {
/* 190 */     this.textIdentity.setText(this.prm.getIdentity());
/* 191 */     this.personalRelativesExplorerCompositePrev.getTextGenNo().setText("-1");
/* 192 */     this.personalRelativesExplorerCompositePrev.getTextComAncM().setText(this.prm.getComAncMPrev());
/* 193 */     this.personalRelativesExplorerCompositePrev.getTextComAncF().setText(this.prm.getComAncFPrev());
/* 194 */     Table parents = this.personalRelativesExplorerCompositePrev.getTableParents();
/* 195 */     parents.removeAll();
/*     */     try
/*     */     {
/* 198 */       Iterator<PersonalRelativesItem> iterator = this.prm.getLastGen().iterator();
/*     */       
/* 200 */       while (iterator.hasNext()) {
/* 201 */         PersonalRelativesItem item = (PersonalRelativesItem)iterator.next();
/* 202 */         TableItem ti = new TableItem(parents, 0);
/* 203 */         ti.setText(item.getStrings());
/*     */       }
/*     */     } catch (Exception e) {
/* 206 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 207 */       LOGGER.info(e.getClass() + " " + e.getMessage());
/*     */     }
/*     */     
/* 210 */     this.personalRelativesExplorerCompositeThis.getTextGenNo().setText("0");
/* 211 */     this.personalRelativesExplorerCompositeThis.getTextComAncM().setText(this.prm.getComAncMThis());
/* 212 */     this.personalRelativesExplorerCompositeThis.getTextComAncF().setText(this.prm.getComAncFThis());
/* 213 */     Table current = this.personalRelativesExplorerCompositeThis.getTableParents();
/* 214 */     current.removeAll();
/*     */     try
/*     */     {
/* 217 */       Iterator<PersonalRelativesItem> iterator = this.prm.getThisGen().iterator();
/*     */       
/* 219 */       while (iterator.hasNext()) {
/* 220 */         PersonalRelativesItem personalRelativesItem = (PersonalRelativesItem)iterator.next();
/* 221 */         TableItem ti = new TableItem(current, 0);
/* 222 */         ti.setText(personalRelativesItem.getStrings());
/*     */       }
/*     */     } catch (Exception e) {
/* 225 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 226 */       LOGGER.info(e.getClass() + " " + e.getMessage());
/*     */     }
/*     */     
/* 229 */     this.personalRelativesExplorerCompositeNext.getTextGenNo().setText("+1");
/* 230 */     this.personalRelativesExplorerCompositeNext.getTextComAncM().setText(this.prm.getComAncMNext());
/* 231 */     this.personalRelativesExplorerCompositeNext.getTextComAncF().setText(this.prm.getComAncFNext());
/* 232 */     Table children = this.personalRelativesExplorerCompositeNext.getTableParents();
/* 233 */     children.removeAll();
/*     */     try
/*     */     {
/* 236 */       Iterator<PersonalRelativesItem> iterator = this.prm.getNextGen().iterator();
/*     */       
/* 238 */       while (iterator.hasNext()) {
/* 239 */         PersonalRelativesItem personalRelativesItem = (PersonalRelativesItem)iterator.next();
/* 240 */         TableItem ti = new TableItem(children, 0);
/* 241 */         ti.setText(personalRelativesItem.getStrings());
/*     */       }
/*     */     } catch (Exception e) {
/* 244 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 245 */       LOGGER.info(e.getClass() + " " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   @Focus
/*     */   public void setFocus() {}
/*     */   
/*     */   protected void updateGui() {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonalRelativesExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */