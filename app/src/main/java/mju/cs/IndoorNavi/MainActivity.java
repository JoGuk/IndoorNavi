package mju.cs.IndoorNavi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import mju.cs.IndoorNavi.APSearch.*;

import java.text.DecimalFormat;
import java.util.List;

import mju.cs.IndoorNavi.APSearch.APJSONParser;

public class MainActivity extends Activity implements OnClickListener {
    private Button moveleftButton;

    private Bitmap bitmap;

    private ImageView background, refresh, location_ikon;
    private TextView location_name;

    private APJSONParser jsonParser;


    // WifiManager variable
    WifiManager wifimanager;

    private int scanCount = 0;
    String text = "";
    String result = "";

    WifiInfo[] wifiList = new WifiInfo[1000];
    int[] sum = new int[1000];
    double[] avg = new double[1000];
    DecimalFormat dFormat = new DecimalFormat("#.###");

    private List<ScanResult> mScanResult; // ScanResult List

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                getWIFIScanResult(); // get WIFISCanResult
                wifimanager.startScan(); // for refresh
            } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mju.cs.IndoorNavi.R.layout.activity_main);

        // xml에 불러온 이미뷰를 iv에 넣어 속성사용 하기위한 구문
//        background = (ImageView) findViewById(mju.cs.CodeBay.R.id.background);
        refresh = (ImageView) findViewById(mju.cs.IndoorNavi.R.id.refresh);
//        location_name = (TextView) findViewById(mju.cs.CodeBay.R.id.location_name);
        location_ikon  = (ImageView) findViewById(mju.cs.IndoorNavi.R.id.location_ikon);

        jsonParser = new APJSONParser();
        refresh.setOnClickListener(this);

        location_ikon.setScaleType(ScaleType.MATRIX);   // 혹은 현재 xml에서 android:scaleType="matrix" 처리

        bitmap = BitmapFactory.decodeResource(getResources(), mju.cs.IndoorNavi.R.drawable.location_ikon);
        location_ikon.setImageBitmap(bitmap);
        location_ikon.bringToFront();

        moveleftButton = (Button) findViewById(mju.cs.IndoorNavi.R.id.moveleft);
        moveleftButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setMove(100, 100);
                // AP를 검색
                searchAP();
            }
        });

        // AP를 검색
//        searchAP();

        // Setup WIFI
        wifimanager = (WifiManager) getSystemService(WIFI_SERVICE);
        Log.d(Constants.TAG, "Setup WIfiManager getSystemService");

        // if WIFIEnabled
        if (wifimanager.isWifiEnabled() == false)
            wifimanager.setWifiEnabled(true);
    }

    //private Bitmap setMove(int flag){
    private void setMove(int x, int y){
        Matrix matrix = new Matrix();

        matrix.postTranslate(x, y);
//        switch(flag){
//            case 1://left
//                Log.i("setMove", "left");
//                matrix.postTranslate(-100, 0); //
//                break;
//            case 2://right
//                Log.i("setMove", "right");
//                matrix.postTranslate(100, 0); //
//                break;
//        }
        //앞에서 제작된 y축 대칭 매트릭스를 이미지에 적용한다.
        location_ikon.setImageMatrix(matrix);//이미지에 매트릭스 적용
    }

    // JSONParser
    private void searchAP() {
        jsonParser.parseJSONFromURL(Constants.APDATA_URL);
        Log.d("URL은 파싱했다더라", "그렇다더라");

        int ap_no;
        String ap_name;
        int x_position;
        int y_position;
        double avg_sig;

        Log.d("JsonParserSize", Integer.toString(jsonParser.object.size()));

        for (int i = 0; i < jsonParser.object.size(); i++) {
            ap_no = jsonParser.object.get(i).getAp_no();
            ap_name = jsonParser.object.get(i).getAp_name();
            x_position = jsonParser.object.get(i).getX_position();
            y_position = jsonParser.object.get(i).getY_position();
            avg_sig = jsonParser.object.get(i).getAvg_sig();

            Toast.makeText(this, ap_name, Toast.LENGTH_LONG).show();
        }
    }

    public void getWIFIScanResult() {

        mScanResult = wifimanager.getScanResults(); // ScanResult
        // Scan count
//        textStatus.setText("Scan count is \t" + ++scanCount + " times \n");
//
//        textStatus.append("=======================================\n");

        int networkID = 0;
        android.net.wifi.WifiInfo tempWifiInfo;
        tempWifiInfo = wifimanager.getConnectionInfo();
        for (int i = 0; i < mScanResult.size(); i++) {
            ScanResult result = mScanResult.get(i);
            wifiList[i] = new WifiInfo(result.SSID.toString(), result.level);
            sum[i] += wifiList[i].getRSSI();
            networkID = tempWifiInfo.getNetworkId();

            Toast.makeText(this, result.SSID.toString(), Toast.LENGTH_LONG).show();
        }
        Log.d(Constants.TAG, "networkID : " + Integer.toString(networkID));
        Log.d(Constants.TAG, "sum : " + Integer.toString(sum[0]));
        Log.d(Constants.TAG, "count : " + Integer.toString(scanCount));

//        for (int i = 0; i < mScanResult.size(); i++) {
//            if((double)sum[i]/scanCount>(-80))
//                textStatus.append((i+1) + ". SSID : " + wifiList[i].getSSID() + "\t\t RSSI 평균 : " +  (double)sum[i]/scanCount + " dBm\n");
//        }
//        textStatus.append("=======================================\n");
    }

    public void initWIFIScan() {
        // init WIFISCAN
        scanCount = 0;
        text = "";
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
        wifimanager.startScan();
        Log.d(Constants.TAG, "initWIFIScan()");
    }

    @Override
    public void onClick(View v) {
//
//        if (v.getId() == R.id.btnScanStart) {
//            Log.d(TAG, "OnClick() btnScanStart()");
//            printToast("WIFI SCAN START");
//            initWIFIScan(); // start WIFIScan
//        }
//        if (v.getId() == R.id.btnScanStop) {
//            Log.d(TAG, "OnClick() btnScanStop()");
//            printToast("SCAN STOP");
//            unregisterReceiver(mReceiver); // stop WIFISCan
//        }
        if (v.getId() == R.id.refresh) {
            Toast.makeText(this, "Scan", Toast.LENGTH_LONG).show();
            Log.d(Constants.TAG, "OnClick() btnScanStart()");
            initWIFIScan(); // start WIFIScan
        }
    }
}

// 1. wifi를 읽어온다
// 2. 읽어온 wifi를 DB의 ap_data의 ap_name과 비교한다.
// 3. 해당 ap의 x,y 좌표를 찾는다.
// 4. 1~3번 과정에서 3개 이상의 ap들을 비교하고 그 신호세기를 이용하여 중간위치를 찾는다.

// 해야할 일
// 1. 와이파이 받아오기
// 2. DB접근(값 읽어오기)
// 3. 비교해서 아무 점이나 찍기
// 4. 이미지 중첩