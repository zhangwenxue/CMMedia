package com.cm.media.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class WebAddressToolbar extends Toolbar {
    public WebAddressToolbar(Context context) {
        this(context,null);
    }

    public WebAddressToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WebAddressToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
