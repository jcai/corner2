//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.TestCase;

public class TapestryHtmlFormatterTest extends TestCase {
	public void testVoid(){
	
	}
	/*
	 * Test method for 'corner.util.TapestryHtmlFormatter.format(InputStream)'
	 */
//	public void testFormat() throws IOException {
//		String src="<input name=\"test\"/>";
//		String expectStr="<input name=\"test\" jwcid=\"testField\"/>";
//		byte [] content=src.getBytes();
//		ByteArrayInputStream in=new ByteArrayInputStream(content);
//		StringBuffer sb=TapestryHtmlFormatter.format(in);
//		System.out.println("'"+expectStr+"'");
//		System.out.println("'"+sb.toString()+"'");
//				
//		assertEquals(expectStr,sb.toString());
//		
//	}
//	/*
//	 * Test method for 'corner.util.TapestryHtmlFormatter.format(InputStream)'
//	 */
//	public void testCorrectFormat() throws IOException {
//		String src="<input name=\"test\" jwcid=\"asdfField\" type=\"text\" />";
//		String expectStr="<input name=\"test\" jwcid=\"testField\" type=\"text\" />";
//		byte [] content=src.getBytes();
//		ByteArrayInputStream in=new ByteArrayInputStream(content);
//		StringBuffer sb=TapestryHtmlFormatter.format(in);	
//		System.out.println(sb.toString());
//		assertEquals(expectStr,sb.toString());
//	}
	/*public void testCreateFile() throws IOException{
		FileInputStream fi=new FileInputStream(new File("E:\\dev-projects\\poisoning\\poison-system\\context\\back\\MiFirstAidMeasure.html"));
		StringBuffer sb=TapestryHtmlFormatter.format(fi);
		
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("outfilename.html"));
	        out.write(sb.toString());
	        out.close();
	    } catch (IOException e) {
	    }
	}*/
//	public void testOutputFile() throws IOException{
//		StringBuffer sb=new StringBuffer();
//		FileInputStream fi=new FileInputStream(new File("/back/test.txt"));
//		BufferedReader in = null;
//		try {
//			in = new BufferedReader(new InputStreamReader(fi));
//			String str;
//			int j=0;
//			int id=0,parentId=0,level=0,lastLevel=0;
//			String value="";
//			int [] parents=new int[4];
//			for(int i=0;i<4;i++){
//				parents[i]=0;
//			}
//			sb.append("");
//			
//			while ((str = in.readLine()) != null) {
//				str=str.replace("\"","");
//				String[] array = str.split(";");
//				for(int i=0;i<array.length;i++){
//					id=Integer.parseInt(array[1]);
//					value=array[2];
//					level=Integer.parseInt(array[3]);
//				}
//				if(level==1){
//					if(sb.length()>0){
//						sb.append("parentNodes[1]._indentImage=8;");
//						sb.append("parentNodes[1]._loading=false;");
//						
//						write(sb,parents[1]+"");
//					}
//					sb=new StringBuffer();
//					sb.append("parentNodes[1]=d._rootNode._cns["+(j++)+"];\n");
//				}
//				if(level == 1){
//					
//				}
//				else if(level == lastLevel || level== 3)
//					sb.append("parentNodes["+(level-1)+"].addNode(new Node("+id+",\""+value+"\",\"#\","+level+"));\n");
//				else{
//					
//					sb.append("tmpNode=new Node("+id+",\""+value+"\",\"#\","+level+");\n");
//					sb.append("parentNodes["+(level-1)+"].addNode(tmpNode);\n");
//					sb.append("parentNodes["+(level)+"]=tmpNode;\n");
//				}
//				lastLevel=level;	
//				
//				//sb.append("districtNodes["+(j++)+"]  = new Node(\""+id+","+parents[level-1]+"|"+value+"|JavaScript:selectDistrict('"+value+"');\";\n");
//				parents[level]=id;
//				
//			}
//
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e1) {
//					//do noting
//				}
//		}
//		
//		
//		
//		
//		
//	}
	private void write(StringBuffer sb,String fileName){
		try {
	        BufferedWriter out1 = new BufferedWriter(new FileWriter("/back/"+fileName+".js"));
	        out1.write(sb.toString());
	        out1.close();
	    } catch (IOException e) {
	    }
	}
	private void process(String str, StringBuffer sb) {
		
		
	}
	

}
