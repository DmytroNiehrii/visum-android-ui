package com.visum.community;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CommunityContent {

    private static CommunityContent instance;
    private List<CommunityItem> ITEMS = new ArrayList<CommunityItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, CommunityItem> ITEM_MAP = new HashMap<String, CommunityItem>();
    private int COUNT = 25;

    private CommunityContent() {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCommunityItem(i));
        }
    }

    public static CommunityContent getInstance() {
        if (instance == null) {
            instance = new CommunityContent();
        }
        return instance;
    }

    private void addItem(CommunityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private CommunityItem createCommunityItem(int position) {
        return new CommunityItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public List<CommunityItem> getItems() {
        return ITEMS;
    }

    /**
     * A dummy item representing a piece of content.
     */
    //@NoArgsConstructor
    //@Data
    public class CommunityItem {
        public String id;
        public String name;
        public String description;

        public CommunityItem() {

        }



        public CommunityItem(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public void getDataFromApi() {
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, CommunityItem> {
        @Override
        protected CommunityItem doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0.107:8040/community/2";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                CommunityItem item = restTemplate.getForObject(url, CommunityItem.class);
                return item;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(CommunityItem item) {
            ITEMS.add(item);
        }

    }



}
