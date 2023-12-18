package com.example.ticketmachinemobile.util;

import android.content.Context;
import android.widget.Toast;
import android.zyapi.CommonApi;

import com.zkteco.android.biometric.core.device.ParameterHelper;
import com.zkteco.android.biometric.core.device.TransportType;
import com.zkteco.android.biometric.module.idcard.IDCardReader;
import com.zkteco.android.biometric.module.idcard.IDCardReaderFactory;
import com.zkteco.android.biometric.module.idcard.IDCardType;
import com.zkteco.android.biometric.module.idcard.exception.IDCardReaderException;
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


public class IDCardSDK {

    private static final String TAG = IDCardSDK.class.getName();

    private static IDCardSDK idCardSDK;


    private static Thread readCardThread;

    private static volatile boolean isReadingCard = false;

    private CommonApi mCommonApi;
    private IDCardReader idCardReader = null;
    private String serialName = "/dev/ttyMT3";



    private IDCardSDK() {

    }

    public static IDCardSDK getInstance() {
        if (idCardSDK == null) {
            idCardSDK = new IDCardSDK();
        }
        return idCardSDK;
    }


    public void initSDK(Context context) {
        int ret = openGPIO(context);
        if(ret!=0)
            return;
        openDevice(context);
    }

    public void unInitSDK() {
        closeDevice();
        closeGPIO();
    }

    public void closeGPIO() {

        mCommonApi.setGpioMode(53, 0);
        mCommonApi.setGpioDir(53, 1);
        mCommonApi.setGpioOut(53, 0);

        mCommonApi.setGpioMode(83, 0);
        mCommonApi.setGpioDir(83, 1);
        mCommonApi.setGpioOut(83, 0);

    }

    public int openGPIO(Context context) {

        mCommonApi = new CommonApi();

        mCommonApi.setGpioMode(53, 0);
        mCommonApi.setGpioDir(53, 1);
        int ret = mCommonApi.setGpioOut(53, 1);

        mCommonApi.setGpioMode(83, 0);
        mCommonApi.setGpioDir(83, 1);
        mCommonApi.setGpioOut(83, 1);

        mCommonApi.setGpioMode(68, 0);
        mCommonApi.setGpioDir(68, 1);
        mCommonApi.setGpioOut(68, 1);

        if (ret == 0) {
            Toast.makeText(context, "身份证模块初始化成功",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "身份证模块初始化失败",
                    Toast.LENGTH_SHORT).show();
        }

        return ret;

    }


    private void openDevice(Context context)
    {
        if (null != idCardReader)
        {
            IDCardReaderFactory.destroy(idCardReader);
            idCardReader = null;
        }
        // Define output log level
        //LogHelper.setLevel(Log.VERBOSE);
        // Start fingerprint sensor
        Map idrparams = new HashMap();
        idrparams.put(ParameterHelper.PARAM_SERIAL_SERIALNAME, serialName);
        idrparams.put(ParameterHelper.PARAM_SERIAL_BAUDRATE, 115200);
        idCardReader = IDCardReaderFactory.createIDCardReader(context, TransportType.SERIALPORT, idrparams);
        if(null!=idCardReader) {
            try {

                idCardReader.open(0);
            }catch (IDCardReaderException e){
                e.printStackTrace();
            }
        }
    }

    private void closeDevice()
    {
        if (isReadingCard)
        {
            StopReadCard();
            try {
                idCardReader.close(0);
            } catch (IDCardReaderException e) {
                e.printStackTrace();
            }
        }
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
        if (!readCardThread.isInterrupted())
            readCardThread.interrupt();
    }

    private class ReadCardRunnable implements Runnable {
        @Override
        public void run() {
            IDCardInfo idCardInfo;
            try {
                while (true) {
                    Thread.sleep(5 * 100);
                    try {
                        idCardReader.findCard(0);
                        idCardReader.selectCard(0);
                    }catch (IDCardReaderException e)
                    {
                        continue;
                    }
                    int cardType = 0;
                    try {
                        cardType = idCardReader.readCardEx(0, 0);
                    }
                    catch (IDCardReaderException e)
                    {
                        continue;
                    }
                    if (cardType == IDCardType.TYPE_CARD_SFZ || cardType == IDCardType.TYPE_CARD_GAT)
                    {
                        idCardInfo = idCardReader.getLastIDCardInfo();
                        ReadCardEvent cardEvent = new ReadCardEvent();
                        cardEvent.setCardInfo(idCardInfo);
                        EventBus.getDefault().post(cardEvent);
                    }

                }
            } catch (InterruptedException e) {
                isReadingCard = false;
            }
        }
    }
}