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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//YotubeFragment 리싸이클러뷰에 들어갈 Contents 배정
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Contents> arrayList;
    private Context context;

    private String type, header, url, imageurl, text;
    private String URL = "http://10.0.2.2/topick/youtubebookmark.php";
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

    //뷰에서 보여질 이미지와 텍스트 set
    //Glide를 이용하여 이미지 로드
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

            //뷰 항목 클릭시 UtubePlay.class로 이동
            //UtubePlay.class - Youtube 영상 재생 클래스
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

                    type = "youtube";
                    url = arrayList.get(pos).getVidold();
                    header = arrayList.get(pos).getName();
                    imageurl = arrayList.get(pos).getThumb_url();
                    text = arrayList.get(pos).getSummary();

                    if(!url.equals("") && !header.equals("") && !imageurl.equals("") && !text.equals("")) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(context, "북마크에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                } else if (response.equals("failure")) {
                                    Toast.makeText(context, "등록 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString().trim() ,Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("type", type);
                                data.put("url", url);
                                data.put("header", header);
                                data.put("imageurl", imageurl);
                                data.put("text", text);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                    } else {
                        Toast.makeText(context, "잘못된 요청", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });


        }



        }
    }
