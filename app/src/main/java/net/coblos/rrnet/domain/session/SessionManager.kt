package net.coblos.rrnet.domain.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    var userSession: SharedPreferences =
        context.getSharedPreferences("userSessionData", Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = userSession.edit()

    fun createLoginSession(id: String, name: String, mobile: String,  token: String, isRemOn: Boolean) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_ID, id)
        editor.putString(KEY_USERNAME, name)
        editor.putString(KEY_MOBILE, mobile)
        editor.putString(KEY_TOKEN, token)
        editor.putBoolean(KEY_REMEMBER_ME, isRemOn)

        editor.commit()
    }

    fun setUrl(url: String) {
        editor.putString(KEY_URL, "http://${url}/")

        editor.commit()
    }

    fun setServer(server: String) {
        editor.putString(KEY_SERVER, server)

        editor.commit()
    }

    fun setServerPort(serverPort: String) {
        editor.putString(KEY_SERVER_PORT, serverPort)

        editor.commit()
    }

    fun getUrl(): String? = userSession.getString(KEY_URL, "http://coblos.network/")

    fun getServerURL(): String = "http://${userSession.getString(KEY_SERVER, "192.168.0.100")}:${userSession.getString(
        KEY_SERVER_PORT, "7119")}/"


    fun isRememberMeOn(): Boolean = userSession.getBoolean(KEY_REMEMBER_ME, false)

    fun getToken(): String? = userSession.getString(KEY_TOKEN, null)

    fun getUserDataFromSession(): HashMap<String, String?> {
        return hashMapOf(
            KEY_ID to userSession.getString(KEY_ID, null),
            KEY_USERNAME to userSession.getString(KEY_USERNAME, null),
            KEY_MOBILE to userSession.getString(KEY_MOBILE, null),
            KEY_TOKEN to userSession.getString(KEY_TOKEN, null)
        )
    }

    fun getUserIdFromSession(): String? = userSession.getString(KEY_ID, null)

    fun isLoggedIn(): Boolean = userSession.getBoolean(IS_LOGIN, false)

    fun logoutFromSession() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val IS_LOGIN = "isLoggedIn"
        private const val KEY_ID = "userId"
        private const val KEY_MOBILE = "userMobile"
        private const val KEY_USERNAME = "userName"
        private const val KEY_TOKEN = "userToken"
        private const val KEY_REMEMBER_ME = "isRemOn"
        private const val KEY_URL = "activeUrl"
        private const val KEY_SERVER = "server"
        private const val KEY_SERVER_PORT = "serverPort"
    }
}