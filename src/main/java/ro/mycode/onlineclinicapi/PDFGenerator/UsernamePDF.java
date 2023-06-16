package ro.mycode.onlineclinicapi.PDFGenerator;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Username;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;

public class UsernamePDF {

    public List<Username> usernameList;

    public UsernamePDF(List<Username> usernameList) {
        this.usernameList = usernameList;
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
        cell.setPhrase(new Phrase("Email",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Data",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Adress",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Role",font));
        table.addCell(cell);
    }

    public void writeDataHeader(PdfPTable table){
        for(Username username : usernameList){
            table.addCell(String.valueOf(username.getId()));
            table.addCell(username.getName());
            table.addCell(username.getEmail());
            table.addCell(String.valueOf(username.getDob()));
            table.addCell(username.getAddress());
            table.addCell(String.valueOf(username.getRole()));
        }
    }

    public void generate(HttpServletResponse response) throws Exception{
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Username details",fontTitle);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{1,3,2,3,1,2});
        table.setSpacingBefore(6);

        writeTableHeader(table);
        writeDataHeader(table);

        document.add(paragraph);
        document.add(table);

        document.close();

    }
}
