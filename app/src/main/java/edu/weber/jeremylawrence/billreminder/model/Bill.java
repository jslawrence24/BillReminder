package edu.weber.jeremylawrence.billreminder.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Bill
{
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private String key;
    private String name;
    private Date due_date;
    private int recurrences;
    private String amount;

    public Bill(){}

    public Bill(String name, Date due_date, String amount)
    {
        this.name =  name;
        this.due_date = due_date;
        if (!amount.equals(""))
            this.amount = amount;
    }

    public String getKey() { return key; }

    public void setKey(String key) { if (this.key == null) this.key = key; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Date getDue_date() { return due_date; }

    public void setDue_date(Date due_date) { this.due_date = due_date; }

    public int getRecurrences() { return recurrences; }

    public void setRecurrences(int recurrences) { this.recurrences = recurrences; }

    public String getAmount() { return amount; }

    public void setAmount(String amount) { this.amount = (!amount.equals("")) ? amount : null; }

    public int getDaysToDue()
    {
        Date dateToday;
        long diff;

        int days = 0;

        String strToday = dateFormat.format(new Date());

        try {
            dateToday = dateFormat.parse(strToday);
            diff = due_date.getTime() - dateToday.getTime();
            days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
        }
        return days;
    }

    @Override
    public String toString() { return getName(); }
} // end class
