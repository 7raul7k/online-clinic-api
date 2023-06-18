package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Patient;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

public class PatientPDF {


    public Patient patient;

    public PatientPDF(Patient patient) {
        this.patient = patient;
    }

    public void writeTableHeader(PdfPTable table){

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.cyan);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Full name",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Mobile",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Adress",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email",font));
        table.addCell(cell);
    }

    public void writeDataTable(PdfPTable table){
        table.addCell(String.valueOf(patient.getId()));
        table.addCell(patient.getFullName());
        table.addCell(patient.getNumber());
        table.addCell(patient.getAdress());
        table.addCell(patient.getEmail());
    }

    public void generate(HttpServletResponse response) throws Exception{
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Patient Details",fontTitle);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(5);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{1,3,2,1,2});
        table.setSpacingBefore(6);

        writeTableHeader(table);
        writeDataTable(table);

        document.add(paragraph);

        document.add(table);

        document.close();


    }
}
