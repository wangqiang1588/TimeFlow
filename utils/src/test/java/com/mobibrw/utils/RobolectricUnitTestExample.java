package com.mobibrw.utils;

import android.os.Build;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@Config(application = android.app.Application.class, manifest = Config.NONE, sdk = Build.VERSION_CODES.P)
@PrepareForTest(Example.class)
public class RobolectricUnitTestExample {

    @Before
    public void setUp() {
        //输出日志
        ShadowLog.stream = System.out;
    }

    @Test
    public void robolectricExample() {
        Example example = PowerMockito.mock(Example.class);
        Mockito.when(example.inc(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
        example.inc(1, 1);

        Mockito.verify(example, Mockito.times(1)).inc(Matchers.anyInt(), Matchers.anyInt());
    }
}
