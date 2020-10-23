package com.beershop.adgaon.barcode.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.fragment.app.activityViewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.barcode.viewmodel.ViewModelBarcode
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.utility.extension.show
import com.beershop.adgaon.base.view.BaseFragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_barcode.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentBarcode : BaseFragment(layoutResourceId = R.layout.fragment_barcode) {

    override fun className() = this.javaClass.simpleName

    private val viewModelBarcode: ViewModelBarcode by activityViewModels()

    private val REQUEST_CODE = 101

    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var toneGenerator: ToneGenerator

    private var lastDetectTime = SystemClock.elapsedRealtime()

    private var barcodeResult: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
    }

    override fun init() {
        cameraPermission()
    }

    private fun initSurfaceView() {
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    cameraSource.start(surfaceHolder)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
    }

    override fun initListener() {
        buttonAdd.setOnClickListener {
            viewModelBarcode.barcode(barcodeResult)
            requireActivity().onBackPressed()
        }
    }

    private fun vibrate() {
        GlobalScope.launch {
            val vibrator: Vibrator? =
                requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(
                        VibrationEffect.createOneShot(
                            300,
                            VibrationEffect.CONTENTS_FILE_DESCRIPTOR
                        )
                    )
                } else {
                    it.vibrate(300)
                }
            }
        }
    }

    private fun playSound() {
        GlobalScope.launch {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 200)
            delay(900)
            stopSound()
        }
    }

    private suspend fun updateBarcode(result: String) {
        withContext(Dispatchers.Main) {
            txtDetails.text = result.substring(0, 1)
                .plus("-")
                .plus(result.substring(1, 7))
                .plus("-")
                .plus(result.substring(7))
            barcodeResult = result
            buttonAdd.show()
        }
    }

    private fun isValidateBarcode(barcode: String): Boolean {
        return if (barcode.isNotBlank())
            barcode.length == 13
        else false
    }

    private fun stopSound() {
        toneGenerator.stopTone()
    }

    // need to fix animation issue
    private fun detectCode() {
        barcodeDetector = BarcodeDetector.Builder(requireContext()).build()
        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setAutoFocusEnabled(true)
            .build()
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detector: Detector.Detections<Barcode>?) {
                if (detector != null && detector.detectedItems.isNotEmpty()) {
                    val result: String = detector.detectedItems.valueAt(0).displayValue
                    if (isValidateBarcode(result)) {
                        GlobalScope.launch {
                            if ((SystemClock.elapsedRealtime() - lastDetectTime) > 1000L) {
                                lastDetectTime = SystemClock.elapsedRealtime()
                                vibrate()
                                playSound()
                                updateBarcode(result)
                            }
                        }
                    } else {
                        Log.d(TAG, "Invalid barcode is $result")
                    }
                }
            }
        })
    }

    private fun cameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                initSurfaceView()
                detectCode()
                toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 150)
            }
            shouldShowRequestPermissionRationale(getString(R.string.camera_permission)) -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                // You can directly ask for the permission.
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initSurfaceView()
                    detectCode()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.camera_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}

