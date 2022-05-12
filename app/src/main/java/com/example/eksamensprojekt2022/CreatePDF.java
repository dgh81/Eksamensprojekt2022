package com.example.eksamensprojekt2022;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class CreatePDF extends AppCompatActivity {
    ArrayList<Integer> answers;
    int index = 0;
    Context context;

    public void createPdf(Context context) throws IOException {
        this.context = context;
        answers = answersInList();
        Table personalInfo = createTablePersonalInfo();
        Table questions = createTableQuestions();
        Table questionsPage2 = createTableQuestions();

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF1.pdf");
        OutputStream outputstream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(createHeader(1));
        document.add(new Paragraph("Elinstallation - Vertifikation af mindre elinstallation").setFontSize(18f).setPaddingTop(5f));

        document.add(personalInfo);

        ArrayList<String> questionsGenerelt = questionGroup1();
        ArrayList<String> questionsTavlen = questionGroup2();
        ArrayList<String> questionsInstallation = questionGroup3();
        ArrayList<String> questionsIndbygningsarmaturer = questionGroup4();
        ArrayList<String> questionsBeskyttelsesledere = questionGroup5();
        ArrayList<String> questionsFejlbeskyttelse = questionGroup6();

        questions.addCell(new Cell(2, 1).add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        questions.addCell(new Cell(2, 1).add(new Paragraph("Ja")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        questions.addCell(new Cell(2, 1).add(new Paragraph("Nej")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        questions.addCell(new Cell(2, 1).add(new Paragraph("Ikke relevant")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        questions.addCell(new Cell(1, 4).add(new Paragraph("1. Generelt:").setBold()).setBorder(Border.NO_BORDER));

        for (int i = 0; i < questionsGenerelt.size(); i++) {
            questions.addCell(new Cell().add(new Paragraph(questionsGenerelt.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questions);
        }

        questions.addCell(new Cell(1, 4).add(new Paragraph("\n2. Tavlen:").setBold()).setBorder(Border.NO_BORDER));
        for (int i = 0; i < questionsTavlen.size(); i++) {
            questions.addCell(new Cell().add(new Paragraph(questionsTavlen.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questions);
        }

        document.add(questions);

        document.add(new AreaBreak());
        document.add(createHeader(2));


        questionsPage2.addCell(new Cell(1, 4).add(new Paragraph("\n3. Installation:").setBold()).setBorder(Border.NO_BORDER));
        for (int i = 0; i < questionsInstallation.size(); i++) {
            questionsPage2.addCell(new Cell().add(new Paragraph(questionsInstallation.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questionsPage2);
        }

        questionsPage2.addCell(new Cell(1, 4).add(new Paragraph("\n4. Indbygningsarmaturer:").setBold()).setBorder(Border.NO_BORDER));
        for (int i = 0; i < questionsIndbygningsarmaturer.size(); i++) {
            questionsPage2.addCell(new Cell().add(new Paragraph(questionsIndbygningsarmaturer.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questionsPage2);
        }

        questionsPage2.addCell(new Cell(1, 4).add(new Paragraph("\n5. Beskyttelsesledere og udligningsforbindelser:").setBold()).setBorder(Border.NO_BORDER));
        for (int i = 0; i < questionsBeskyttelsesledere.size(); i++) {
            questionsPage2.addCell(new Cell().add(new Paragraph(questionsBeskyttelsesledere.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questionsPage2);
        }

        questionsPage2.addCell(new Cell(1, 4).add(new Paragraph("\n6. Fejlbeskyttelse/supplerende beskyttelse:\n").setBold()).setBorder(Border.NO_BORDER));
        for (int i = 0; i < questionsFejlbeskyttelse.size(); i++) {
            questionsPage2.addCell(new Cell().add(new Paragraph(questionsFejlbeskyttelse.get(i))).setBorder(Border.NO_BORDER));
            createCheckbox(answers.get(index), questionsPage2);
        }

        document.add(questionsPage2);

        document.add(new AreaBreak());
        document.add(createHeader(3));
        document.add(new Paragraph("Måleresultater").setFontSize(18f).setPaddingTop(5f));

        document.add(createTableKredsdetaljer(5));
        document.add(createTableAfprovning(5));
        document.add(createTableKortslutning(5));
        document.add(createTableBemaerkninger());

        document.close();
        Toast.makeText(context, "Pdf Created", Toast.LENGTH_LONG).show();
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

    public ArrayList<String> questionGroup1() {
        ArrayList<String> questionsGenerelt = new ArrayList<>();
        questionsGenerelt.add("Er der taget hensyn til ydre påvirkninger og anvendt korrekt kapslingsklasse?");
        questionsGenerelt.add("Er der brandtætnet ved gennemføringer?");
        questionsGenerelt.add("Er installationen isolationsprøvet?");
        questionsGenerelt.add("Er der foretaget polaritetsprøve og kontrol af fasefølgen?");
        questionsGenerelt.add("Er der foretaget funktionsprøver af installationen?");
        questionsGenerelt.add("Er nul- og beskyttelsesledere korrekt identificeret?");
        questionsGenerelt.add("Er ledere korrekt overstrømsbeskyttet og valgt efter strømværdi?");
        questionsGenerelt.add("Er SPD’er (overspændingsbeskyttelsesudstyr) korrekt valgt og installeret?");
        questionsGenerelt.add("Er permanent tilsluttede brugsgenstande egnet til den pågældende anvendelse?");
        questionsGenerelt.add("Er nødvendig dokumentation til stede?");
        questionsGenerelt.add("Er spændingsfald kontrolleret?");
        questionsGenerelt.add("Er der foretaget foranstaltninger mod elektromagnetiske påvirkninger?");
        questionsGenerelt.add("Er ejer/bruger informeret om funktion og betjening?");

        return questionsGenerelt;
    }

    public ArrayList<String> questionGroup2() {
        ArrayList<String> questionsTavlen = new ArrayList<>();
        questionsTavlen.add("Er der tilstrækkelig plads til at arbejde på/adgang til tavlen?");
        questionsTavlen.add("Er overstrømsbeskyttelsesudstyr korrekt valgt og evt. indstillet?");
        questionsTavlen.add("Er der en entydig mærkning af beskyttelsesudstyr med tilhørsforhold?");
        questionsTavlen.add("Er der mærkning om max. mærke-/indstillingsstrøm?");
        questionsTavlen.add("Er mærkning med oplysninger om tekniske data for tavlen foretaget?");
        questionsTavlen.add("Er udgående beskyttelsesledere anbragt i separate klemmer i tavlen?");
        questionsTavlen.add("Er afdækning og dækplader monteret?");
        questionsTavlen.add("Er indføringer tilpasset/tætnet, så tavlens kapslingsklasse er som mærket?");

        return questionsTavlen;
    }

    public ArrayList<String> questionGroup3() {
        ArrayList<String> questionsInstallation = new ArrayList<>();
        questionsInstallation.add("Er udstyr til adskillelse og afbrydelse korrekt valgt, placeret og installeret?");
        questionsInstallation.add("Er stikkontakter og udtag m.m. installeret i henhold til gældende bestemmelser?");
        questionsInstallation.add("Er kabler/ledninger korrekt oplagt, afsluttet og forbundet?");
        questionsInstallation.add("Er kabler beskyttet mod mekanisk overlast ved opføringer fra gulv/jord?");
        questionsInstallation.add("Er tilledninger aflastet for træk og vridning ved tilslutning til installationen?");
        questionsInstallation.add("Er alle dæksler og afdækninger monteret, så der ikke er berøringsfare?");
        questionsInstallation.add("Er alle samlinger let tilgængelige?");

        return questionsInstallation;
    }

    public ArrayList<String> questionGroup4() {
        ArrayList<String> questionsIndbygningsarmaturer = new ArrayList<>();
        questionsIndbygningsarmaturer.add("Er indbygningsarmaturer korrekt valgt og monteret?");
        questionsIndbygningsarmaturer.add("Er indbygningsarmaturer installeret således, at overophedning undgås?");

        return questionsIndbygningsarmaturer;
    }

    public ArrayList<String> questionGroup5() {
        ArrayList<String> questionsBeskyttelsesledere = new ArrayList<>();
        questionsBeskyttelsesledere.add("Er jordingslederen korrekt valgt (minimum 6 mm\u00B2)?");
        questionsBeskyttelsesledere.add("Er der etableret beskyttende potentialudligning?");
        questionsBeskyttelsesledere.add("Er supplerende beskyttende potentialudligning etableret?");
        questionsBeskyttelsesledere.add("Er den gennemgående forbindelse i udligningsforbindelser kontrolleret?");
        questionsBeskyttelsesledere.add("Er den gennemgående forbindelse i beskyttelsesledere kontrolleret?");
        questionsBeskyttelsesledere.add("Er overgangsmodstand for jordelektroden kontrolleret?");

        return questionsBeskyttelsesledere;
    }

    public ArrayList<String> questionGroup6() {
        ArrayList<String> questionsFejlbeskyttelse = new ArrayList<>();
        questionsFejlbeskyttelse.add("Er beskyttelsesmetode korrekt valgt i forhold til installationstype og systemjording?");
        questionsFejlbeskyttelse.add("Er RCD’er (fejlstrømsafbrydere) kontrolleret og afprøvet?");
        questionsFejlbeskyttelse.add("Er klasse I brugsgenstande tilsluttet til beskyttelseslederen?");

        return questionsFejlbeskyttelse;
    }

    public ArrayList<Integer> answersInList() {
        ArrayList<Integer> answers = new ArrayList<>();
        for (int i = 0; i < 39; i++) {
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

    private Table createTablePersonalInfo() {
        float[] columnWidth1 = {150, 200, 200};
        Table personalInfo = new Table(columnWidth1);
        personalInfo.setFontSize(10f);
        personalInfo.setPadding(0);

        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Kundenavn: ")));
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Adresse: ")));
        personalInfo.addCell(new Cell().add(new Paragraph("Post nr: ")));
        personalInfo.addCell(new Cell().add(new Paragraph("By: ")));
        personalInfo.addCell(new Cell().add(new Paragraph("Ordernummer: ")));
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Identifikation af installationen: ")));
        personalInfo.addCell(new Cell(1, 3).add(new Paragraph("Installationen er udført af: ")));
        personalInfo.addCell(new Cell(1, 2).add(new Paragraph("Verifikation af installationen er udført af: ")));
        personalInfo.addCell(new Cell().add(new Paragraph("Dato: ")));

        return personalInfo;
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
        table.addCell(new Cell().add(new Paragraph("OB (I\u2099)")));
        table.addCell(new Cell().add(new Paragraph("Karakteristik")));
        table.addCell(new Cell().add(new Paragraph("Tværsnit")));
        table.addCell(new Cell().add(new Paragraph("Maks. OB")));
        table.addCell(new Cell().add(new Paragraph("Zs")));
        table.addCell(new Cell().add(new Paragraph("RA")));
        table.addCell(new Cell().add(new Paragraph("Isolation")));

        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                table.addCell(new Cell().add(new Paragraph("Lala").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("12" + " A").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("Boom boom").setTextAlignment(TextAlignment.LEFT)));
                table.addCell(new Cell().add(new Paragraph("54" + " mm\u00B2").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("6" + " A").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell(1, 2).add(new Paragraph("9" + " Ohm").setTextAlignment(TextAlignment.RIGHT)));
                table.addCell(new Cell().add(new Paragraph("25" + " Ohm").setTextAlignment(TextAlignment.RIGHT)));
            }
        }

        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));
        table.addCell(new Cell(1, 5).add(new Paragraph("Overgangsmodstand for jordingsleder og jordelektrode R:").setTextAlignment(TextAlignment.LEFT)).setBorderRight(Border.NO_BORDER));
        table.addCell(new Cell(1, 3).add(new Paragraph("8954" + " Ohm").setTextAlignment(TextAlignment.RIGHT)).setBorderLeft(Border.NO_BORDER));
        table.addCell(new Cell(1, 8).setBorder(Border.NO_BORDER).setHeight(10f));


        return table;
    }

    public Table createTableAfprovning(int rows) {
        float[] columnWidth = {150, 150, 150, 150, 150, 150, 150, 150};
        Table table = new Table(columnWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setFontSize(10f);
        table.setPadding(0);

        table.addCell(new Cell(1,8).add(new Paragraph("Afprøvning af RCD’er").setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(ColorConstants.GRAY));
        table.addCell(new Cell(2, 1).add(new Paragraph("")));
        table.addCell(new Cell(2, 4).add(new Paragraph("Sinus (Type A og AC)")));
        table.addCell(new Cell(2, 2).add(new Paragraph("Pulserende overlejret \npå 6 mA d.c. (Type-A)")));
        table.addCell(new Cell(2, 1).add(new Paragraph("Prøve\u0002knap")));

        //Trekant virker ikke
        table.addCell(new Cell().add(new Paragraph("RCD")));
        table.addCell(new Cell().add(new Paragraph("0º 1xI∆n")));
        table.addCell(new Cell().add(new Paragraph("180º 1xI∆n")));
        table.addCell(new Cell().add(new Paragraph("0º 5xI∆n")));
        table.addCell(new Cell().add(new Paragraph("0º ½xI∆n")));
        table.addCell(new Cell().add(new Paragraph("0º 1xI∆n")));
        table.addCell(new Cell().add(new Paragraph("180º 1xI∆n")));
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
        table.addCell(new Cell().add(new Paragraph("Ik")));
        table.addCell(new Cell(1, 2).add(new Paragraph("Målt i punkt")));
        table.addCell(new Cell().add(new Paragraph("Gruppe")));
        table.addCell(new Cell().add(new Paragraph("ΔU")));
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
