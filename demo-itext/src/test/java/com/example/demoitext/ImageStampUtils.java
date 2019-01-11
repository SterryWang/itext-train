package com.example.demoitext;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ImageStampUtils {
	public static void main(String[] args) {

		// 获得pdf页数
		//int pdfPage = getPdfPage(filePath); // 指定将和 图片拼接的 PDF

		// 获取第一页宽和高
		PdfReader pdfreader = new PdfReader("filepath");
		int pdfPage=pdfreader.getNumberOfPages();
		Rectangle pageSize=pdfreader.getPageSize(1);
		//Document document = new Document(pdfreader.getPageSize(1));
		// 获取页面宽度
		float width = pageSize.getWidth();
		// 获取页面高度
		float height = pageSize.getHeight();
		if (pdfreader != null)
			pdfreader.close();
		
		System.out.println("width = " + width + ", height = " + height);
		//String picturePath; // 图片路径


		PdfReader pdf = new PdfReader("filePath");
		PdfStamper stamper = null;

		try {

			stamper = new PdfStamper(pdf, new FileOutputStream("outPath"));// 生成的PDF 路径 outPath
			for (int i = 1; i <= pdfPage; i++) {
				PdfContentByte overContent = stamper.getOverContent(i);
				String picturePath = "/wordTemplate/" + "cutContract" + i + ".png";
				// 剪切图片
				File directory = new File("");// 参数为空
				String courseFile = directory.getCanonicalPath();
				DocUtil.cutPicture("png", courseFile + "\\src\\main\\webapp\\WEB-INF\\docPicture\\contract.png",
						113 / pdfPage * (i - 1), 0, 113 / pdfPage, 113, picturePath);
				// 添加图片
				Image image = Image.getInstance(picturePath);// 图片名称
				image.setAbsolutePosition((int) width - 113 / pdfPage, (int) height / 2);// 左边距、底边距
				overContent.addImage(image);
				overContent.stroke();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != stamper) {
					stamper.close();
				}
				if (pdf != null) {
					pdf.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	

}
