package com.cm.media.util;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.meizu.flyme.blur.drawable.BlurDrawable;

public class BlurUtil {
    private BlurUtil() {
    }

    public static Drawable createBlurDrawable(int background, int divider, float level, Rect layerInset) {
        ColorDrawable dividerDrawable = new ColorDrawable(divider);
        BlurDrawable blurDrawable = new BlurDrawable();
        blurDrawable.setColorFilter(background);
        blurDrawable.setBlurLevel(level);
        blurDrawable.setForce(true);
        LayerDrawable ld = new LayerDrawable(new Drawable[]{blurDrawable, dividerDrawable});
        ld.setLayerInset(1, layerInset.left, layerInset.top, layerInset.right, layerInset.bottom);
        return ld;
    }
}
