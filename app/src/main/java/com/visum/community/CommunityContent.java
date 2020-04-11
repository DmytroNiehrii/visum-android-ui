package com.visum.community;

import android.os.AsyncTask;
import android.util.Log;

import com.visum.util.page.Page;
import com.visum.dto.OutCommunityDto;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class CommunityContent {

    private static CommunityContent instance;
    private List<OutCommunityDto> ITEMS = new ArrayList();

    public static CommunityContent getInstance() {
        if (instance == null) {
            instance = new CommunityContent();
        }
        return instance;
    }

    public List<OutCommunityDto> getItems() {
        return ITEMS;
    }

    public void getDataFromApi() {
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Page<OutCommunityDto>> {
        @Override
        protected Page<OutCommunityDto> doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0.107:8040/community";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Page<OutCommunityDto> page = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Page<OutCommunityDto>>() {
                }).getBody();
                return page;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Page<OutCommunityDto> page) {
            ITEMS = page.content;
        }

    }


}
