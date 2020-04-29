package com.ashokcouhan.blooduser.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashokcouhan.blooduser.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class Common {
    public static User currentUser;

    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static final String BANK_ID = "bankid";

    public  static String convertCodeToStatus(String code)
    {
        if(code.equals("0"))
        {
            return "Pending";
        }
        else
            return "Confirm";
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info =connectivityManager.getAllNetworkInfo();
            if(info !=null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }

        }
        return  false;
    }

    public static String dateFormat(long milliseconds)
    {
        SimpleDateFormat sdf= new SimpleDateFormat(" dd-MM-yyyy");
         return sdf.format(new Date(milliseconds));

    }

    public static String dateToString(Date date)
    {
        SimpleDateFormat sdf= new SimpleDateFormat(" dd-MMM-yyyy");
        return sdf.format(date);
    }

    public static String getGroupType(String bloodGroup)
    {
        String groupName;
        switch (bloodGroup)
        {
            case "A+": groupName="Aposi";
                break;
            case "A-": groupName="Aneg";
                break;
            case "AB+": groupName="ABposi";
                break;
            case "AB-": groupName="ABneg";
                break;
            case "B+": groupName="Bposi";
                break;
            case "B-": groupName="Bneg";
                break;
            case "O+": groupName="Oposi";
                break;
            case "O-": groupName="Oneg";
                break;
            default:
                groupName="";
        }
        return groupName;
    }

    public static String getGroupName(String bloodGroup)
    {
        String groupName="";

        switch (bloodGroup)
        {
            case "Aposi": groupName="A+";
                          break;
            case "Aneg": groupName="A-";
                         break;
            case "Bposi": groupName="B+";
                          break;
            case "Bneg":  groupName="B-";
                          break;
            case "Oposi": groupName="O+";
                          break;
            case "Oneg":  groupName="O-";
                          break;
            case "ABposi": groupName="AB+";
                           break;
            case "ABneg": groupName="AB-";
                          break;
        }
        return groupName;
    }


    public static String getAge(String date)  {
        int age = 0;
        try {
            Date date1 =  new SimpleDateFormat(" dd-MMM-yyyy").parse(date);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(age) ;
    }

    public static String getMilliseconds(String dt)  {
        long milliseconds=000;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(" dd-MM-yyyy");
            Date date = sdf.parse(dt);
            milliseconds= date.getTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(milliseconds);
    }
}
