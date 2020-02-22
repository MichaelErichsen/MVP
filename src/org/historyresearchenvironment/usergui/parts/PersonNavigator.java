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
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.eclipse.swt.widgets.TableItem;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.models.PersonNavigatorModel;
/*     */ import org.historyresearchenvironment.usergui.providers.PersonNavigatorProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonNavigator
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   private Table tablePerson;
/*  38 */   private PersonNavigatorProvider provider = new PersonNavigatorProvider();
/*     */   private TableItem tableItem;
/*     */   private PersonNavigatorModel person;
/*  41 */   private int perNo = 1;
/*  42 */   private String perName = "Frank+Alexander";
/*     */   
/*     */ 
/*     */ 
/*     */   private Composite grandParent;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void callBusinessLayer(int perNo)
/*     */   {
/*  53 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  54 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*     */     
/*  56 */     this.req = new ServerRequest("GET", "personlist", this.provider);
/*     */     
/*  58 */     long before = System.nanoTime();
/*     */     
/*  60 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     
/*  62 */     long after = System.nanoTime();
/*  63 */     LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */     
/*  65 */     if (this.resp == null) {
/*  66 */       this.eventBroker.post("MESSAGE", "Call not successful");
/*  67 */       LOGGER.severe("Call not successful");
/*  68 */     } else if (this.resp.getReturnCode() != 0) {
/*  69 */       this.eventBroker.post("MESSAGE", this.resp.getReturnMessage());
/*  70 */       LOGGER.severe(this.resp.getReturnMessage());
/*     */     }
/*     */     else {
/*  73 */       this.provider = ((PersonNavigatorProvider)this.resp.getProvider());
/*     */       try
/*     */       {
/*  76 */         if (this.tablePerson != null) {
/*  77 */           this.perNo = perNo;
/*  78 */           updateGui();
/*     */         }
/*     */       } catch (Exception e2) {
/*  81 */         LOGGER.severe(
/*  82 */           "Error in request " + this.req.getOperation() + " " + this.req.getModelName() + ", " + e2.getMessage());
/*  83 */         this.eventBroker.post("MESSAGE", e2.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  93 */     parent.setLayout(new GridLayout(1, false));
/*  94 */     this.grandParent = parent;
/*     */     
/*  96 */     this.tablePerson = new Table(parent, 2052);
/*  97 */     this.tablePerson.setFont(getHreFont(parent));
/*  98 */     this.tablePerson.addListener(13, new Listener()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       public void handleEvent(Event e)
/*     */       {
/*     */ 
/*     */ 
/* 107 */         TableItem[] selection = PersonNavigator.this.tablePerson.getSelection();
/* 108 */         TableItem ti = selection[0];
/* 109 */         PersonNavigator.this.perName = ti.getText(0);
/*     */         
/* 111 */         PersonNavigator.LOGGER.fine("Name: " + PersonNavigator.this.perName);
/* 112 */         String[] sa = PersonNavigator.this.perName.split(",");
/* 113 */         String[] sa2 = sa[1].split(" ");
/* 114 */         PersonNavigator.this.perName = (sa2[1] + "+" + sa[0]);
/* 115 */         PersonNavigator.this.perName = PersonNavigator.this.perName.replace(" ", "+");
/* 116 */         PersonNavigator.LOGGER.fine("Name: " + PersonNavigator.this.perName);
/*     */         
/* 118 */         PersonNavigator.this.store.putValue("PERNAME", PersonNavigator.this.perName);
/* 119 */         String string = ti.getText(1);
/* 120 */         PersonNavigator.this.perNo = Integer.parseInt(string);
/*     */       }
/*     */       
/* 123 */     });
/* 124 */     this.tablePerson.addMouseListener(new MouseAdapter()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       public void mouseDoubleClick(MouseEvent e)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 134 */         PersonNavigator.this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(PersonNavigator.this.perNo));
/* 135 */         PersonNavigator.this.eventBroker.post("NAME_UPDATE_TOPIC", PersonNavigator.this.perName);
/* 136 */         PersonNavigator.LOGGER.fine("Name: " + PersonNavigator.this.perName);
/*     */       }
/* 138 */     });
/* 139 */     this.tablePerson.setLayoutData(new GridData(4, 4, true, true, 1, 1));
/* 140 */     this.tablePerson.setHeaderVisible(true);
/* 141 */     this.tablePerson.setLinesVisible(true);
/*     */     
/* 143 */     TableColumn tblclmnPersonName = new TableColumn(this.tablePerson, 0);
/* 144 */     tblclmnPersonName.setWidth(175);
/* 145 */     tblclmnPersonName.setText("Person Name");
/*     */     
/* 147 */     TableColumn tblclmnPersonId = new TableColumn(this.tablePerson, 0);
/* 148 */     tblclmnPersonId.setWidth(75);
/* 149 */     tblclmnPersonId.setText("Person ID");
/*     */     
/* 151 */     TableColumn tblclmnBorn = new TableColumn(this.tablePerson, 0);
/* 152 */     tblclmnBorn.setWidth(80);
/* 153 */     tblclmnBorn.setText("Born");
/*     */     
/* 155 */     TableColumn tblclmnDied = new TableColumn(this.tablePerson, 0);
/* 156 */     tblclmnDied.setWidth(80);
/* 157 */     tblclmnDied.setText("Died");
/*     */     
/* 159 */     TableColumn tblclmnFathersId = new TableColumn(this.tablePerson, 0);
/* 160 */     tblclmnFathersId.setWidth(75);
/* 161 */     tblclmnFathersId.setText("Father's ID");
/*     */     
/* 163 */     TableColumn tblclmnMothersId = new TableColumn(this.tablePerson, 0);
/* 164 */     tblclmnMothersId.setWidth(75);
/* 165 */     tblclmnMothersId.setText("Mother's ID");
/*     */     
/* 167 */     callBusinessLayer(this.perNo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose()
/*     */   {
/* 175 */     this.provider.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*     */   {
/* 185 */     callBusinessLayer(perNo);
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
/* 196 */     int index = 0;
/* 197 */     int selection = 1;
/*     */     try
/*     */     {
/* 200 */       Iterator<PersonNavigatorModel> iterator = this.provider.getPersons().iterator();
/*     */       
/* 202 */       this.tablePerson.removeAll();
/*     */       
/* 204 */       while (iterator.hasNext()) {
/* 205 */         this.person = ((PersonNavigatorModel)iterator.next());
/* 206 */         this.tableItem = new TableItem(this.tablePerson, 0);
/* 207 */         this.tableItem.setFont(getHreFont(this.grandParent));
/* 208 */         this.tableItem.setText(0, this.person.getName());
/* 209 */         this.tableItem.setText(1, Integer.toString(this.person.getPerNo()));
/* 210 */         this.tableItem.setText(2, this.person.getPbirth());
/* 211 */         this.tableItem.setText(3, this.person.getPdeath());
/* 212 */         this.tableItem.setText(4, Integer.toString(this.person.getFather()));
/* 213 */         this.tableItem.setText(5, Integer.toString(this.person.getMother()));
/* 214 */         index++;
/*     */         
/* 216 */         if (this.person.getPerNo() == this.perNo) {
/* 217 */           selection = index - 1;
/*     */         }
/*     */       }
/* 220 */       this.tablePerson.setSelection(selection);
/*     */     }
/*     */     catch (Exception e) {
/* 223 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 224 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonNavigator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */