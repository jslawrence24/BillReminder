package edu.weber.jeremylawrence.billreminder.model;

public class Bill
{
    private String key;
    private String name;
    private String due_date;
    private String duration;
    private String amount;

    public Bill(){}

    public Bill(String name, String due_date, String duration, String amount)
    {
        this.name = name;
        this.due_date = due_date;
        this.duration = duration;
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

    public String getDaysToDue()
    {
        //TODO CALCULATE DAYS UNTIL DUE
        return "XX";
    }
}
