package com.example.demoitext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
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

	private static Font titleFont;
	private static Font subtitleFont;
	private static Font commonTextFont;

	public static Font getTitleFont() {
		return titleFont;
	}

	public static void setTitleFont(Font titleFont) {
		SimplePdfGenerator.titleFont = titleFont;
	}

	public static Font getSubtitleFont() {
		return subtitleFont;
	}

	public static void setSubtitleFont(Font subtitleFont) {
		SimplePdfGenerator.subtitleFont = subtitleFont;
	}

	public static Font getCommonTextFont() {
		return commonTextFont;
	}

	public static void setCommonTextFont(Font commonTextFont) {
		SimplePdfGenerator.commonTextFont = commonTextFont;
	}

	private Document document;
	private PdfWriter pdfWriter;

	/**
	 * 初始化pdf文档
	 * 
	 * @param pageSize
	 *            页面格式，不输默认A4
	 * @param titleFontSize
	 *            大标题字体大小，不输取默认值
	 * @param subtitleFontSize
	 *            副标题字体大小，不输取默认值
	 * @param commonTextFontSize
	 *            正文内容字体大小，不输取默认值
	 * @throws Exception
	 */
	public void initPdfDoc(Rectangle pageSize, Float titleFontSize, Float subtitleFontSize, Float commonTextFontSize)
			throws Exception {
		// 第一步：创建一个文档实例 设置文档纸张为A4，文档排列方式为横向排列
		// 实现A4纸页面 并且纵向排列（不设置则为横向

		document = (pageSize == null) ? new Document(DEF_PDF_PAGE_SIZE) : new Document(pageSize);

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

	/**
	 * 添加普通文字段落或行
	 * 
	 * @param value
	 *            添加的内容
	 * @param horizonAlignment
	 *            水平位置
	 * @throws DocumentException
	 */
	public void addCommonParagraph(String value, Integer horizonAlignment) throws DocumentException {
		Paragraph para = new Paragraph(value, commonTextFont);
		if (horizonAlignment != null)
			para.setAlignment(horizonAlignment);

		document.add(para);

	}

	public void addTable(PdfPTable table) throws DocumentException {
		document.add(table);
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

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SimplePdfGenerator pdfGenerator = new SimplePdfGenerator();
		pdfGenerator.initPdfDoc(null, null, null, null);
		// 打开写入流
		String noStampedPdfPath = "d:/no-stamped.pdf";
		String stampedPdfPath = "d:/stamped.pdf";
		pdfGenerator.openPdfWriteStream(noStampedPdfPath);
		// 添加标题
		pdfGenerator.addTitle("交易市场收费明细凭证hahahaha\n\n");
		// 添加普通文字
		pdfGenerator.addCommonParagraph("回单类型：清单式回单   回单编号:11111111", null);
		pdfGenerator.addCommonParagraph("交易币种：RMB 总笔数：10", null);
		// 写完这行，后面要插入表格了，所以后面空出两行
		pdfGenerator.addCommonParagraph("查询区间：20190101-20190110\n\n", null);
		// 下面开始制作表格
		PdfTableGenerator tableGenerator = new PdfTableGenerator();
		tableGenerator.initTable(9, null);// 生成一个9列的table
		// 创建表头
		List<PdfPCell> tableHeaderCells = new ArrayList<PdfPCell>(9);
		tableHeaderCells.add(tableGenerator.createCell("序号", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("合同编号", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("明细类型", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("金额", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("交易席位号", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("证件类型", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("证件号码", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("账号名称", SimplePdfGenerator.commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("交易日期", SimplePdfGenerator.commonTextFont));
		// 以行形式添加表头
		tableGenerator.addTHeadByRow(tableHeaderCells);

		int tradeSeatNo = 1000;
		int certNo = 143057910;
		int hetongNo = 370100000;
		// 写个60行吧
		for (int i = 0; i < 60; i++) {
			// 每次循环添加一行内容
			

			tableGenerator.addTBodyCell(Integer.toString(i + 1), commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(hetongNo++), commonTextFont);
			tableGenerator.addTBodyCell("收取", commonTextFont);
			tableGenerator.addTBodyCell("1.00", commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(tradeSeatNo++), commonTextFont);
			tableGenerator.addTBodyCell("营业执照", commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(certNo++), commonTextFont);
			tableGenerator.addTBodyCell("xxxx有限公司", commonTextFont);
			tableGenerator.addTBodyCell("2018-09-17 ", commonTextFont);

		}

		// 添加表格到pdf
		pdfGenerator.addTable(tableGenerator.getTable());
		// pdf制作完成，关闭写入流
		pdfGenerator.closeDocWriting();
		System.out.println("pdf写入完成！");
		// 给pdf盖个章,大工告成
		String src = noStampedPdfPath;
		String dest = stampedPdfPath;
		String picPath = "d:/sign.jpg";// 要插入的图章
		int pageToStamp = 1;// 在第一页盖章
		ImageStampUtils.addStamp(src, dest, picPath, pageToStamp, null, null,null);
		System.out.println("pdf盖章完成！");

	}

}
