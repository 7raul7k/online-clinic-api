package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.TestPatient;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;

public class TestPatientPDF {

    List<TestPatient> testPatientList;

    public TestPatientPDF(List<TestPatient> testPatientList) {
        this.testPatientList = testPatientList;
    }

    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.white);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Type",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Cost",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Report",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Patient",font));
        table.addCell(cell);
    }

    public void writeTableData(PdfPTable table){
        for(TestPatient testPatient : testPatientList){
            table.addCell(String.valueOf(testPatient.getId()));
            table.addCell(testPatient.getName());
            table.addCell(testPatient.getType());
            table.addCell(String.valueOf(testPatient.getCost()));
            table.addCell(testPatient.getReport());
            table.addCell(testPatient.getDescription());
            table.addCell(String.valueOf(testPatient.getPatient()));
}
    }

    public void generate(HttpServletResponse response) throws Exception {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Test Patient details",fontTitle);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{1,2,3,2,1,3});
        table.setSpacingBefore(6);

        writeTableHeader(table);
        writeTableData(table);

        document.add(paragraph);
        document.add(table);

        document.close();

    }
}
