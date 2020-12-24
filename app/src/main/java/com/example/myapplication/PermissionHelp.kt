package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Build

/*

*/
/**
 * Uses PackageManager getAllPermissionGroups() and queryPermissionsByGroup()
 * to enumerate the Android permission hierarchy.
 *//*

private open fun showPermissionTree() {
    val pm: PackageManager = m_context.getPackageManager() ?: return

    */
/*
     * Get a list of all permission groups and sort them alphabetically.
     * Then add to the end of the list the special case of a null group name. There can be
     * numerous permissions that are not listed under a group name.
     *//*

    val groupInfoList = pm.getAllPermissionGroups(0) ?: return
    val groupNameList: ArrayList<String?> = ArrayList()
    for (groupInfo in groupInfoList) {
        var groupName = groupInfo.name
        if (groupName != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                */
/*
                 * SDK 17 added the flags field. If non-zero, the permission group contains
                 * permissions that control access to user personal data.
                 * N.B. These are the permissions groups that are called "dangerous" in
                 * Marshmallow.
                 *//*

                if (groupInfo.flags != 0) {
                    groupName += " (personal)"
                }
            }
            groupNameList.add(groupName)
        }
    }
    Collections.sort(groupNameList)
    groupNameList.add(null)

    */
/*
     * Loop though each permission group, adding to the StringBuilder the group name and
     * the list of all permissions under that group.
     *//*

    val sb = StringBuilder(10000)
    val INDENT = "   "
    for (groupName in groupNameList) {
        if (groupName == null) groupName = "null"
        sb.append("* ").append(groupName).append("\n")
        val permissionNameList = getPermissionsForGroup(groupName)
        if (permissionNameList.size() > 0) {
            for (permission in permissionNameList) {
                sb.append(INDENT).append(permission).append("\n")
            }
        } else {
            sb.append(INDENT).append("no permissions under group\n")
        }
        sb.append("\n")
    }
    m_textView.setText(sb.toString())
}


*/
/*
 * Gets and returns a list of all permission under the specified group, sorted alphabetically.
 *
 * N.B. groupName can be null. The docs for PackageManager.queryPermissionsByGroup() say
 * "Use null to find all of the permissions not associated with a group."
 *//*

private open fun getPermissionsForGroup(groupName: String): ArrayList<String> {
    val pm: PackageManager = m_context.getPackageManager()
    val permissionNameList: ArrayList<String> = ArrayList()
    try {
        val permissionInfoList = pm.queryPermissionsByGroup(groupName, PackageManager.GET_META_DATA)
        if (permissionInfoList != null) {
            for (permInfo in permissionInfoList) {
                var permName = permInfo.name
                if (permName == null) {
                    permName = "null"
                } else if (permName.isEmpty()) {
                    permName = "empty"
                }
                permissionNameList.add(permName)
            }
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // e.printStackTrace();
        Log.d(TAG, "permissions not found for group = $groupName")
    }
    Collections.sort(permissionNameList)
    return permissionNameList
}*/


