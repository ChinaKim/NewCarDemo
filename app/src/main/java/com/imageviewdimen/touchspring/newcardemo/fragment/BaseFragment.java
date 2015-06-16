package com.imageviewdimen.touchspring.newcardemo.fragment;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by KIM on 2015/5/21 0021.
 */
public abstract class BaseFragment extends Fragment {

    public Resources getRes(){
        return getActivity().getResources();
    }

    abstract protected void initView(View view);

    abstract protected void initListener();

    abstract protected void initAdapter();

    abstract protected void initListenerData();

}
