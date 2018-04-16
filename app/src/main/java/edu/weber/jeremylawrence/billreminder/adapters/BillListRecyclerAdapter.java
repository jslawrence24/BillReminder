package edu.weber.jeremylawrence.billreminder.adapters;

import android.arch.lifecycle.HolderFragment;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.weber.jeremylawrence.billreminder.R;
import edu.weber.jeremylawrence.billreminder.model.Bill;

public class BillListRecyclerAdapter extends RecyclerView.Adapter<BillListRecyclerAdapter.ViewHolder>
{
    private List<Bill> list;
    public OnBillClickedListener mCallback;

    public interface OnBillClickedListener
    {
        void onBillClicked(Bill bill);
    }

    public BillListRecyclerAdapter(List<Bill> list, OnBillClickedListener mCallback)
    {
        this.list = list;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Bill bill = list.get(position);

        if (bill != null) {
            holder.txvBillName.setText(bill.getName());

            int days = bill.getDaysToDue();

            String strDue, strDtd, strDays;

            if (days > 0){
                strDue = "Due in ";
                strDtd = String.valueOf(days);
                strDays = " days.";
            } else if (days == 0){
                strDue = "";
                strDtd = "AW, SNAP! DUE TODAY!!";
                strDays = "";
            } else {

                strDue = "";
                strDtd = "DUE  " + Math.abs(days) + "  DAYS AGO!!!";
                strDays = "";
            }

            holder.txvDue.setText(strDue);
            holder.txvDaysToDue.setText(strDtd);
            holder.txvDays.setText(strDays);


            if (bill.getAmount() != null) {
                holder.txvBillAmount.setVisibility(View.VISIBLE);
                holder.txvBillAmount.setText("$" + bill.getAmount());
            } else {
                holder.txvBillAmount.setVisibility(View.GONE);
            }
            holder.view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mCallback.onBillClicked(bill);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public void setBillList(List<Bill> list)
    {
        this.list.clear();
        this.list.addAll(list);

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txvBillName, txvDaysToDue, txvBillAmount,
                txvDue, txvDays;
        public View view;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txvBillName = (TextView) itemView.findViewById(R.id.txvBillName);
            txvDays = (TextView) itemView.findViewById(R.id.txvDays);
            txvDaysToDue = (TextView) itemView.findViewById(R.id.txvDaysToDue);
            txvDue = (TextView) itemView.findViewById(R.id.txvDue);
            txvBillAmount = (TextView) itemView.findViewById(R.id.txvBillAmount);
            view = itemView;
        }
    }
}
