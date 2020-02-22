/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.eclipse.swt.custom.CLabel;
/*    */ import org.eclipse.swt.events.SelectionAdapter;
/*    */ import org.eclipse.swt.events.SelectionEvent;
/*    */ import org.eclipse.swt.graphics.Image;
/*    */ import org.eclipse.swt.graphics.ImageData;
/*    */ import org.eclipse.swt.graphics.ImageLoader;
/*    */ import org.eclipse.swt.widgets.Button;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Display;
/*    */ import org.eclipse.swt.widgets.FileDialog;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ import org.eclipse.wb.swt.SWTResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductEditor
/*    */ {
/*    */   public static final String ID = "rcp_demo.Editor.product";
/* 27 */   private static final String[] FILTER_NAMES = { "Images(*.jpg)", "Images(*.jpeg)", "Images(*.png)", 
/* 28 */     "All Files (*.*)" };
/*    */   
/* 30 */   private static final String[] FILTER_EXTS = { "*.jpg", "*.jpeg", "*.png", "*.*" };
/*    */   
/*    */ 
/*    */   private Text text;
/*    */   
/*    */ 
/*    */   private CLabel lbl_image_text;
/*    */   
/*    */ 
/*    */   @PostConstruct
/*    */   public void createPartControl(Composite parent)
/*    */   {
/* 42 */     parent.setLayout(null);
/*    */     
/*    */ 
/* 45 */     this.text = new Text(parent, 2048);
/* 46 */     this.text.setBounds(25, 57, 169, 19);
/*    */     
/* 48 */     Button btnOpen = new Button(parent, 0);
/* 49 */     btnOpen.setText("open");
/* 50 */     btnOpen.addSelectionListener(new SelectionAdapter()
/*    */     {
/*    */ 
/*    */ 
/*    */       public void widgetSelected(SelectionEvent e)
/*    */       {
/*    */ 
/*    */ 
/* 58 */         Display display = Display.getDefault();
/* 59 */         Shell shell = new Shell(display);
/* 60 */         FileDialog dialog = new FileDialog(shell, 4096);
/* 61 */         dialog.setFilterNames(ProductEditor.FILTER_NAMES);
/* 62 */         dialog.setFilterExtensions(ProductEditor.FILTER_EXTS);
/* 63 */         String result = dialog.open();
/* 64 */         if (result != null) {
/* 65 */           ProductEditor.this.text.setText(result);
/* 66 */           Image image = SWTResourceManager.getImage(result);
/* 67 */           ImageData imgData = image.getImageData();
/* 68 */           imgData = imgData.scaledTo(200, 200);
/*    */           
/* 70 */           ImageLoader imageLoader = new ImageLoader();
/* 71 */           imageLoader.data = new ImageData[] { imgData };
/* 72 */           imageLoader.save(result, 0);
/*    */           
/* 74 */           System.out.println(imgData.width + "....." + imgData.height);
/* 75 */           ProductEditor.this.lbl_image_text.setBounds(25, 88, imgData.width + 10, imgData.height + 10);
/*    */           
/*    */ 
/* 78 */           ProductEditor.this.lbl_image_text.setImage(SWTResourceManager.getImage(result));
/*    */         }
/*    */       }
/* 81 */     });
/* 82 */     btnOpen.setText("open");
/* 83 */     this.lbl_image_text = new CLabel(parent, 11);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\ProductEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */