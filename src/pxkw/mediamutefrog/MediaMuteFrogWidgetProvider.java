package pxkw.mediamutefrog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public final class MediaMuteFrogWidgetProvider extends AppWidgetProvider {
	
	@Override
	public void onEnabled(Context context) {
		Log.v(CommonValues.LOG_TAG, "onEnabled");
		super.onEnabled(context);
		
		Intent intent = new Intent(context, MediaMuteFrogService.class);
		context.startService(intent);
	}
	
	/**
	 * Will be called every 30 minutes.
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v(CommonValues.LOG_TAG, "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Intent intent = new Intent(context, MediaMuteFrogService.class);
		intent.setAction(CommonValues.ACTION_WIDGET_CHECK);
		context.startService(intent);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v(CommonValues.LOG_TAG, "onDisabled");
		super.onDeleted(context, appWidgetIds);
		
		Intent intent = new Intent(context, MediaMuteFrogService.class);
		context.stopService(intent);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(CommonValues.LOG_TAG, "onReceive");
		super.onReceive(context, intent);
		
	}
}
