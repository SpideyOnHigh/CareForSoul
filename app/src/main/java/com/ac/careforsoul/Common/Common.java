package com.ac.careforsoul.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ac.careforsoul.Model.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class Common {

    public static User currentuser;



    public static String PHONE_TEXT = "phone";

    public static String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return"Placed";
        else if(status.equals("1"))
            return "Accepted";
        else
            return "Canceled";

    }

    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PRN_KEY = "Prn";
    public static final String PWD_KEY = "Password";
    public static final String PHONE = "Phone";


    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static BigDecimal formatCurrency(String amount, Locale locale) throws ParseException
    {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        if(format instanceof DecimalFormat)
            ((DecimalFormat)format).setParseBigDecimal(true);
        return (BigDecimal)format.parse(amount.replace("[^\\d.,]",""));
    }

    public static String getDate(long time)
    {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date = new StringBuilder(android.text.format.DateFormat.format("dd-MM-yyyy",
                calendar).
                toString());
        return date.toString();
    }



}
