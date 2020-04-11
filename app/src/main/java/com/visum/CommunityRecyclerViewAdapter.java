package com.visum;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visum.CommunityFragment.OnListFragmentInteractionListener;
import com.visum.community.CommunityContent;
import com.visum.community.CommunityContent.CommunityItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CommunityItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    public CommunityRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.community_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<CommunityItem> mValues = CommunityContent.getInstance().getItems();
        holder.mItem = mValues.get(position);
        holder.vName.setText(mValues.get(position).id);
        holder.vDescription.setText(mValues.get(position).name);

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
        return CommunityContent.getInstance().getItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        public final TextView vName;
        public final TextView vDescription;
        public CommunityItem mItem;

        public ViewHolder(@NonNull CardView view) {
            super(view);
            cardView = view;
            vName = (TextView) view.findViewById(R.id.community_name);
            vDescription = (TextView) view.findViewById(R.id.community_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + vName.getText() + "'";
        }
    }
}
