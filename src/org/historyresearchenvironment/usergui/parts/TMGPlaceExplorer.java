/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
/*     */ import org.eclipse.jface.layout.TableColumnLayout;
/*     */ import org.eclipse.jface.viewers.ColumnPixelData;
/*     */ import org.eclipse.jface.viewers.TableViewer;
/*     */ import org.eclipse.jface.viewers.TableViewerColumn;
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
/*     */ import org.historyresearchenvironment.usergui.models.TMGPlaceModel;
/*     */ import org.historyresearchenvironment.usergui.models.TMGPlaceVector;
/*     */ import org.historyresearchenvironment.usergui.providers.TMGPlaceProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TMGPlaceExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   private Table tablePlaces;
/*  42 */   private TMGPlaceProvider provider = new TMGPlaceProvider();
/*     */   
/*     */ 
/*     */   private TableItem tableItem;
/*     */   
/*     */ 
/*     */   private int recNo;
/*     */   
/*     */ 
/*     */   private TableViewer tableViewerPlaces;
/*     */   
/*     */ 
/*     */ 
/*     */   protected void callBusinessLayer(int i)
/*     */   {
/*  57 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  58 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*     */     
/*  60 */     this.req = new ServerRequest("GET", "locationlist", this.provider);
/*     */     
/*  62 */     long before = System.nanoTime();
/*     */     
/*  64 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     
/*  66 */     long after = System.nanoTime();
/*  67 */     LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */     
/*  69 */     if (this.resp == null) {
/*  70 */       this.eventBroker.post("MESSAGE", "Call not successful");
/*  71 */       LOGGER.severe("Call not successful");
/*  72 */     } else if (this.resp.getReturnCode() != 0) {
/*  73 */       this.eventBroker.post("MESSAGE", this.resp.getReturnMessage());
/*  74 */       LOGGER.severe(this.resp.getReturnMessage());
/*     */     } else {
/*     */       try {
/*  77 */         updateGui();
/*     */       } catch (Exception e) {
/*  79 */         LOGGER.severe(
/*  80 */           "Error in request " + this.req.getOperation() + " " + this.req.getModelName() + ", " + e.getMessage());
/*  81 */         this.eventBroker.post("MESSAGE", e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  91 */     parent.setLayout(new GridLayout(1, false));
/*  92 */     parent.setFont(getHreFont(parent));
/*     */     
/*  94 */     Composite compositePlaces = new Composite(parent, 0);
/*  95 */     GridData gd_compositePlaces = new GridData(4, 4, true, true, 1, 1);
/*  96 */     gd_compositePlaces.heightHint = 480;
/*  97 */     compositePlaces.setLayoutData(gd_compositePlaces);
/*  98 */     TableColumnLayout tcl_compositePlaces = new TableColumnLayout();
/*  99 */     compositePlaces.setLayout(tcl_compositePlaces);
/*     */     
/* 101 */     this.tableViewerPlaces = new TableViewer(compositePlaces, 67584);
/* 102 */     this.tablePlaces = this.tableViewerPlaces.getTable();
/* 103 */     this.tablePlaces.setFont(getHreFont(parent));
/* 104 */     this.tablePlaces.addListener(13, new Listener()
/*     */     {
/*     */       public void handleEvent(Event e) {
/* 107 */         TableItem[] selection = TMGPlaceExplorer.this.tablePlaces.getSelection();
/* 108 */         TableItem ti = selection[0];
/* 109 */         String string = ti.getText(0);
/*     */         
/* 111 */         if (string.length() > 0) {
/* 112 */           TMGPlaceExplorer.this.recNo = Integer.parseInt(string);
/*     */         } else {
/* 114 */           TMGPlaceExplorer.this.recNo = 1;
/*     */         }
/* 116 */         TMGPlaceExplorer.LOGGER.fine("Record number: " + TMGPlaceExplorer.this.recNo);
/*     */       }
/* 118 */     });
/* 119 */     this.tablePlaces.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDoubleClick(MouseEvent e) {
/* 122 */         String s = TMGPlaceExplorer.this.provider.getVector().getItem(TMGPlaceExplorer.this.recNo).toString();
/* 123 */         TMGPlaceExplorer.this.eventBroker.post("LOCATION_UPDATE_TOPIC", s);
/* 124 */         TMGPlaceExplorer.LOGGER.info("City: " + s);
/*     */       }
/* 126 */     });
/* 127 */     this.tablePlaces.setHeaderVisible(true);
/* 128 */     this.tablePlaces.setLinesVisible(true);
/*     */     
/* 130 */     TableViewerColumn tableViewerColumn = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 131 */     TableColumn tblclmnRecordNumber = tableViewerColumn.getColumn();
/* 132 */     tcl_compositePlaces.setColumnData(tblclmnRecordNumber, new ColumnPixelData(120, true, true));
/* 133 */     tblclmnRecordNumber.setText(this.provider.getLabel(0));
/*     */     
/* 135 */     TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 136 */     TableColumn tblclmnNewColumn = tableViewerColumn_1.getColumn();
/* 137 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn, new ColumnPixelData(120, true, true));
/* 138 */     tblclmnNewColumn.setText(this.provider.getLabel(1));
/*     */     
/* 140 */     TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 141 */     TableColumn tblclmnNewColumn_1 = tableViewerColumn_2.getColumn();
/* 142 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_1, new ColumnPixelData(120, true, true));
/* 143 */     tblclmnNewColumn_1.setText(this.provider.getLabel(2));
/*     */     
/* 145 */     TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 146 */     TableColumn tblclmnNewColumn_2 = tableViewerColumn_3.getColumn();
/* 147 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_2, new ColumnPixelData(120, true, true));
/* 148 */     tblclmnNewColumn_2.setText(this.provider.getLabel(3));
/*     */     
/* 150 */     TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 151 */     TableColumn tblclmnNewColumn_3 = tableViewerColumn_4.getColumn();
/* 152 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_3, new ColumnPixelData(120, true, true));
/* 153 */     tblclmnNewColumn_3.setText(this.provider.getLabel(4));
/*     */     
/* 155 */     TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 156 */     TableColumn tblclmnNewColumn_4 = tableViewerColumn_5.getColumn();
/* 157 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_4, new ColumnPixelData(120, true, true));
/* 158 */     tblclmnNewColumn_4.setText(this.provider.getLabel(5));
/*     */     
/* 160 */     TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 161 */     TableColumn tblclmnNewColumn_5 = tableViewerColumn_6.getColumn();
/* 162 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_5, new ColumnPixelData(120, true, true));
/* 163 */     tblclmnNewColumn_5.setText(this.provider.getLabel(6));
/*     */     
/* 165 */     TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 166 */     TableColumn tblclmnNewColumn_6 = tableViewerColumn_7.getColumn();
/* 167 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_6, new ColumnPixelData(120, true, true));
/* 168 */     tblclmnNewColumn_6.setText(this.provider.getLabel(7));
/*     */     
/* 170 */     TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 171 */     TableColumn tblclmnNewColumn_7 = tableViewerColumn_8.getColumn();
/* 172 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_7, new ColumnPixelData(120, true, true));
/* 173 */     tblclmnNewColumn_7.setText(this.provider.getLabel(8));
/*     */     
/* 175 */     TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 176 */     TableColumn tblclmnNewColumn_8 = tableViewerColumn_9.getColumn();
/* 177 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_8, new ColumnPixelData(120, true, true));
/* 178 */     tblclmnNewColumn_8.setText(this.provider.getLabel(9));
/*     */     
/* 180 */     TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(this.tableViewerPlaces, 0);
/* 181 */     TableColumn tblclmnNewColumn_9 = tableViewerColumn_10.getColumn();
/* 182 */     tcl_compositePlaces.setColumnData(tblclmnNewColumn_9, new ColumnPixelData(120, true, true));
/* 183 */     tblclmnNewColumn_9.setText(this.provider.getLabel(10));
/*     */     
/* 185 */     callBusinessLayer(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose()
/*     */   {
/* 193 */     this.provider.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*     */   {
/* 204 */     callBusinessLayer(perNo);
/*     */   }
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
/*     */     try
/*     */     {
/* 218 */       Iterator<TMGPlaceModel> iterator = this.provider.getVector().iterator();
/* 219 */       this.tablePlaces.removeAll();
/*     */       TMGPlaceModel tmgPlaceItem;
/* 221 */       int i; for (; iterator.hasNext(); 
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 226 */           i < tmgPlaceItem.getPlaceParts().length)
/*     */       {
/* 222 */         tmgPlaceItem = (TMGPlaceModel)iterator.next();
/* 223 */         this.tableItem = new TableItem(this.tablePlaces, 0);
/* 224 */         this.tableItem.setText(0, Integer.toString(tmgPlaceItem.getRecNo()));
/*     */         
/* 226 */         i = 1; continue;
/* 227 */         this.tableItem.setText(i, tmgPlaceItem.getPlacePart(i));i++;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 231 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\TMGPlaceExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */