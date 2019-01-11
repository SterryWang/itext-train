package com.example.demoitext;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfProdTests {

	private static final String WINDOWS_FONTS = "C:\\Windows\\Fonts\\SIMSUN.TTC";
	private static final String LINUX_FONTS = "/usr/share/fonts/SIMSUN.TTC";
	private static final BaseColor BORDER_COLOR = new BaseColor(0, 0, 0);
	private static final BaseColor HCELL_BACKGROUNDCOLOR = new BaseColor(135, 135, 135);// 175, 238, 238
	private static final BaseColor BCELL_NAME_COLOR = new BaseColor(65, 105, 225);// 72, 209, 204
	private static final BaseColor BCELL_OPINION_BACKGROUNDCOLOR = new BaseColor(220, 220, 220);
	private static final float TABLE_BORDER_WIDTH = 0.7f;



	@Test
	public void simplePdfProdTest() throws Exception {
		// 第一步：创建一个文档实例 设置文档纸张为A4，文档排列方式为横向排列
		// 实现A4纸页面 并且纵向排列（不设置则为横向
		Document document = new Document(PageSize.A4);
		// 设置字体
		BaseFont bf = BaseFont.createFont(getChineseFont() + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 注意这里有一个,1
		Font titleFont = new Font(bf, 15, Font.BOLD);
		Font subTitleFont = new Font(bf, 11, Font.NORMAL);
		Font tableFont = new Font(bf, 9, Font.NORMAL);

		/*// 第二步：创建PdfWriter对象，设置pdf生成路径
		PdfWriter pdfWriter = PdfWriter.getInstance(document,
				new FileOutputStream("d:/demo-itext-" + System.currentTimeMillis() + ".pdf"));*/
		PdfWriter pdfWriter = PdfWriter.getInstance(document,
				new FileOutputStream("D:/demo-itext-1547122850094.pdf"));
		// 第三步：打开文档进行我们需要的操作

		// 第五步：在文档中添加内容
		/*document.addTitle("本凭证由资金监管系统提供");
		document.addCreationDate();*/
		document.open();
		document.newPage();
		// 第四步：创建第一页（如果只有一页的话，这一步可以省略）
		//document.newPage();

		// document.add(new Paragraph("my first pdf demo"));
       //设置标题格式
		Paragraph title = new Paragraph("交易市场收费明细凭证hahahaha\n\n", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);

		document.add(title);
		document.newPage();
		document.add(new Paragraph("回单类型：清单式回单   回单编号:11111111",subTitleFont));
		document.add(new Paragraph("交易币种：RMB 总笔数：10",subTitleFont));
		document.add(new Paragraph("查询区间：20190101-20190110",subTitleFont));
		
		document.add(new Paragraph("\n\n"));
		document.add(createTable(tableFont));
		

		// 关闭文档
		document.close();
		// 关闭书写流
		pdfWriter.close();

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
	
	
	public static PdfPTable createTable(Font font) throws Exception {
		PdfPTable table = new PdfPTable(9);// 创建一个有9列的表格
		createTHead(table, font);
		createTBody(table, font);
		return table;
	}
	public static void createTHead(PdfPTable table, Font font) throws Exception {
		table.addCell(getHCell("序号", font));
		table.addCell(getHCell("合同编号", font));
		table.addCell(getHCell("明细类型", font));
		table.addCell(getHCell("金额", font));
		table.addCell(getHCell("交易席位号", font));
		table.addCell(getHCell("证件类型", font));
		table.addCell(getHCell("证件号码", font));
		table.addCell(getHCell("账号名称", font));
		table.addCell(getHCell("交易日期", font));
		
	}

	public static PdfPCell getHCell(String name, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase((name), font));
		cell.setBorderColor(BORDER_COLOR);
		cell.setBackgroundColor(HCELL_BACKGROUNDCOLOR);
		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(1);
		return cell;
	}

	public static void createTBody(PdfPTable table, Font font) {
		Font nameFont = new Font(font);
		nameFont.setColor(BCELL_NAME_COLOR);
		int   tradeSeatNo=1000;
		int   certNo=143057910;
		int   hetongNo=370100000;
		for (int i = 0; i < 60; i++) {
			/*PdfPCell cell = new PdfPCell(new Phrase("张三", nameFont));
			cell.setBorderWidth(TABLE_BORDER_WIDTH);
			cell.setBorderColor(BORDER_COLOR);
			table.addCell(cell);*/
		    table.addCell(getBCommonCell(Integer.toString(i+1), font));
			table.addCell(getBCommonCell(Integer.toString(hetongNo++), font));//合同编号先空着
			table.addCell(getBCommonCell("收取", font));
			table.addCell(getBCommonCell("1.00", font));
			table.addCell(getBCommonCell(Integer.toString(tradeSeatNo++),font));//自动生成交易席位号
			table.addCell(getBCommonCell("营业执照", font));
			table.addCell(getBCommonCell(Integer.toString(certNo++), font));//自动生成证件号码
			table.addCell(getBCommonCell("xxxx有限公司", font));
			table.addCell(getBCommonCell("2018-09-17 ", font));
			
		}
	}

	public static PdfPCell getBCommonCell(String value, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(value, font));
		cell.setBorderColor(BORDER_COLOR);
		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(1);
		return cell;
	}
 
	public static PdfPCell getBOpinionCell(String value, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(value, font));
		cell.setBackgroundColor(BCELL_OPINION_BACKGROUNDCOLOR);
		cell.setBorderColor(BORDER_COLOR);
		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
		return cell;
	}



}
