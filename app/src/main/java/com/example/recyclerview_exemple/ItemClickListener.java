package com.example.recyclerview_exemple;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}

