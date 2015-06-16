package com.imageviewdimen.touchspring.newcardemo.custom;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imageviewdimen.touchspring.newcardemo.R;

/**
 * Created by KIM on 2015/5/21 0021.
 */
public class ShareAppDialog extends Dialog{

    private static GridView gv_share_view;
    private static Button btn_cancel;
    private static Window dialogWindow;

    public ShareAppDialog(Context context){
      this(context, R.style.myStyle, R.layout.dialog_shareapp);
    }


    public ShareAppDialog(Context context, int theme,int layoutId) {
        super(context, theme);
        dialogWindow = this.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId);
        initView();
    }

    private void initView() {
        gv_share_view = (GridView) findViewById(R.id.gv_share_view);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        ShareAdapter shareAdapter = new ShareAdapter();
        gv_share_view.setAdapter(shareAdapter);
    }

    class ShareAdapter extends BaseAdapter{
        private int[] imgIds = { R.drawable.weixing_share, R.drawable.qq_share,
                R.drawable.sina_share };
        private int[] strs = { R.string.weixin, R.string.QQ, R.string.weibo };
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = ShareAppDialog.this.getLayoutInflater().inflate(R.layout.item_shareproduct,null);
                viewHolder = new ViewHolder();
                viewHolder.img_share = (ImageView)convertView.findViewById(R.id.img_share_icon);
                viewHolder.tv_share = (TextView)convertView.findViewById(R.id.tv_share_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder =(ViewHolder) convertView.getTag();
            }
            viewHolder.img_share.setImageResource(imgIds[position]);
            viewHolder.tv_share.setText(strs[position]);
            return convertView;
        }

        class ViewHolder{
            public ImageView img_share;
            public TextView tv_share;
        }
    }


    public static class Builder{
        private ShareAppDialog shareDialog;
        private Context context;

        public Builder(Context context) {
            shareDialog = new ShareAppDialog(context, R.style.myStyle,
                    R.layout.dialog_shareapp);
            this.context = context;
        }

        public Builder setGvItemClick(String messgae,
                                      android.widget.AdapterView.OnItemClickListener itemClickListener) {
            gv_share_view.setOnItemClickListener(itemClickListener);
            return this;
        }

        public Builder setNoButton(String messgae,
                                   android.view.View.OnClickListener clickListener) {
            btn_cancel.setText(messgae);
            btn_cancel.setOnClickListener(clickListener);
            return this;
        }

        public ShareAppDialog show() {
            if (shareDialog != null) {
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                DisplayMetrics dm = new DisplayMetrics();

                shareDialog.getWindow().getWindowManager().getDefaultDisplay()
                        .getMetrics(dm);
                int width = dm.widthPixels;
                lp.width = width - DensityUtil.dip2px(context, 30);
                dialogWindow.setGravity(Gravity.CENTER);
                shareDialog.setCanceledOnTouchOutside(true);
                dialogWindow.setAttributes(lp);
                shareDialog.show();
            }
            return shareDialog;
        }

        public ShareAppDialog getAlterDialog() {
            return shareDialog;
        }

    }

}
