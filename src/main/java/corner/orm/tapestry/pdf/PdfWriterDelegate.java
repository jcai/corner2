/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-10
 */

package corner.orm.tapestry.pdf;

import org.apache.tapestry.NestedMarkupWriter;
import org.apache.tapestry.engine.NullWriter;
import org.apache.tapestry.markup.Attribute;

import com.lowagie.text.pdf.PdfWriter;

/**
 * 一个包装了PdfWriter的代理类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfWriterDelegate extends NullWriter{
	/** pdfwriter **/
	private PdfWriter _writer;

	public PdfWriterDelegate(PdfWriter writer){
		_writer=writer;
	}

	public PdfWriter getPdfWriter(){
		return this._writer;
	}
	@Override
	public void appendAttribute(String name, int value) {
		
		
	}

	@Override
	public void appendAttribute(String name, boolean value) {
		
		
	}

	@Override
	public void appendAttribute(String name, String value) {
		
		
	}

	@Override
	public void appendAttributeRaw(String name, String value) {
		
		
	}

	@Override
	public void attribute(String name, int value) {
		
		
	}

	@Override
	public void attribute(String name, boolean value) {
		
		
	}

	@Override
	public void attribute(String name, String value) {
		
		
	}

	@Override
	public void attributeRaw(String name, String value) {
		
		
	}

	@Override
	public void begin(String name) {
		
		
	}

	@Override
	public void beginEmpty(String name) {
		
		
	}

	@Override
	public boolean checkError() {
		
		return false;
	}

	@Override
	public void clearAttributes() {
		
		
	}

	@Override
	public void close() {
		
		
	}

	@Override
	public void closeTag() {
		
		
	}

	@Override
	public void comment(String value) {
		
		
	}

	@Override
	public void end() {
		
		
	}

	@Override
	public void end(String name) {
		
		
	}

	@Override
	public void flush() {
		
		
	}

	@Override
	public Attribute getAttribute(String name) {
		
		return null;
	}

	@Override
	public String getContentType() {
		
		return null;
	}

	@Override
	public NestedMarkupWriter getNestedWriter() {
		
		return null;
	}

	@Override
	public boolean hasAttribute(String name) {
		
		return false;
	}

	@Override
	public void print(char value) {
		
		
	}

	@Override
	public void print(int value) {
		
		
	}

	@Override
	public void print(String value) {
		
		
	}

	@Override
	public void print(String value, boolean raw) {
		
		
	}

	@Override
	public void print(char[] data, int offset, int length) {
		
		
	}

	@Override
	public void print(char[] data, int offset, int length, boolean raw) {
		
		
	}

	@Override
	public void printRaw(String value) {
		
		
	}

	@Override
	public void printRaw(char[] buffer, int offset, int length) {
		
		
	}

	@Override
	public void println() {
		
		
	}

	@Override
	public Attribute removeAttribute(String name) {
		
		return null;
	}

}
