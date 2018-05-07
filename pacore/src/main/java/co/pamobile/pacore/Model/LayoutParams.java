package co.pamobile.pacore.Model;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by tuongvan on 3/2/18.
 */

public class LayoutParams {
    public void setParamMargin(View view, int top, int bottom, int left, int right) {
        FrameLayout.LayoutParams layoutParams = null;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.topMargin = top;
        layoutParams.bottomMargin = bottom;
        layoutParams.rightMargin = right;
        layoutParams.leftMargin = left;
        //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        view.setLayoutParams(layoutParams);
    }

    public void setParamWidthHeight(View view, int width, int height) {
        FrameLayout.LayoutParams layoutParams = null;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width = width;
        layoutParams.height = height;
        //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        view.setLayoutParams(layoutParams);
    }

    int getStatusHeight(Activity mActivity) {
        Rect rectangle = new Rect();
        Window window = mActivity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentViewTop - statusBarHeight;

    }

    int getNavBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    int getActioBarHeight(Activity mActivity) {
        int h = 0;
        TypedValue tv = new TypedValue();
        if (mActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            h = TypedValue.complexToDimensionPixelSize(tv.data, mActivity.getResources().getDisplayMetrics());
        }
        return h;
    }
}
