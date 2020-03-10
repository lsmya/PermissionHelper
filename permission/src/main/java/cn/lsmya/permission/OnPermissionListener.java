package cn.lsmya.permission;

import java.util.List;

/**
 * 权限申请监听
 */
public interface OnPermissionListener {
    /**
     * 申请权限拒绝回调
     *
     * @param permission 未申请的权限
     */
    void onNo(List<String> permission);

    /**
     * 申请权限同意回调
     */
    void onYes();
}
