/*     */ package org.historyresearchenvironment.usergui.handlers;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.core.runtime.IProgressMonitor;
/*     */ import org.eclipse.core.runtime.IStatus;
/*     */ import org.eclipse.core.runtime.SubMonitor;
/*     */ import org.eclipse.core.runtime.jobs.IJobChangeEvent;
/*     */ import org.eclipse.core.runtime.jobs.JobChangeAdapter;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.di.annotations.Execute;
/*     */ import org.eclipse.e4.ui.di.UISynchronize;
/*     */ import org.eclipse.e4.ui.workbench.IWorkbench;
/*     */ import org.eclipse.equinox.p2.core.IProvisioningAgent;
/*     */ import org.eclipse.equinox.p2.engine.ProvisioningContext;
/*     */ import org.eclipse.equinox.p2.operations.ProvisioningJob;
/*     */ import org.eclipse.equinox.p2.operations.ProvisioningSession;
/*     */ import org.eclipse.equinox.p2.operations.UpdateOperation;
/*     */ import org.eclipse.jface.dialogs.MessageDialog;
/*     */ import org.eclipse.jface.dialogs.ProgressMonitorDialog;
/*     */ import org.eclipse.jface.operation.IRunnableWithProgress;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdateHandler
/*     */ {
/*  35 */   private static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ScopedPreferenceStore store;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void configureProvisioningJob(ProvisioningJob provisioningJob, final Shell shell, final UISynchronize sync, final IWorkbench workbench, IProgressMonitor monitor)
/*     */   {
/*  50 */     LOGGER.fine("Configure Provisioning job");
/*  51 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 25);
/*  52 */     subMonitor.beginTask("Configure Provisioning Job", 25);
/*     */     
/*  54 */     provisioningJob.addJobChangeListener(new JobChangeAdapter()
/*     */     {
/*     */       public void done(IJobChangeEvent event) {
/*  57 */         if (event.getResult().isOK()) {
/*  58 */           sync.syncExec(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*  62 */               boolean restart = MessageDialog.openQuestion(this.val$shell, "Updates installed, restart?", 
/*  63 */                 "Updates for HRE have been installed from " + UpdateHandler.this.store.getString("UPDATESITE") + 
/*  64 */                 ". Do you want to restart?");
/*  65 */               if (restart) {
/*  66 */                 this.val$workbench.restart();
/*     */               }
/*     */               
/*     */             }
/*     */           });
/*     */         } else {
/*  72 */           sync.syncExec(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*  76 */               MessageDialog.openWarning(this.val$shell, "Update failed", 
/*  77 */                 "Update of HRE from " + UpdateHandler.this.store.getString("UPDATESITE") + " have failed");
/*     */             }
/*     */           });
/*     */         }
/*  81 */         super.done(event);
/*     */       }
/*  83 */     });
/*  84 */     subMonitor.worked(25);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private UpdateOperation configureUpdate(UpdateOperation operation, IProgressMonitor monitor)
/*     */   {
/*  96 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 25);
/*  97 */     subMonitor.beginTask("Configure Update", 25);
/*  98 */     LOGGER.fine("Configure Update");
/*     */     
/* 100 */     URI uri = null;
/*     */     try {
/* 102 */       uri = new URI(this.store.getString("UPDATESITE"));
/*     */     } catch (URISyntaxException e) {
/* 104 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/* 105 */       return null;
/*     */     }
/*     */     
/* 108 */     operation.getProvisioningContext().setArtifactRepositories(new URI[] { uri });
/* 109 */     operation.getProvisioningContext().setMetadataRepositories(new URI[] { uri });
/* 110 */     subMonitor.worked(25);
/* 111 */     return operation;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Execute
/*     */   public void execute(final IProvisioningAgent agent, final Shell shell, final UISynchronize sync, final IWorkbench workbench)
/*     */   {
/* 123 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 124 */     LOGGER.fine("Repository location: " + this.store.getString("UPDATESITE"));
/*     */     try
/*     */     {
/* 127 */       ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
/* 128 */       dialog.run(true, true, new IRunnableWithProgress()
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         public void run(IProgressMonitor monitor)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 138 */           SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
/*     */           
/* 140 */           UpdateHandler.LOGGER.fine("Check for updates");
/*     */           
/* 142 */           ProvisioningSession session = new ProvisioningSession(agent);
/* 143 */           UpdateOperation operation = new UpdateOperation(session);
/* 144 */           UpdateHandler.this.configureUpdate(operation, subMonitor.split(25, 0));
/*     */           
/*     */ 
/* 147 */           IStatus status = UpdateHandler.this.resolveModal(monitor, operation, 
/* 148 */             subMonitor.split(25, 0));
/*     */           
/*     */ 
/* 151 */           if (status.getCode() == 10000) {
/* 152 */             UpdateHandler.this.showMessage(shell, sync);
/* 153 */             return;
/*     */           }
/*     */           
/*     */ 
/* 157 */           ProvisioningJob provisioningJob = UpdateHandler.this.getProvisioningJob(monitor, operation, 
/* 158 */             subMonitor.split(25, 0));
/*     */           
/*     */ 
/* 161 */           if (provisioningJob == null) {
/* 162 */             UpdateHandler.LOGGER.severe("Trying to update from the Eclipse IDE? This won't work!");
/* 163 */             return;
/*     */           }
/* 165 */           UpdateHandler.this.configureProvisioningJob(provisioningJob, shell, sync, workbench, 
/* 166 */             subMonitor.split(25, 0));
/*     */           
/* 168 */           provisioningJob.schedule();
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 173 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     } catch (InterruptedException e) {
/* 175 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ProvisioningJob getProvisioningJob(IProgressMonitor monitor, UpdateOperation operation, IProgressMonitor monitor2)
/*     */   {
/* 187 */     SubMonitor subMonitor = SubMonitor.convert(monitor2, 25);
/* 188 */     subMonitor.beginTask("Get Provisioning Job", 25);
/* 189 */     LOGGER.info("Get Provisioning Job");
/* 190 */     ProvisioningJob provisioningJob = operation.getProvisioningJob(monitor);
/* 191 */     subMonitor.worked(25);
/* 192 */     return provisioningJob;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private IStatus resolveModal(IProgressMonitor monitor, UpdateOperation operation, IProgressMonitor monitor2)
/*     */   {
/* 203 */     SubMonitor subMonitor = SubMonitor.convert(monitor2, 25);
/* 204 */     subMonitor.beginTask("Resolve Modal Operation", 25);
/* 205 */     LOGGER.info("Resolve Modal Operation");
/* 206 */     IStatus status = operation.resolveModal(monitor);
/* 207 */     subMonitor.worked(25);
/* 208 */     return status;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void showMessage(final Shell parent, UISynchronize sync)
/*     */   {
/* 216 */     LOGGER.fine("Show message");
/* 217 */     sync.syncExec(new Runnable()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       public void run()
/*     */       {
/*     */ 
/*     */ 
/* 226 */         MessageDialog.openWarning(parent, "No update", 
/* 227 */           "No updates for HRE have been found at " + UpdateHandler.this.store.getString("UPDATESITE"));
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\UpdateHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */