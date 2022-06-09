package com.project.bitereg.view.dashboard.qrscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.project.bitereg.databinding.FragmentScanBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ScanFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var codeScanner: CodeScanner? = null

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
        handlePermissions()
    }

    private fun setupCodeScanner() {
        if (codeScanner != null) return
        codeScanner = CodeScanner(requireContext(), binding.qrScanner).apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.TWO_DIMENSIONAL_FORMATS
            autoFocusMode = AutoFocusMode.CONTINUOUS
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
            codeScanner?.startPreview()
        }
    }

    private fun handleScan(qrString: String) {
        Toast.makeText(requireContext(), qrString, Toast.LENGTH_SHORT).show()
    }

    private fun handlePermissions() {
        if (hasPermissions()) {
            startScanning()
        } else {
            makePermissionRequest()
        }
    }

    private fun startScanning() {
        setupCodeScanner()
        initViews()
        lifecycleScope.launch {
            delay(200)
            if(this.isActive) codeScanner?.startPreview()
        }
    }

    private fun initViews() {
        binding.qrScanner.visibility = View.VISIBLE
    }

    private fun makePermissionRequest() {
        EasyPermissions.requestPermissions(
            this,
            "Location permission is required for this feature!",
            PERMISSION_REQUEST_CODE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            makePermissionRequest()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        initViews()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        handlePermissions()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun hasPermissions(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA
        )
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 101
    }
}