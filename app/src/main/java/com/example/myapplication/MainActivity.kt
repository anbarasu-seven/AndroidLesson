package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

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
            val colors = arrayOf(" Open App", " App Info", getPermissionsByPackageName(installedApps!![i].packages))
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

            /*
                PackageInfo
                    Overall information about the contents of a package. This corresponds to all of
                    the information collected from AndroidManifest.xml.
            */
            /*
                String[] requestedPermissions
                    Array of all <uses-permission> tags included under <manifest>, or null if there
                    were none. This is only filled in if the flag GET_PERMISSIONS was set. This list
                    includes all permissions requested, even those that were not granted or known
                    by the system at install time.
            */
            /*
                int[] requestedPermissionsFlags
                    Array of flags of all <uses-permission> tags included under <manifest>, or null
                    if there were none. This is only filled in if the flag GET_PERMISSIONS was set.
                    Each value matches the corresponding entry in requestedPermissions, and will
                    have the flag REQUESTED_PERMISSION_GRANTED set as appropriate.
            */
            /*
                int REQUESTED_PERMISSION_GRANTED
                    Flag for requestedPermissionsFlags: the requested permission is currently
                    granted to the application.
            */

            // Loop through the package info requested permissions
            for (i in packageInfo.requestedPermissions.indices) {
                if (packageInfo.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED == 0) {
                    val permission = packageInfo.requestedPermissions[i]
                    // To make permission name shorter
                    //permission = permission.substring(permission.lastIndexOf(".")+1);
                    builder.append("$counter. $permission\n")
                    counter++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return builder.toString()
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