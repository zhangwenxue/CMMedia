package com.cm.media.ui.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.cm.media.databinding.IFragmentBinding;
import com.cm.media.repository.db.entity.VodHistory;
import com.cm.media.ui.adapter.VodHistoryAdapter;
import com.cm.media.util.CollectionUtils;
import com.cm.media.viewmodel.MineViewModel;

public class MineFragment extends Fragment {

    private MineViewModel mViewModel;
    private IFragmentBinding binding;
    private VodHistoryAdapter mAdapter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = IFragmentBinding.inflate(inflater, container, false);
        return binding.root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        mAdapter = new VodHistoryAdapter(mViewModel.getVodHistoryList().getValue());
        mAdapter.bindToRecyclerView(binding.recyclerView);
        mViewModel.getVodHistoryList().observe(this, vodHistories -> {
            mAdapter.setNewData(vodHistories);
            binding.setHasHistory(!CollectionUtils.isEmptyList(vodHistories));
        });
        mViewModel.start(getActivity());
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        binding.recyclerView.setAdapter(mAdapter);


    }

    private VodHistory mSwipeHistory;
    private OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            mSwipeHistory = mAdapter.getData().get(pos);
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            if (mSwipeHistory != null) {
                mViewModel.removeHistory(getActivity(), mSwipeHistory);
            }
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };


}
