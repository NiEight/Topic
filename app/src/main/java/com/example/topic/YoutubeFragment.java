package com.example.topic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.Inflater;

//MainActivity 탭 레이아웃 중 하나인 YoutubeFragment
//리싸이클러뷰 사용
public class YoutubeFragment extends Fragment{

    ArrayList<Contents> contents;
    RecyclerView customListView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    private Context ct;

    AsyncTask<?, ?, ?> searchTask;


    String tag;
    //Youtube 검색을 위한 API_KEY
    final String API_KEY="AIzaSyCj8kWg557Lj2JvsBtq0EtEMCbYvhfPqT4";
    private String videoID = "";


    public YoutubeFragment(String tag) {
        this.tag = tag;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //뷰어 생성(검색 결과)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);
        customListView = (RecyclerView) rootView.findViewById(R.id.listView_custom);

        customListView.setHasFixedSize(true);           //리사이클러뷰 기존성능  강화
        ct = container.getContext();
        layoutManager = new LinearLayoutManager(ct);
        customListView.setLayoutManager(layoutManager);

        //data를 가져와서 어답터와 연결
        contents = new ArrayList<>();
        searchTask = new SearchTask().execute();
        return rootView;


    }


    private class SearchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                paringJsonData(getUtube());
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            customAdapter = new CustomAdapter(contents,ct);
            customListView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();

        }
    }

    //유튜브 url에 접근하여 검색한 결과들을 json 객체로 만들어준다
    public JSONObject getUtube() throws IOException {
        String originUrl;
        if(tag==null)
            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + "IT"
                + "&key="+ API_KEY+"&maxResults=50";
        else
            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + tag
                    + "&key="+ API_KEY+"&maxResults=50";

        String myUrl = String.format(originUrl);

        URL url = new URL(myUrl);

        HttpURLConnection connection =(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.connect();

        String line;
        String result="";
        InputStream inputStream=connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        System.out.println("검색결과"+ response);
        result=response.toString();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;

    }

    //json 객체를 가지고 와서 필요한 데이터를 파싱한다.
    //파싱을 하면 여러가지 값을 얻을 수 있는데 필요한 값들을 세팅하셔서 사용하시면 됩니다.
    private void paringJsonData(JSONObject jsonObject) throws JSONException {
        //재검색할때 데이터들이 쌓이는걸 방지하기 위해 리스트를 초기화 시켜준다.
        contents.clear();

        JSONArray contacts = jsonObject.getJSONArray("items");

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);
            String kind =  c.getJSONObject("id").getString("kind"); // 종류를 체크하여 playlist도 저장
            if(kind.equals("youtube#video")){
                vodid = c.getJSONObject("id").getString("videoId"); // 유튜브 동영상 아이디 값
            }else{
                vodid = c.getJSONObject("id").getString("playlistId");
            }

            String title = c.getJSONObject("snippet").getString("title"); //유튜브 제목
            String changString = stringToHtmlSign(title);


            String description = c.getJSONObject("snippet").getString("description");
            String changString2 = stringToHtmlSign(description);

            String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("default").getString("url");  //썸네일 이미지 URL값

            //JSON으로 파싱한 정보들을 객체화 시켜서 리스트에 담아준다.

            //뷰어에 추가. 어댑터 set후 뷰어에 나타남
            contents.add(new Contents(vodid, changString,imgUrl,changString2));


        }
    }

    String vodid = "";


    //영상 제목을 받아올때 &quot; &#39; 문자가 그대로 출력되기 때문에 다른 문자로 대체 해주기 위해 사용하는 메서드
    private String stringToHtmlSign(String str) {

        return str.replaceAll("&amp;", "[&]")

                .replaceAll("[<]", "&lt;")

                .replaceAll("[>]", "&gt;")

                .replaceAll("&quot;", "'")

                .replaceAll("&#39;", "'");
    }


}




