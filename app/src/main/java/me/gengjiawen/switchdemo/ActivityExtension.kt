package me.gengjiawen.switchdemo

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: CharSequence, length: Int = Toast.LENGTH_LONG) = Toast.makeText(this, message, length).show()
