package com.example.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

//ArrayAdapter implements AdapterView.OnItemClickListener
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Contents> arrayList;
    private Context context;
    int bookMark_count = 0;



    public CustomAdapter(ArrayList<Contents> arrayList, Context context )
    {
        this.arrayList = arrayList;
        this.context = context;

    }



    public interface OnItemClickListener{
        void onItemClick(View v, int pos);

    }

    private OnItemClickListener
            mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewHolder(View itemView) {
            super(itemView) ;



        }
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem, parent, false);
        CustomViewHolder holder= new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {

        Glide.with(holder.imageView)
                .load(arrayList.get(position).getThumb_url())
                .into(holder.imageView);
        holder.title.setText(arrayList.get(position).getName());
        holder.content.setText(arrayList.get(position).getSummary());


    }


    @Override
    public int getItemCount() {
        return (arrayList != null? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView content;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView_thumb);
            this.title = itemView.findViewById(R.id.title);
            this.content = itemView.findViewById(R.id.textView_summary);

           // a_menu = itemView.(R.menu.menu_home);


            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    Intent intent = new Intent(context ,UtubePlay.class);
                    intent.putExtra("id", arrayList.get(pos).getVidold());
                    context.startActivity(intent);

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition() ;
                    //;

                    Toast.makeText(context, "북마크에 등록되었습니다.",Toast.LENGTH_LONG).show();
                    Log.d("str"," "+ bookMark_count);
                    Intent intent = new Intent(context ,Bookmark.class);
                    intent.putExtra("vidold", arrayList.get(pos).getVidold());
                    intent.putExtra("name", arrayList.get(pos).getName());
                    intent.putExtra("thumb_url", arrayList.get(pos).getThumb_url());
                    intent.putExtra("summary", arrayList.get(pos).getSummary());
                    intent.putExtra("bookMark_count", bookMark_count++);
                    context.startActivity(intent);
                    return true;
                }
            });


        }



        }
    }
