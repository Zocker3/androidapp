package com.example.myapplication

import android.app.Application
import android.util.Log
import dji.common.error.DJIError
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import dji.common.error.DJISDKError

class DJIApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DJISDKManager.getInstance().registerApp(this, object : DJISDKManager.SDKManagerCallback {
            override fun onRegister(error: DJIError?) {
                if (error == DJISDKError.REGISTRATION_SUCCESS) {
                    Log.d("DJI", "SDK đăng ký thành công")
                    DJISDKManager.getInstance().startConnectionToProduct()
                } else {
                    if (error != null) {
                        Log.e("DJI", "SDK đăng ký thất bại: ${error.description}")
                    }
                }
            }

            override fun onProductDisconnect() {
                Log.d("DJI", "Flycam đã mất kết nối")
            }

            override fun onProductConnect(product: BaseProduct?) {
                Log.d("DJI", "Flycam đã kết nối: ${product?.model}")
            }

            override fun onProductChanged(p0: BaseProduct?) {
                TODO("Not yet implemented")
            }

            override fun onComponentChange(
                p0: BaseProduct.ComponentKey?,
                p1: BaseComponent?,
                p2: BaseComponent?
            ) {
                TODO("Not yet implemented")
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