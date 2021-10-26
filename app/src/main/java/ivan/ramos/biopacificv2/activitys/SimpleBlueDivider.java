package ivan.ramos.biopacificv2.activitys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import ivan.ramos.biopacificv2.R;

public class SimpleBlueDivider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public SimpleBlueDivider(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.recycler_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //divider padding give some padding whatever u want or disable
        int left =parent.getPaddingLeft()+260;
        int right = parent.getWidth() - parent.getPaddingRight()-30;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

}