package com.example.demoitext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SimplePdfGenerator {

	private static final String WINDOWS_FONTS = "C:\\Windows\\Fonts\\SIMSUN.TTC";
	private static final String LINUX_FONTS = "/usr/share/fonts/SIMSUN.TTC";
	/*
	 * private static final BaseColor BORDER_COLOR = new BaseColor(0, 0, 0); private
	 * static final BaseColor HCELL_BACKGROUNDCOLOR = new BaseColor(135, 135,
	 * 135);// 175, 238, 238 private static final BaseColor BCELL_NAME_COLOR = new
	 * BaseColor(65, 105, 225);// 72, 209, 204 private static final BaseColor
	 * BCELL_OPINION_BACKGROUNDCOLOR = new BaseColor(220, 220, 220); private static
	 * final float TABLE_BORDER_WIDTH = 0.7f;
	 */
	private static final float DEF_TITLE_FONT_SIZE = 15;
	private static final float DEF_SUBTITLE_FONT_SIZE = 11;
	private static final float DEF_COMMON_TEXT_FONT_SIZE = 9;
	private static final Rectangle DEF_PDF_PAGE_SIZE = PageSize.A4;

	private Document document;
	private Font titleFont;
	private Font subtitleFont;
	private Font commonTextFont;
	private PdfWriter pdfWriter;

	public void initPdfDoc(Rectangle pageSize, Float titleFontSize, Float subtitleFontSize, Float commonTextFontSize)
			throws Exception {
		// 第一步：创建一个文档实例 设置文档纸张为A4，文档排列方式为横向排列
		// 实现A4纸页面 并且纵向排列（不设置则为横向
		document = new Document(DEF_PDF_PAGE_SIZE);
		// 设置字体
		BaseFont bf = BaseFont.createFont(getChineseFont() + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 注意这里有一个,1
		titleFont = (titleFontSize == null) ? new Font(bf, DEF_TITLE_FONT_SIZE, Font.BOLD)
				: new Font(bf, titleFontSize, Font.BOLD);
		subtitleFont = (subtitleFontSize == null) ? new Font(bf, DEF_SUBTITLE_FONT_SIZE, Font.NORMAL)
				: new Font(bf, subtitleFontSize, Font.NORMAL);
		commonTextFont = (commonTextFontSize == null) ? new Font(bf, DEF_COMMON_TEXT_FONT_SIZE, Font.NORMAL)
				: new Font(bf, commonTextFontSize, Font.NORMAL);

	}

	public void openPdfWriteStream(String filePath) throws FileNotFoundException, DocumentException {
		pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filePath));
		document.open();
	}

	public void addTitle(String value) throws DocumentException {
		Paragraph title = new Paragraph(value, titleFont);

		title.setAlignment(Element.ALIGN_CENTER);

		document.add(title);
	}

	public void addSubTitle(String value, Integer horizonAlignment) throws DocumentException {
		Paragraph subtitle = new Paragraph(value, subtitleFont);
		if (horizonAlignment == null) {
			subtitle.setAlignment(Element.ALIGN_CENTER);
		} else {
			subtitle.setAlignment(horizonAlignment);
		}
		document.add(subtitle);

	}

	public void addCommonParagraph(String value, Integer horizonAlignment) throws DocumentException {
		Paragraph para = new Paragraph(value, commonTextFont);
		if (horizonAlignment != null)
			para.setAlignment(horizonAlignment);

		document.add(para);

	}

	public void addTable(PdfPTable table) throws DocumentException {
		document.add(table);
	}

	public void addImage() {
	}

	public void newPage() {
		document.newPage();
	}

	public void closeDocWriting() {
		// 关闭文档
		if (document != null) {
			document.close();
		}
		// 关闭书写流
		if (pdfWriter != null) {
			pdfWriter.close();
		}

	}

	private static String getChineseFont() {
		String font1 = WINDOWS_FONTS;
		// 判断系统类型，加载字体文件
		String osName = getOsName();
		if (osName.indexOf("linux") > -1) {
			font1 = LINUX_FONTS;
		}
		if (!new File(font1).exists()) {
			throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！" + font1);
		}
		return font1;
	}

	public static String getOsName() {
		java.util.Properties prop = System.getProperties();
		return prop.getProperty("os.name").toLowerCase();
	}

}
