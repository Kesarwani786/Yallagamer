package com.augurs.yallagamers.api_module;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.augurs.yallagamers.R;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class DialogUtil {

	public static void showDialog(final Context ctx, String msg)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
			.setTitle(ctx.getString(R.string.dialog_title_msg))
			.setMessage(msg)
			.setPositiveButton(ctx.getString(R.string.dialog_btn_ok), null)
			.create();
		alertDialog.show();
	}
	
	
	public static void showDialogYesNo(final Context ctx, String title, String msg, final Callback callback)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
			.setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(ctx.getString(R.string.dialog_btn_yes), new DialogInterface.OnClickListener() {
 
                    public void onClick(DialogInterface dialog, int which) {
                    	
                    	callback.get("YES"); 
                    }
                })
            .setNegativeButton(ctx.getString(R.string.dialog_btn_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	callback.get("NO");
                    }
                })
            .create();
        alertDialog.show();
	}

	public static void showDialogDyanmicButtonName(final Context ctx, String title, String msg,String btn_name_1,String btn_name_2, final Callback callback)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(btn_name_1, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						callback.get("YES");
					}
				})
				.setNegativeButton(btn_name_2,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						callback.get("NO");
					}
				})
				.create();
		alertDialog.show();
	}


	//----------Show List Dialog---------------
	public static void showDialogList(Context ctx, String title, final String[] listDesc, final String[] listID, final CallbackList callback)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
				.setTitle(title)
				.setItems(listDesc, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int i) {
						callback.get(listID[i],listDesc[i]);
						dialog.dismiss();
					}
				})
				.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})

				.create();
		alertDialog.show();
	}


	//----------Show Progress Dialog---------------
	public static ProgressDialog getProgressDialog(Context ctx, String msgTitle, String msgValue)
	{
		ProgressDialog mProgressDialog = ProgressDialog.show(ctx, msgTitle, msgValue);

		setDialogFontSize(mProgressDialog,16);

		return mProgressDialog;
	}


	private static void setDialogFontSize(Dialog dialog, int size)
	{
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontSize(view,size);
	}
	private static void setViewFontSize(View view, int size)
	{
		if(view instanceof ViewGroup)
		{
			ViewGroup parent = (ViewGroup)view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++)
			{
				setViewFontSize(parent.getChildAt(i),size);
			}
		}
		else if(view instanceof TextView){
			TextView textview = (TextView)view;
			textview.setTextSize(size);
		}
	}

	//----------Get Progress Message---------------
	public static String getProgressMsg(int curPos, int sumPos)
	{
		int locPer = 0;
		if(curPos <= 0){
			locPer = 0;
		}else if (curPos >= sumPos) {
			locPer = sumPos;
		}else {
			float floc = (float) curPos/sumPos * 100;
			locPer = (int) floc;
		}

		return String.valueOf(locPer);
	}


	//----------Show Mul Select List Dialog---------------
	public static void showDialogMulSelList(String selectedValues, Context ctx, String title, final String[] listDesc, final String[] listID, boolean defValue, final CallbackList2 callback)
	{
		String[] selVal = new String[0];
		//String selectedValues="[str1, str2]";

		if(selectedValues!=null && !selectedValues.trim().isEmpty()) {
			selectedValues = selectedValues.replace("[", "");
			selectedValues = selectedValues.replace("]", "");
			Log.e("data", "=>" + selectedValues);
			if (selectedValues.contains(",")) {
				selVal = selectedValues.split(",");
				Log.e("data", "=>" + selVal.length);
			} else {
				selVal = new String[]{selectedValues};
			}
		}

		final ArrayList MultiChoiceID = new ArrayList();
		final ArrayList MultiChoiceValue = new ArrayList();
		boolean[] itemBolean = null;
		if (listDesc.length > 0) {
			itemBolean = new boolean[listDesc.length];
			for (int i = 0 ; i < listDesc.length; i++) {
				itemBolean[i] = defValue;
				if (defValue == true) MultiChoiceID.add(listID[i]);
			}

			if(selVal!=null && selVal.length>0){
				for(int j=0;j<selVal.length;j++){
					boolean check = Arrays.asList(listID).contains(selVal[j].toString());
					Log.e("check", "=>" + check);
					Log.e("datastr", "=>" + selVal[j].toString());
					int pos = new ArrayList<String>(Arrays.asList(listID)).indexOf(selVal[j].trim().toString());
					Log.e("pos", "=>" + pos);
					itemBolean[pos] = true;
					MultiChoiceID.add(listID[pos]);
					MultiChoiceValue.add(listDesc[pos]);

				}
			}
		}

		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
				.setTitle(title)
				.setMultiChoiceItems(listDesc, itemBolean, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked){
							MultiChoiceID.add(listID[which]);
							MultiChoiceValue.add(listDesc[which]);
						}else {
							MultiChoiceID.remove(listID[which]);
							MultiChoiceValue.remove(listDesc[which]);
						}
					}
				})

				.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//callback.get(MultiChoiceID);
						callback.get(MultiChoiceID,MultiChoiceValue);
						dialog.dismiss();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})

				.create();
		alertDialog.show();


	}


	//----------Interface---------------

	public interface Callback{
        public void get(String result);
    }

	public interface CallbackList{
		public void get(String retID, String retValue);
	}
	public interface CallbackList2{
		public void get(ArrayList retID, ArrayList retValue);
	}
	public interface CallbackArraylist{
		public void get(ArrayList listValue);

	}
}
