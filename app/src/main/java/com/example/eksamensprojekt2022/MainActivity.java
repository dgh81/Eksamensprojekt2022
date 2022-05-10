package com.example.eksamensprojekt2022;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) throws InterruptedException {

        //Flyt til onCreate?
        //Research hvad der foregår her. Vi bliver måske spurgt ;)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("Klik");

        MySQL mysql = new MySQL();
        Thread t = new Thread(mysql);
        // t.run(): kør den ene tråd først
        // t.start(): kør tråde synkront

        t.start();
        // join venter på threads er færdige inden resten af koden køres
        t.join();

        //System.out.println(mysql.getConnection());
        System.out.println(mysql.userType("a@a.dk"));
        //System.out.println(t.isAlive());

        //Print alle questiongroups fra SQL:
        ArrayList<String> questionGroups = mysql.getQuestionGroups();
        for (int i = 0; i < questionGroups.size(); i++) {
            //System.out.println(questionGroups.get(i));
        }

        /*//Print alle questions i en valgt questionGroup
        ArrayList<String> questions = mysql.getQuestionsFromGroupTitle("Installation");
        for (int i = 0; i < questions.size(); i++) {
            //System.out.println(questions.get(i));
        }

        //Print alle questiongroups og deres tilhørende questions
        for (int i = 0; i < questionGroups.size(); i++) {
            System.out.println(questionGroups.get(i) + ":");
            ArrayList<String> questions2 = mysql.getQuestionsFromGroupTitle(questionGroups.get(i));
            for (int j = 0; j < questions2.size(); j++) {
                System.out.println(questions2.get(j));
            }
        }*/

        //Print alle questiongroups og deres tilhørende questions 2
        for (int i = 0; i < questionGroups.size(); i++) {
            System.out.println(questionGroups.get(i) + ":");
            ArrayList<Question> questions2 = mysql.getQuestionsFromGroupTitle(questionGroups.get(i));
            for (int j = 0; j < questions2.size(); j++) {
                System.out.println(questions2.get(j).getQuestion());
            }
        }

        mysql.getAnswerInfo();
    }


    /*class Task extends AsyncTask<Void, Void, Void>{
        String records = "";
        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db?useSSL=false";  // danielguldberg_dk_db
                Connection connection = DriverManager.getConnection(url, "danielguldberg_dk", "280781");
                PreparedStatement userType = connection.
                        prepareStatement("SELECT Type FROM PasswordTable WHERE Email = '" + "a@a.dk" + "'");
                ResultSet rs = userType.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("Type"));
                }
            } catch (Exception e) {
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            System.out.println("test");
            super.onPostExecute(aVoid);
        }
    }*/
}