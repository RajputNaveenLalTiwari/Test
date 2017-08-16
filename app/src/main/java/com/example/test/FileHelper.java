package com.example.test;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by 2114 on 27-02-2017.
 */

public class FileHelper
{
    private static final String TAG = "FileHelper";
    public static String readFileFromAssets(Context context,String fileName)
    {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }

            return stringBuilder.toString();
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG,"File Not Found Exception"+e.getMessage());
        }
        catch (Exception e)
        {
            Log.e(TAG,e.getMessage());
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    Log.e(TAG,e.getMessage());
                }
            }
        }

        return null;
    }
}
