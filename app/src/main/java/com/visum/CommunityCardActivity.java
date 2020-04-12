package com.visum;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class CommunityCardActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getParent() instanceof CommunityUpdateListener) {
            addCommunityUpdateListener((CommunityUpdateListener)getParent());
        }
        setContentView(R.layout.activity_community_card);
        id = getIntent().getLongExtra(EXTRA_COMMUNITY_ID, 0L);
        editMode = getIntent().getBooleanExtra(EXTRA_EDIT_MODE, false);
        CommunityApi.getCommunityItem(new CommunityItemResponseHandler() {
            @Override
            public void handle(OutCommunityDto community) {
                CommunityCardActivity.this.outCommunityDto = community;
                bind(community);
                setEditMode(editMode);
            }
        }, id);
    }

    private void bind(OutCommunityDto community) {
        vName = (AutoCompleteTextView)findViewById(R.id.community_card_name);
        vDescription = (MultiAutoCompleteTextView)findViewById(R.id.community_card_description);
        vName.setText(community.getName());
        vDescription.setText(community.getDescription());
        cancelButton = (FloatingActionButton)findViewById(R.id.cancelButton);
        editButton = (FloatingActionButton)findViewById(R.id.editButton);
        saveButton = (FloatingActionButton)findViewById(R.id.saveButton);
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

    public void onClickEdit(View view) {
        setEditMode(true);
    }

    public void onClickCancel(View view) {
        setEditMode(false);
        bind(this.outCommunityDto);
    }

    public void onClickSave(View view) {
        InCommunityDto dto = InCommunityDto.builder()
                .name(vName.getText().toString())
                .description(vDescription.getText().toString())
                .build();
        CommunityApi.updateCommunity(this.id, dto, new CommunityItemResponseHandler() {
            @Override
            public void handle(OutCommunityDto community) {
                Toast.makeText(CommunityCardActivity.this, R.string.community_updated, Toast.LENGTH_SHORT).show();
                CommunityCardActivity.this.outCommunityDto = community;
                setEditMode(false);
                bind(CommunityCardActivity.this.outCommunityDto);
                //communityUpdateListeners.stream().forEach(listener -> listener.onCommunityUpdate(community));

            }
        });
    }
}
