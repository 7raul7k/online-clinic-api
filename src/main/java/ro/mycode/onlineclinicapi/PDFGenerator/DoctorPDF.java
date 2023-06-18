package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Doctor;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

public class DoctorPDF {

    private Doctor doctor;

    public DoctorPDF(Doctor doctor) {
        this.doctor = doctor;
    }

    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.cyan);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Full Name",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Mobile",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Adress",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Clinic",font));
        table.addCell(cell);
    }

    public void writeTableData(PdfPTable table){
        table.addCell(String.valueOf(doctor.getId()));
        table.addCell(doctor.getFullName());
        table.addCell(doctor.getMobile());
        table.addCell(doctor.getEmail());
        table.addCell(doctor.getAdress());
        table.addCell(String.valueOf(doctor.getClinic()));
    }

    public void generate(HttpServletResponse response) throws Exception{
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Doctor Detail",fontTitle);
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
