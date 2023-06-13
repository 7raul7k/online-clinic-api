package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Appointment;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AppointmentPDF {


    private List<Appointment> appointmentList;

    public AppointmentPDF( List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public void writeTableHeader(PdfPTable table){

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.cyan);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Number",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Type",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Doctor",font));
        table.addCell(cell);
    }

    public void writeTableDataAppointment(PdfPTable table){

        for(Appointment appointment : appointmentList){
            table.addCell(String.valueOf(appointment.getId()));
            table.addCell(appointment.getNumber());
            table.addCell(appointment.getType());
            table.addCell(appointment.getDescription());
            table.addCell(String.valueOf(appointment.getDate()));
            table.addCell(String.valueOf(appointment.getDoctor()));
        }
    }

    public void generate(HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document();

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Appointments",fontTitle);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable pdfPTable = new PdfPTable(6);

        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setWidths(new int[]{1,2,3,2,2,2});

        pdfPTable.setSpacingBefore(6);

        writeTableHeader(pdfPTable);
        writeTableDataAppointment(pdfPTable);

        document.add(paragraph);

        document.add(pdfPTable);

        document.close();
    }


}
