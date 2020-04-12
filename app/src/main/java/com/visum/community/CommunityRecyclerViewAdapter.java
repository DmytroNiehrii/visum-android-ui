package com.visum.community;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visum.CommunityFragment.OnListFragmentInteractionListener;
import com.visum.R;
import com.visum.api.CommunityApi;
import com.visum.api.handler.CommunityPageResponseHandler;
import com.visum.dto.OutCommunityDto;
import com.visum.dto.OutCommunityShortDto;
import com.visum.listener.CommunityUpdateListener;
import com.visum.util.conversion.ConversionServiceFactory;
import com.visum.util.page.response.Page;

import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommunityRecyclerViewAdapter
        extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder>
        implements CommunityUpdateListener {

    private ConversionService conversionService = ConversionServiceFactory.getInstance();

    private List<OutCommunityShortDto> items = new ArrayList();
    private Page<OutCommunityShortDto> lastLoadedPage;
    private final OnListFragmentInteractionListener mListener;

    public CommunityRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
        pullData();
    }

    public void pullData() {
        CommunityApi.getCommunityPage(new CommunityPageResponseHandler() {
            @Override
            public void handle(Page<OutCommunityShortDto> page) {
                lastLoadedPage = page;
                items.addAll(page.getContent());
                notifyItemRangeChanged(0, items.size());
            }
        }, CommunityApi.getPageableRequest(lastLoadedPage == null ? 0 : lastLoadedPage.getNumber() + 1));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.community_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        holder.vName.setText( holder.mItem.getName());
        holder.vDescription.setText(items.get(position).getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void onCommunityUpdate(OutCommunityDto dto) {
        Optional<OutCommunityShortDto> foundCommunity = this.items.stream()
                .filter(item -> item.getId().equals(dto.getId()))
                .findFirst();
        if (foundCommunity.isPresent()) {
            // Update community in list
            foundCommunity.get().pushValues(dto);
        } else {
            // Add community to list
            this.items.add(conversionService.convert(dto, OutCommunityShortDto.class));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        public final TextView vName;
        public final TextView vDescription;
        public OutCommunityShortDto mItem;

        public ViewHolder(@NonNull CardView view) {
            super(view);
            cardView = view;
            vName = (TextView) view.findViewById(R.id.name);
            vDescription = (TextView) view.findViewById(R.id.community_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + vName.getText() + "'";
        }
    }

    public void loadNextDataFromApi(int offset) {
        pullData();
    }
}
