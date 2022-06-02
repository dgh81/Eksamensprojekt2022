package com.example.eksamensprojekt2022.Tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt2022.Enteties.FileHandler;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Picture;
import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Enteties.Question;
import com.example.eksamensprojekt2022.Enteties.Room;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UI.Activitys.SelectDocumentAndRoomActivityActivity;
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
import java.util.concurrent.CountDownLatch;

public class CreatePDF extends AppCompatActivity  implements Runnable {
    private CountDownLatch countDownLatch;
    ArrayList<Integer> answers;
    int index = 0;
    private Context context;
    MySQL mysql = new MySQL();
    private  int orderID;
    PdfFont freeSansFont;
    ArrayList<InspectionInformation> inspectionInfo;

    public static File pdfFile;




    @Override
    public void run() {

        runOnUiThread(new Runnable() {

            public void run() {

                try {



                    CreatePDF.this.context = context;
                    inspectionInfo = GetAllInspectionInformation(orderID);
                    ProjectInformation projectInfo = mysql.getProjectInformation(orderID);

                    AssetManager am = context.getAssets();
                    try (InputStream inStream = am.open("freesans.ttf")) {
                        byte[] fontData = IOUtils.toByteArray(inStream);
                        freeSansFont = PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H);
                    }
        /*String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF1.pdf");
        OutputStream outputstream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);*/
/*
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "sagsnummer_" + projectInfo.getProjectInformationID() + ".pdf");*/

                    //test dgh:
        /*String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "sagsnummer_" + projectInfo.getProjectInformationID() + ".pdf");*/

                    //TODO getPDFFile() burde tage argument (String filename). Tilpas her og i FileHandler. Brug navne konkatinering ovenfor.
                    pdfFile = new FileHandler().getPDFFile();
                    System.out.println(pdfFile.toString());

                    OutputStream outputstream = new FileOutputStream(pdfFile);

                    PdfWriter writer = new PdfWriter(pdfFile);
                    PdfDocument pdfDocument = new PdfDocument(writer);
                    Document document = new Document(pdfDocument);

                    //handler der sætter header ind når der er sideskift
                    TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
                    pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
                    //Tilføjet margin så headeren starter længere nede på siden
                    float topMargin = 30 + handler.getTableHeight();
                    document.setMargins(topMargin, 36, 36, 36);

                    //Laver dokumentet for hvert område
                    for (int i = 0; i < inspectionInfo.size(); i++) {
                        if (i != 0) {
                            document.add(new AreaBreak());
                        }

                        Table tablePersonalInfo = createTablePersonalInfo(projectInfo, inspectionInfo.get(i));
                        Table tableQuestions = createTableQuestions();

                        document.add(new Paragraph("Elinstallation - Vertifikation af mindre elinstallation").setFontSize(18f));
                        document.add(tablePersonalInfo);

                        if (inspectionInfo.get(i).getQuestionGroups().size() > 0) {
                            answerOptions(tableQuestions);
                        }

                        for (int j = 0; j < inspectionInfo.get(i).getQuestionGroups().size(); j++) {
                            ArrayList<Question> list = new ArrayList<>();
                            ArrayList<Integer> answers = new ArrayList<>();
                            for (int k = 0; k < inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().size(); k++) {
                                list.add(inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().get(k));
                                answers.add(inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().get(k).getAnswerID());
                            }
                            createQuestions(tableQuestions, inspectionInfo.get(i).getQuestionGroups().get(j).getTitle(), list, answers);

                        }

                        document.add(tableQuestions);

                        if (inspectionInfo.get(i).getQuestionGroups().size() > 0) {
                            document.add(new AreaBreak());
                        }

                        document.add(new Paragraph("Måleresultater").setFontSize(18f).setPaddingTop(5f));


                        document.add(createTableKredsdetaljer(inspectionInfo.get(i)));
                        document.add(createTableAfprovning(inspectionInfo.get(i)));
                        document.add(createTableKortslutning(inspectionInfo.get(i)));
                        if (inspectionInfo.get(i).getPDFComment().size() > 0) {
                            document.add(createTableBemaerkninger(inspectionInfo.get(i)));
                        }


                    }

                    //TODO skriv bilag ind i pdf
                    document.add(new AreaBreak());
                    ArrayList<Picture> pics = mysql.getPicturesFromProjectInformationID(InspectionInformation.getInstance().getFk_projectID());
                    System.out.println(pics.toString());

                    createBilag(pics, document);


                    document.close();

                    Toast.makeText(context, "Pdf Created", Toast.LENGTH_LONG).show();

                    ((SelectDocumentAndRoomActivityActivity) context).loadingAlert.dismiss();

                    ((SelectDocumentAndRoomActivityActivity) context).pdfCreated();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }



    public CreatePDF(Context context , int orderID ) {
        this.context = context;
        this.orderID = orderID;
    }




    public void createPdf(Context context , int  orderID ) throws IOException {
        this.context = context;
        inspectionInfo = GetAllInspectionInformation(orderID);
        ProjectInformation projectInfo = mysql.getProjectInformation(orderID);

        AssetManager am = context.getAssets();
        try (InputStream inStream = am.open("freesans.ttf")) {
            byte[] fontData = IOUtils.toByteArray(inStream);
            freeSansFont = PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H);
        }
        /*String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF1.pdf");
        OutputStream outputstream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);*/
/*
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "sagsnummer_" + projectInfo.getProjectInformationID() + ".pdf");*/

        //test dgh:
        /*String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "sagsnummer_" + projectInfo.getProjectInformationID() + ".pdf");*/

        //TODO getPDFFile() burde tage argument (String filename). Tilpas her og i FileHandler. Brug navne konkatinering ovenfor.
        pdfFile = new FileHandler().getPDFFile();
        System.out.println(pdfFile.toString());

        OutputStream outputstream = new FileOutputStream(pdfFile);

        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //handler der sætter header ind når der er sideskift
        TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
        //Tilføjet margin så headeren starter længere nede på siden
        float topMargin = 30 + handler.getTableHeight();
        document.setMargins(topMargin, 36, 36, 36);

        //Laver dokumentet for hvert område
        for (int i = 0; i < inspectionInfo.size(); i++) {
            if (i != 0) {
                document.add(new AreaBreak());
            }

            Table tablePersonalInfo = createTablePersonalInfo(projectInfo, inspectionInfo.get(i));
            Table tableQuestions = createTableQuestions();

            document.add(new Paragraph("Elinstallation - Vertifikation af mindre elinstallation").setFontSize(18f));
            document.add(tablePersonalInfo);

            if (inspectionInfo.get(i).getQuestionGroups().size() > 0) {
                answerOptions(tableQuestions);
            }

            for (int j = 0; j < inspectionInfo.get(i).getQuestionGroups().size(); j++) {
                ArrayList<Question> list = new ArrayList<>();
                ArrayList<Integer> answers = new ArrayList<>();
                for (int k = 0; k < inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().size(); k++) {
                    list.add(inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().get(k));
                    answers.add(inspectionInfo.get(i).getQuestionGroups().get(j).getQuestions().get(k).getAnswerID());
                }
                createQuestions(tableQuestions, inspectionInfo.get(i).getQuestionGroups().get(j).getTitle(), list, answers);

            }

            document.add(tableQuestions);

            if (inspectionInfo.get(i).getQuestionGroups().size() > 0) {
                document.add(new AreaBreak());
            }

            document.add(new Paragraph("Måleresultater").setFontSize(18f).setPaddingTop(5f));


            document.add(createTableKredsdetaljer(inspectionInfo.get(i)));
            document.add(createTableAfprovning(inspectionInfo.get(i)));
            document.add(createTableKortslutning(inspectionInfo.get(i)));
            if(inspectionInfo.get(i).getPDFComment().size() > 0) {
                document.add(createTableBemaerkninger(inspectionInfo.get(i)));
            }


        }

        //TODO skriv bilag ind i pdf
        document.add(new AreaBreak());
        ArrayList<Picture> pics = mysql.getPicturesFromProjectInformationID(InspectionInformation.getInstance().getFk_projectID());
        System.out.println(pics.toString());

        createBilag(pics, document);


        document.close();
        // Toast.makeText(context, "Pdf Created", Toast.LENGTH_LONG).show();


        this.countDownLatch.countDown();



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


    public void answerOptions(Table table) {
        table.addCell(new Cell(2, 1).add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Ja")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Nej")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(2, 1).add(new Paragraph("Ikke relevant")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
    }

    public void createQuestions(Table table, String groupTitle, ArrayList<Question> questions, ArrayList<Integer> answers) {

        if (questions.size() < 1) {
            return;

        } else {
            table.addCell(new Cell(1, 4).add(new Paragraph(groupTitle).setBold()).setBorder(Border.NO_BORDER));

            for (int i = 0; i < questions.size(); i++) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(questions.get(i).getQuestion()))).setBorder(Border.NO_BORDER));
                createCheckbox(answers.get(i), table);
                if (!(questions.get(i).getComment().equals(""))) {
                    createComment(table, questions.get(i));
                }
            }
            table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
        }

    }

    public void createComment(Table table, Question question) {
        table.addCell(new Cell(1, 1).add(new Paragraph("- " + question.getComment()).setItalic().setPaddingLeft(20).setPaddingLeft(30)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER));
        table.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER));

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
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Identifikation af installationen: " + projectInfo.getCaseNumber())));
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

    public Table createTableKredsdetaljer(InspectionInformation information) {
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


        if (information.getKredsdetaljer().size() > 0) {

            if (information.getKredsdetaljer().get(0).iszSRa()) {
                table.addCell(new Cell(1, 2).add(new Paragraph().add(new Text("R")).add(new Text("A").setFontSize(6))));
            } else {
                table.addCell(new Cell(1, 2).add(new Paragraph().add(new Text("Z")).add(new Text("S").setFontSize(6))));
            }

        } else {
            table.addCell(new Cell().add(new Paragraph().add(new Text("Z")).add(new Text("S").setFontSize(6))));
            table.addCell(new Cell().add(new Paragraph().add(new Text("R")).add(new Text("A").setFontSize(6))));
        }

        table.addCell(new Cell().add(new Paragraph("Isolation")));


        for (int i = 0; i < information.getKredsdetaljer().size(); i++) {
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getGroup()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getoB() + " A").setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getKarakteristik()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getTvaersnit() + " mm\u00B2").setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getMaksOB() + " A").setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell(1, 2).add(new Paragraph(information.getKredsdetaljer().get(i).getZsRaValue() + " \u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getKredsdetaljer().get(i).getIsolation() + " M\u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)));
        }


        if (!information.getOvergangsmodstandR().equals("")) {
            table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));
            table.addCell(new Cell(1, 5).add(new Paragraph("Overgangsmodstand for jordingsleder og jordelektrode R:").setTextAlignment(TextAlignment.LEFT)).setBorderRight(Border.NO_BORDER));
            table.addCell(new Cell(1, 3).add(new Paragraph(information.getOvergangsmodstandR() + " \u2126").setFont(freeSansFont).setTextAlignment(TextAlignment.RIGHT)).setBorderLeft(Border.NO_BORDER));
        }
        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));

        return table;
    }

    public Table createTableAfprovning(InspectionInformation information) {
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

        table.addCell(new Cell().add(new Paragraph("RCD")));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("180º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 5xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º ½xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("0º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph().add(new Text("180º 1xI")).add(new Text("\u0394n").setFontSize(6))));
        table.addCell(new Cell().add(new Paragraph("OK")));


        for (int i = 0; i < information.getAfprøvningAfRCD().size(); i++) {
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getRCD()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField1()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField2()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField3()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField4()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField5()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getField6()).setTextAlignment(TextAlignment.RIGHT)));
         //TODO: fix it so its a bool   table.addCell(new Cell().add(new Paragraph(information.getAfprøvningAfRCD().get(i).getOK()).setTextAlignment(TextAlignment.LEFT)));
        }

        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));

        return table;
    }

    public Table createTableKortslutning(InspectionInformation information) {
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


        for (int i = 0; i < information.getKortslutningsstroms().size(); i++) {
            table.addCell(new Cell().add(new Paragraph(information.getKortslutningsstroms().get(i).getK_gruppe()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getKortslutningsstroms().get(i).getK_KiK() + " kA").setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell(1, 2).add(new Paragraph(information.getKortslutningsstroms().get(i).getK_maaltIPunkt()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell().add(new Paragraph(information.getKortslutningsstroms().get(i).getS_gruppe()).setTextAlignment(TextAlignment.LEFT)));
            table.addCell(new Cell().add(new Paragraph(information.getKortslutningsstroms().get(i).getS_U()).setTextAlignment(TextAlignment.RIGHT)));
            table.addCell(new Cell(1, 2).add(new Paragraph(information.getKortslutningsstroms().get(i).getS_maaltIPunkt()).setTextAlignment(TextAlignment.LEFT)));
        }

        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));

        return table;
    }

    public Table createTableBemaerkninger(InspectionInformation information) {

        float[] columnWidth = {1000};
        Table table = new Table(columnWidth);
        table.setPadding(0);


        table.addCell(new Cell().add(new Paragraph("Bemærkning:")).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell().add(new Paragraph(information.getPDFComment().get(0)).setMultipliedLeading(1.0f).setItalic()));

        return table;
    }

    public ArrayList<InspectionInformation> GetAllInspectionInformation (int projectID) {

        ArrayList<InspectionInformation> inspectionInformations = new ArrayList<>();

        ArrayList<Room> roomsIDs = ProjectInformation.getRoomsFromProjectInformationID(projectID);

        for (int i = 0; i < roomsIDs.size(); i++) {

            InspectionInformation.setInspectionInformationFromDB(roomsIDs.get(i).getRoomID() , projectID);

            InspectionInformation.appendAllQuestionsWithAnswersToInspectionInformation();

            InspectionInformation.appendAllMeasurements(InspectionInformation.getInstance().getInspectionInformationID());

            InspectionInformation.getInstance().removeAllUnansweredQuestions();

            inspectionInformations.add(InspectionInformation.getInstance());

        }

        return inspectionInformations;

    }

    public void createBilag(ArrayList<Picture> list, Document doc) {
        doc.add(new Paragraph("Bilag").setFontSize(18));
        float[] columnWidth = {500, 500};
        int pictures = 0;

        for (int i = 0; i < list.size(); i += 2) {

            if (list.size() % 2 != 0) {

            }
            if (i == list.size() - 1) {

                if (pictures == 4) {
                    doc.add(new AreaBreak());
                    pictures = 0;
                }

                Table table = new Table(columnWidth);
                table.addCell(new Cell().add(bitmapToImage(list.get(i).getBitmap()).setHorizontalAlignment(HorizontalAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHeight(200).setWidth(1000));
                table.addCell(new Cell().setHeight(200).setWidth(1000));
                table.addCell(new Cell().add(new Paragraph("Evt. kommentar")).setBackgroundColor(ColorConstants.GRAY));
                table.addCell(new Cell().add(new Paragraph("Evt. kommentar")).setBackgroundColor(ColorConstants.GRAY));
                table.addCell(new Cell().add(new Paragraph(imageComment(list.get(i))).setItalic()));
                table.addCell(new Cell().add(new Paragraph("")));

                doc.add(table);
                pictures += 1;

            } else {
                Table table = new Table(columnWidth);
                table.addCell(new Cell().add(bitmapToImage(list.get(i).getBitmap()).setHorizontalAlignment(HorizontalAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHeight(200));
                table.addCell(new Cell().add(bitmapToImage(list.get(i + 1).getBitmap()).setHorizontalAlignment(HorizontalAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHeight(200));
                table.addCell(new Cell().add(new Paragraph("Evt. kommentar")).setBackgroundColor(ColorConstants.GRAY));
                table.addCell(new Cell().add(new Paragraph("Evt. kommentar")).setBackgroundColor(ColorConstants.GRAY));
                table.addCell(new Cell().add(new Paragraph(imageComment(list.get(i))).setItalic()));
                table.addCell(new Cell().add(new Paragraph(imageComment(list.get(i + 1))).setItalic()));

                doc.add(table);
                doc.add(new Paragraph().setHeight(20));
                pictures += 2;


            }
        }
    }

    public String imageComment(Picture picture) {
        if (picture.getComment().equals("")) {
            return "Ingen kommentar til dette billede";
        }
        return picture.getComment();
    }

    public Image bitmapToImage(Bitmap bitmap) {
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outPutStream);
        byte[] tickBitmapData = outPutStream.toByteArray();

        ImageData tickImageData = ImageDataFactory.create(tickBitmapData);
        Image image = new Image(tickImageData);
        image.setMaxHeight(200);
        image.setMaxWidth(200);


        return image;
    }
}

