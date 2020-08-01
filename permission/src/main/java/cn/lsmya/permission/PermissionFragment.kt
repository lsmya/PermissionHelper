package cn.lsmya.permission

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.collections.ArrayList


/**
 * 权限请求处理Fragment
 */
class PermissionFragment : Fragment() {

    private var mYes: (() -> Unit)? = null
    private var mNo: ((ArrayList<String>) -> Unit)? = null

    /**
     * 请求权限
     */
    fun request(
        activity: FragmentActivity,
        yes: (() -> Unit)? = null, no: ((ArrayList<String>) -> Unit)? = null
    ) {
        mYes = yes
        mNo = no
        activity.supportFragmentManager.beginTransaction()
            .add(this, activity.javaClass.name).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val permissions =
            arguments!!.getStringArrayList(PERMISSION_GROUP)
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(permissions!!.toArray(arrayOf()), 1)
        } else {
            mYes?.invoke()
        }
    }

    /**
     * 请求权限回调
     *
     * @param requestCode  The request code passed in [.requestPermissions].
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 1) {
            val permissionList = checkPermission(permissions)
            if (permissionList.size == 0) {
                mYes?.invoke()
            } else {
                mNo?.invoke(permissionList)
            }
        }
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    /**
     * 获取请求的权限中未同意的权限
     *
     * @param permissions 需要申请的全部权限
     * @return 申请失败的权限
     */
    private fun checkPermission(permissions: Array<String>): ArrayList<String> {
        val permissionList =
            ArrayList<String>()
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(permissions[i])
            }
        }
        return permissionList
    }

    companion object {
        private const val PERMISSION_GROUP = "permission"
        fun getInstance(permissions: ArrayList<String>): PermissionFragment {
            val fragment = PermissionFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(PERMISSION_GROUP, permissions)
            fragment.arguments = bundle
            return fragment
        }
    }
}