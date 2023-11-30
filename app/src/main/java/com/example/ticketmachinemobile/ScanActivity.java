package com.example.ticketmachinemobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ticketmachinemobile.constant.TicketConstant;
import com.example.ticketmachinemobile.scan.zxing.ZXingView;
import com.example.ticketmachinemobile.scan.zxing.core.BarcodeType;
import com.example.ticketmachinemobile.scan.zxing.core.QRCodeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ScanActivity extends ComponentActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ZXingView mZXingView;

    private boolean flashlight = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了相机权限，执行打开相机的操作
                mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
            } else {
                // 用户拒绝了相机权限，可以给出提示或采取其他适当的措施
                Toast.makeText(this, "相机权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_scan);
        // 设置 action bar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setActionBar(toolbar);

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
        // 检查相机权限
        boolean hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            // 如果没有权限，请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

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
        // Toast 提示扫码成功和结果
        Toast.makeText(this, "扫码成功：" + result, Toast.LENGTH_SHORT).show();
        vibrate();
        // 将扫描结果通过 Intent 返回给调用者
        Intent intent = new Intent();
        intent.putExtra(TicketConstant.SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        // 关闭当前 Activity
        finish();

//        mZXingView.startSpot(); // 开始识别
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
        if (viewId == R.id.flashlightButton){
            if (flashlight){
                mZXingView.closeFlashlight();
                flashlight = false;
            }else{
                mZXingView.openFlashlight();
                flashlight = true;
            }
        }
        else if (viewId == R.id.start_preview) {
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


}