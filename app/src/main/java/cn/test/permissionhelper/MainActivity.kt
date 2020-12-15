package cn.test.permissionhelper

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.lsmya.permission.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        click.setOnClickListener {
            PermissionHelper(this)
                .permission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .permission(Manifest.permission.CAMERA)
                .builder {

                }
            val checkPermission =
                PermissionHelper.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}
