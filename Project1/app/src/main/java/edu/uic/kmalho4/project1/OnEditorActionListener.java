package edu.uic.kmalho4.project1;

import android.view.KeyEvent;
import android.widget.TextView;

public interface OnEditorActionListener {
    public abstract boolean onEditorAction(TextView v, int actionId, KeyEvent event);
}
