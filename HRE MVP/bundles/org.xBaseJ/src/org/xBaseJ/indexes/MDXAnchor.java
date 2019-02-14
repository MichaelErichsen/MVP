package org.xBaseJ.indexes;
/**
 * xBaseJ - Java access to dBase files
 *<p>Copyright 1997-2014 - American Coders, LTD  - Raleigh NC USA
 *<p>All rights reserved
 *<p>Currently supports only dBase III format DBF, DBT and NDX files
 *<p>                        dBase IV format DBF, DBT, MDX and NDX files
*<p>American Coders, Ltd
*<br>P. O. Box 97462
*<br>Raleigh, NC  27615  USA
*<br>1-919-846-2014
*<br>http://www.americancoders.com
@author Joe McVerry, American Coders Ltd.
@Version 20140310
*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library Lesser General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
*/

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.xBaseJ.DBF;
import org.xBaseJ.Util;


public class MDXAnchor
  {

static final short BLOCKLENGTH = 512;

  byte filetype;    /* error if not MDXTYPE                 */
  byte lastreIndex[] = new byte[3];        /* last reIndex date                    */
  byte DBFname[] = new byte[16];        /* root name of associated .DBF         */
  short blocksize;        /* SET BLOCKSIZE value, minimum = 2     */
  short blockbytes;        /* block size in bytes                  */
  byte production;        /* 1 if production .MDX, else 0         */
  byte resrvd1[] = new byte[3];
  short indexes;        /* number of indexes in the file        */
  byte resrvd2[] = new byte[2];
  int nextAvailable;        /* page number of end of file           */
  int nextfreepage;        /* page number of next free block       */
  int freepages;        /* pages in next free block             */
  byte created[] = new byte[3];        /* file creation date                   */
  byte resrvd3;
  int something[] = new int[4];
  RandomAccessFile nfile;
  String knownName;




 public MDXAnchor(RandomAccessFile ifile)
  {
    nfile = ifile;
  }

 public void set(String Name)
  {

  filetype = 2;
  lastreIndex[0] = 0; //yyyy
  lastreIndex[1] = 0; //mm
  lastreIndex[2] = 0; //dd
  byte dname[];
  try {dname  = Name.substring(0,Math.min(16,Name.length())).getBytes(DBF.encodedType);}
  catch (UnsupportedEncodingException UEE){ dname  = Name.substring(0,Math.min(16,Name.length())).getBytes();}
  for (int x=0; x<dname.length; x++)
    DBFname[x] = dname[x];
  production = 1;
  nextAvailable = 4;
  blocksize = 2;
  blockbytes = (short) (blocksize * BLOCKLENGTH);
  indexes = 0;
  resrvd1[0] = 48;
  resrvd1[1] = 32;
  resrvd2[1] = 27;
  created[0] = 0; //yyyy
  created[1] = 0; //mm
  created[2] = 0; //dd
  something[0] = 25;
  something[1] = 1;
  knownName = new String(Name);
  }

 public void reset(RandomAccessFile ifile)
  {
  nfile = ifile;
  filetype = 2;
  lastreIndex[0] = 0; //yyyy
  lastreIndex[1] = 0; //mm
  lastreIndex[2] = 0; //dd
  nextAvailable = 4;
  created[0] = 0; //yyyy
  created[1] = 0; //mm
  created[2] = 0; //dd
  }
 public void read() throws IOException
  {
  nfile.seek(0);
  filetype = nfile.readByte();
  nfile.read(lastreIndex);
  nfile.read(DBFname);
  blocksize = nfile.readShort();
  blockbytes = nfile.readShort();
  production =  nfile.readByte();
  nfile.read(resrvd1);
  indexes = nfile.readShort();
  nfile.read(resrvd2);
  nextAvailable = nfile.readInt();
  nextfreepage = nfile.readInt();
  freepages = nfile.readInt();
  nfile.read(created);
  resrvd3 = nfile.readByte();
  something[0] = nfile.readInt();
  something[1] = nfile.readInt();
  something[2] = nfile.readInt();
  something[3] = nfile.readInt();
  redo_numbers();
  }

 public void write() throws IOException
  {
  redo_numbers();
  nfile.seek(0);
  nfile.writeByte(filetype);
  //1.0Date d = new Date();
  //1.0lastreIndex[0] = (byte) d.getYear();
  //1.0lastreIndex[1] = (byte) (d.getMonth() + 1);
  //1.0lastreIndex[2] = (byte) (d.getDay() + 1);

  Calendar d =  Calendar.getInstance();
  lastreIndex[0] = (byte) (d.get(Calendar.YEAR) - 1900);
  lastreIndex[1] = (byte) (d.get(Calendar.MONTH) + 1);
  lastreIndex[2] = (byte) (d.get(Calendar.DAY_OF_MONTH));

  nfile.write(lastreIndex);
  nfile.write(DBFname);
  nfile.writeShort(blocksize);
  nfile.writeShort(blockbytes);
  nfile.writeByte(production);
  nfile.write(resrvd1);
  nfile.writeShort(indexes);
  nfile.write(resrvd2);
  nfile.writeInt(nextAvailable);
  nfile.writeInt(nextfreepage);
  nfile.writeInt(freepages);
  nfile.write(created);
  nfile.writeByte(resrvd3);
  nfile.writeInt(something[0]);
  nfile.writeInt(something[1]);
  nfile.writeInt(something[2]);
  nfile.writeInt(something[3]);
  redo_numbers();

  }

public void redo_numbers()
{
   blocksize=Util.x86(blocksize);
   blockbytes=Util.x86(blockbytes);
   indexes=Util.x86(indexes);
   nextAvailable=Util.x86(nextAvailable);
   nextfreepage=Util.x86(nextfreepage);
   freepages=Util.x86(freepages);
}

public int get_nextavailable() { return nextAvailable; }

public void update_nextavailable()  throws IOException
{
nextAvailable += blocksize;
write();
}

public void reset_nextavailable()  throws IOException
{
nextAvailable -= blocksize;
write();
}

public int get_blocksize()
{
return  blocksize;
}
public int get_blockbytes()
{
return  blockbytes;
}

public static short getBLOCKLENGTH() {
	return BLOCKLENGTH;
}

public short getBlockbytes() {
	return blockbytes;
}

public short getBlocksize() {
	return blocksize;
}

public byte[] getCreated() {
	return created;
}

public byte[] getDBFname() {
	return DBFname;
}

public byte getFiletype() {
	return filetype;
}

public int getFreepages() {
	return freepages;
}

public void setIndexes(short ind) {
	indexes = ind;
}
public void addOneToIndexes() {
	indexes++;
}

public short getIndexes() {
	return indexes;
}

public String getKnownName() {
	return knownName;
}

public byte[] getLastreIndex() {
	return lastreIndex;
}

public int getnextAvailable() {
	return nextAvailable;
}

public int getNextfreepage() {
	return nextfreepage;
}

public RandomAccessFile getNfile() {
	return nfile;
}

public byte getProduction() {
	return production;
}


}

