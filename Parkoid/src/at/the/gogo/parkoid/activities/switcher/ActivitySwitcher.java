/*
 * Copyright (c) 2011 Robert Heim
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package at.the.gogo.parkoid.activities.switcher;

import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import at.the.gogo.parkoid.animation.Rotate3dAnimation;

/**
 * This ActivitySwitcher uses a 3D rotation to animate an activity during its
 * start or finish.
 * 
 * see: http://blog.robert-heim.de/karriere/android-startactivity-rotate-3d-
 * animation-activityswitcher/
 * 
 * @author Robert Heim
 * 
 */
public class ActivitySwitcher {

    private final static int   DURATION = 300;
    private final static float DEPTH    = 400.0f;

    /* ----------------------------------------------- */

    public interface AnimationFinishedListener {
        /**
         * Called when the animation is finished.
         */
        public void onAnimationFinished();
    }

    /* ----------------------------------------------- */

    public static void animationIn(final View container,
            final WindowManager windowManager) {
        animationIn(container, windowManager, null);
    }

    public static void animationIn(final View container,
            final WindowManager windowManager,
            final AnimationFinishedListener listener) {
        apply3DRotation(90, 0, false, container, windowManager, listener);
    }

    public static void animationOut(final View container,
            final WindowManager windowManager) {
        animationOut(container, windowManager, null);
    }

    public static void animationOut(final View container,
            final WindowManager windowManager,
            final AnimationFinishedListener listener) {
        apply3DRotation(0, -90, true, container, windowManager, listener);
    }

    /* ----------------------------------------------- */

    private static void apply3DRotation(final float fromDegree,
            final float toDegree, final boolean reverse, final View container,
            final WindowManager windowManager,
            final AnimationFinishedListener listener) {
        final Display display = windowManager.getDefaultDisplay();
        final float centerX = display.getWidth() / 2.0f;
        final float centerY = display.getHeight() / 2.0f;

        final Rotate3dAnimation a = new Rotate3dAnimation(fromDegree, toDegree,
                centerX, centerY, DEPTH, reverse);
        a.reset();
        a.setDuration(DURATION);
        a.setFillAfter(true);
        a.setInterpolator(new AccelerateInterpolator());
        if (listener != null) {
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(final Animation animation) {
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    listener.onAnimationFinished();
                }
            });
        }
        container.clearAnimation();
        container.startAnimation(a);
    }
}
