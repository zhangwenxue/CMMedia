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
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.cm.media.R;
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
        mViewModel.getVodHistoryList().observe(this, vodHistories -> {
            mAdapter.setNewData(vodHistories);
            binding.setHasHistory(!CollectionUtils.isEmptyList(vodHistories));
        });
        mViewModel.start(getActivity());
        binding.recyclerView.setAdapter(mAdapter);
        // 开启滑动删除
        mAdapter.enableSwipeItem();
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    private OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            int index = viewHolder.getAdapterPosition();
            if (index >= 0 && index < mAdapter.getItemCount()) {
                mViewModel.removeHistory(getContext(), mAdapter.getData().get(index));
            }
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };


}
