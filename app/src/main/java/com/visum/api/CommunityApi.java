package com.visum.api;

import android.os.AsyncTask;
import android.util.Log;

import com.visum.R;
import com.visum.api.handler.CommunityItemResponseHandler;
import com.visum.api.handler.CommunityPageResponseHandler;
import com.visum.dto.InCommunityDto;
import com.visum.dto.OutCommunityDto;
import com.visum.dto.OutCommunityShortDto;
import com.visum.util.App;
import com.visum.util.page.request.PageableRequest;
import com.visum.util.page.request.SortDirection;
import com.visum.util.page.response.Page;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class CommunityApi {

    public static void getCommunityPage(CommunityPageResponseHandler handler) {
        getCommunityPage(handler, getPageableRequestDefault());
    }

    public static void getCommunityPage(final CommunityPageResponseHandler handler, PageableRequest pageableRequest) {
       new AsyncTask<PageableRequest, Void, Page<OutCommunityShortDto>>() {
            @Override
            protected Page<OutCommunityShortDto> doInBackground(PageableRequest... params) {
                PageableRequest pr = params[0];
                try {
                    final String url = String.format("http://%s:%s/%s?page=%s&size=%s&sort=%s",
                            App.getAppResources().getString(R.string.api_host),
                            App.getAppResources().getString(R.string.api_port),
                            App.getAppResources().getString(R.string.api_uri_get_community_list),
                            pr.getPageNumber(),
                            pr.getPageSize(),
                            pr.getSortString()
                    );
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    Page<OutCommunityShortDto> page = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Page<OutCommunityShortDto>>() {
                    }).getBody();
                    return page;
                } catch (Exception e) {
                    Log.e("CommunityAPI", e.getMessage(), e);
                }
                return null;
            }

           @Override
           protected void onPostExecute(Page<OutCommunityShortDto> page) {
               handler.handle(page);
           }
        }.execute(pageableRequest);
    }

    public static void getCommunityItem(final CommunityItemResponseHandler handler, Long id) {
        new AsyncTask<Long, Void, OutCommunityDto>() {
            @Override
            protected OutCommunityDto doInBackground(Long... params) {
                Long id = params[0];
                try {
                    final String url = String.format("http://%s:%s/%s/%s",
                            App.getAppResources().getString(R.string.api_host),
                            App.getAppResources().getString(R.string.api_port),
                            App.getAppResources().getString(R.string.api_uri_get_community_item),
                            id
                    );
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    OutCommunityDto community = restTemplate.exchange(url, HttpMethod.GET, null, OutCommunityDto.class).getBody();
                    return community;
                } catch (Exception e) {
                    Log.e("CommunityAPI", e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(OutCommunityDto community) {
                handler.handle(community);
            }
        }.execute(id);
    }

    public static void updateCommunity(Long id, final InCommunityDto dto, final CommunityItemResponseHandler handler) {
        new AsyncTask<Long, Void, OutCommunityDto>() {
            @Override
            protected OutCommunityDto doInBackground(Long... params) {
                Long id = params[0];
                try {
                    final String url = String.format("http://%s:%s/%s/%s",
                            App.getAppResources().getString(R.string.api_host),
                            App.getAppResources().getString(R.string.api_port),
                            App.getAppResources().getString(R.string.api_uri_put_community),
                            id
                    );
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    OutCommunityDto community = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<InCommunityDto>(dto), OutCommunityDto.class).getBody();
                    return community;
                } catch (Exception e) {
                    Log.e("CommunityAPI.Update", e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(OutCommunityDto community) {
                handler.handle(community);
            }
        }.execute(id);
    }

    public static PageableRequest getPageableRequest(int pageNumber) {
        PageableRequest pageableRequest = getPageableRequestDefault();
        pageableRequest.setPageNumber(pageNumber);
        return pageableRequest;
    }

    private static PageableRequest getPageableRequestDefault() {
        return PageableRequest.builder()
                .pageNumber(0)
                .pageSize(App.getAppResources().getInteger(R.integer.page_size))
                .sort(new String[] {"createdAt"})
                .sortDirection(SortDirection.ASC)
                .build();
    }
}
