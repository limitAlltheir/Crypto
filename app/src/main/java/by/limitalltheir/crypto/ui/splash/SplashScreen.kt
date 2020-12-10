package by.limitalltheir.crypto.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import by.limitalltheir.crypto.BuildConfig
import by.limitalltheir.crypto.ui.MainActivity
import by.limitalltheir.crypto.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashScreenIV = findViewById<ImageView>(R.id.splash_screen_iv)
        val splashScreenTV = findViewById<TextView>(R.id.version)

        splashScreenIV.apply {
            alpha = 0f
            animate().setDuration(2000).alpha(1f).withEndAction {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                splashScreenTV.text = BuildConfig.VERSION_NAME
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }
}