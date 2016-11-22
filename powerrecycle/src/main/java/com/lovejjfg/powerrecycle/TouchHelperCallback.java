package com.lovejjfg.powerrecycle;

import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lovejjfg.powerrecycle.holder.NewBottomViewHolder;


/**
 * Created by Joe on 2016-03-28
 * Email: lovejjfg@163.com
 */
public class TouchHelperCallback extends ItemTouchHelper.Callback {
    //        int swipeFlags =  ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        return makeMovementFlags(0, swipeFlags);
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.ACTION_STATE_IDLE);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return callBack != null  && callBack.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (callBack != null && !(viewHolder instanceof NewBottomViewHolder)) {
            callBack.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float width = (float) viewHolder.itemView.getWidth();
            float alpha = 1.0f - Math.abs(dX) / width;
            viewHolder.itemView.setAlpha(alpha);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            if (isCurrentlyActive) {
                viewHolder.itemView.setScaleX(1.2f);
                viewHolder.itemView.setScaleY(1.2f);
            } else {
                viewHolder.itemView.setScaleX(1);
                viewHolder.itemView.setScaleY(1);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                actionState, isCurrentlyActive);
    }


    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void setItemDragSwipeCallBack(@Nullable ItemDragSwipeCallBack callBack) {
        this.callBack = callBack;
    }

    @Nullable
    private ItemDragSwipeCallBack callBack;

    public interface ItemDragSwipeCallBack {
        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }
}
