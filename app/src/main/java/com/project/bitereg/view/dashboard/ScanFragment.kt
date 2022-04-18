package com.project.bitereg.view.dashboard

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.snackbar.Snackbar
import com.project.bitereg.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPermissions()
        setupCodeScanner()
    }

    private fun setupCodeScanner() {
        codeScanner = CodeScanner(requireContext(), binding.qrScanner).apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    handleScan(it.text)
                }
            }
        }

        binding.qrScanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun handleScan(qrString: String) {
        Toast.makeText(requireContext(), qrString, Toast.LENGTH_SHORT).show()
    }

    private fun setupPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        )
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            makePermissionRequest()
        }
    }

    private fun makePermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(
                        binding.root,
                        "Camera permission is required for QR Scan!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    // Camera request is successful!
                }
            }
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 101
    }
}