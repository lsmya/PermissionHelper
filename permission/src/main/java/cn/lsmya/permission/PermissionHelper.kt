package cn.lsmya.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.*
import kotlin.collections.ArrayList

/**
 * 权限申请工具类
 */
class PermissionHelper(val mActivity: FragmentActivity) {
    private var mPermissions: ArrayList<String> = ArrayList()

    /**
     * 添加权限
     *
     * @param permission 需要申请的权限
     * @return 返回EasyPermission
     */
    fun permission(vararg permission: String): PermissionHelper {
        mPermissions.addAll(listOf(*permission))
        return this
    }

    fun permission(vararg permission: Array<String>): PermissionHelper {
        for (mPermission in permission) {
            mPermissions.addAll(listOf(*mPermission))
        }
        return this
    }

    /**
     * 开始申请权限
     */
    fun builder(no: ((ArrayList<String>) -> Unit)? = null, yes: (() -> Unit)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            request23ToActivity(yes, no)
        } else {
            yes?.invoke()
        }
    }

    /**
     * sdk23以上申请权限
     */
    private fun request23ToActivity(
        yes: (() -> Unit)? = null,
        no: ((ArrayList<String>) -> Unit)? = null
    ) {
        if (checkPermission(mActivity, mPermissions)) {
            PermissionFragment.getInstance(
                mPermissions
            )
                .request(mActivity, yes, no)
        } else {
            yes?.invoke()
        }
    }

    companion object {
        /**
         * 读写存储卡
         */
        val EXTERNAL_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        /**
         * 相机
         */
        val CAMERA = arrayOf(
            Manifest.permission.CAMERA
        )

        /**
         * 读写日历
         */
        val ALENDAR = arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )

        /**
         * 读写联系人
         */
        val CONTACTS = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS
        )

        /**
         * 读位置信息
         */
        val LOCATION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        /**
         * 使用麦克风
         */
        val RECORD_AUDIO = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )

        /**
         * 读电话状态、打电话、读写电话记录
         */
        val PHONE = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP
        )

        /**
         * 传感器
         */
        val SENSORS = arrayOf(
            Manifest.permission.BODY_SENSORS
        )

        /**
         * 读写短信、收发短信
         */
        val SMS = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS
        )

        /**
         * 检查权限是否需要申请
         *
         * @return true:需要申请；false:已申请
         */
        fun checkPermission(context: Context, permissions: ArrayList<String>): Boolean {
            for (s in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        s
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return true
                }
            }
            return false
        }

        /**
         * 检查权限是否需要申请
         *
         * @return true:需要申请；false:已申请
         */
        fun checkPermission(context: Context, vararg permission: String): Boolean {
            for (s in listOf(*permission)) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        s
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return true
                }
            }
            return false
        }

    }

}
