package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.products.Aircraft
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        DJIHelper.init(this)
        setContent {
//            MyApplicationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
            MapboxScreen()
        }
    }
}
@Composable
fun MapboxScreen() {
    val context = LocalContext.current
    val mapView = remember(context) { MapView(context) }
    LaunchedEffect(Unit) {
        mapView.mapboxMap.loadStyle(Style.SATELLITE_STREETS) { style ->    // Xử lý sau khi style load thành công
        }
        }

    // Lấy GPS từ drone
    val gpsLocation = remember { mutableStateOf("GPS: Loading...") }
    val latitude = remember { mutableStateOf(0.0) }
    val longitude = remember { mutableStateOf(0.0) }
    LaunchedEffect(Unit) {
        val drone = DJISDKManager.getInstance().product as? Aircraft
        drone?.flightController?.setStateCallback { state ->
            val lat = state.aircraftLocation?.latitude ?: 0.0
            val lon = state.aircraftLocation?.longitude ?: 0.0

            latitude.value = lat
            longitude.value = lon
            gpsLocation.value = "GPS: ${lat}, ${lon}"
        }
        Log.d("DJI", "Latitude: ${latitude.value}, Longitude: ${longitude.value}")

    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(gpsLocation.value)
        AndroidView({ mapView }) // Hiển thị bản đồ
    }
}
object DJIHelper {
    fun init(context: Context) {
        if (DJISDKManager.getInstance() != null) return
        DJISDKManager.getInstance().registerApp(context, object : DJISDKManager.SDKManagerCallback {
            override fun onRegister(error: DJIError?) {
                if (error == DJISDKError.REGISTRATION_SUCCESS) {
                    Log.d("DJI", "SDK đăng ký thành công")
                    DJISDKManager.getInstance().startConnectionToProduct()
                } else {
                    Log.e("DJI", "SDK đăng ký thất bại: ${error?.description}")
                }
            }

            override fun onProductDisconnect() {
                Log.d("DJI", "Drone bị ngắt kết nối")
            }

            override fun onProductConnect(product: BaseProduct?) {
                Log.d("DJI", "Drone đã kết nối: ${product?.model}")
            }

            override fun onProductChanged(p0: BaseProduct?) {
                TODO("Not yet implemented")
            }

            override fun onComponentChange(
                componentKey: BaseProduct.ComponentKey?,
                oldComponent: BaseComponent?,
                newComponent: BaseComponent?
            ) {
                Log.d("DJI", "Component thay đổi: $componentKey")
            }

            override fun onInitProcess(p0: DJISDKInitEvent?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onDatabaseDownloadProgress(p0: Long, p1: Long) {
                TODO("Not yet implemented")
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}