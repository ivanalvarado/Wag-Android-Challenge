package com.ivanalvaradoapps.wagandroidchallenge.helper;

import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by ivanalvarado on 2/28/18.
 */

public class URLSpanNoUnderline extends URLSpan {

    public URLSpanNoUnderline (String p_Url) {
        super(p_Url);
    }

    public void updateDrawState(TextPaint p_DrawState) {
        super.updateDrawState(p_DrawState);
        p_DrawState.setUnderlineText(false);
    }
}

