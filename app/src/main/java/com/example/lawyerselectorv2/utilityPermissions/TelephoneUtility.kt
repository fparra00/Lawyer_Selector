package com.example.lawyerselectorv2.utilityPermissions

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object TelephoneUtility {
    fun hasTelephonePermissions(context: Context) = run {
        EasyPermissions.hasPermissions(context,
            android.Manifest.permission.CALL_PHONE
        )
    }
}