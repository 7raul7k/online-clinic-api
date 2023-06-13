package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Clinic;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;




public class ClinicPDF {

    Clinic clinic;

    public ClinicPDF(Clinic clinic) {
        this.clinic = clinic;
    }

    public void writeTableHeader(PdfPTable table){

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.cyan);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Type",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Place",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Adress",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description",font));
        table.addCell(cell);


    }

    public void writeTableData(PdfPTable table){
        table.addCell(String.valueOf(clinic.getId()));
        table.addCell(clinic.getName());
        table.addCell(clinic.getType());
        table.addCell(clinic.getPlace());
        table.addCell(clinic.getAddress());
        table.addCell(clinic.getDescription());
    }

    public void generate(HttpServletResponse response) throws Exception{
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Clinic Details",fontTitle);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{1,3,2,1,3,2});
        table.setSpacingBefore(6);

        writeTableHeader(table);
        writeTableData(table);

        document.add(paragraph);

        document.add(table);

        document.close();
    }
}
