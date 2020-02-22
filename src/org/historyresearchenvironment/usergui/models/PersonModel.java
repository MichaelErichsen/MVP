/*      */ package org.historyresearchenvironment.usergui.models;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.util.Date;
/*      */ import java.util.logging.Logger;
/*      */ import org.historyresearchenvironment.tmg.h2.models.Dataset;
/*      */ import org.historyresearchenvironment.tmg.h2.models.Name;
/*      */ import org.historyresearchenvironment.tmg.h2.models.Person;
/*      */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PersonModel
/*      */   extends AbstractHreProvider
/*      */ {
/*      */   private int perNo;
/*      */   private String refer;
/*      */   private String sentence;
/*      */   private String sndxgvn;
/*      */   private String sndxsurn;
/*      */   private String srnamedisp;
/*      */   private String srnamesort;
/*      */   private String srtdate;
/*      */   private int styleid;
/*      */   private int surid;
/*      */   private String xprimary;
/*      */   private String adopted;
/*      */   private String anceInt;
/*      */   private String birthorder;
/*      */   private String descInt;
/*      */   private int dsid;
/*      */   private String datasetName;
/*      */   private int father;
/*      */   private String fatherName;
/*      */   private String motherName;
/*      */   private String flag1;
/*      */   private String flag2;
/*      */   private String flag3;
/*      */   private String flag4;
/*      */   private String flag5;
/*      */   private String flag6;
/*      */   private String flag7;
/*      */   private String flag8;
/*      */   private String flag9;
/*      */   private Date lastEdit;
/*      */   private String lastEditString;
/*      */   private String living;
/*      */   private int mother;
/*      */   private String multibirth;
/*      */   private String pbirth;
/*      */   private String pdeath;
/*      */   private int refId;
/*      */   private String reference;
/*      */   private int relate;
/*      */   private int relatefo;
/*      */   private String scbuff;
/*      */   private String sex;
/*      */   private int spoulast;
/*      */   private String spoulastName;
/*      */   private String tt;
/*      */   private int recno;
/*      */   private int altype;
/*      */   private String dsure;
/*      */   private String fsure;
/*      */   private int givid;
/*      */   private String gvnamesort;
/*      */   private String infg;
/*      */   private String infs;
/*      */   private String ispicked;
/*      */   private String ndate;
/*      */   private String nnote;
/*      */   private int nper;
/*      */   private String nsure;
/*      */   private int prefId;
/*      */   private String message;
/*      */   
/*      */   public PersonModel() {}
/*      */   
/*      */   public PersonModel(int perNo)
/*      */   {
/*   85 */     this.perNo = perNo;
/*   86 */     readFromH2(this.perNo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void close() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAdopted()
/*      */   {
/*   97 */     return this.adopted;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getAltype()
/*      */   {
/*  104 */     return this.altype;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAnceInt()
/*      */   {
/*  111 */     return this.anceInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getBirthorder()
/*      */   {
/*  118 */     return this.birthorder;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getDatasetName()
/*      */   {
/*  125 */     return this.datasetName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getDescInt()
/*      */   {
/*  132 */     return this.descInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getDsid()
/*      */   {
/*  139 */     return this.dsid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getDsure()
/*      */   {
/*  146 */     return this.dsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getFather()
/*      */   {
/*  153 */     return this.father;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFatherName()
/*      */   {
/*  160 */     return this.fatherName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag1()
/*      */   {
/*  167 */     return this.flag1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag2()
/*      */   {
/*  174 */     return this.flag2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag3()
/*      */   {
/*  181 */     return this.flag3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag4()
/*      */   {
/*  188 */     return this.flag4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag5()
/*      */   {
/*  195 */     return this.flag5;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag6()
/*      */   {
/*  202 */     return this.flag6;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag7()
/*      */   {
/*  209 */     return this.flag7;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag8()
/*      */   {
/*  216 */     return this.flag8;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFlag9()
/*      */   {
/*  223 */     return this.flag9;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getFsure()
/*      */   {
/*  230 */     return this.fsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getGivid()
/*      */   {
/*  237 */     return this.givid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getGvnamesort()
/*      */   {
/*  244 */     return this.gvnamesort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getInfg()
/*      */   {
/*  251 */     return this.infg;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getInfs()
/*      */   {
/*  258 */     return this.infs;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getIspicked()
/*      */   {
/*  265 */     return this.ispicked;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Date getLastEdit()
/*      */   {
/*  272 */     return this.lastEdit;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getLastEditString()
/*      */   {
/*  279 */     return this.lastEditString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getLiving()
/*      */   {
/*  286 */     return this.living;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getMessage()
/*      */   {
/*  293 */     return this.message;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getMother()
/*      */   {
/*  300 */     return this.mother;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getMotherName()
/*      */   {
/*  307 */     return this.motherName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getMultibirth()
/*      */   {
/*  314 */     return this.multibirth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getNdate()
/*      */   {
/*  321 */     return this.ndate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getNnote()
/*      */   {
/*  328 */     return this.nnote;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getNper()
/*      */   {
/*  335 */     return this.nper;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getNsure()
/*      */   {
/*  342 */     return this.nsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getPbirth()
/*      */   {
/*  349 */     return this.pbirth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getPdeath()
/*      */   {
/*  356 */     return this.pdeath;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getPerNo()
/*      */   {
/*  363 */     return this.perNo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getPrefId()
/*      */   {
/*  370 */     return this.prefId;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getRecno()
/*      */   {
/*  377 */     return this.recno;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getRefer()
/*      */   {
/*  384 */     return this.refer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getReference()
/*      */   {
/*  391 */     return this.reference;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getRefId()
/*      */   {
/*  398 */     return this.refId;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getRelate()
/*      */   {
/*  405 */     return this.relate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getRelatefo()
/*      */   {
/*  412 */     return this.relatefo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getScbuff()
/*      */   {
/*  419 */     return this.scbuff;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSentence()
/*      */   {
/*  426 */     return this.sentence;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSex()
/*      */   {
/*  433 */     return this.sex;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSndxgvn()
/*      */   {
/*  440 */     return this.sndxgvn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSndxsurn()
/*      */   {
/*  447 */     return this.sndxsurn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getSpoulast()
/*      */   {
/*  454 */     return this.spoulast;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSpoulastName()
/*      */   {
/*  461 */     return this.spoulastName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSrnamedisp()
/*      */   {
/*  468 */     return this.srnamedisp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSrnamesort()
/*      */   {
/*  475 */     return this.srnamesort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSrtdate()
/*      */   {
/*  482 */     return this.srtdate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getStyleid()
/*      */   {
/*  489 */     return this.styleid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getSurid()
/*      */   {
/*  496 */     return this.surid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getTt()
/*      */   {
/*  503 */     return this.tt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getXprimary()
/*      */   {
/*  510 */     return this.xprimary;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void readFromH2(int i)
/*      */   {
/*  518 */     String s = null;
/*  519 */     StringBuilder sb = new StringBuilder();
/*  520 */     Person p = null;
/*      */     
/*  522 */     setMessage("");
/*      */     try
/*      */     {
/*  525 */       p = new Person(this.conn, i);
/*      */       
/*  527 */       if (p.getPerNo() == 0) {
/*  528 */         setMessage("No person found");
/*  529 */         return;
/*      */       }
/*      */       
/*  532 */       Name n = new Name(this.conn, i);
/*      */       
/*  534 */       setSrnamedisp(n.getSrnamedisp());
/*  535 */       setFather(p.getFather());
/*  536 */       n = new Name(this.conn, p.getFather());
/*  537 */       setFatherName(n.getSrnamedisp());
/*  538 */       setMother(p.getMother());
/*  539 */       n = new Name(this.conn, p.getMother());
/*  540 */       setMotherName(n.getSrnamedisp());
/*      */       
/*  542 */       setLastEditString(p.getLastEdit().toString());
/*      */       
/*  544 */       Dataset ds = new Dataset(this.conn, p.getDsid());
/*  545 */       setDatasetName(ds.getDsname());
/*      */       
/*  547 */       n = new Name(this.conn, p.getSpoulast());
/*  548 */       setSpoulastName(n.getSrnamedisp());
/*      */       try
/*      */       {
/*  551 */         s = p.getPbirth().substring(1, 9);
/*  552 */         sb = new StringBuilder(s).insert(4, '-').insert(7, '-');
/*  553 */         setPbirth(sb.toString());
/*      */       } catch (Exception localException1) {
/*  555 */         setPbirth("");
/*      */       }
/*      */       try
/*      */       {
/*  559 */         s = p.getPdeath().substring(1, 9);
/*  560 */         sb = new StringBuilder(s).insert(4, '-').insert(7, '-');
/*  561 */         setPdeath(sb.toString());
/*      */       } catch (Exception localException2) {
/*  563 */         setPdeath("");
/*      */       }
/*      */       
/*  566 */       setSex(p.getSex());
/*      */       
/*  568 */       this.conn.close();
/*      */     } catch (Exception e) {
/*  570 */       e.printStackTrace();
/*  571 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAdopted(String adopted)
/*      */   {
/*  582 */     this.adopted = adopted;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAltype(int altype)
/*      */   {
/*  590 */     this.altype = altype;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAnceInt(String anceInt)
/*      */   {
/*  598 */     this.anceInt = anceInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBirthorder(String birthorder)
/*      */   {
/*  606 */     this.birthorder = birthorder;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDatasetName(String datasetName)
/*      */   {
/*  614 */     this.datasetName = datasetName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDescInt(String descInt)
/*      */   {
/*  622 */     this.descInt = descInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDsid(int dsid)
/*      */   {
/*  630 */     this.dsid = dsid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDsure(String dsure)
/*      */   {
/*  638 */     this.dsure = dsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFather(int father)
/*      */   {
/*  646 */     this.father = father;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFatherName(String fatherName)
/*      */   {
/*  654 */     this.fatherName = fatherName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag1(String flag1)
/*      */   {
/*  662 */     this.flag1 = flag1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag2(String flag2)
/*      */   {
/*  670 */     this.flag2 = flag2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag3(String flag3)
/*      */   {
/*  678 */     this.flag3 = flag3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag4(String flag4)
/*      */   {
/*  686 */     this.flag4 = flag4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag5(String flag5)
/*      */   {
/*  694 */     this.flag5 = flag5;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag6(String flag6)
/*      */   {
/*  702 */     this.flag6 = flag6;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag7(String flag7)
/*      */   {
/*  710 */     this.flag7 = flag7;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag8(String flag8)
/*      */   {
/*  718 */     this.flag8 = flag8;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFlag9(String flag9)
/*      */   {
/*  726 */     this.flag9 = flag9;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFsure(String fsure)
/*      */   {
/*  734 */     this.fsure = fsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGivid(int givid)
/*      */   {
/*  742 */     this.givid = givid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGvnamesort(String gvnamesort)
/*      */   {
/*  750 */     this.gvnamesort = gvnamesort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setInfg(String infg)
/*      */   {
/*  758 */     this.infg = infg;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setInfs(String infs)
/*      */   {
/*  766 */     this.infs = infs;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setIspicked(String ispicked)
/*      */   {
/*  774 */     this.ispicked = ispicked;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLastEdit(Date lastEdit)
/*      */   {
/*  782 */     this.lastEdit = lastEdit;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLastEditString(String lastEditString)
/*      */   {
/*  790 */     this.lastEditString = lastEditString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLiving(String living)
/*      */   {
/*  798 */     this.living = living;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMessage(String message)
/*      */   {
/*  806 */     this.message = message;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMother(int mother)
/*      */   {
/*  814 */     this.mother = mother;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMotherName(String motherName)
/*      */   {
/*  822 */     this.motherName = motherName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMultibirth(String multibirth)
/*      */   {
/*  830 */     this.multibirth = multibirth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNdate(String ndate)
/*      */   {
/*  838 */     this.ndate = ndate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNnote(String nnote)
/*      */   {
/*  846 */     this.nnote = nnote;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNper(int nper)
/*      */   {
/*  854 */     this.nper = nper;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNsure(String nsure)
/*      */   {
/*  862 */     this.nsure = nsure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPbirth(String pbirth)
/*      */   {
/*  870 */     this.pbirth = pbirth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPdeath(String pdeath)
/*      */   {
/*  878 */     this.pdeath = pdeath;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPerNo(int perNo)
/*      */   {
/*  886 */     this.perNo = perNo;
/*  887 */     readFromH2(perNo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPrefId(int prefId)
/*      */   {
/*  895 */     this.prefId = prefId;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRecno(int recno)
/*      */   {
/*  903 */     this.recno = recno;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRefer(String refer)
/*      */   {
/*  911 */     this.refer = refer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setReference(String reference)
/*      */   {
/*  919 */     this.reference = reference;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRefId(int refId)
/*      */   {
/*  927 */     this.refId = refId;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRelate(int relate)
/*      */   {
/*  935 */     this.relate = relate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRelatefo(int relatefo)
/*      */   {
/*  943 */     this.relatefo = relatefo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setScbuff(String scbuff)
/*      */   {
/*  951 */     this.scbuff = scbuff;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSentence(String sentence)
/*      */   {
/*  959 */     this.sentence = sentence;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSex(String sex)
/*      */   {
/*  967 */     this.sex = sex;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSndxgvn(String sndxgvn)
/*      */   {
/*  975 */     this.sndxgvn = sndxgvn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSndxsurn(String sndxsurn)
/*      */   {
/*  983 */     this.sndxsurn = sndxsurn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSpoulast(int spoulast)
/*      */   {
/*  991 */     this.spoulast = spoulast;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSpoulastName(String spoulastName)
/*      */   {
/*  999 */     this.spoulastName = spoulastName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSrnamedisp(String srnamedisp)
/*      */   {
/* 1007 */     this.srnamedisp = srnamedisp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSrnamesort(String srnamesort)
/*      */   {
/* 1015 */     this.srnamesort = srnamesort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSrtdate(String srtdate)
/*      */   {
/* 1023 */     this.srtdate = srtdate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStyleid(int styleid)
/*      */   {
/* 1031 */     this.styleid = styleid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSurid(int surid)
/*      */   {
/* 1039 */     this.surid = surid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTt(String tt)
/*      */   {
/* 1047 */     this.tt = tt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setXprimary(String xprimary)
/*      */   {
/* 1055 */     this.xprimary = xprimary;
/*      */   }
/*      */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */