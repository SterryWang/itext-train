package com.example.demoitext;

import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ImageStampUtils {

	private static final Logger log = LoggerFactory.getLogger(ImageStampUtils.class);

	public static void main(String[] args) throws Exception {

		// 获得pdf页数
		// int pdfPage = getPdfPage(filePath); // 指定将和 图片拼接的 PDF
		String src = "d:/demo-itext-manypages.pdf";
		String dest = "d:/picInPDf.pdf";
		String picPath = "d:/sign.jpg";
		addStamp(src, dest, picPath, 2, null, null, 50f);

	}

	/**
	 * 
	 * @param src
	 *            源文件地址
	 * @param dest
	 *            盖章后的文件保存地址
	 * @param picPath
	 *            待插入的图片地址
	 * @param pageNumToAdd
	 *            待插入图片的页码
	 * @param x
	 *            图片位于页面横向位置（以页宽比例表示0-1，从左边算起），不输入有默认值
	 * @param y
	 *            图片位于页面纵向位置（以页高比例表示0-1，从页底算起），不输入有默认值
	 * @param scalePercentage
	 *            图片缩放比例，不输入有默认值
	 * @throws Exception
	 */
	public static void addStamp(String src, String dest, String picPath, Integer pageNumToAdd, Double x, Double y,
			Float scalePercentage) throws Exception {

		// 获取第一页宽和高
		PdfReader pdfreader = new PdfReader(src);

		Rectangle pageSize = pdfreader.getPageSize(1);// 获取第一页的pageSize
		int totalPageNum = pdfreader.getNumberOfPages();
		// 获取页面宽度
		float width = pageSize.getWidth();
		// 获取页面高度
		float height = pageSize.getHeight();

		System.out.println("width = " + width + ", height = " + height);

		PdfStamper stamper = null;

		try {

			stamper = new PdfStamper(pdfreader, new FileOutputStream(dest));// 生成的PDF 路径 outPath
			PdfContentByte overContent = null;
			if (pageNumToAdd == null) {
				overContent = stamper.getOverContent(1);

			} else if (pageNumToAdd != null && (pageNumToAdd <= 0 || pageNumToAdd > totalPageNum)) {
				throw new Exception("invalid page number!");
			} else {
				overContent = stamper.getOverContent(pageNumToAdd);
			}

			// 添加图片
			// Image image = Image.getInstance(picPath);// 图片名称
		
			Image image =loadPic(picPath);

			if (x == null || y == null) {
				// 任意一个坐标不输入都使用默认位置
				image.setAbsolutePosition((int) (width * 0.7), (int) (height * 0.85));
			} else {
				image.setAbsolutePosition((int) (width * x), (int) (height * y));

			}
			// 如果不设定图片缩放比例，则固定图片大小
			if (scalePercentage != null) {
				image.scalePercent(scalePercentage);
			} else {
				image.scaleToFit(100, 100);
			}

			overContent.addImage(image);
			overContent.stroke();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != stamper) {
					stamper.close();
				}
				if (pdfreader != null) {
					pdfreader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}

	}

	public static Image loadPic(String picPath) throws Exception {
		Image image = null;
		if (picPath == null) {
			throw new Exception("图片路径不可以为空！");

		} else if (picPath.indexOf("classpath") == 0) {
			// 使用了classpath类路径
			System.out.println("使用了classpath路径");
			log.info("使用了classpath路径");
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource resource = resolver.getResource(picPath);
			System.out.println("通过相对路径获得的图片的绝对路径为：" + resource.getURI().toString());
			System.out.println("通过相对路径获得的图片的绝对路径为：" + resource.getURL().toString());
			image = Image.getInstance(resource.getURL());

		} else {
			log.info("使用了绝对路径！");
			image = Image.getInstance(picPath);

		}
		return image;

	}

}
