package com.example.eksamensprojekt2022.Enteties;

import static com.example.eksamensprojekt2022.Tools.CreatePDF.pdfFile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;

public class EmailThread extends Thread {
    Context context;
    public EmailThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        System.out.println("run by: EmailClientThread");
        Uri pdfUri = FileProvider.getUriForFile(context,"com.example.eksamensprojekt2022.fileprovider",pdfFile);
        final Intent emailIntent = new Intent( Intent.ACTION_SEND);
        //Set permissions
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //Build email contents
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "danielguldberg@gmail.com" });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Email Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Email Body");
        emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        //Start og vælg mail klient
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
