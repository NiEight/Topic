package com.example.topic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//ArticleFragment에 들어갈 리스트뷰에 관한 이벤트 처리 클래스
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private ArrayList<ArticleContents> mlist, filteredList;
    private Context context;

    private String type, header, text, url;
    private String URL = "http://10.0.2.2/topick/articlebookmark.php";


    public ArticleAdapter(ArrayList<ArticleContents> list, Context context) {
        mlist = list;
        this.filteredList = list;
        this.context = context;
    }

    public ArrayList<ArticleContents> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(ArrayList<ArticleContents> filteredList) {
        this.filteredList = filteredList;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.articleitem, parent, false);
        ArticleAdapter.ViewHolder vh = new ArticleAdapter.ViewHolder(view);

        return vh;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {

        ArticleContents item = filteredList.get(position);


        holder.title.setText(item.getTitle().replaceAll("&quot;","\""));
        holder.description.setText(item.getDescription().replaceAll("&quot;","\""));


        Log.v("onBindViewHolder 호출","position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시 "+"position: "+Integer.toString(position));

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        Log.v("getItemCount","전체 데이터 갯수 리턴: "+Integer.toString(filteredList.size()));
        return filteredList.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title ;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView) ;


            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.titleText);
            description = itemView.findViewById(R.id.contentText);

            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    Intent intent = new Intent(context ,WebViewActivity.class);
                    intent.putExtra("url", mlist.get(pos).getLink());
                    context.startActivity(intent);

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition() ;

                    type = "article".trim();
                    url = mlist.get(pos).getLink().trim();
                    header = mlist.get(pos).getTitle().trim();
                    text = mlist.get(pos).getDescription().trim();

                    if(!url.equals("") && !header.equals("") && !text.equals("")) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("art",response);
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


    //Item클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }


    private ArticleAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(ArticleAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }

}
