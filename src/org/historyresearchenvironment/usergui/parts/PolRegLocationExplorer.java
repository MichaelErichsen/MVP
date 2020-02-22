/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
/*     */ import org.eclipse.jface.viewers.ArrayContentProvider;
/*     */ import org.eclipse.jface.viewers.ColumnLabelProvider;
/*     */ import org.eclipse.jface.viewers.TableViewer;
/*     */ import org.eclipse.jface.viewers.TableViewerColumn;
/*     */ import org.eclipse.swt.dnd.Clipboard;
/*     */ import org.eclipse.swt.dnd.TextTransfer;
/*     */ import org.eclipse.swt.dnd.Transfer;
/*     */ import org.eclipse.swt.events.SelectionAdapter;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Menu;
/*     */ import org.eclipse.swt.widgets.MenuItem;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.historyresearchenvironment.usergui.models.PolRegLocationModel;
/*     */ import org.historyresearchenvironment.usergui.providers.PolRegLocationProvider;
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
/*     */ public class PolRegLocationExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   private Table tablePolRegLocation;
/*     */   private TableViewer tableViewerPolRegLocation;
/*     */   private PolRegLocationProvider polRegLocationProvider;
/*     */   protected String registerbladId;
/*     */   private TableColumn tblclmnAddress;
/*     */   private TableViewerColumn tableViewerColumn;
/*     */   private TableColumn tblclmnDate;
/*     */   private TableViewerColumn tableViewerColumn_1;
/*     */   private TableColumn tblclmnLatitude;
/*     */   private TableViewerColumn tableViewerColumn_2;
/*     */   private TableColumn tblclmnLongitude;
/*     */   private TableViewerColumn tableViewerColumn_3;
/*     */   private Clipboard clipboard;
/*     */   
/*     */   protected void callBusinessLayer(int i) {}
/*     */   
/*     */   @PostConstruct
/*     */   public void postConstruct(Composite parent)
/*     */   {
/*  71 */     LOGGER.info("Constructor");
/*  72 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  73 */     parent.setLayout(new GridLayout(2, false));
/*     */     
/*  75 */     this.tableViewerPolRegLocation = new TableViewer(parent, 67584);
/*  76 */     this.tablePolRegLocation = this.tableViewerPolRegLocation.getTable();
/*  77 */     this.tablePolRegLocation.setFont(getHreFont(parent));
/*  78 */     this.tablePolRegLocation.setLinesVisible(true);
/*  79 */     this.tablePolRegLocation.setHeaderVisible(true);
/*  80 */     this.tablePolRegLocation.setLayoutData(new GridData(4, 4, true, true, 2, 1));
/*     */     
/*  82 */     this.tableViewerColumn = new TableViewerColumn(this.tableViewerPolRegLocation, 0);
/*  83 */     this.tblclmnAddress = this.tableViewerColumn.getColumn();
/*  84 */     this.tblclmnAddress.setWidth(250);
/*  85 */     this.tblclmnAddress.setText("Address");
/*  86 */     this.tableViewerColumn.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/*  89 */         PolRegLocationModel item = (PolRegLocationModel)element;
/*  90 */         return item.getAddress();
/*     */       }
/*     */       
/*  93 */     });
/*  94 */     this.tableViewerColumn_1 = new TableViewerColumn(this.tableViewerPolRegLocation, 0);
/*  95 */     this.tblclmnDate = this.tableViewerColumn_1.getColumn();
/*  96 */     this.tblclmnDate.setWidth(100);
/*  97 */     this.tblclmnDate.setText("Date");
/*  98 */     this.tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 101 */         PolRegLocationModel item = (PolRegLocationModel)element;
/* 102 */         return item.getDate();
/*     */       }
/*     */       
/* 105 */     });
/* 106 */     this.tableViewerColumn_2 = new TableViewerColumn(this.tableViewerPolRegLocation, 0);
/* 107 */     this.tblclmnLatitude = this.tableViewerColumn_2.getColumn();
/* 108 */     this.tblclmnLatitude.setWidth(150);
/* 109 */     this.tblclmnLatitude.setText("Latitude");
/* 110 */     this.tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 113 */         PolRegLocationModel item = (PolRegLocationModel)element;
/* 114 */         return item.getLatitude();
/*     */       }
/*     */       
/* 117 */     });
/* 118 */     this.tableViewerColumn_3 = new TableViewerColumn(this.tableViewerPolRegLocation, 0);
/* 119 */     this.tblclmnLongitude = this.tableViewerColumn_3.getColumn();
/* 120 */     this.tblclmnLongitude.setWidth(150);
/* 121 */     this.tblclmnLongitude.setText("Longitude");
/* 122 */     this.tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider()
/*     */     {
/*     */       public String getText(Object element) {
/* 125 */         PolRegLocationModel item = (PolRegLocationModel)element;
/* 126 */         return item.getLongitude();
/*     */       }
/*     */       
/* 129 */     });
/* 130 */     Menu menu = new Menu(this.tablePolRegLocation);
/* 131 */     this.tablePolRegLocation.setMenu(menu);
/*     */     
/* 133 */     MenuItem mntmCopyToClipboard = new MenuItem(menu, 0);
/* 134 */     mntmCopyToClipboard.addSelectionListener(new SelectionAdapter()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent e)
/*     */       {
/* 138 */         PolRegLocationExplorer.LOGGER.info("Copy to clipboard");
/* 139 */         Display display = Display.getCurrent();
/* 140 */         PolRegLocationExplorer.this.clipboard = new Clipboard(display);
/* 141 */         List<PolRegLocationModel> list = PolRegLocationExplorer.this.polRegLocationProvider.getModelList();
/*     */         
/* 143 */         StringBuilder sb = new StringBuilder();
/*     */         
/* 145 */         for (PolRegLocationModel polRegLocationModel : list) {
/* 146 */           sb.append(polRegLocationModel.toString() + "\n");
/*     */         }
/*     */         
/* 149 */         PolRegLocationExplorer.this.clipboard.setContents(new Object[] { sb.toString() }, new Transfer[] { TextTransfer.getInstance() });
/* 150 */         PolRegLocationExplorer.this.clipboard.dispose();
/*     */       }
/* 152 */     });
/* 153 */     mntmCopyToClipboard.setText("Copy to Clipboard");
/*     */     
/* 155 */     this.tableViewerPolRegLocation.setContentProvider(ArrayContentProvider.getInstance());
/* 156 */     this.polRegLocationProvider = new PolRegLocationProvider();
/* 157 */     this.tableViewerPolRegLocation.setInput(this.polRegLocationProvider.getModelList());
/*     */   }
/*     */   
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribeNameUpdateTopic(@UIEventTopic("NAME_UPDATE_TOPIC") String perName) {
/* 163 */     LOGGER.fine("Subscribing to " + perName);
/*     */     
/* 165 */     if ((this.tablePolRegLocation != null) && (this.tableViewerPolRegLocation != null) && (this.tablePolRegLocation.isVisible())) {
/* 166 */       updateGui();
/*     */     } else {
/* 168 */       LOGGER.fine("Not visible");
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
/* 180 */     LOGGER.fine("Updating GUI");
/*     */     
/* 182 */     this.polRegLocationProvider = new PolRegLocationProvider();
/* 183 */     LOGGER.fine("Set input: Get item list");
/* 184 */     this.tableViewerPolRegLocation.setInput(this.polRegLocationProvider.getModelList());
/* 185 */     LOGGER.fine("Refresh");
/* 186 */     this.tableViewerPolRegLocation.refresh();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PolRegLocationExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */