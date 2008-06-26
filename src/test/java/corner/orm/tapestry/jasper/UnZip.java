//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.jasper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import corner.demo.model.one.A;
import corner.util.BaseModelTestCase;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class UnZip extends BaseModelTestCase{
	
	//解压缩的文件
	String fileName = System.getProperty("user.dir") + "\\src\\test\\resources\\jasper\\Test.zip";
	
	//获得系统的临时目录路径
	String tmpdir = System.getProperty("java.io.tmpdir");
	
	/**
	 * @see com.bjmaxinfo.piano.model.BaseModelTestCase#getMappingDirectoryLocations()
	 */
	@Override
	protected String[] getMappingDirectoryLocations() {
		return new String[]{"classpath:/corner/demo/model/one"
							};
	}
	
	static final int BUFFER = 2048;
	
	
	/**
	 * 
	 */
	public void testUnZipBlobSingleFile(){
		A main = null;
		main = (A) this.load(A.class, "282828c1154a53a501154a54c4780001");

		InputStream bos = new ByteArrayInputStream(main.getBlobData());

		ZipInputStream zis = new ZipInputStream(bos);
		BufferedOutputStream dest = null;
		ZipEntry entry = null;
		
		String fileName = "Atest.jasper".toLowerCase();
		
		try {
			while ((entry = zis.getNextEntry()) != null) {
				
				String entryName = entry.getName().toLowerCase();
				
				if(entryName.endsWith(fileName)){
					System.out.println("Extracting: " + entry.getName() + "\t"
							+ entry.getSize() + "\t" + entry.getCompressedSize());
					
					int count;
					byte data[] = new byte[BUFFER];
	
					// write the files to the disk
					ByteArrayOutputStream fos = new ByteArrayOutputStream((int)entry.getSize());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					
					dest.flush();
					dest.close();
					
					byte [] b = fos.toByteArray();
					
					System.out.println("length  " + b.length);
				}
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
//	@Test(groups="model")
	public void testUnZipBlob(){
		A main = null;
//		main.setName("a");
//		
//		this.saveOrUpdate(main);
//		
//		this.flushAndClearSession();
		main = (A) this.load(A.class, "282828c1154a53a501154a54c4780001");

		InputStream bos = new ByteArrayInputStream(main.getBlobData());

		ZipInputStream zis = new ZipInputStream(bos);
		BufferedOutputStream dest = null;
		ZipEntry entry = null;
		try {
			while ((entry = zis.getNextEntry()) != null) {
				//会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(tmpdir + entry.getName()).mkdirs();
					continue;
				}
				
//				if(entry.getName().equals("Test/Atest.jasper")){
				
				
					System.out.println("Extracting: " + entry.getName() + "\t"
							+ entry.getSize() + "\t" + entry.getCompressedSize());
					
					int count;
					byte data[] = new byte[BUFFER];
	
					File file = new File(tmpdir + entry.getName());
					
					// write the files to the disk
					FileOutputStream fos = new FileOutputStream(file);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					
					dest.flush();
					dest.close();
				
//				}
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过文件测试
	 */
//	@Test
	public void testUnZip() {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.UnZipAction(zipFile);
	}

	/**
	 * 
	 * 处理需要解压缩的文件
	 */
	private void UnZipAction(ZipFile zipFile) {
		try {
			Enumeration emu = zipFile.entries();
			
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(tmpdir + entry.getName()).mkdirs();
					continue;
				}
				
				System.out.println("Extracting: " + entry.getName() + "\t"
						+ entry.getSize() + "\t" + entry.getCompressedSize());
				
				BufferedInputStream bis = new BufferedInputStream(zipFile
						.getInputStream(entry));
				File file = new File(tmpdir + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

				int count;
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}