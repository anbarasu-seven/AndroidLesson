package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var installedApps: List<AppModel>? = null
    private var installedAppAdapter: AppListAdapter? = null
    var userInstalledApps: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInstalledApps = findViewById<View>(R.id.installed_app_list) as ListView
        installedApps = getInstalledApps()
        installedAppAdapter = AppListAdapter(this@MainActivity, installedApps)
        userInstalledApps!!.adapter = installedAppAdapter
        userInstalledApps!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->

            val colors = getGrantedPermissions(installedApps!![i].packages).toTypedArray()
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Choose Action")
                    .setItems(colors) { dialog, which -> // The 'which' argument contains the index position of the selected item
                        if (which == 0) {
                            val intent = packageManager.getLaunchIntentForPackage(installedApps!![i].packages)
                            if (intent != null) {
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@MainActivity, installedApps!![i].packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show()
                            }
                        }
                        if (which == 1) {
                            displayAppDetailsSettings(installedApps!![i].packages)
                        }
                    }
            builder.show()
        }

        //Total Number of Installed-Apps(i.e. List Size)
        val abc = userInstalledApps!!.count.toString() + ""
        val countApps = findViewById<View>(R.id.countApps) as TextView
        countApps.text = "Total Installed Apps: $abc"
        Toast.makeText(this, "$abc Apps", Toast.LENGTH_SHORT).show()
    }

    protected fun getPermissionsByPackageName(packageName: String?): String? {
        // Initialize a new string builder instance
        val builder = StringBuilder()
        try {
            // Get the package info
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)

            // Permissions counter
            var counter = 1

            // Loop through the package info requested permissions
            for (i in packageInfo.requestedPermissions.indices) {
                if (packageInfo.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                    val permission = packageInfo.requestedPermissions[i]
                    builder.append("$counter. $permission\n")
                    counter++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return builder.toString()
    }

    fun getGrantedPermissions(appPackage: String?): ArrayList<String> {
        val builder = ArrayList<String>()
        builder.add(" Open App")
        builder.add(" App Info")
        var counter = 1
        val pi = packageManager.getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS)
        for (i in pi.requestedPermissions.indices) {
            if (pi.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                val permissionInfo = packageManager.getPermissionInfo(pi.requestedPermissions[i], 0)
                if (permissionInfo.group != null) {

                    val permission_path = pi.requestedPermissions[i]

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        val name = Helper.getGroupName(permission_path)
                        name?.let {
                            if(!builder.contains(it)){
                                builder.add(it)
                            }
                        }
                    }else{
                        val permissionGroupInfo = packageManager.getPermissionGroupInfo(permissionInfo.group, 0)
                        val permission_group_name = permissionGroupInfo.loadLabel(packageManager).toString()
                        if(!builder.contains(permission_group_name)){
                            builder.add(permission_group_name)
                        }

                    }
                }

            }
            counter++
        }
        return builder
    }

    // Custom method go to application details settings by package name
    protected fun displayAppDetailsSettings(packageName: String) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            val alternateIntent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            startActivity(alternateIntent)
        }
    }

    private fun getInstalledApps(): List<AppModel> {
        val pm = packageManager
        val apps: MutableList<AppModel> = ArrayList()
        val packs = packageManager.getInstalledPackages(0)
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (i in packs.indices) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                val packages = p.applicationInfo.packageName
                apps.add(AppModel(appName, icon, packages))
            }
        }
        return apps
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}