/*     */ package org.apache.commons.logging.impl;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public final class WeakHashtable
/*     */   extends Hashtable<Object, Object>
/*     */ {
/*     */   private static final long serialVersionUID = -1546036869799732453L;
/*     */   private static final int MAX_CHANGES_BEFORE_PURGE = 100;
/*     */   private static final int PARTIAL_PURGE_COUNT = 10;
/*     */   
/*     */   private static final class Entry
/*     */     implements Map.Entry
/*     */   {
/*     */     private final Object key;
/*     */     private final Object value;
/*     */     
/*     */     private Entry(Object key, Object value)
/*     */     {
/* 120 */       this.key = key;
/* 121 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o)
/*     */     {
/* 126 */       boolean result = false;
/* 127 */       if ((o != null) && ((o instanceof Map.Entry))) {
/* 128 */         Map.Entry entry = (Map.Entry)o;
/* 129 */         result = (getKey() == null ? entry.getKey() == null : getKey().equals(entry.getKey())) && 
/* 130 */           (getValue() == null ? entry.getValue() == null : getValue().equals(entry.getValue()));
/*     */       }
/* 132 */       return result;
/*     */     }
/*     */     
/*     */     public Object getKey()
/*     */     {
/* 137 */       return this.key;
/*     */     }
/*     */     
/*     */     public Object getValue()
/*     */     {
/* 142 */       return this.value;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 147 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*     */     }
/*     */     
/*     */     public Object setValue(Object value)
/*     */     {
/* 152 */       throw new UnsupportedOperationException("Entry.setValue is not supported.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Referenced
/*     */   {
/*     */     private final WeakReference reference;
/*     */     
/*     */ 
/*     */     private final int hashCode;
/*     */     
/*     */ 
/*     */     private Referenced(Object referant)
/*     */     {
/* 168 */       this.reference = new WeakReference(referant);
/*     */       
/*     */ 
/* 171 */       this.hashCode = referant.hashCode();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Referenced(Object key, ReferenceQueue queue)
/*     */     {
/* 180 */       this.reference = new WeakHashtable.WeakKey(key, queue, this, null);
/*     */       
/*     */ 
/* 183 */       this.hashCode = key.hashCode();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/* 189 */       boolean result = false;
/* 190 */       if ((o instanceof Referenced)) {
/* 191 */         Referenced otherKey = (Referenced)o;
/* 192 */         Object thisKeyValue = getValue();
/* 193 */         Object otherKeyValue = otherKey.getValue();
/* 194 */         if (thisKeyValue == null) {
/* 195 */           result = otherKeyValue == null;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 203 */           result = (result) && (hashCode() == otherKey.hashCode());
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 209 */           result = thisKeyValue.equals(otherKeyValue);
/*     */         }
/*     */       }
/* 212 */       return result;
/*     */     }
/*     */     
/*     */     private Object getValue() {
/* 216 */       return this.reference.get();
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 221 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class WeakKey
/*     */     extends WeakReference
/*     */   {
/*     */     private final WeakHashtable.Referenced referenced;
/*     */     
/*     */ 
/*     */     private WeakKey(Object key, ReferenceQueue queue, WeakHashtable.Referenced referenced)
/*     */     {
/* 235 */       super(queue);
/* 236 */       this.referenced = referenced;
/*     */     }
/*     */     
/*     */     private WeakHashtable.Referenced getReferenced() {
/* 240 */       return this.referenced;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */   private final ReferenceQueue<?> queue = new ReferenceQueue();
/*     */   
/*     */ 
/* 262 */   private int changeCount = 0;
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
/*     */   public boolean containsKey(Object key)
/*     */   {
/* 277 */     Referenced referenced = new Referenced(key, null);
/* 278 */     return super.containsKey(referenced);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration elements()
/*     */   {
/* 286 */     purge();
/* 287 */     return super.elements();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set entrySet()
/*     */   {
/* 295 */     purge();
/* 296 */     Set referencedEntries = super.entrySet();
/* 297 */     Set unreferencedEntries = new HashSet();
/* 298 */     for (Iterator it = referencedEntries.iterator(); it.hasNext();) {
/* 299 */       Map.Entry entry = (Map.Entry)it.next();
/* 300 */       Referenced referencedKey = (Referenced)entry.getKey();
/* 301 */       Object key = referencedKey.getValue();
/* 302 */       Object value = entry.getValue();
/* 303 */       if (key != null) {
/* 304 */         Entry dereferencedEntry = new Entry(key, value, null);
/* 305 */         unreferencedEntries.add(dereferencedEntry);
/*     */       }
/*     */     }
/* 308 */     return unreferencedEntries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object get(Object key)
/*     */   {
/* 317 */     Referenced referenceKey = new Referenced(key, null);
/* 318 */     return super.get(referenceKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 326 */     purge();
/* 327 */     return super.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration keys()
/*     */   {
/* 335 */     purge();
/* 336 */     final Enumeration enumer = super.keys();
/* 337 */     new Enumeration()
/*     */     {
/*     */       public boolean hasMoreElements() {
/* 340 */         return enumer.hasMoreElements();
/*     */       }
/*     */       
/*     */       public Object nextElement()
/*     */       {
/* 345 */         WeakHashtable.Referenced nextReference = (WeakHashtable.Referenced)enumer.nextElement();
/* 346 */         return WeakHashtable.Referenced.access$1(nextReference);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set keySet()
/*     */   {
/* 356 */     purge();
/* 357 */     Set referencedKeys = super.keySet();
/* 358 */     Set unreferencedKeys = new HashSet();
/* 359 */     for (Iterator it = referencedKeys.iterator(); it.hasNext();) {
/* 360 */       Referenced referenceKey = (Referenced)it.next();
/* 361 */       Object keyValue = referenceKey.getValue();
/* 362 */       if (keyValue != null) {
/* 363 */         unreferencedKeys.add(keyValue);
/*     */       }
/*     */     }
/* 366 */     return unreferencedKeys;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void purge()
/*     */   {
/* 373 */     List toRemove = new ArrayList();
/* 374 */     synchronized (this.queue) {
/*     */       WeakKey key;
/* 376 */       while ((key = (WeakKey)this.queue.poll()) != null) { WeakKey key;
/* 377 */         toRemove.add(key.getReferenced());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 384 */     int size = toRemove.size();
/* 385 */     for (int i = 0; i < size; i++) {
/* 386 */       super.remove(toRemove.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void purgeOne()
/*     */   {
/* 394 */     synchronized (this.queue) {
/* 395 */       WeakKey key = (WeakKey)this.queue.poll();
/* 396 */       if (key != null) {
/* 397 */         super.remove(key.getReferenced());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Object put(Object key, Object value)
/*     */   {
/* 408 */     if (key == null) {
/* 409 */       throw new NullPointerException("Null keys are not allowed");
/*     */     }
/* 411 */     if (value == null) {
/* 412 */       throw new NullPointerException("Null values are not allowed");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 417 */     if (this.changeCount++ > 100) {
/* 418 */       purge();
/* 419 */       this.changeCount = 0;
/*     */ 
/*     */     }
/* 422 */     else if (this.changeCount % 10 == 0) {
/* 423 */       purgeOne();
/*     */     }
/*     */     
/* 426 */     Referenced keyRef = new Referenced(key, this.queue, null);
/* 427 */     return super.put(keyRef, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void putAll(Map t)
/*     */   {
/* 435 */     if (t != null) {
/* 436 */       Set entrySet = t.entrySet();
/* 437 */       for (Iterator it = entrySet.iterator(); it.hasNext();) {
/* 438 */         Map.Entry entry = (Map.Entry)it.next();
/* 439 */         put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void rehash()
/*     */   {
/* 450 */     purge();
/* 451 */     super.rehash();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Object remove(Object key)
/*     */   {
/* 461 */     if (this.changeCount++ > 100) {
/* 462 */       purge();
/* 463 */       this.changeCount = 0;
/*     */ 
/*     */     }
/* 466 */     else if (this.changeCount % 10 == 0) {
/* 467 */       purgeOne();
/*     */     }
/* 469 */     return super.remove(new Referenced(key, null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 477 */     purge();
/* 478 */     return super.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 486 */     purge();
/* 487 */     return super.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection values()
/*     */   {
/* 495 */     purge();
/* 496 */     return super.values();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\WeakHashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */