package com.example.topic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ArticleFragment extends Fragment {


    ArrayList<ArticleContents> mlist;
    RecyclerView customListView2;
    RecyclerView.LayoutManager layoutManager;
    ArticleAdapter articltAdapter;
    private Context ct;
    String naverHtml;
    String tag = "뉴스";
    String k;

    // TODO: Rename and change types of parameters

    public ArticleFragment(String tag) {
        this.tag = tag;
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            naverHtml = bun.getString("NAVER_HTML");
            naverHtml = naverHtml.replaceAll("<b>","");
            naverHtml = naverHtml.replaceAll("</b>","");
            naverHtml = naverHtml.replaceAll("&lt;", "<");
            naverHtml = naverHtml.replaceAll("&gt;", ">");
            naverHtml = naverHtml.replaceAll("&amp;", "&");

            try {
                JSONObject jsonObject = new JSONObject(naverHtml);
                JSONArray newsArray = jsonObject.getJSONArray("items");
                mlist.clear();
                for(int i = 0; i < newsArray.length(); i++)
                {
                    JSONObject newsObject = newsArray.getJSONObject(i);
                    ArticleContents item = new ArticleContents(newsObject.getString("title"),newsObject.getString("description"),newsObject.getString("link"));




                    mlist.add(item);
                    articltAdapter = new ArticleAdapter(mlist, ct);
                    customListView2.setAdapter(articltAdapter);
                    articltAdapter.notifyDataSetChanged();
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        customListView2 = (RecyclerView) rootView.findViewById(R.id.listView_custom2);
        ct = container.getContext();
        mlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(ct);



        customListView2.setHasFixedSize(true);
        customListView2.setLayoutManager(layoutManager);

        SearchNews(tag);

        //data를 가져와서 어답터와 연결해 준다. 서버에서 가져오는게 대부분 이다.






        return rootView;
    }

            private void SearchNews( final String searchWord) {


                    new Thread() {
                        @Override
                        public void run() {

                            String clientId = "Tj5VOBHJ7wITkcUdmlhT";//애플리케이션 클라이언트 아이디값";
                            String clientSecret = "rNyCg7ZYpn";//애플리케이션 클라이언트 시크릿값";
                            String text = searchWord;
                            try {

                                String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=20"; // json 결과
                                URL url = new URL(apiURL);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestMethod("GET");
                                con.setRequestProperty("X-Naver-Client-Id", clientId);
                                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                                int responseCode = con.getResponseCode();
                                BufferedReader br;
                                if (responseCode == 200) { // 정상 호출
                                    br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                                } else {  // 에러 발생
                                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                                }
                                String inputLine;
                                StringBuffer response = new StringBuffer();
                                while ((inputLine = br.readLine()) != null) {
                                    response.append(inputLine);
                                    response.append("\n");
                                }
                                br.close();

                                String naverHtml = response.toString();

                                Bundle bun = new Bundle();
                                bun.putString("NAVER_HTML", naverHtml);
                                Message msg = handler.obtainMessage();
                                msg.setData(bun);
                                handler.sendMessage(msg);

                                //testText.setText(response.toString());
                                //System.out.println(response.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();



            }
}



