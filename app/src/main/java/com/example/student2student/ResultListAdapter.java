package com.example.student2student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {
    private ArrayList<MatchingStudentItem> mDataset;
    private Context ctx;

    private String email, first;

    public ResultListAdapter(Context context, ArrayList<MatchingStudentItem> myDataset, String first, String email) {
        mDataset = myDataset;
        ctx = context;
        this.first = first;
        this.email = email;
    }

    public void add(MatchingStudentItem item) {
        mDataset.add(item);
    }

    public void add(int position, MatchingStudentItem item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(MatchingStudentItem item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultitems, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String g = ":"+mDataset.get(position).getGrade().split("\\#")[0].trim();
        String c = ":"+mDataset.get(position).getGrade().split("\\#")[1].trim();

        String msg ="";
        if(g.equals(":0"))
            msg = "\nציונו טרם נקבע";
        else
            msg = "\n ציונו "+g+"\n מדרגים "+c;

        holder.name.setText(mDataset.get(position).getFirstName() + " " + mDataset.get(position).getLastName() + "\nיכול ללמד"  +"\n"+ msg);
        String allCourses = mDataset.get(position).getTeach();
        String arr[] = allCourses.split("\\#");
        String canTeach = "";
        for (String s : arr) {
            canTeach += s + "\n";
        }
        holder.course.setText(canTeach);
//        Log.e("sss", "" + mDataset.get(position).getPhone());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arr[] = {
                        "שלח מייל",
                        "התקשר",
                        "שלח הודעה"
                };
                new AlertDialog.Builder(ctx)
                        .setTitle("איזו פעולה תרצה לבצע?")
                        .setItems(arr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phone = mDataset.get(position).getPhone();

                                switch (which) {
                                    case 0:
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("message/rfc822");
                                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mDataset.get(position).getStudentMail()});
                                        i.putExtra(Intent.EXTRA_SUBJECT, "Hi, I want to learn from you");
                                        i.putExtra(Intent.EXTRA_TEXT, "Hi,\n My name is "+first +
                                                ", and I would like to learn from you.\n" +
                                                "please contact me at: "+email+"\nThank you!\n\n -This message sent from Student2Student");
                                        try {
                                            ctx.startActivity(Intent.createChooser(i, "Send mail..."));
                                        } catch (android.content.ActivityNotFoundException ex) {
                                            Toast.makeText(ctx, "There are no email clients installed.", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 1:
                                        if (phone == null) {
                                            Toast.makeText(ctx, "המשתמש בחר לא להכניס טלפון", Toast.LENGTH_LONG).show();
                                            break;
                                        }
                                        Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                        ctx.startActivity(phoneIntent);
                                        break;
                                    case 2:
                                        if (phone == null) {
                                            Toast.makeText(ctx, "המשתמש בחר לא להכניס טלפון", Toast.LENGTH_LONG).show();
                                            break;
                                        }
                                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                                        ctx.startActivity(smsIntent);
                                        break;
                                }
                            }
                        })
                        .setPositiveButton("סגור", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, course;
        private RelativeLayout relativeLayout;

        public ViewHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            course = (TextView) view.findViewById(R.id.course);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.layout);
        }
    }
}
