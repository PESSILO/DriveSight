package com.pessilogroup.drivesight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pessilogroup.drivesight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase through Native NDK
        //initFirebase(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }

    /**
     * A native method that initializes Firebase.
     */
    //external fun initFirebase(activity: MainActivity)

    /**
     * A native method that is implemented by the 'drivesight' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'drivesight' library on application startup.
        init {
            System.loadLibrary("drivesight")
        }
    }
}