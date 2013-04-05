package pxkw.mediamutefrog;

import android.media.AudioManager;

public class CommonValues {
	public static final int    STREAM_TYPE         = AudioManager.STREAM_MUSIC;
	public static final String LOG_TAG             = "MediaMuteFrog";
	public static final String MESSAGE_MUTE        = "Muted."; 
	public static final String MESSAGE_UNMUTE      = "Unmuted."; 
	public static final String ACTION_WIDGET_TOUCH = "pxkw.mediamutefrog.intent.ACTION_WIDGET_TOUCH";
	public static final String ACTION_WIDGET_CHECK = "pxkw.mediamutefrog.intent.ACTION_WIDGET_CHECK";
}
