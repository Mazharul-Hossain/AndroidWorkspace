package org.wooyd.android.JNIExample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class JNIExampleInterface {

	static Handler h;

	public JNIExampleInterface() {
	}

	public JNIExampleInterface(Handler h) {
		this.h = h;
	}

	public static native void callVoid();

	public static native Data getNewData(int i, String s);

	public static native String getDataString(Data d);

	public static void callBack(String s) {

		Bundle b = new Bundle();
		b.putString("callback_string", s);
		Message m = Message.obtain();
		m.setData(b);
		m.setTarget(h);
		m.sendToTarget();
	}
}
