package corner.orm.tapestry.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.hivemind.ApplicationRuntimeException;
import org.springframework.util.FileCopyUtils;
import org.testng.annotations.Test;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaUtilTest {
	ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
	
	@Test(invocationCount=100)
	public void test_jcaptch() throws IOException {
		Random r=new Random();
		String id = String.valueOf(r.nextLong());
		System.out.println(id);
		final BufferedImage image = imageCaptchaService.getImageChallengeForID(id);
		byte[] bytes=toImageBytes(getImageWriter(),image);
		FileCopyUtils.copy(bytes,new File("target/"+id+".jpg"));
		
	}
	@Test
	public void test_question(){
		String id="helloworld";
		String q=imageCaptchaService.getQuestionForID(id);
		System.out.println(q);
		
	}

	private ImageWriter getImageWriter() {
		String mimeType = "image/jpeg";
		Iterator writers = ImageIO.getImageWritersByMIMEType(mimeType);
		if (!writers.hasNext()) {
			throw new ApplicationRuntimeException(
					"Unable to find image writer for mime type " + mimeType
							+ ".");
		}
		return (ImageWriter) writers.next();
	}

	private byte[] toImageBytes(ImageWriter writer, BufferedImage image)
			throws IOException {
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final ImageOutputStream stream = ImageIO.createImageOutputStream(bout);
		writer.setOutput(stream);
		writer.write(image);
		bout.close();
		return bout.toByteArray();
	}
}
