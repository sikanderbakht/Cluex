package com.example.cluex.Helper;

import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by salman on 8/7/17.
 */




    public class MapMarkerBounce implements GoogleMap.OnMarkerClickListener {

        private final Handler mHandler;
        private Runnable mAnimation;

        public MapMarkerBounce() {
            mHandler = new Handler();
        }

        @Override
        public boolean onMarkerClick( Marker marker) {
            // This causes the marker at Perth to bounce into position when it is clicked.
            final long start = SystemClock.uptimeMillis();
            final long duration = 3000L;

            // Cancels the previous animation
            mHandler.removeCallbacks(mAnimation);

            // Starts the bounce animation
            mAnimation = new BounceAnimation(start, duration, marker, mHandler);
            mHandler.post(mAnimation);
            // for the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            return false;
        }

        /**
         * Performs a bounce animation on a {@link Marker}.
         */
        private static class BounceAnimation implements Runnable {

            private final long mStart, mDuration;
            private final Interpolator mInterpolator;
            private final Marker mMarker;
            private final Handler mHandler;

            private BounceAnimation(long start, long duration, Marker marker, Handler handler) {
                mStart = start;
                mDuration = duration;
                mMarker = marker;
                mHandler = handler;
                mInterpolator = new BounceInterpolator();
            }

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - mStart;
                float t = Math.max(1 - mInterpolator.getInterpolation((float) elapsed / mDuration), 0f);
                mMarker.setAnchor(0.5f, 1.0f + 1.2f * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    mHandler.postDelayed(this, 30L);
                }
            }
        }


}