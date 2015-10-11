package br.com.tisengenharia.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Gabriel on 11/10/2015.
 */
class ObjectAlertDialog<T> extends AlertDialog.Builder {
    public T object;

    public AlertDialog.Builder setObject(T object) {
        this.object = object;
        return this;
    }

    public ObjectAlertDialog(Context context) {
        super(context);
        object = null;
    }

    public ObjectAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        object = null;
    }

    public ObjectAlertDialog(Context context, T object) {
        super(context);
        this.object = object;
    }

    public ObjectAlertDialog(Context context, int themeResId, T object) {
        super(context, themeResId);
        this.object = object;
    }

}
