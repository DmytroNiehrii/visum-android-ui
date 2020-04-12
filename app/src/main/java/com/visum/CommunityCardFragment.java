package com.visum;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.visum.api.CommunityApi;
import com.visum.api.handler.CommunityItemResponseHandler;
import com.visum.dto.InCommunityDto;
import com.visum.dto.OutCommunityDto;
import com.visum.listener.CommunityUpdateListener;

import java.util.HashSet;
import java.util.Set;

public class CommunityCardFragment extends Fragment {

    public static final String EXTRA_COMMUNITY_ID = "id";
    public static final String EXTRA_EDIT_MODE = "edit-mode";

    private Long id;
    private OutCommunityDto outCommunityDto;
    private boolean editMode = false;

    private AutoCompleteTextView vName;
    private MultiAutoCompleteTextView vDescription;
    private FloatingActionButton cancelButton;
    private FloatingActionButton editButton;
    private FloatingActionButton saveButton;

    private Set<CommunityUpdateListener> communityUpdateListeners = new HashSet();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommunityCardFragment() {}

    public static CommunityCardFragment getInstance(Long id, boolean editMode) {
        CommunityCardFragment fragment = new CommunityCardFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_COMMUNITY_ID, id);
        args.putBoolean(EXTRA_EDIT_MODE, editMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getLong(EXTRA_COMMUNITY_ID, 0);
            this.editMode = getArguments().getBoolean(EXTRA_EDIT_MODE, false);
        }
        CommunityApi.getCommunityItem(new CommunityItemResponseHandler() {
            @Override
            public void handle(OutCommunityDto community) {
                CommunityCardFragment.this.outCommunityDto = community;
                bind(community);
                setEditMode(editMode);
            }
        }, id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_card, container, false);
        FloatingActionButton editButton = (FloatingActionButton)view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode(true);
            }
        });
        FloatingActionButton cancelButton = (FloatingActionButton)view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode(false);
                bind(CommunityCardFragment.this.outCommunityDto);
            }
        });
        FloatingActionButton saveButton = (FloatingActionButton)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InCommunityDto dto = InCommunityDto.builder()
                        .name(vName.getText().toString())
                        .description(vDescription.getText().toString())
                        .build();
                CommunityApi.updateCommunity(CommunityCardFragment.this.id, dto, new CommunityItemResponseHandler() {
                    @Override
                    public void handle(OutCommunityDto community) {
                        Toast.makeText(getActivity(), R.string.community_updated, Toast.LENGTH_SHORT).show();
                        CommunityCardFragment.this.outCommunityDto = community;
                        setEditMode(false);
                        bind(CommunityCardFragment.this.outCommunityDto);
                        //communityUpdateListeners.stream().forEach(listener -> listener.onCommunityUpdate(community));

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //bind(this.outCommunityDto);
        //setEditMode(this.editMode);
    }

    private void bind(OutCommunityDto community) {
        vName = (AutoCompleteTextView)getView().findViewById(R.id.community_card_name);
        vDescription = (MultiAutoCompleteTextView)getView().findViewById(R.id.community_card_description);
        vName.setText(community.getName());
        vDescription.setText(community.getDescription());
        cancelButton = (FloatingActionButton)getView().findViewById(R.id.cancelButton);
        editButton = (FloatingActionButton)getView().findViewById(R.id.editButton);
        saveButton = (FloatingActionButton)getView().findViewById(R.id.saveButton);
    }

    @SuppressLint("RestrictedApi")
    private void setEditMode(boolean editMode) {
        this.editMode = editMode;
        vName.setEnabled(this.editMode);
        vDescription.setEnabled(this.editMode);

        cancelButton.setVisibility(editMode ? View.VISIBLE : View.GONE);
        editButton.setVisibility(editMode ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(editMode ? View.VISIBLE : View.GONE);


    }

    public void addCommunityUpdateListener(CommunityUpdateListener listener) {
        this.communityUpdateListeners.add(listener);
    }

    /*public void onClickEdit(View view) {
        setEditMode(true);
    }*/

    /*public void onClickCancel(View view) {
        setEditMode(false);
        bind(this.outCommunityDto);
    }*/

    /*public void onClickSave(View view) {
        InCommunityDto dto = InCommunityDto.builder()
                .name(vName.getText().toString())
                .description(vDescription.getText().toString())
                .build();
        CommunityApi.updateCommunity(this.id, dto, new CommunityItemResponseHandler() {
            @Override
            public void handle(OutCommunityDto community) {
                Toast.makeText(getActivity(), R.string.community_updated, Toast.LENGTH_SHORT).show();
                CommunityCardActivity.this.outCommunityDto = community;
                setEditMode(false);
                bind(CommunityCardActivity.this.outCommunityDto);
                //communityUpdateListeners.stream().forEach(listener -> listener.onCommunityUpdate(community));

            }
        });
    }*/
}
