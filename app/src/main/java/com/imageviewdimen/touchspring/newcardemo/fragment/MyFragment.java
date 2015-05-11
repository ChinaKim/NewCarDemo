package com.imageviewdimen.touchspring.newcardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imageviewdimen.touchspring.newcardemo.R;

/**
 * Created by KIM on 2015/5/11 0011.
 */
public class MyFragment extends Fragment {

    public MyFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,null);
        return view;
    }
}
