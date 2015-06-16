package com.imageviewdimen.touchspring.newcardemo.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.imageviewdimen.touchspring.newcardemo.R;

/**
 * @author lizhen 自定义Toast防止重复显示
 */
public class CustomToast {
	private static Toast mToast;
	private static View view;
	private static TextView mTextView;

	/** toast显示的位置 */
	public static final int TOP = 1;
	public static final int CENTER = 2;
	public static final int BOTTOM = 3;

	public static void showToast(Context context, String text, int duration,
			int location) {
		if (mToast == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.dialog_toast_black, null);
			mTextView = (TextView) view.findViewById(R.id.toast_message);
			mTextView.setText(text);
			mToast = new Toast(context);
			mToast.setDuration(duration);
			mToast.setView(view);
		} else {
			mTextView.setText(text);
		}
		setLoaction(location);
		mToast.show();
	}

	public static void showToast(Context context, String text, int duration) {
		showToast(context, text, duration, BOTTOM);
	}

	public static void showToast(Context context, int textId, int duration) {
		showToast(context, context.getResources().getString(textId), duration,
				BOTTOM);
	}

	public static void showToast(Context context, int textId, int duration,
			int location) {
		showToast(context, context.getResources().getString(textId), duration,
				location);
	}

	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	private static void setLoaction(int location) {
		if (location == 1) {
			mToast.setGravity(Gravity.TOP, 0, 300);
		} else if (location == 2) {
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setGravity(Gravity.BOTTOM, 0, 150);
		}
	}

}
