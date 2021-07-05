package heuristics.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ExportToPDF {

	private List<FinalHeuristic> fHList;

	private void writeTableHeader(PdfPTable table)	{
		PdfPCell cell = new PdfPCell();
		
		cell.setBackgroundColor(Color.BLUE);
		cell.setPaddingRight(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Yes", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("No", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("N/A", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Heuristic description", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table) {
		PdfPCell blanckCell = new PdfPCell();
		blanckCell.setPhrase(new Phrase(""));
		blanckCell.setMinimumHeight(40f);

		for (FinalHeuristic fH : fHList) {
			table.addCell(blanckCell);
			table.addCell(blanckCell);
			table.addCell(blanckCell);
			table.addCell(fH.getHeuristicString());
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.BLUE);
		font.setSize(18);
		
		Paragraph title = new Paragraph("List of heuristics", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] {0.75f, 0.75f, 0.75f, 7f});

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();


	}

}
