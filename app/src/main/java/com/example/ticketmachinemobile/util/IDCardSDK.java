package com.example.ticketmachinemobile.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.huashi.otg.sdk.HSIDCardInfo;
import com.huashi.otg.sdk.HsOtgApi;

import org.greenrobot.eventbus.EventBus;

import java.io.FileInputStream;
import java.io.IOException;

public class IDCardSDK {

    private static final String TAG = IDCardSDK.class.getName();

    private static IDCardSDK idCardSDK;

    public static final int INIT_SUCCESS = 1;

    private static Thread readCardThread;

    private String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";


    private HsOtgApi hsOtgApi;
    private static volatile boolean isReadingCard = false;
    private Handler handler;

    private IDCardSDK() {

    }

    public static IDCardSDK getInstance() {
        if (idCardSDK == null) {
            idCardSDK = new IDCardSDK();
        }
        return idCardSDK;
    }


    public int initSDK(Handler handler, Context context) {
        this.handler = handler;

        hsOtgApi = new HsOtgApi(handler, context);
        // 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息
        int ret = hsOtgApi.init();

        if (ret == INIT_SUCCESS) {
            Log.i("initSDK", "初始化 身份证读卡器成功");
        }
        return ret;
    }

    public void unInitSDK() {
        hsOtgApi.unInit();
    }

    public void StartReadCard() {
        if (isReadingCard) {
            return;
        }
        readCardThread = new Thread(new ReadCardRunnable());
        readCardThread.start();
        isReadingCard = true;
    }

    public void StopReadCard() {
        if (readCardThread != null && !readCardThread.isInterrupted())
            readCardThread.interrupt();
    }

    private class ReadCardRunnable implements Runnable {
        @Override
        public void run() {
            HSIDCardInfo icCardInfo;
            try {
                while (true) {
                    synchronized (hsOtgApi) {
                        Thread.sleep(4 * 100);
                        if (hsOtgApi.Authenticate(200, 200) != 1) {
                            Thread.sleep(1000);
                        } else {
                            icCardInfo = new HSIDCardInfo();
                            ReadCardEvent cardEvent = new ReadCardEvent();
                            if (hsOtgApi.ReadCard(icCardInfo, 200, 1300) == 1) {
                                cardEvent.setCardInfo(icCardInfo);
                                EventBus.getDefault().post(cardEvent);
                                throw new InterruptedException();
                            } else {
                                Thread.sleep(1000);
                            }
                            //SystemClock.sleep(3 * 1000);
                        }
                    }
                }
            } catch (InterruptedException e) {
                isReadingCard = false;
            }
        }
    }

    private Bitmap unZipHeadPic(HSIDCardInfo icCardInfo) {
        Bitmap bmp = null;
        int ret = hsOtgApi.Unpack(filepath, icCardInfo.getwltdata());// 照片解码
        if (ret != 0) {
            return bmp;
        }

        try {
            FileInputStream fis = new FileInputStream(filepath + "/zp.bmp");
            bmp = BitmapFactory.decodeStream(fis);
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

}
