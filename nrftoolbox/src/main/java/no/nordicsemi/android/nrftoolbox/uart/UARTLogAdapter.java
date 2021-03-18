/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.nordicsemi.android.nrftoolbox.uart;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import androidx.annotation.NonNull;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import no.nordicsemi.android.log.LogContract.Log.Level;
import no.nordicsemi.android.nrftoolbox.R;

public class UARTLogAdapter extends CursorAdapter {
	private static final SparseIntArray colors = new SparseIntArray();

	static {
		colors.put(Level.DEBUG, 0xFF009CDE);
		colors.put(Level.VERBOSE, 0xFFB8B056);
		colors.put(Level.INFO, Color.BLACK);
		colors.put(Level.APPLICATION, 0xFF238C0F);
		colors.put(Level.WARNING, 0xFFD77926);
		colors.put(Level.ERROR, Color.RED);
	}

	UARTLogAdapter(@NonNull final Context context) {
		super(context, null, 0);
	}

	@Override
	public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
		final View view = LayoutInflater.from(context).inflate(R.layout.log_item, parent, false);

		final ViewHolder holder = new ViewHolder();
		holder.time = view.findViewById(R.id.time);
		holder.data = view.findViewById(R.id.data);
		view.setTag(holder);
		return view;
	}

	@Override
	public void bindView(final View view, final Context context, final Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cursor.getLong(1 /* TIME */));
		holder.time.setText(context.getString(R.string.log, calendar));

		final int level = cursor.getInt(2 /* LEVEL */);
		holder.data.setText(cursor.getString(3 /* DATA */));
		holder.data.setTextColor(colors.get(level));

		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();

		String ip = Formatter.formatIpAddress(manager.getConnectionInfo().getIpAddress());

		//Log.i("TAG", "cursor.getString(3 /* DATA */) : " + cursor.getString(3 /* DATA */).replace("\"", "").replace("received", "").trim());



		if(cursor.getString(3 /* DATA */).contains("received")){
			String str = cursor.getString(3 /* DATA */).replace("\"", "").replace("received", "").trim();

			if(!str.split(",")[0].equals("0")){


			Map<String, String> params = new HashMap<String, String>();
			params.put("macaddress", address);
			//params.put("heartrate", cursor.getString(3 /* DATA */).replace("received", "").replace("\"", "").split(",")[0]);//cursor.getString(3 /* DATA */).split(",")[0]);
			//params.put("spo2", cursor.getString(3 /* DATA */).replace("received", "").replace("\"", "").split(",")[1]);//cursor.getString(3 /* DATA */).split(",")[1]);
			params.put("heartrate", str.split(",")[1]);
			params.put("spo2", str.split(",")[0]);
			params.put("temperature", "36");
			params.put("ip", ip);

			SharedPreferences  mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			String id = mSharedPref.getString("memberid", "");

			//Log.i("TAG" , "context.getSharedPreferences().getString(\"password\", \"\") : " + id);

			params.put("memberid", id);
			RequestQueue requestQueue = Volley.newRequestQueue(context);
			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
					"http://nanonewworld.cafe24.com/api/create.php", new JSONObject(params),
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.i("JSONPost", response.toString());
							//pDialog.hide();
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i("JSONPost", "Error: " + error.getMessage());
							//pDialog.hide();
						}
			});
			requestQueue.add(jsonObjReq);

			}

		}




	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	private class ViewHolder {
		private TextView time;
		private TextView data;
	}



}
