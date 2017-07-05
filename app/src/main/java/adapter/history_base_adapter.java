package adapter;

/**
 * Created by Mr singh on 3/23/2017.
 */

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import flexbillet.flexbillet.R;


public class history_base_adapter extends BaseAdapter {
    Context context;
    ArrayList<String> list_for_checkbox = new ArrayList<String>();

    public history_base_adapter(Context context,  ArrayList<String> list_for_checkboxx) {
        this.context = context;
        this.list_for_checkbox = list_for_checkboxx;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView TxtStatus;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.history_adapter, null);
            holder = new ViewHolder();
//            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.Txt_Header);
            holder.TxtStatus= (TextView) convertView.findViewById(R.id.Txt_satus);
            holder.imageView= (ImageView) convertView.findViewById(R.id.img_Status);
            final String childText = list_for_checkbox.get(position);
            String[] separated = childText.split(":,//");
            final String Tittle = separated[0]; // this will contain "Fruit"
            final String status = separated[1];
            holder.txtTitle.setText(Tittle);
            holder.TxtStatus.setText(status);
            if (status.contentEquals("TICKET_OK")) {

                holder.imageView.setImageResource(R.drawable.ok);

                holder.TxtStatus.setText("OK");
            } else if (status.contentEquals("TICKET_VOIDED")) {

                holder.imageView.setImageResource(R.drawable.failed);

                holder.TxtStatus.setText("has been voided");
            } else if (status.contentEquals("NOT_RECOGNIZED")) {
                holder.imageView.setImageResource(R.drawable.failed);

                holder.TxtStatus.setText("is not recognized as a valid ticket");

            } else if (status.contentEquals("SCAN_COUNT_EXCEEDED")) {
                holder.imageView.setImageResource(R.drawable.failed);

                holder.TxtStatus.setText("is used and cannot be scanned again");

            } else if (status.contentEquals("WRONG_TICKET_TYPE")) {
                holder.imageView.setImageResource(R.drawable.warning_ico);

                holder.TxtStatus.setText("is a valid ticket, but does not give access here");
            }

//            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return list_for_checkbox.size();
    }

    @Override
    public Object getItem(int position) {
        return list_for_checkbox.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_for_checkbox.indexOf(getItem(position));
    }
}