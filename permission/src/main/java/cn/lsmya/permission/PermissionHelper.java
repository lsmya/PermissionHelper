package cn.lsmya.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限申请工具类
 */
public class PermissionHelper {
    /**
     * 读写存储卡
     */
    public static final String[] EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 相机
     */
    public static final String[] CAMERA = new String[]{
            Manifest.permission.CAMERA};
    /**
     * 读写日历
     */
    public static final String[] ALENDAR = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    /**
     * 读写联系人
     */
    public static final String[] CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS};
    /**
     * 读位置信息
     */
    public static final String[] LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 使用麦克风
     */
    public static final String[] RECORD_AUDIO = new String[]{
            Manifest.permission.RECORD_AUDIO};
    /**
     * 读电话状态、打电话、读写电话记录
     */
    public static final String[] PHONE = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP};
    /**
     * 传感器
     */
    public static final String[] SENSORS = new String[]{
            Manifest.permission.BODY_SENSORS};
    /**
     * 读写短信、收发短信
     */
    public static final String[] SMS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS};

    private FragmentActivity mActivity;
    private OnPermissionListener mOnPermissionListener;
    private String[] mPermissions;

    public PermissionHelper(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 初始化
     *
     * @param activity 当前activity
     * @return 返回EasyPermission
     */
    public static PermissionHelper init(FragmentActivity activity) {
        return new PermissionHelper(activity);
    }

    /**
     * 添加权限
     *
     * @param permission 需要申请的权限
     * @return 返回EasyPermission
     */
    public PermissionHelper permission(String... permission) {
        mPermissions = permission;
        return this;
    }

    public PermissionHelper permission(String[]... permission) {
        List<String> permissionList = new ArrayList<>();
        for (String[] mPermission : permission) {
            permissionList.addAll(Arrays.asList(mPermission));
        }
        mPermissions = permissionList.toArray(new String[]{});
        return this;
    }

    /**
     * 开始申请权限
     */
    public void builder() {
        builder(null);
    }

    /**
     * 开始申请权限
     *
     * @param onPermissionListener 权限申请监听
     */
    public void builder(OnPermissionListener onPermissionListener) {
        mOnPermissionListener = onPermissionListener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            request23ToActivity();
        } else {
            if (mOnPermissionListener != null) {
                mOnPermissionListener.onYes();
            }
        }
    }

    /**
     * sdk23以上申请权限
     */
    private void request23ToActivity() {
        if (checkPermission()) {
            EasyPermissionFragment.getInstance(mPermissions).request(mActivity, mOnPermissionListener);
        } else {
            if (mOnPermissionListener != null) {
                mOnPermissionListener.onYes();
            }
        }
    }

    /**
     * 检查权限
     *
     * @return true:需要申请；false:已申请
     */
    private boolean checkPermission() {
        for (int i = 0; i < mPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mActivity, mPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
