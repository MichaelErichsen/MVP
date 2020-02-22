/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.commands.ParameterizedCommand;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.commands.ECommandService;
/*     */ import org.eclipse.e4.core.commands.EHandlerService;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
/*     */ import org.eclipse.jface.viewers.ArrayContentProvider;
/*     */ import org.eclipse.jface.viewers.ColumnLabelProvider;
/*     */ import org.eclipse.jface.viewers.TableViewer;
/*     */ import org.eclipse.jface.viewers.TableViewerColumn;
/*     */ import org.eclipse.swt.events.SelectionAdapter;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Event;
/*     */ import org.eclipse.swt.widgets.Listener;
/*     */ import org.eclipse.swt.widgets.Menu;
/*     */ import org.eclipse.swt.widgets.MenuItem;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.eclipse.swt.widgets.TableItem;
/*     */ import org.historyresearchenvironment.usergui.models.PolRegModel;
/*     */ import org.historyresearchenvironment.usergui.providers.PolRegProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolRegExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   @Inject
/*     */   private ECommandService commandService;
/*     */   @Inject
/*     */   private EHandlerService handlerService;
/*     */   private Table tablePolReg;
/*     */   private TableViewer tableViewerPolReg;
/*     */   private PolRegProvider polRegProvider;
/*     */   private TableColumn tblclmnId;
/*     */   private TableViewerColumn tableViewerColumn;
/*     */   private TableColumn tblclmnRegisterblad;
/*     */   private TableViewerColumn tableViewerColumn_1;
/*     */   private TableColumn tblclmnFirstNames;
/*     */   private TableViewerColumn tableViewerColumn_3;
/*     */   private TableColumn tblclmnSurname;
/*     */   private TableViewerColumn tableViewerColumn_4;
/*     */   private TableColumn tblclmnType_1;
/*     */   private TableViewerColumn tableViewerColumn_5;
/*     */   private TableColumn tblclmnBirthPlace;
/*     */   private TableViewerColumn tableViewerColumn_6;
/*     */   private TableColumn tblclmnBirthDate;
/*     */   private TableViewerColumn tableViewerColumn_7;
/*     */   private TableColumn tblclmnDeathDate;
/*     */   private TableViewerColumn tableViewerColumn_8;
/*     */   protected String registerbladId;
/*     */   private Menu menu;
/*     */   
/*     */   protected void callBusinessLayer(int i) {}
/*     */   
/*     */   @PostConstruct
/*     */   public void postConstruct(Composite parent)
/*     */   {
/*  85 */     LOGGER.info("Constructor");
/*  86 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  87 */     parent.setLayout(new GridLayout(2, false));
/*     */     
/*  89 */     this.tableViewerPolReg = new TableViewer(parent, 67584);
/*  90 */     this.tablePolReg = this.tableViewerPolReg.getTable();
/*  91 */     this.tablePolReg.setToolTipText("Select a person and right clik to continue");
/*  92 */     this.tablePolReg.addListener(13, new Listener()
/*     */     {
/*     */ 
/*     */ 
/*     */       public void handleEvent(Event event)
/*     */       {
/*     */ 
/*  99 */         TableItem[] selection = PolRegExplorer.this.tablePolReg.getSelection();
/* 100 */         TableItem item = selection[0];
/* 101 */         PolRegExplorer.this.registerbladId = item.getText(1);
/* 102 */         PolRegExplorer.LOGGER.info("Selected id: " + PolRegExplorer.this.registerbladId);
/* 103 */         PolRegExplorer.this.store.putValue("POLREGID", PolRegExplorer.this.registerbladId);
/*     */       }
/* 105 */     });
/* 106 */     this.tablePolReg.setFont(getHreFont(parent));
/* 107 */     this.tablePolReg.setLinesVisible(true);
/* 108 */     this.tablePolReg.setHeaderVisible(true);
/* 109 */     this.tablePolReg.setLayoutData(new GridData(4, 4, true, true, 2, 1));
/*     */     
/* 111 */     this.tableViewerColumn = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 112 */     this.tblclmnId = this.tableViewerColumn.getColumn();
/* 113 */     this.tblclmnId.setWidth(100);
/* 114 */     this.tblclmnId.setText("ID");
/* 115 */     this.tableViewerColumn.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 118 */         PolRegModel item = (PolRegModel)element;
/* 119 */         return item.getId();
/*     */       }
/*     */       
/* 122 */     });
/* 123 */     this.tableViewerColumn_1 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 124 */     this.tblclmnRegisterblad = this.tableViewerColumn_1.getColumn();
/* 125 */     this.tblclmnRegisterblad.setWidth(100);
/* 126 */     this.tblclmnRegisterblad.setText("Registerblad");
/* 127 */     this.tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 130 */         PolRegModel item = (PolRegModel)element;
/* 131 */         return item.getRegisterblad_id();
/*     */       }
/*     */       
/* 134 */     });
/* 135 */     this.tableViewerColumn_3 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 136 */     this.tblclmnFirstNames = this.tableViewerColumn_3.getColumn();
/* 137 */     this.tblclmnFirstNames.setWidth(200);
/* 138 */     this.tblclmnFirstNames.setText("First Names");
/* 139 */     this.tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 142 */         PolRegModel item = (PolRegModel)element;
/* 143 */         return item.getFirstnames();
/*     */       }
/*     */       
/* 146 */     });
/* 147 */     this.tableViewerColumn_4 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 148 */     this.tblclmnSurname = this.tableViewerColumn_4.getColumn();
/* 149 */     this.tblclmnSurname.setWidth(150);
/* 150 */     this.tblclmnSurname.setText("Surname");
/* 151 */     this.tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 154 */         PolRegModel item = (PolRegModel)element;
/* 155 */         return item.getLastname();
/*     */       }
/*     */       
/* 158 */     });
/* 159 */     this.tableViewerColumn_5 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 160 */     this.tblclmnType_1 = this.tableViewerColumn_5.getColumn();
/* 161 */     this.tblclmnType_1.setWidth(100);
/* 162 */     this.tblclmnType_1.setText("Type");
/* 163 */     this.tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 166 */         PolRegModel item = (PolRegModel)element;
/* 167 */         return item.getPerson_type();
/*     */       }
/*     */       
/* 170 */     });
/* 171 */     this.tableViewerColumn_6 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 172 */     this.tblclmnBirthPlace = this.tableViewerColumn_6.getColumn();
/* 173 */     this.tblclmnBirthPlace.setWidth(100);
/* 174 */     this.tblclmnBirthPlace.setText("Birth Place");
/* 175 */     this.tableViewerColumn_6.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 178 */         PolRegModel item = (PolRegModel)element;
/* 179 */         return item.getBirthplace();
/*     */       }
/*     */       
/* 182 */     });
/* 183 */     this.tableViewerColumn_7 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 184 */     this.tblclmnBirthDate = this.tableViewerColumn_7.getColumn();
/* 185 */     this.tblclmnBirthDate.setWidth(100);
/* 186 */     this.tblclmnBirthDate.setText("Birth date");
/* 187 */     this.tableViewerColumn_7.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 190 */         PolRegModel item = (PolRegModel)element;
/* 191 */         return item.getDateofbirth();
/*     */       }
/*     */       
/* 194 */     });
/* 195 */     this.tableViewerColumn_8 = new TableViewerColumn(this.tableViewerPolReg, 0);
/* 196 */     this.tblclmnDeathDate = this.tableViewerColumn_8.getColumn();
/* 197 */     this.tblclmnDeathDate.setWidth(100);
/* 198 */     this.tblclmnDeathDate.setText("Death date");
/* 199 */     this.tableViewerColumn_8.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 202 */         PolRegModel item = (PolRegModel)element;
/* 203 */         return item.getDateofdeath();
/*     */       }
/*     */       
/* 206 */     });
/* 207 */     this.menu = new Menu(this.tablePolReg);
/* 208 */     this.tablePolReg.setMenu(this.menu);
/*     */     
/* 210 */     MenuItem mntmOpenOriginalIn = new MenuItem(this.menu, 0);
/* 211 */     mntmOpenOriginalIn.addSelectionListener(new SelectionAdapter()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent e) {
/* 214 */         PolRegExplorer.LOGGER.info("Browser selected on " + PolRegExplorer.this.registerbladId);
/* 215 */         ParameterizedCommand browseCommand = PolRegExplorer.this.commandService
/* 216 */           .createCommand("org.historyresearchenvironment.usergui.command.polregbrowser", null);
/* 217 */         PolRegExplorer.this.handlerService.executeHandler(browseCommand);
/*     */       }
/* 219 */     });
/* 220 */     mntmOpenOriginalIn.setText("Open Original in Browser");
/*     */     
/* 222 */     MenuItem mntmLocations = new MenuItem(this.menu, 0);
/* 223 */     mntmLocations.addSelectionListener(new SelectionAdapter()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent e) {
/* 226 */         PolRegExplorer.LOGGER.info("Locations selected on " + PolRegExplorer.this.registerbladId);
/* 227 */         ParameterizedCommand browseCommand = PolRegExplorer.this.commandService
/* 228 */           .createCommand("org.historyresearchenvironment.usergui.command.polreglocation", null);
/* 229 */         PolRegExplorer.this.handlerService.executeHandler(browseCommand);
/*     */       }
/* 231 */     });
/* 232 */     mntmLocations.setText("Locations");
/*     */     
/* 234 */     this.tableViewerPolReg.setContentProvider(ArrayContentProvider.getInstance());
/* 235 */     this.polRegProvider = new PolRegProvider();
/* 236 */     this.tableViewerPolReg.setInput(this.polRegProvider.getModelList());
/*     */   }
/*     */   
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribeNameUpdateTopic(@UIEventTopic("NAME_UPDATE_TOPIC") String perName) {
/* 242 */     LOGGER.fine("Subscribing to " + perName);
/*     */     
/* 244 */     if ((this.tablePolReg != null) && (this.tableViewerPolReg != null) && (this.tablePolReg.isVisible())) {
/* 245 */       updateGui();
/*     */     } else {
/* 247 */       LOGGER.fine("Not visible");
/*     */     }
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
/* 259 */     LOGGER.fine("Updating GUI");
/*     */     
/* 261 */     this.polRegProvider = new PolRegProvider();
/* 262 */     LOGGER.fine("Set input: Get item list");
/* 263 */     this.tableViewerPolReg.setInput(this.polRegProvider.getModelList());
/* 264 */     LOGGER.fine("Refresh");
/* 265 */     this.tableViewerPolReg.refresh();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PolRegExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */