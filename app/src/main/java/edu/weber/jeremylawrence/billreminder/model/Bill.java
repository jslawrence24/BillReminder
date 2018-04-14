package edu.weber.jeremylawrence.billreminder.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Bill
{
    private String key;
    private String name;
    private String due_date;
    private String duration;
    private String amount;
    private int daysToDue;

    public Bill(){}

    public Bill(String name, String due_date, String amount)
    {
        this.name = name;
        this.due_date = due_date;
//        this.duration = duration;
        if (!amount.equals(""))
            this.amount = amount;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDue_date()
    {
        return due_date;
    }

    public void setDue_date(String due_date)
    {
        this.due_date = due_date;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public int getDaysToDue()
    {
        Date dateDue, dateToday;
        long diff;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        int days = 0;

        String strToday = sdf.format(new Date());

        try {
            dateDue = sdf.parse(due_date);
            dateToday = sdf.parse(strToday);
            diff = dateDue.getTime() - dateToday.getTime();
            days = (int)TimeUnit.DAYS.convert(diff , TimeUnit.MILLISECONDS);

        } catch (ParseException e) {}
        return days;
    }

    public void setDaysToDue(int days){}

    @Override
    public String toString()
    {
        return getName();
    }


}
