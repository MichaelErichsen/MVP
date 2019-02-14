package org.xBaseJ;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Vector;

public class Message extends Object
{

   Vector<String> idVector;
   Vector<String> dataVector;

/**
 * creates a message class used by the client/server objects
*/

public Message()
 {
   idVector = new Vector<String>();
   dataVector = new Vector<String>();
 }

/**
 * creates a message class used by the client/server objects
 * @param InStream data input
 * @throws IOException
 *                          communication line error
 * @throws xBaseJException
 *                          error conversing with server
*/

public Message(DataInputStream InStream) throws IOException, xBaseJException
{
   int dataLen, j, i;
   int waitLen;
   String inString;
   dataLen = 0;

   do {
   waitLen = InStream.available();
   try { Thread.sleep(100); }
   catch (InterruptedException e) { }
   finally { }
   } while (waitLen < 4);


   try { dataLen = InStream.readInt(); }
   catch (EOFException e) {
   System.out.println("caught a " + e.getMessage());
   }

   finally {   }

   byte DataIn[] = new byte[dataLen];
   waitLen = dataLen;

   InStream.readFully(DataIn, 0, dataLen);


   idVector = new Vector<String>();
   dataVector = new Vector<String>();


   for (i=0; i<dataLen; )
     {
        for (j=i;  j < dataLen && DataIn[j] != 0; j++);
        //1.0inString = new String(DataIn, 0, i, j-i);
        inString = new String(DataIn, i, j-i);
        idVector.addElement(inString);
        i = j + 1;
        for (j=i;  j < dataLen && DataIn[j] != 0; j++);
        //1.0 inString = new String(DataIn, 0, i, j-i);
        inString = new String(DataIn, i, j-i);
        dataVector.addElement(inString);
        i = j +1;
     }

   inString = (String) idVector.elementAt(0);
   if (inString.compareTo("Exception") == 0)
      {
         inString = (String) dataVector.elementAt(0);
         throw new xBaseJException(inString);
      }
   if (inString.compareTo("xBaseJException") == 0)
      {
         inString = (String) dataVector.elementAt(0);
         throw new xBaseJException(inString);
      }

}

/**
 * writes to the queue
 * @param OutStream data output
 * @throws IOException
 *                           communication line error
*/
public void write(DataOutputStream OutStream) throws IOException
{

   String tString;
   int i, outLength = 0;
   byte dataByteOut[];
   for (i = 0; i < idVector.size(); i++)
      {
          tString = (String) idVector.elementAt(i);
          outLength += tString.length();
          tString = (String) dataVector.elementAt(i);
          outLength += tString.length();
          outLength += 2;
      }


   OutStream.writeInt(outLength);

   for (i = 0; i < idVector.size(); i++)
      {

          tString = (String) idVector.elementAt(i);
          dataByteOut = new byte[tString.length()];

          //1.0 tString.getBytes(0, tString.length(), dataByteOut, 0);
          dataByteOut = tString.getBytes();

          //1.0tString.getBytes(0, tString.length(), dataByteOut, 0);
          dataByteOut = tString.getBytes();

          OutStream.write(dataByteOut, 0, tString.length());
          OutStream.writeByte(0);
          tString = (String) dataVector.elementAt(i);
          dataByteOut = new byte[tString.length()];

          //1.0 tString.getBytes(0, tString.length(), dataByteOut, 0);
          dataByteOut = tString.getBytes();

          OutStream.write(dataByteOut, 0, tString.length());
          OutStream.writeByte(0);
      }

   OutStream.flush();


}

/**
 * set header information
*/
public void setHeader(String ID, String DBFName)
 {
  if ( idVector.size() == 0 )
    {
     idVector.addElement(ID);
     dataVector.addElement(DBFName);
    }
  else
    {
     idVector.setElementAt(ID, 0);
     dataVector.setElementAt(DBFName, 0);
    }
  return;
 }

public void setField(String ID, String FieldData)
{
 int i;
 String idt;
 for (i = 1; i < idVector.size(); i++)
  {
      idt = (String) idVector.elementAt(i);
      if (idt.compareTo(ID) == 0)
          {
             dataVector.setElementAt(FieldData, i);
             return;
         }
  }
 idVector.addElement(ID);
 dataVector.addElement(FieldData);
 return;
}

public void setException(String ID, String FieldData)
{
 idVector.removeAllElements();
 dataVector.removeAllElements();
 idVector.addElement(ID);
 dataVector.addElement(FieldData);
 return;
}

public String getID(int i)
{
  return (String) idVector.elementAt(i);
}

public String getField(String ID) throws xBaseJException
{
 int i;
 String idt;
 for (i = 0; i < idVector.size(); i++)
  {
      idt = (String) idVector.elementAt(i);
      if (idt.compareTo(ID) == 0)
          {
             return (String) dataVector.elementAt(i);
         }
  }

 throw new xBaseJException("Field " + ID + " not found");

}

public String getField(int pos) throws xBaseJException
{
 return (String) dataVector.elementAt(pos);

}

public int getCount() {return dataVector.size();}

}


