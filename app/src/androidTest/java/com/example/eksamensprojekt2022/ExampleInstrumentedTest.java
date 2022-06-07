package com.example.eksamensprojekt2022;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

import android.content.Context;
import android.os.StrictMode;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Question;
import com.example.eksamensprojekt2022.Enteties.QuestionGroup;
import com.example.eksamensprojekt2022.Tools.FileHandler;
import com.example.eksamensprojekt2022.Tools.LoginAuthentication;
import com.example.eksamensprojekt2022.Tools.PostNumberToCity;
import com.example.eksamensprojekt2022.UI.Activitys.MainActivity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /*
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.eksamensprojekt2022", appContext.getPackageName());
    }

     */

    @Test
    public void testGetQuestionGroupIndexByQuestionID() {

        InspectionInformation inspectionInformation = new InspectionInformation();

        inspectionInformation.setQuestionGroups(new ArrayList<QuestionGroup>());

        for (int i = 0; i < 4; i++) {
        QuestionGroup qG = new QuestionGroup(
                i,
                "test"
        );

            for (int j = 0; j < 2; j++) {
                Question q = new Question(
                        j,
                        i,
                        "test question",
                        4
                );
                qG.getQuestions().add(q);
            }
            inspectionInformation.getQuestionGroups().add(qG);
    }

       int id =  inspectionInformation.getQuestionGroupIndexByQuestionID(6);

        int id2 = inspectionInformation.getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(1 , 1 );

        assertEquals(3 , id);

        assertEquals(3 , id2);
    }

    @Test
    public void testPostalCodeToCity() {

        PostNumberToCity.createPostCodeHasMap();

        String city =  PostNumberToCity.getCityFromPostCode(4700);
        assertEquals("Næstved" , city);

        city =  PostNumberToCity.getCityFromPostCode(5000);
        assertEquals("Odense C" , city);

        city =  PostNumberToCity.getCityFromPostCode(4760);
        assertEquals("Vordingborg" , city);

        city =  PostNumberToCity.getCityFromPostCode(0);
        assertEquals(null , city);

        city =  PostNumberToCity.getCityFromPostCode(-5);
        assertEquals(null , city);

    }
    @Test
    public void testFileReader() throws IOException {

        FileHandler fileHandler = new FileHandler();

        File file =  fileHandler.getImageFile();

        assertTrue(file != null);


    }




}