package com.example.ticketmachinemobile;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import androidx.activity.ComponentActivity;

import com.example.ticketmachinemobile.scan.zxing.ZXingView;
import com.example.ticketmachinemobile.scan.zxing.core.BarcodeType;
import com.example.ticketmachinemobile.scan.zxing.core.QRCodeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanActivity extends ComponentActivity implements QRCodeView.Delegate, EasyPermissions.PermissionCallbacks {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    private ZXingView mZXingView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_scan);
        // 设置 action bar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setActionBar(toolbar);

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        setTitle("扫描结果为：" + result);
        vibrate();

        mZXingView.startSpot(); // 开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.start_preview) {
            mZXingView.startCamera();
        } else if (viewId == R.id.stop_preview) {
            mZXingView.stopCamera();
        } else if (viewId == R.id.start_spot) {
            mZXingView.startSpot();
        } else if (viewId == R.id.stop_spot) {
            mZXingView.stopSpot();
        } else if (viewId == R.id.start_spot_showrect) {
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.stop_spot_hiddenrect) {
            mZXingView.stopSpotAndHiddenRect();
        } else if (viewId == R.id.show_scan_rect) {
            mZXingView.showScanRect();
        } else if (viewId == R.id.hidden_scan_rect) {
            mZXingView.hiddenScanRect();
        } else if (viewId == R.id.decode_scan_box_area) {
            mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true);
        } else if (viewId == R.id.decode_full_screen_area) {
            mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(false);
        } else if (viewId == R.id.open_flashlight) {
            mZXingView.openFlashlight();
        } else if (viewId == R.id.close_flashlight) {
            mZXingView.closeFlashlight();
        } else if (viewId == R.id.scan_one_dimension) {
            mZXingView.changeToScanBarcodeStyle();
            mZXingView.setType(BarcodeType.ONE_DIMENSION, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_two_dimension) {
            mZXingView.changeToScanQRCodeStyle();
            mZXingView.setType(BarcodeType.TWO_DIMENSION, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_qr_code) {
            mZXingView.changeToScanQRCodeStyle();
            mZXingView.setType(BarcodeType.ONLY_QR_CODE, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_code128) {
            mZXingView.changeToScanBarcodeStyle();
            mZXingView.setType(BarcodeType.ONLY_CODE_128, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_ean13) {
            mZXingView.changeToScanBarcodeStyle();
            mZXingView.setType(BarcodeType.ONLY_EAN_13, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_high_frequency) {
            mZXingView.changeToScanQRCodeStyle();
            mZXingView.setType(BarcodeType.HIGH_FREQUENCY, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_all) {
            mZXingView.changeToScanQRCodeStyle();
            mZXingView.setType(BarcodeType.ALL, null);
            mZXingView.startSpotAndShowRect();
        } else if (viewId == R.id.scan_custom) {
            mZXingView.changeToScanQRCodeStyle();
            Map<DecodeHintType, Object> hintMap = new EnumMap<>(DecodeHintType.class);
            List<BarcodeFormat> formatList = new ArrayList<>();
            formatList.add(BarcodeFormat.QR_CODE);
            formatList.add(BarcodeFormat.UPC_A);
            formatList.add(BarcodeFormat.EAN_13);
            formatList.add(BarcodeFormat.CODE_128);
            hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList);
            hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mZXingView.setType(BarcodeType.CUSTOM, hintMap);
            mZXingView.startSpotAndShowRect();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

}