package com.imageviewdimen.touchspring.newcardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.imageviewdimen.touchspring.newcardemo.R;
import com.imageviewdimen.touchspring.newcardemo.custom.ShareAppDialog;
import com.imageviewdimen.touchspring.newcardemo.share.ItemClickListenter;

/**
 * Created by KIM on 2015/5/11 0011.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Button btn_share;


    private ShareAppDialog shareAppDialog;

    public MyFragment() {
    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        initListener();
    }

    @Override
    protected void initView(View view) {
        btn_share = (Button) view.findViewById(R.id.btn_share);


    }

    @Override
    protected void initListener() {
        btn_share.setOnClickListener(this);

    }

    @Override
    protected void initAdapter() {

    }

    @Override
    protected void initListenerData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_share) {
            showDialog(v);
        }
    }

    private void showDialog(View view) {
        shareAppDialog = new ShareAppDialog.Builder(getActivity())
                .setGvItemClick("", new ItemClickListenter(getActivity(), getRes()
                .getString(R.string.product_sharetext), getRes()
                .getString(R.string.url_code), getRes()
                .getString(R.string.url_root_Huang))).setNoButton(getRes().getString(R.string.cancle), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareAppDialog.dismiss();
            }
        }).show();
    }
}
