package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "MainActivity";
    private static final String PREFERENCE_FILE_NAME = "SHARED_PREFERENCE";
    private Context context;
    private int screenWidth,screenHeight;

    private TextView questionText;
    private int questionTextWidth,questionTextHeight;
    private ViewGroup.LayoutParams questionTextParams;

    private RelativeLayout optionsContainer;
    private int optionsConatinerWidth,optionsConatinerHeight;
    private ViewGroup.LayoutParams optionsContainerParams;

    private AppCompatRadioButton optionA,optionB,optionC,optionD;

    private Button previousButton;
    private int previousButtonWidth,previousButtonHeight;
    private ViewGroup.LayoutParams previousButtonParams;

    private Button nextButton;
    private int nextButtonWidth,nextButtonHeight;
    private ViewGroup.LayoutParams nextButtonParams;

    SparseArray<JsonHelper> jsonHelperSparseArray;
    private int questionNumber=0;
    SparseArray<String> inputSparseArray;
    String optionValue="NONE";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;

        questionText                = (TextView) findViewById(R.id.qestionTextID);
        questionTextWidth           = (screenWidth);
        questionTextHeight          = (screenHeight*50)/100;
        questionTextParams          = (ViewGroup.LayoutParams) questionText.getLayoutParams();
        questionTextParams.width    = questionTextWidth;
        questionTextParams.height   = questionTextHeight;

        optionsContainer                = (RelativeLayout) findViewById(R.id.optionsConatinerID);
        optionsConatinerWidth           = (screenWidth);
        optionsConatinerHeight          = (screenHeight*35)/100;
        optionsContainerParams          = (ViewGroup.LayoutParams) optionsContainer.getLayoutParams();
        optionsContainerParams.width    = optionsConatinerWidth;
        optionsContainerParams.height   = optionsConatinerHeight;

        optionA = (AppCompatRadioButton) findViewById(R.id.optionAID);
        optionB = (AppCompatRadioButton) findViewById(R.id.optionBID);
        optionC = (AppCompatRadioButton) findViewById(R.id.optionCID);
        optionD = (AppCompatRadioButton) findViewById(R.id.optionDID);

        previousButton              = (Button) findViewById(R.id.previousButtonID);
        previousButtonWidth         = (screenWidth*50)/100;
        previousButtonHeight        = (screenHeight*15)/100;
        previousButtonParams        =  (ViewGroup.LayoutParams) previousButton.getLayoutParams();
        previousButtonParams.width  = previousButtonWidth;
        previousButtonParams.height = previousButtonHeight;

        nextButton              = (Button) findViewById(R.id.nextButtonID);
        nextButtonWidth         = (screenWidth*50)/100;
        nextButtonHeight        = (screenHeight*15)/100;
        nextButtonParams        = (ViewGroup.LayoutParams) nextButton.getLayoutParams();
        nextButtonParams.width  = nextButtonWidth;
        nextButtonParams.height = nextButtonHeight;

        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);


        jsonHelperSparseArray   = new SparseArray<>();
        inputSparseArray        = new SparseArray<>();

        String jsonResultantText = FileHelper.readFileFromAssets(this,"data.json");

        try
        {
            JSONArray jsonArray = new JSONArray(jsonResultantText);

            for (int i=0; i<jsonArray.length();i++)
            {
                JSONObject item = (JSONObject) jsonArray.get(i);
                String question = item.getString("question");
                String option_a = item.getString("option a");
                String option_b = item.getString("option b");
                String option_c = item.getString("option c");
                String option_d = item.getString("option d");
                String answer   = item.getString("answer");

                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.question = question;
                jsonHelper.option_a = option_a;
                jsonHelper.option_b = option_b;
                jsonHelper.option_c = option_c;
                jsonHelper.option_d = option_d;
                jsonHelper.answer = answer;

                jsonHelperSparseArray.put(i,jsonHelper);
            }

//            Log.i(TAG,"There are "+jsonHelperSparseArray.size()+" items");
        }
        catch (JSONException e)
        {
            Log.e(TAG,e.getMessage());
        }

        previousButton.setVisibility(View.GONE);
        questionText.setText(""+(questionNumber+1)+". "+jsonHelperSparseArray.get(questionNumber).question);
        optionA.setText(jsonHelperSparseArray.get(questionNumber).option_a);
        optionB.setText(jsonHelperSparseArray.get(questionNumber).option_b);
        optionC.setText(jsonHelperSparseArray.get(questionNumber).option_c);
        optionD.setText(jsonHelperSparseArray.get(questionNumber).option_d);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.optionAID:
                Log.i(TAG,"Option A");
                optionValue = "A";
                break;
            case R.id.optionBID:
                Log.i(TAG,"Option B");
                optionValue = "B";
                break;
            case R.id.optionCID:
                Log.i(TAG,"Option C");
                optionValue = "C";
                break;
            case R.id.optionDID:
                Log.i(TAG,"Option D");
                optionValue = "D";
                break;
            case R.id.previousButtonID:
                if(questionNumber > 0)
                {
                    /*if(inputSparseArray.get(questionNumber)=="NONE")
                    {
                        optionA.setEnabled(false);
                        optionB.setEnabled(false);
                        optionC.setEnabled(false);
                        optionB.setEnabled(false);
                    }
                    else
                    {
                        switch (inputSparseArray.get(questionNumber))
                        {
                            case "A":
                                optionA.setEnabled(true);
                                break;
                            case "B":
                                optionB.setEnabled(true);
                                break;
                            case "C":
                                optionC.setEnabled(true);
                                break;
                            case "D":
                                optionD.setEnabled(true);
                                break;
                        }
                    }*/
                    questionNumber--;
                    if(questionNumber == 0)
                    {
                        previousButton.setVisibility(View.GONE);
                    }
                    nextButton.setText(getString(R.string.next_button_text));
                    questionText.setText(""+(questionNumber+1)+". "+jsonHelperSparseArray.get(questionNumber).question);
                    optionA.setText(jsonHelperSparseArray.get(questionNumber).option_a);
                    optionB.setText(jsonHelperSparseArray.get(questionNumber).option_b);
                    optionC.setText(jsonHelperSparseArray.get(questionNumber).option_c);
                    optionD.setText(jsonHelperSparseArray.get(questionNumber).option_d);
                    inputSparseArray.put(questionNumber+1,optionValue);
                }
                else
                {

                }
//                Log.i(TAG,"Button Previous"+inputSparseArray);
                break;
            case R.id.nextButtonID:

                if(questionNumber < jsonHelperSparseArray.size()-1)
                {
                    questionNumber++;
                    if(questionNumber == jsonHelperSparseArray.size()-1)
                    {
                        nextButton.setText(getString(R.string.submit_button_text));
                        inputSparseArray.put(questionNumber-1,optionValue);
                    }
                    else if(questionNumber == jsonHelperSparseArray.size())
                    {
                        inputSparseArray.put(questionNumber,optionValue);
                    }
                    else
                    {
                        nextButton.setText(getString(R.string.next_button_text));
                        inputSparseArray.put(questionNumber-1,optionValue);
                    }
                    previousButton.setVisibility(View.VISIBLE);
                    questionText.setText(""+(questionNumber+1)+". "+jsonHelperSparseArray.get(questionNumber).question);
                    optionA.setText(jsonHelperSparseArray.get(questionNumber).option_a);
                    optionB.setText(jsonHelperSparseArray.get(questionNumber).option_b);
                    optionC.setText(jsonHelperSparseArray.get(questionNumber).option_c);
                    optionD.setText(jsonHelperSparseArray.get(questionNumber).option_d);
                }
                else
                {
                    inputSparseArray.put(questionNumber,optionValue);
                    finish();
                    callResultsActivity(context,ResultsActivity.class);
                }
                /*if(inputSparseArray.get(questionNumber)=="NONE")
                {
                    optionA.setEnabled(false);
                    optionB.setEnabled(false);
                    optionC.setEnabled(false);
                    optionB.setEnabled(false);
                }
                else
                {
                    switch (inputSparseArray.get(questionNumber))
                    {
                        case "A":
                            optionA.setEnabled(true);
                            break;
                        case "B":
                            optionB.setEnabled(true);
                            break;
                        case "C":
                            optionC.setEnabled(true);
                            break;
                        case "D":
                            optionD.setEnabled(true);
                            break;
                    }
                }
                Log.i(TAG,"Button Next"+inputSparseArray);*/
                break;
        }
    }

    private void callResultsActivity(Context context,Class aClass)
    {
        Intent intent = new Intent(context,aClass);
        startActivity(intent);
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(preferenceName,preferenceValue);

        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String dafaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(preferenceName,dafaultValue);
    }
}
