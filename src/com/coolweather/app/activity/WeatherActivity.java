package com.coolweather.app.activity;


import com.coolweather.app.R;
import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener{
	private LinearLayout weatherInfoLayout;
	//������ʾ������
	private TextView cityNameText;
	//������ʾ����ʱ��
	private TextView publishText;
	//������ʾ����������Ϣ
	private TextView weatherDespText;
	//������ʾ����1
	private TextView temp1Text;
	//������ʾ����2
	private TextView temp2Text;
	//������ʾ��ǰ����
	private TextView currentDateText;
	//�л����а�ť
	private Button switchCity;
	//�������а�ť
	private Button refreshWeather;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		//��ʼ�����ؼ�
		weatherInfoLayout=(LinearLayout)findViewById(R.id.weather_info_layout);
		cityNameText=(TextView)findViewById(R.id.city_name);
		publishText=(TextView)findViewById(R.id.publish_text);
		weatherDespText=(TextView)findViewById(R.id.weather_desp);
		temp2Text=(TextView)findViewById(R.id.temp2);
		temp1Text=(TextView)findViewById(R.id.temp1);		
		currentDateText=(TextView)findViewById(R.id.current_date);
		String countyCode=getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			//���ؼ�����ʱ��ȥ��ѯ����
			publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
			
		}else{
			//û���ؼ�����ʱ��ֱ����ʾ��������
			showWeather();
		}
		
		switchCity=(Button)findViewById(R.id.switch_city);
		switchCity.setOnClickListener(this);
		refreshWeather=(Button)findViewById(R.id.refresh_weather);
		refreshWeather.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent=new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("ͬ����...");
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode=prefs.getString("weather_code", "");
			if(!TextUtils.isEmpty(weatherCode)){
				queryWeatherInfo(weatherCode);
			}
		default:
			break;
		}
	}
	
	//��ѯ�ؼ���������Ӧ����������
	private void queryWeatherCode(String countyCode){
		String address="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address, "countyCode");
	}
	//��ѯ������������Ӧ������
	private void queryWeatherInfo(String weatherCode){
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address, "weatherCode");
	}
	
	
	//���ݴ���ĵ�ַ������ȥ���������ѯ�������Ż���������Ϣ
	private void queryFromServer(final String address,final String type){
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						//�ӷ��������ص������н�������������
						String[] array=response.split("\\|");
						if(array!=null&&array.length==2){
							String weatherCode=array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if("weatherCode".equals(type)){
					//c������������ص�������Ϣ
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showWeather();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						publishText.setText("ͬ��ʧ��");
					}
				});
			}
		} );
	}
	
	//��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ������
	private void showWeather(){
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));		
		temp2Text.setText(prefs.getString("temp2", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("����"+prefs.getString("publish_time", "")+"����");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		Intent intent=new Intent(this, AutoUpdateService.class);
		startService(intent);
	}

}
