package ro.mycode.onlineclinicapi.PDFGenerator;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ro.mycode.onlineclinicapi.models.Permission;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
public class PermissionPDF {

    List<Permission> permissionList;

    public PermissionPDF(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.white);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setColor(Color.white);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Title",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Module",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Role",font));
        table.addCell(cell);


    }

    public void writeTableData(PdfPTable table){
        for(Permission permission: permissionList){
            table.addCell(String.valueOf(permission.getId()));
            table.addCell(permission.getTitle());
            table.addCell(permission.getModule());
            table.addCell(permission.getDescription());
            table.addCell(String.valueOf(permission.getRole()));
        }
    }

    public void generate(HttpServletResponse response) throws Exception{

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.black);

        Paragraph paragraph = new Paragraph("Permission details",fontTitle);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(5);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{1,3,2,1,2});
        table.setSpacingBefore(6);

        writeTableHeader(table);
        writeTableData(table);

        document.add(paragraph);
        document.add(table);

        document.close();

    }
}
