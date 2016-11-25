package don.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import don.myapplication.R;
import don.myapplication.activity.ThirdActivity;
import don.myapplication.model.Doctor;

public class AdapterView extends RecyclerView.Adapter<AdapterView.ViewHolder> {
    private Context context;
    private List<Doctor> data;

    public AdapterView(Context context, List<Doctor> data) {
        this.context = context;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvArea, tvJob, tvRate;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvArea = (TextView) itemView.findViewById(R.id.tv_area);
            tvJob = (TextView) itemView.findViewById(R.id.tv_job);
            tvRate = (TextView) itemView.findViewById(R.id.tv_price);
            imageView = (ImageView) itemView.findViewById(R.id.iv_photo);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_doctor, viewGroup, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvArea.setText(data.get(position).getArea());
        viewHolder.tvJob.setText(data.get(position).getSpeciality());

        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(Double.valueOf(data.get(position).getRate().toString()));
        viewHolder.tvRate.setText(data.get(position).getCurrency().toString() + " " + format);

        Picasso.with(context).load(data.get(position).getPhoto()).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ThirdActivity.class);
                intent.putExtra("id", data.get(position).getId().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

}
