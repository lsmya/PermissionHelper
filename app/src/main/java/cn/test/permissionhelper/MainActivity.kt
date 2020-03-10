package cn.test.permissionhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.lsmya.permission.PermissionHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionHelper.init(this)
                .builder()
    }
}
