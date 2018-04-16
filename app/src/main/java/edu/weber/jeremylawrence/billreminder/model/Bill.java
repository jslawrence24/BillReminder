package edu.weber.jeremylawrence.billreminder.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Bill
{
    private String key;
    private String name;
    private Date due_date;
    private int recurrences;
    private String amount;
    private int daysToDue;

    public Bill()
    {
    }

    public Bill(String name, Date due_date, String amount)
    {
        this.name = name;
        this.due_date = due_date;
//        this.recurrences = recurrences;
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

    public Date getDue_date()
    {
        return due_date;
    }

    public void setDue_date(Date due_date)
    {
        this.due_date = due_date;
    }

    public int getRecurrences()
    {
        return recurrences;
    }

    public void setRecurrences(int recurrences)
    {
        this.recurrences = recurrences;
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
        Date dateToday;
        long diff;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        int days = 0;

        String strToday = sdf.format(new Date());

        try {
//            dateDue = sdf.parse(due_date);
            dateToday = sdf.parse(strToday);
            diff = due_date.getTime() - dateToday.getTime();
            days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
        }
        return days;
    }

    public void setDaysToDue(int days)
    {
    }

    @Override
    public String toString()
    {
        return getName();
    }


}
