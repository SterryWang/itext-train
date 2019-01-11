package com.example.demoitext;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfTableGenerator {

	// 默认表头灰色背景
	private static final BaseColor DEF_HCELL_BACKGROUNDCOLOR = new BaseColor(135, 135, 135);
	// 表格默认边框宽度
	private static final float DEF_TABLE_BORDER_WIDTH = 0.7f;
	// 表格边框颜色默认黑色
	private static final BaseColor DEF_BORDER_COLOR = new BaseColor(0, 0, 0);
	// 表格默认列宽
	private static final int DefaultColSpan = 1;

	private int ColumnsNum;
	private List<Integer> colSpans = new ArrayList<>();
	private PdfPTable table;
	private BaseColor borderColor = DEF_BORDER_COLOR;
	private BaseColor hCellBackgroundColor = DEF_HCELL_BACKGROUNDCOLOR;
	private float tableBorderWidth = DEF_TABLE_BORDER_WIDTH;
	

	public BaseColor getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(BaseColor borderColor) {
		this.borderColor = borderColor;
	}

	public BaseColor gethCellBackgroundColor() {
		return hCellBackgroundColor;
	}

	public void sethCellBackgroundColor(BaseColor hCellBackgroundColor) {
		this.hCellBackgroundColor = hCellBackgroundColor;
	}

	public float getTableBorderWidth() {
		return tableBorderWidth;
	}

	public void setTableBorderWidth(float tableBorderWidth) {
		this.tableBorderWidth = tableBorderWidth;
	}

	public void initTable(int columnCounts, ArrayList<Integer> colSpans) throws Exception {
		PdfPTable newtable = new PdfPTable(columnCounts);// 创建有columnsCounts列的pdf表格
		this.table = newtable;
		if (colSpans != null && colSpans.size() == columnCounts) {
			for (int integer : colSpans) {
				this.colSpans.add(integer);
			}
		} else if (colSpans.size() != columnCounts) {

			throw new Exception("colSpans setting  does not match columnCounts setting!");

		} else {
			for (int i = 0; i < columnCounts; i++) {
				this.colSpans.add(DefaultColSpan);
			}
		}

		this.ColumnsNum = columnCounts;

	}

	public void addTHeadByRow(List<PdfPCell> cells) throws Exception {

		if (ColumnsNum != cells.size()) {
			throw new Exception("Number of cells for this row  does not equal to  this table's column number!");
		}

		for (int i = 0; i < ColumnsNum; i++) {

			cells.get(i).setColspan(colSpans.get(i));
			formatTHeadCell(cells.get(i));

			table.addCell(cells.get(i));

		}

	}

	public void addTBodyByRow(List<PdfPCell> cells) throws Exception {

		if (ColumnsNum != cells.size()) {
			throw new Exception("Number of cells for this row  does not equal to  this table's column number!");
		}

		for (int i = 0; i < ColumnsNum; i++) {

			cells.get(i).setColspan(colSpans.get(i));
			formatTBodyCell(cells.get(i));

			table.addCell(cells.get(i));

		}

	}

	
	public PdfPCell createHCell(String content, Font contentFont) {
		PdfPCell cell = new PdfPCell(new Phrase((content), contentFont));

		return cell;
	}

	public PdfPCell createBodyCell(String content, Font contentFont) {
		PdfPCell cell;

		cell = new PdfPCell(new Phrase((content), contentFont));

		return cell;
	}

	/**
	 * 设定表头单元格格式
	 * 
	 * @param cell
	 * @return
	 */
	private void formatTHeadCell(PdfPCell cell) {

		cell.setBorderColor(borderColor);
		cell.setBackgroundColor(hCellBackgroundColor);
		cell.setBorderWidthTop(tableBorderWidth);
		cell.setBorderWidthBottom(tableBorderWidth);
		cell.setBorderWidthLeft(tableBorderWidth);
		cell.setBorderWidthRight(tableBorderWidth);
		//单元格内容默认居中
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);//
		cell.setVerticalAlignment(Element.ALIGN_CENTER);

	}

	/**
	 * 设定表主体单元格格式
	 * 
	 * @param cell
	 * @return
	 */
	private void formatTBodyCell(PdfPCell cell) {
		cell.setBorderColor(borderColor);
		cell.setBorderWidthTop(tableBorderWidth);
		cell.setBorderWidthBottom(tableBorderWidth);
		cell.setBorderWidthLeft(tableBorderWidth);
		cell.setBorderWidthRight(tableBorderWidth);
		//单元格内容默认居中
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);

	}

}