package kh.edu.rupp.ite.memo.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kh.edu.rupp.ite.memo.utils.Constant.PREFS_TOKEN_FILE
import kh.edu.rupp.ite.memo.utils.Constant.USER_TOKEN
import javax.inject.Inject

class Token @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}