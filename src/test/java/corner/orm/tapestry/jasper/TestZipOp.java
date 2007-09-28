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

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author SonyMusic
 * 
 * 用于测试java.util.zip包压缩和解压缩文件zip文件的例子. 基于JUnit编写，包括两个test方法，和三个辅助方法.
 * 注意到使用过程中操作的全是流，所以不仅仅可以读写文件。这只是一个简单的例子.
 */
public class TestZipOp extends Assert{

	/**
	 * 测试解压缩功能. 将d:\\download\\test.zip文件解压到d:\\temp\\zipout目录下.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReadZip() throws Exception {
		// InputStream is=new BufferedInputStream(new FileInputStream());
		String baseDir = "d:\\temp\\zipout";
		ZipFile zfile = new ZipFile("d:\\temp\\Test.zip");
		System.out.println(zfile.getName());
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			// 从ZipFile中得到一个ZipEntry
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				System.out.println("Dir: " + ze.getName() + " skipped..");
				continue;
			}
			System.out.println("Extracting: " + ze.getName() + "\t"
					+ ze.getSize() + "\t" + ze.getCompressedSize());

			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(baseDir, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			System.out.println("Extracted: " + ze.getName());
		}
		zfile.close();
	}
	
	
	/**
	 * zip压缩功能测试. 将d:\\temp\\zipout目录下的所有文件连同子目录压缩到d:\\temp\\out.zip.
	 * 
	 * @throws Exception
	 */
//	@Test
	public void testCreateZip() throws Exception {
		// 压缩baseDir下所有文件，包括子目录
		String baseDir = "d:\\temp\\zipout";
		List fileList = getSubFiles(new File(baseDir));

		// 压缩文件名
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
				"d:\\temp\\out.zip"));

		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			System.out.print("Adding: " + f.getPath() + f.getName());

			// 创建一个ZipEntry，并设置Name和其它的一些属性
			ze = new ZipEntry(getAbsFileName(baseDir, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());

			// 将ZipEntry加到zos中，再写入实际的文件内容
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
			System.out.println("   done...");
		}
		zos.close();
	}


	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	private File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		// System.out.println(dirs.length);
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {
			ret.mkdirs();
		}
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else {
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		// File base=new File(baseDir);
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory()) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

}