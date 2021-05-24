package com.example.todolist.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.Toadapter;
import com.example.todolist.R;

public class Recycleritemtouchhelper extends ItemTouchHelper.SimpleCallback {
    private Toadapter Adapter;
    public Recycleritemtouchhelper(Toadapter adapter)
    {
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        Adapter = adapter;
    }
  @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target)
  {
      return (false);
  }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction ==ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this task?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Adapter.deleteitem(position);
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Adapter.edititem(position);

        }
    }

@Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,float dX,float dY,int actionState,boolean isCurrentState)
{

    Drawable icon;
    ColorDrawable background;
    View item = viewHolder.itemView;
    int offset =20;
    if(dX>0)
    {
        icon= ContextCompat.getDrawable(Adapter.getContext(), R.drawable.ic_baseline_edit);
        background = new ColorDrawable(ContextCompat.getColor(Adapter.getContext(),R.color.green));
    }
    else
    {
        icon = ContextCompat.getDrawable(Adapter.getContext(), R.drawable.ic_baseline_delete);
        background = new ColorDrawable(ContextCompat.getColor(Adapter.getContext(),R.color.red));
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentState);

    }
    int iconMargin = (item.getHeight() - icon.getIntrinsicHeight())/2;
    int icontop = item.getTop() + (item.getHeight() - icon.getIntrinsicHeight())/2;
    int iconbottom = icontop + icon.getIntrinsicHeight();

    if(dX>0)
    {
        int iconLeft = item.getLeft() + iconMargin;
        int iconRight = item.getRight() + iconMargin + icon.getIntrinsicWidth();
        icon.setBounds(iconLeft,icontop,iconRight,iconbottom);
        background.setBounds(item.getLeft(),item.getTop(), item.getLeft() + ((int)dX) + offset,item.getBottom());

}
    else if(dX<0)
    {
        int iconLeft = item.getRight() - iconMargin -icon.getIntrinsicWidth();
        int iconRight = item.getRight() - iconMargin ;
        icon.setBounds(iconLeft,icontop,iconRight,iconbottom);
        background.setBounds(item.getRight() + ((int)dX)-offset ,item.getTop(), item.getRight(),item.getBottom());
    }
    else
    {
        background.setBounds(0,0,0,0);
    }

background.draw(c);
    icon.draw(c);


}


}
