package com.agung.xsettings

import android.app.Activity
import android.os.Bundle
import android.os.BatteryManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import android.os.Build
import android.view.WindowManager
import java.util.Locale

class MainActivity : Activity() {
    private val cyan = Color.rgb(0, 214, 255)
    private val panel = Color.rgb(18, 28, 45)
    private lateinit var info: TextView

    private val features = listOf(
        "Deteksi perangkat & refresh rate",
        "Informasi refresh rate layar",
        "Pilihan target 60 / 90 / 120 Hz",
        "Status FPS (indikator, bukan pemaksa FPS)",
        "Status mode hemat baterai",
        "Panduan kompatibilitas Vulkan",
        "Touch pointer speed (status/panduan)",
        "Touch slop & respons sentuhan (panduan)",
        "Optimasi animasi 0.5x (opsional via Shizuku)",
        "Mode game / fokus bermain",
        "Monitoring suhu baterai",
        "Monitoring level baterai",
        "Monitoring status pengisian",
        "Monitoring RAM tersedia",
        "Monitoring kapasitas RAM total",
        "Monitoring model perangkat",
        "Monitoring versi Android",
        "Monitoring resolusi layar",
        "Monitoring refresh rate aktual",
        "Pemeriksaan koneksi jaringan",
        "Panduan stabilitas Wi-Fi",
        "Pembersihan cache aplikasi (panduan)",
        "Pemeriksaan aplikasi latar belakang",
        "Pengingat jeda saat perangkat panas",
        "Panel status & log diagnostik"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8,13,24)
        window.navigationBarColor = Color.rgb(8,13,24)

        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 24, 22, 28)
            setBackgroundColor(Color.rgb(8,13,24))
        }
        scroll.addView(root)
        setContentView(scroll)

        val brand = TextView(this).apply {
            text = "X-SETINGS  V14.1.3\nDEVELOPER: AGUNG"
            textSize = 25f
            setTextColor(cyan)
            gravity = Gravity.CENTER
            setPadding(8, 20, 8, 20)
        }
        root.addView(brand)
        val subtitle = TextView(this).apply {
            text = "FREE FIRE • DEVICE CONTROL CENTER"
            textSize = 12f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }
        root.addView(subtitle)

        section(root, "AKTIVASI KEY")
        val keyInput = EditText(this).apply {
            hint = "Masukkan KEY gungtzy"
            setSingleLine(true)
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setPadding(14, 12, 14, 12)
            setBackgroundColor(panel)
        }
        root.addView(keyInput, matchWrap())
        val activate = button("AKTIVASI")
        root.addView(activate, matchWrap())
        val keyStatus = label("Status: belum diaktivasi")
        root.addView(keyStatus)
        activate.setOnClickListener {
            if (keyInput.text.toString().trim() == "gungtzy") {
                keyStatus.text = "Status: KEY lokal diterima • demo"
                Toast.makeText(this, "KEY diterima (mode demo)", Toast.LENGTH_LONG).show()
            } else {
                keyStatus.text = "Status: KEY tidak valid"
            }
        }
        root.addView(note("PENTING: validasi ini masih mode demo lokal. Lisensi 1 perangkat yang aman memerlukan server/backend; jangan menganggap KEY ini terlindungi dari pembongkaran APK."))

        section(root, "ROG MONITOR")
        info = label("")
        root.addView(info)
        val refresh = button("REFRESH MONITOR")
        root.addView(refresh, matchWrap())
        refresh.setOnClickListener { updateMonitor() }
        updateMonitor()

        section(root, "25 FITUR X-SETINGS")
        features.forEachIndexed { i, feature ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(12, 10, 12, 10)
                setBackgroundColor(panel)
            }
            val name = TextView(this).apply {
                text = String.format(Locale.US, "%02d  %s", i + 1, feature)
                textSize = 14f
                setTextColor(Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            }
            val status = TextView(this).apply {
                text = if (i in listOf(0,10,11,12,13,14,15,16,17,18,19)) "LIVE" else "INFO"
                textSize = 10f
                setTextColor(cyan)
            }
            row.addView(name); row.addView(status)
            val lp = LinearLayout.LayoutParams(-1, -2)
            lp.bottomMargin = 7
            root.addView(row, lp)
        }
        root.addView(note("Catatan kompatibilitas: Android membatasi perubahan refresh rate, FPS, Vulkan, touch driver, dan proses latar belakang. Aplikasi hanya menampilkan status/panduan untuk fitur yang tidak diizinkan sistem. Aksi melalui Shizuku harus diminta dan dijalankan secara eksplisit; tidak ada klaim boost performa palsu."))
    }

    private fun updateMonitor() {
        val battery = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = battery?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = battery?.getIntExtra(BatteryManager.EXTRA_SCALE, 100) ?: 100
        val pct = if (level >= 0 && scale > 0) level * 100 / scale else -1
        val temp = (battery?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1) / 10f
        val status = battery?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val charging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        val mem = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val mi = android.app.ActivityManager.MemoryInfo()
        mem.getMemoryInfo(mi)
        val dm = resources.displayMetrics
        val hz = if (Build.VERSION.SDK_INT >= 30) display?.refreshRate ?: dm.refreshRate else dm.refreshRate
        info.text = "DEVICE: ${Build.MANUFACTURER} ${Build.MODEL}\nANDROID: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})\nDISPLAY: ${dm.widthPixels} × ${dm.heightPixels}\nREFRESH: ${"%.1f".format(hz)} Hz\nBATTERY: $pct% • ${if (charging) "CHARGING" else "NOT CHARGING"}\nBATTERY TEMP: ${if (temp >= 0) "$temp °C" else "N/A"}\nRAM AVAILABLE: ${mi.availMem / 1024 / 1024} MB / ${mi.totalMem / 1024 / 1024} MB"
    }

    private fun section(parent: LinearLayout, title: String) {
        val t = TextView(this).apply {
            text = title
            textSize = 16f
            setTextColor(cyan)
            setPadding(2, 22, 2, 10)
        }
        parent.addView(t)
    }
    private fun label(s: String) = TextView(this).apply {
        text = s; textSize = 13f; setTextColor(Color.WHITE); setPadding(4, 8, 4, 12)
    }
    private fun note(s: String) = TextView(this).apply {
        text = s; textSize = 11f; setTextColor(Color.LTGRAY); setPadding(6, 12, 6, 12)
    }
    private fun button(s: String) = Button(this).apply {
        text = s; setTextColor(Color.WHITE); setBackgroundColor(Color.rgb(0,110,145))
    }
    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = 8 }
}
