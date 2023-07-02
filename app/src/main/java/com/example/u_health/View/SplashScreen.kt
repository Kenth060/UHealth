package com.example.u_health.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.u_health.R
import com.example.u_health.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity()
{

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        try
        {
            super.onCreate(savedInstanceState)
            binding=ActivitySplashScreenBinding.inflate(layoutInflater)
            val view=binding.root
            setContentView(view)

            val LogoApp: ImageView = binding.imgSplashScreen
            val AnimLogo: Animation = AnimationUtils.loadAnimation(this,
                R.anim.anim1
            )
            LogoApp.startAnimation(AnimLogo)

            val intent = Intent(this, Menu::class.java)
            //Navigation.findNavController(view).navigate(R.id.frequency)

            AnimLogo.setAnimationListener(object: Animation.AnimationListener {

                override fun onAnimationStart(animation: Animation?) {
                }
                override fun onAnimationEnd(animation: Animation?) {
                    startActivity(intent)
                    finish()
                }
                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        }
        catch (e:java.lang.Exception)
        { e.printStackTrace() }


    }
}