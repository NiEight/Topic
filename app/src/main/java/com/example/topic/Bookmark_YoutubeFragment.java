package com.example.topic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//북마크 탭 레이아웃에 들어갈 기사 Fragment
//리싸이클러뷰 사용
public class Bookmark_YoutubeFragment extends Fragment{

    ArrayList<Contents> contents;
    RecyclerView customListView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    private Context ct;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_TITLE = "title";
    private static final String TAG_URL = "url";
    private static final String TAG_IMAGEURL = "imageurl";
    private static final String TAG_TEXT = "text";
    private String URL = "http://10.0.2.2/topick/getyoutubedata.php";

    String mJsonString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //리사이클러뷰의 화면 생성
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark__youtube, container, false);
        customListView = (RecyclerView) rootView.findViewById(R.id.listView_custom);
        customListView.setHasFixedSize(true);           //리사이클러뷰 기존성능  강화
        ct = container.getContext();
        layoutManager = new LinearLayoutManager(ct);

        //data를 가져와서 어답터와 연결
        contents = new ArrayList<>();

        Bookmark_YoutubeFragment.GetData task = new Bookmark_YoutubeFragment.GetData();
        task.execute(URL);


        return rootView;


    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ct,"Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d("json_test", "response - " + result);

            if (result == null) {
                Toast.makeText(ct, errorString, Toast.LENGTH_SHORT).show();
            } else {
                mJsonString = result;
                showResult();
            }
        }

        protected String doInBackground(String... params) {
            String serverURL = params[0];

            try {
                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("json_test", "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();
            } catch (Exception e) {

                Log.d("json_test", "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }
    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString(TAG_TITLE);
                String url = item.getString(TAG_URL);
                String imageurl = item.getString(TAG_IMAGEURL);
                String text = item.getString(TAG_TEXT);

                contents.add(new Contents(url, stringToHtmlSign(title), imageurl, stringToHtmlSign(text)));
                customAdapter = new CustomAdapter(contents, ct);
                customListView.setAdapter(customAdapter);
            }
        } catch (JSONException e) {
            Log.d("json_test", "showResult : ", e);
        }
    }
    //영상 제목을 받아올때 &quot; &#39; 문자가 그대로 출력되기 때문에 다른 문자로 대체 해주기 위해 사용하는 메서드
    private String stringToHtmlSign(String str) {

        return str.replaceAll("&amp;", "[&]")

                .replaceAll("[<]", "&lt;")

                .replaceAll("[>]", "&gt;")

                .replaceAll("&quot;", "'")

                .replaceAll("&#39;", "'");
    }
}


