package cn.lsmya.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

/**
 * 权限请求处理Fragment
 */
public final class EasyPermissionFragment extends Fragment {

    private static final String PERMISSION_GROUP = "permission";

    private OnPermissionListener mOnPermissionListener;

    public static EasyPermissionFragment getInstance(String... permissions) {
        EasyPermissionFragment fragment = new EasyPermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION_GROUP, permissions);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 请求权限
     */
    public void request(FragmentActivity activity, OnPermissionListener onPermissionListener) {
        mOnPermissionListener = onPermissionListener;
        activity.getSupportFragmentManager().beginTransaction().add(this, activity.getClass().getName()).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] permissions = getArguments().getStringArray(PERMISSION_GROUP);
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(permissions, 1);
        } else {
            if (mOnPermissionListener != null) {
                mOnPermissionListener.onYes();
            }
        }
    }

    /**
     * 请求权限回调
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0 && requestCode == 1) {
            ArrayList<String> permissionList = checkPermission(permissions);
            if (permissionList.size() == 0) {
                if (mOnPermissionListener != null) {
                    mOnPermissionListener.onYes();
                }
            } else {
                if (mOnPermissionListener != null) {
                    mOnPermissionListener.onNo(permissionList);
                }
            }
        }
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    /**
     * 获取请求的权限中未同意的权限
     *
     * @param permissions 需要申请的全部权限
     * @return 申请失败的权限
     */
    private ArrayList<String> checkPermission(String[] permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        return permissionList;
    }
}