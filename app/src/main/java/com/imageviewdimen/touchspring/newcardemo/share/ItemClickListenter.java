package com.imageviewdimen.touchspring.newcardemo.share;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.imageviewdimen.touchspring.newcardemo.R;
import com.imageviewdimen.touchspring.newcardemo.custom.CustomToast;

import java.io.Serializable;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

@SuppressWarnings("deprecation")
public class ItemClickListenter implements OnItemClickListener,
		PlatformActionListener {
	private ListView listView;
	private String tag;
	private Activity context;
	private String url_apk;
	private String shareText;
	private String url_code;
	private NotificationManager manager;
	private Notification notification;
	private PendingIntent pendIntent;
	private String title;

	public ItemClickListenter(Activity context) {
		super();
		this.context = context;
		this.title = context.getResources().getString(R.string.app_name);
	}

	public ItemClickListenter(Activity context, String shareText,
			String url_code, String url_apk) {
		this.context = context;
		this.shareText = shareText;
		this.url_apk = url_apk;
		this.url_code = url_code;
		this.title = context.getResources().getString(R.string.app_name);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		id = parent.getId();
		if (id == R.id.gv_share_view) {
			ShareSDK.initSDK(context);
			caseListSelect(position);
		}
	}

	private void caseListSelect(int position) {
		String nc = Context.NOTIFICATION_SERVICE;
		manager = (NotificationManager) context.getSystemService(nc);
		notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "正在分享";
		notification.when = System.currentTimeMillis();
		pendIntent = PendingIntent.getActivity(context, 0, context.getIntent(),
				0);
		notification.setLatestEventInfo(context, "正在分享", "正在分享", pendIntent);
		manager.notify(1, notification);
		manager.cancel(1);
		// String url_apk = context.getResources().getString(R.string.url_apk);
		// String url_code =
		// context.getResources().getString(R.string.url_code);
		// String shareText = context.getResources().getString(
		// R.string.str_share_qrtext);
		// String imagePath = "file:///android_asset/img_default.9.png";
		switch (position) {
		case 0:
			shareWechat(shareText, url_apk, url_code);
			break;
		case 1:
			shareQQ(shareText, url_code, url_apk);
			break;
		case 2:
			shareWeibo(shareText, SinaWeibo.NAME, new SinaWeibo.ShareParams(),
					url_code, url_apk);
			break;
		default:
			break;
		}

	}

	private void shareWechat(String shareText, String url_apk, String url_code) {
		Wechat.ShareParams sp = new Wechat.ShareParams();
		sp.shareType = Platform.SHARE_WEBPAGE;
		sp.text = shareText;
		sp.imageUrl = url_code;
		sp.url = url_apk;
		sp.title = title;
		Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
		if (wechat.isValid()) {
			Log.i("Share", "--------------->wechat客户端可用");
		} else {
			Log.i("Share", "--------------->wechat客户端不可用");
			CustomToast.showToast(context, "抱歉，您还没有安装微信客户端！", 1000,
					CustomToast.TOP);
		}
		wechat.setPlatformActionListener(this);
		wechat.share(sp);
	}

	private void shareQQ(String shareText, String url_code, String url_apk) {
		QQ.ShareParams sp = new QQ.ShareParams();
		sp.imageUrl = url_code;
		sp.title = title;
		sp.titleUrl = url_apk; // 标题的超链接
		sp.text = shareText;
		Platform qq = ShareSDK.getPlatform(context, QQ.NAME);
		if (qq.isValid()) {
			Log.i("Share", "--------------->qq客户端可用");
		} else {
			Log.i("Share", "--------------->qq客户端不可用");
			CustomToast.showToast(context, "抱歉，您还没有安装QQ客户端！", 1000,
					CustomToast.TOP);
		}
		qq.setPlatformActionListener(this);
		qq.share(sp);
	}

	public void shareWeibo(String text, String weiboName,
			Platform.ShareParams name, String imagePath, String url_apk) {
		Platform.ShareParams sp = name;
		// 分享图片的地址
		((SinaWeibo.ShareParams) sp).imageUrl = imagePath;
		sp.text = text + "下载地址:" + url_apk;
		Platform weibo = ShareSDK.getPlatform(context, weiboName);
		if (weibo.isValid()) {
			Log.i("Share", "--------------->weibo客户端可用");
		} else {
			Log.i("Share", "--------------->weibo客户端不可用");
		}
		weibo.setPlatformActionListener(this);
		weibo.share(sp);
	}

	// 案例的ListView选择监听的实现
	public void caseListSelect(View view, int position) {
		if (view != null && view instanceof LinearLayout) {
			Object item = listView.getItemAtPosition(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable(tag, (Serializable) item);
		}
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		Log.i("Share", "--------------->分享失败");
		notification.tickerText = "分享失败";
		notification.setLatestEventInfo(context, "分享失败", "分享失败", pendIntent);
		manager.notify(1, notification);
		manager.cancel(1);
		arg2.printStackTrace();
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		Log.i("Share", "--------------->分享成功");
		notification.tickerText = "分享成功";
		notification.setLatestEventInfo(context, "分享成功", "分享成功", pendIntent);
		manager.notify(1, notification);
		manager.cancel(1);
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		Log.i("Share", "--------------->取消分享");
		notification.tickerText = "取消分享";
		notification.setLatestEventInfo(context, "取消分享", "取消分享", pendIntent);
		manager.notify(1, notification);
		manager.cancel(1);
	}

}
