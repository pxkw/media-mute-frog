package pxkw.mediamutefrog;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MediaMuteFrogService extends Service {
	
	private RemoteViews remoteViews;
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.remoteViews = new RemoteViews(getPackageName(), R.layout.main);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.v(CommonValues.LOG_TAG, "onBind");
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(CommonValues.ACTION_WIDGET_TOUCH.equals(intent.getAction())){
			onClick();
		}
		else if( CommonValues.ACTION_WIDGET_CHECK.equals(intent.getAction())){
			onCheck();
		}
		updateAppWidget();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void updateAppWidget(){
		// ImageViewにクリックで起動するPendingIntentを仕込む
		Intent touchIntent = new Intent( CommonValues.ACTION_WIDGET_TOUCH );
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, touchIntent, 0);
		this.remoteViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);

		ComponentName cn = new ComponentName(getPackageName(), MediaMuteFrogWidgetProvider.class.getName());
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(cn, this.remoteViews);
	}
	 
	private void onCheck(){
		Log.v(CommonValues.LOG_TAG, "onCheck");
		AudioManager aManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		boolean isMute = ( aManager.getStreamVolume(CommonValues.STREAM_TYPE)==0 );
		if( isMute )
			this.remoteViews.setImageViewResource(R.id.imageView, R.drawable.frog_off );
		else 
			this.remoteViews.setImageViewResource(R.id.imageView, R.drawable.frog_on );
	}
	

	private void onClick(){
		Log.v(CommonValues.LOG_TAG, "onClick");
		AudioManager aManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		boolean isMute = ( aManager.getStreamVolume(CommonValues.STREAM_TYPE)==0 );
		boolean isNextMute = !isMute;
		if( isNextMute )
			setMute(aManager, true);
		else
			setUnmute(aManager, true);
	}
	
	private void setMute( AudioManager aManager, boolean doShowToast ){
		this.remoteViews.setImageViewResource(R.id.imageView, R.drawable.frog_off );
		aManager.setStreamVolume(CommonValues.STREAM_TYPE, 0, 0);
		if( doShowToast )
			showToast(CommonValues.MESSAGE_MUTE);
		Log.v(CommonValues.LOG_TAG, "mute");
	}
	
	private void setUnmute( AudioManager aManager, boolean doShowToast ){
		this.remoteViews.setImageViewResource(R.id.imageView, R.drawable.frog_on );
		aManager.setStreamVolume(CommonValues.STREAM_TYPE, 1, AudioManager.FLAG_SHOW_UI);
		if( doShowToast )
			showToast(CommonValues.MESSAGE_UNMUTE);
		Log.v(CommonValues.LOG_TAG, "unmute");
	}
	
	private void showToast( String message ){
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	@Override
	public void onDestroy() {
		Log.v(CommonValues.LOG_TAG, "onDestroy");
		super.onDestroy();
		startService(new Intent(this, MediaMuteFrogWidgetProvider.class));
	}

}
