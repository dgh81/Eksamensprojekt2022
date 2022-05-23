package com.example.eksamensprojekt2022;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt2022.Objeckts.Inspection;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.QuestionGroup;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CreatePDF extends AppCompatActivity {
    ArrayList<Integer> answers;
    int index = 0;
    int questionGroupIndex = 0;
    Context context;
    MySQL mysql = new MySQL();
    int orderID;
    PdfFont freeSansFont;



    public void createPdf(Context context) throws IOException {
        this.context = context;
        answers = answersInList();
        InspectionInformation inspectionInfo = InspectionInformation.getInstance();
        orderID = inspectionInfo.getFk_projectID();
        ProjectInformation projectInfo = mysql.projectInfo(orderID);
        ArrayList<Inspection> inspections = mysql.getAllInspections(orderID);

        AssetManager am = context.getAssets();
        try (InputStream inStream = am.open("freesans.ttf")) {
            byte[] fontData = IOUtils.toByteArray(inStream);
            freeSansFont = PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H);
        }

        Table personalInfo = createTablePersonalInfo(projectInfo, inspectionInfo);
        Table questions = createTableQuestions();

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "sagsnummer_" + projectInfo.getProjectInformationID() + ".pdf");
        OutputStream outputstream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //handler der sætter header ind når der er sideskift
        TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
        //Tilføjet margin så headeren starter længere nede på siden
        float topMargin = 30 + handler.getTableHeight();
        document.setMargins(topMargin, 36, 36, 36);


        for (int i = 0; i < inspections.size(); i++) {
            System.out.println(inspections.get(i));
        }
        document.add(new Paragraph("Elinstallation - Vertifikation af mindre elinstallation").setFontSize(18f));
        document.add(personalInfo);

        ArrayList<String> questionsGenerelt = mysql.getQuestionsFromGroup(1);
        ArrayList<String> questionsTavlen = mysql.getQuestionsFromGroup(2);
        ArrayList<String> questionsInstallation = mysql.getQuestionsFromGroup(3);
        ArrayList<String> questionsIndbygningsarmaturer = mysql.getQuestionsFromGroup(4);
        ArrayList<String> questionsBeskyttelsesledere = mysql.getQuestionsFromGroup(5);
        ArrayList<String> questionsFejlbeskyttelse = mysql.getQuestionsFromGroup(6);
        ArrayList<QuestionGroup> questionGroupTitle = mysql.getQuestionGroupTitle();

        answerOptions(questions);

        createQuestions(questions, questionGroupTitle, questionsGenerelt);
        createQuestions(questions, questionGroupTitle, questionsTavlen);
        createQuestions(questions, questionGroupTitle, questionsInstallation);
        createQuestions(questions, questionGroupTitle, questionsIndbygningsarmaturer);
        createQuestions(questions, questionGroupTitle, questionsBeskyttelsesledere);
        createQuestions(questions, questionGroupTitle, questionsFejlbeskyttelse);

        document.add(questions);

        document.add(new AreaBreak());
        document.add(new Paragraph("Måleresultater").setFontSize(18f).setPaddingTop(5f));

        document.add(createTableKredsdetaljer(5));
        document.add(createTableAfprovning(5));
        document.add(createTableKortslutning(5));
        document.add(createTableBemaerkninger());
        document.add(new AreaBreak());


        document.close();
        Toast.makeText(context, "Pdf Created", Toast.LENGTH_LONG).show();
        
    }

    private class TableHeaderEventHandler implements IEventHandler {
        private Table table;
        private float tableHeight;
        private Document doc;

        public TableHeaderEventHandler(Document doc) {
            this.doc = doc;
            initTable();

            TableRenderer renderer = (TableRenderer) table.createRendererSubTree();
            renderer.setParent(new DocumentRenderer(doc));

            // Simulate the positioning of the renderer to find out how much space the header table will occupy.
            LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
            tableHeight = result.getOccupiedArea().getBBox().getHeight();
        }

        @Override
        public void handleEvent(Event currentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            float coordX = pageSize.getX() + doc.getLeftMargin();
            float coordY = pageSize.getTop() - doc.getTopMargin();
            float width = pageSize.getWidth() - doc.getRightMargin() - doc.getLeftMargin();
            float height = getTableHeight();
            Rectangle rect = new Rectangle(coordX, coordY, width, height);


            new Canvas(canvas, pdfDoc, rect)
                    .add(createHeader(pageNum))
                    .close();
        }

        public float getTableHeight() {
            return tableHeight;
        }

        private void initTable() {

            table = createHeader(1);;
            table.useAllAvailableWidth();
            table.setMarginBottom(15);

        }

        public Table createHeader(int page) {
            Drawable drawable = context.getDrawable(R.drawable.zealand);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image logo = new Image(imageData);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            logo.setHeight(54f);


            float[] columnWidth = {2000f, 2000f, 2000f};
            Table header = new Table(columnWidth);
            header.setHorizontalAlignment(HorizontalAlignment.CENTER);
            header.setTextAlignment(TextAlignment.CENTER);
            header.setPadding(0);

            header.addCell(new Cell(4,1).add(logo).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            header.addCell(new Cell(4,1).add(new Paragraph("Tjekliste").setFontSize(36f).setFontColor(ColorConstants.ORANGE).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM)));
            header.addCell(new Cell().add(new Paragraph("").setHeight(14f)));
            header.addCell(new Cell().add(new Paragraph("").setHeight(14f)));
            header.addCell(new Cell().add(new Paragraph("Side " + page /*+ " af " + total*/)));
            header.addCell(new Cell().add(new Paragraph("Elinstallation")));

            return header;
        }
    }

    public String getQuestionGroupTitle(ArrayList<QuestionGroup> group) {
        String result = group.get(questionGroupIndex).getQuestionGroupID() + ". " + group.get(questionGroupIndex).getTitle() + ":";
        questionGroupIndex++;
        return result;
    }

    public void answerOptions(Table table) {
        table.addCell(new Cell(2, 1).add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Ja")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Nej")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Ikke relevant")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

    }

    public void createQuestions(Table table, ArrayList<QuestionGroup> groupTitle, ArrayList<String> questions) {
        table.addCell(new Cell(1, 4).add(new Paragraph(getQuestionGroupTitle(groupTitle)).setBold()).setBorder(Border.NO_BORDER));


        for (int i = 0; i < questions.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(questions.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), table);
        }
        table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
    }

    public Table createHeader(int page) {
        Drawable drawable = context.getDrawable(R.drawable.zealand);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image logo = new Image(imageData);
        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
        logo.setHeight(54f);


        float[] columnWidth = {2000f, 2000f, 2000f};
        Table header = new Table(columnWidth);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        header.setPadding(0);

        header.addCell(new Cell(4,1).add(logo).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        header.addCell(new Cell(4,1).add(new Paragraph("Tjekliste").setFontSize(36f).setFontColor(ColorConstants.ORANGE).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM)));
        header.addCell(new Cell().add(new Paragraph("").setHeight(14f)));
        header.addCell(new Cell().add(new Paragraph("").setHeight(14f)));
        header.addCell(new Cell().add(new Paragraph("Side " + page + " af 3")));
        header.addCell(new Cell().add(new Paragraph("Elinstallation")));

        return header;
    }

    public Cell createCheckboxOn() {
        Table checkbox = new Table(1);
        checkbox.addCell(new Cell().add(createTick()).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

        return new Cell().add(checkbox.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER)).setBorder(Border.NO_BORDER);
    }

    public Cell createCheckboxOff() {
        Table checkboxOff = new Table(1);
        checkboxOff.setHeight(13f);
        checkboxOff.setWidth(13f);
        checkboxOff.addCell(new Cell());

        return new Cell().add(checkboxOff.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER)).setBorder(Border.NO_BORDER);
    }

    public Image createTick() {
        Drawable tickDrawable = context.getDrawable(R.drawable.tick);
        Bitmap tickBitmap = ((BitmapDrawable)tickDrawable).getBitmap();
        ByteArrayOutputStream tickStream = new ByteArrayOutputStream();
        tickBitmap.compress(Bitmap.CompressFormat.PNG, 100, tickStream);
        byte[] tickBitmapData = tickStream.toByteArray();

        ImageData tickImageData = ImageDataFactory.create(tickBitmapData);
        Image tick = new Image(tickImageData);
        tick.setHeight(8f);
        tick.setWidth(8f);
        tick.setHorizontalAlignment(HorizontalAlignment.CENTER);

        return tick;
    }

    public void createCheckbox(int answer, Table table) {
        if (answer == 1) {
            table.addCell(new Cell().add(createCheckboxOn()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
        } else if (answer == 2) {
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOn()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
        } else if (answer == 3) {
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOn()).setBorder(Border.NO_BORDER));
        } else {
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(createCheckboxOff()).setBorder(Border.NO_BORDER));
        }
        index++;
    }

    public ArrayList<Integer> answersInList() {
        ArrayList<Integer> answers = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            answers.add(randomNumber());
            System.out.println(randomNumber());
        }
        return answers;
    }

    public int randomNumber() {
        int max = 3;
        int min = 1;
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private Table createTablePersonalInfo(ProjectInformation projectInfo, InspectionInformation inspectionInfo) {
        float[] columnWidth1 = {150, 200, 200};
        Table personalInfo = new Table(columnWidth1);
        personalInfo.setFontSize(10f);
        personalInfo.setPadding(0);

        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Kundenavn: " + projectInfo.getCustomerName())));
        personalInfo.addCell(new Cell(1, 2).add(new Paragraph("Adresse: " + projectInfo.getCustomerAddress())));
        personalInfo.addCell(new Cell(1, 1).add(new Paragraph("Område: " + inspectionInfo.getRoomName())));
        personalInfo.addCell(new Cell().add(new Paragraph("Post nr: " + projectInfo.getCustomerPostalCode())));
        personalInfo.addCell(new Cell().add(new Paragraph("By: " + projectInfo.getCustomerCity())));
        personalInfo.addCell(new Cell().add(new Paragraph("Sagsnummer: " + projectInfo.getProjectInformationID())));
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Identifikation af installationen: " + projectInfo.getInstallationIdentification())));
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Installationen er udført af: " + projectInfo.getInstallationName())));
        personalInfo.addCell(new Cell(1, 2).add(new Paragraph("Verifikation af installationen er udført af: " + inspectionInfo.getInspectorName())));
        personalInfo.addCell(new Cell().add(new Paragraph("Dato: " + dateFormatted(inspectionInfo))));

        return personalInfo;
    }

    private String dateFormatted(InspectionInformation info) {
        String date = info.getInspectionDate().toString();
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8);

        return day + "-" + month + "-" + year;
    }

    private Table createTableQuestions() {
        float[] columnWidth2 = {450, 30, 30, 30};
        Table questions = new Table(columnWidth2);
        questions.setFontSize(10f);
        questions.setPadding(0);

        return questions;
    }

    public Table createTableKredsdetaljer(int rows) {
        float[] columnWidth = {150, 150, 150, 150, 150, 75, 75, 150};
        Table table = new Table(columnWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setFontSize(10f);
        table.setPadding(0);

        table.addCell(new Cell(1,8).add(new Paragraph("Kredsdetaljer").setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell().add(new Paragraph("Gruppe")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("OB (I")).add(new Text("n").setFontSize(6)).add(new Text(")"))));
        table.addCell(new Cell().add(new Paragraph("Karakteristik")));
        table.addCell(new Cell().add(new Paragraph("Tværsnit")));
        table.addCell(new Cell().add(new Paragraph("Maks. OB")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("Z")).add(new Text("S").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("R")).add(new Text("A").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph("Isolation")));

        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                table.addCell(new Cell().add(new Paragraph("Lala").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("12" + " A").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("Boom boom").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("54" + " mm\u00B2").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("6" + " A").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell(1, 2).add(new Paragraph("9" + " \u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("25" + " M\u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)));
            }
        }

        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));
        table.addCell(new Cell(1, 5).add(new Paragraph("Overgangsmodstand for jordingsleder og jordelektrode R:").setTextAlignment(TextAlignment.LEFT)).setBorderRight(Border.NO_BORDER));
        table.addCell(new Cell(1, 3).add(new Paragraph("8954" + " \u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)).setBorderLeft(Border.NO_BORDER));
        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));


        return table;
    }

    public Table createTableAfprovning(int rows) {
        float[] columnWidth = {150, 150, 150, 150, 150, 150, 150, 150};
        Table table = new Table(columnWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setFontSize(10f);
        table.setPadding(0);
        table.setFont(freeSansFont);

        table.addCell(new Cell(1,8).add(new Paragraph("Afprøvning af RCD’er").setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell(2, 1).add(new Paragraph("")));
        table.addCell(new Cell(2, 4).add(new Paragraph("Sinus (Type A og AC)")));
        table.addCell(new Cell(2, 2).add(new Paragraph("Pulserende overlejret \npå 6 mA d.c. (Type-A)")));
        table.addCell(new Cell(2, 1).add(new Paragraph("Prøve\u0002knap")));

        //Trekant virker ikke
        table.addCell(new Cell().add(new Paragraph("RCD")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("180º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 5xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º ½xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("180º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph("OK")));

        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                table.addCell(new Cell().add(new Paragraph("Lala").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("12").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("Boom boom").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("54").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("6").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("9").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("25").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("AV!").setTextAlignment(TextAlignment.LEFT)));
            }
        }
        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));

        return table;
    }

    public Table createTableKortslutning(int rows) {
        float[] columnWidth = {150, 150, 150, 150, 150, 150, 150, 150};
        Table table = new Table(columnWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setFontSize(10f);
        table.setPadding(0);

        table.addCell(new Cell(1,4).add(new Paragraph("Kortslutningsstrøm ").setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell(1,4).add(new Paragraph("Spændingsfald").setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(ColorConstants.GRAY));

        table.addCell(new Cell().add(new Paragraph("Gruppe")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("I")).add(new Text("k").setFontSize(6))));
        table.addCell(new Cell(1, 2).add(new Paragraph("Målt i punkt")));
        table.addCell(new Cell().add(new Paragraph("Gruppe")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("Δ").setFontSize(6).setFont(freeSansFont)).add(new Text("U"))));
        table.addCell(new Cell(1, 2).add(new Paragraph("Målt i punkt")));

        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                table.addCell(new Cell().add(new Paragraph("Lala").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("12" + " kA").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell(1, 2).add(new Paragraph("54").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("Boooom").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("6" + " %").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell(1, 2).add(new Paragraph("AV!").setTextAlignment(TextAlignment.LEFT)));
            }
        }
        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));

        return table;
    }

    public Table createTableBemaerkninger() {
        float[] columnWidth = {1000};
        Table table = new Table(columnWidth);
        table.setPadding(0);


        table.addCell(new Cell().add(new Paragraph("Bemærkning:")).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell().add(new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sodales ante ex, ac egestas libero pellentesque nec. " +
                "Vestibulum mattis imperdiet facilisis. Pellentesque aliquam magna quis luctus tristique. " +
                "Proin ac interdum leo. Curabitur eget ultrices sapien, pretium ullamcorper ligula. Ut quis risus luctus, ").setMultipliedLeading(1.0f)));

        return table;
    }

}

