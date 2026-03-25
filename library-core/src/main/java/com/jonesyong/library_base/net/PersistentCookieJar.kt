package com.jonesyong.library_base.net

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistentCookieJar(context: Context) : CookieJar {

    private val prefs = context.getSharedPreferences("wan_cookies", Context.MODE_PRIVATE)
    private val cache = HashMap<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val host = url.host
        val incoming = cookies.associateBy { it.name }
        val list = cache.getOrPut(host) { mutableListOf() }
        list.removeAll { incoming.containsKey(it.name) }
        list.addAll(incoming.values)
        prefs.edit().putStringSet(host, list.map { serialize(it) }.toSet()).apply()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val host = url.host
        if (!cache.containsKey(host)) {
            val stored = prefs.getStringSet(host, emptySet()) ?: emptySet()
            cache[host] = stored.mapNotNull { deserialize(it) }.toMutableList()
        }
        val now = System.currentTimeMillis()
        return cache[host]?.filter { it.expiresAt > now } ?: emptyList()
    }

    private fun serialize(cookie: Cookie): String =
        "${cookie.name}|${cookie.value}|${cookie.domain}|${cookie.path}|${cookie.expiresAt}|${cookie.secure}|${cookie.httpOnly}"

    private fun deserialize(s: String): Cookie? {
        return try {
            val p = s.split("|")
            Cookie.Builder()
                .name(p[0])
                .value(p[1])
                .domain(p[2])
                .path(p[3])
                .expiresAt(p[4].toLong())
                .apply { if (p[5].toBoolean()) secure() }
                .apply { if (p[6].toBoolean()) httpOnly() }
                .build()
        } catch (e: Exception) {
            null
        }
    }
}
