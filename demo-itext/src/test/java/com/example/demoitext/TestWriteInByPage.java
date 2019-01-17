package com.example.demoitext;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;

public class TestWriteInByPage {
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
		Font commonTextFont = SimplePdfGenerator.getCommonTextFont();
		tableHeaderCells.add(tableGenerator.createCell("序号", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("合同编号", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("明细类型", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("金额", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("交易席位号", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("证件类型", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("证件号码", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("账号名称", commonTextFont));
		tableHeaderCells.add(tableGenerator.createCell("交易日期", commonTextFont));
		// 以行形式添加表头
		tableGenerator.addTHeadByRow(tableHeaderCells);

		int tradeSeatNo = 1000;
		int certNo = 143057910;
		int hetongNo = 370100000;
		// 先写第一页

		int entryCount = 0;
		int testTotalCount = 500;
		int restCount = testTotalCount;
		int pages = 0;
		boolean endFlag = true;
		pages++;
		while (restCount > 0) {
			// 每次循环添加一行内容
			if (entryCount >= 20) {
				System.out.println("大于20条，一页已经写完，需要翻页继续写！");
				endFlag = false;
				break;

			}

			tableGenerator.addTBodyCell(Integer.toString(entryCount + 1), commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(hetongNo++), commonTextFont);
			tableGenerator.addTBodyCell("收取", commonTextFont);
			tableGenerator.addTBodyCell("1.00", commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(tradeSeatNo++), commonTextFont);
			tableGenerator.addTBodyCell("营业执照", commonTextFont);
			tableGenerator.addTBodyCell(Integer.toString(certNo++), commonTextFont);
			tableGenerator.addTBodyCell("xxxx有限公司", commonTextFont);
			tableGenerator.addTBodyCell("2018-09-17 ", commonTextFont);
			entryCount++;
			restCount--;

		}
		pdfGenerator.addTable(tableGenerator.getTable());
		if (endFlag) {
			System.out.println("总共只有" + entryCount + "条，第一页已经全部写完，无需继续写");

		}

		// 添加表格到pdf
		if (endFlag == false) {
			int tempEntryCountThisPage = 0;
			pdfGenerator.newPage();
			pages++;
			PdfTableGenerator tableGenerator2 = null;
			tableGenerator2 = new PdfTableGenerator();
			tableGenerator2.initTable(9, null);// 生成一个9列的table
			do {

				// 下面开始制作表格,逐页写

				if (tempEntryCountThisPage == 30) {
					System.out.println("第" + pages + "页已经写完！");
					pdfGenerator.addTable(tableGenerator2.getTable());
					pdfGenerator.newPage();
					pages++;
					System.out.println("翻页至第" + pages + "页！");
					tableGenerator2 = new PdfTableGenerator();
					tableGenerator2.initTable(9, null);// 生成一个9列的table
					tempEntryCountThisPage = 0;

				}

				tableGenerator2.addTBodyCell(Integer.toString(entryCount + 1), commonTextFont);
				tableGenerator2.addTBodyCell(Integer.toString(hetongNo++), commonTextFont);
				tableGenerator2.addTBodyCell("收取", commonTextFont);
				tableGenerator2.addTBodyCell("1.00", commonTextFont);
				tableGenerator2.addTBodyCell(Integer.toString(tradeSeatNo++), commonTextFont);
				tableGenerator2.addTBodyCell("营业执照", commonTextFont);
				tableGenerator2.addTBodyCell(Integer.toString(certNo++), commonTextFont);
				tableGenerator2.addTBodyCell("xxxx有限公司", commonTextFont);
				tableGenerator2.addTBodyCell("2018-09-17 ", commonTextFont);
				restCount--;
				entryCount++;
				tempEntryCountThisPage++;

			} while (restCount > 0);
			// 不足一页的部分也要记得写完
			if (tempEntryCountThisPage > 0) {
				System.out.println("第" + pages + "页已经写完！");
				pdfGenerator.addTable(tableGenerator2.getTable());

			}

		}
		endFlag = true;
		System.out.println(testTotalCount + "条明细实际完成" + entryCount + "条，共写" + pages + "页！");
		// pdf制作完成，关闭写入流
		pdfGenerator.closeDocWriting();
		System.out.println("pdf写入完成！");
		// 给pdf盖个章,大工告成
		String src = noStampedPdfPath;
		String dest = stampedPdfPath;
		String picPath = "d:/sign.jpg";// 要插入的图章
		int pageToStamp = 1;// 在第一页盖章
		ImageStampUtils.addStamp(src, dest, picPath, pageToStamp, null, null, null);
		System.out.println("pdf盖章完成！");
		// 删除未盖章的文件
	}

}
