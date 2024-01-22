package com.kira.android_base.main.fragments.homebluetooth

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_ADMIN
import android.Manifest.permission.BLUETOOTH_ADVERTISE
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.ParcelUuid
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBluetoothBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class HomeBluetoothFragment : BaseFragment(R.layout.fragment_home_bluetooth) {

    companion object {
        private const val REQUEST_BLUETOOTH_PERMISSION = 0
        private val SERVICE_UUID = ParcelUuid.fromString("00001101-0000-1000-8000-00805F9B34FB")
        private val CHAR_UUID = ParcelUuid.fromString("00002a37-0000-1000-8000-00805f9b34fb")
        private val SERVICE_DATA_UUID = ParcelUuid.fromString("0000950d-0000-1000-8000-00805f9b34fb")
    }

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothGatt: BluetoothGatt? = null
    private var bluetoothGattServer: BluetoothGattServer? = null
    private var bluetoothManager: BluetoothManager? = null
    private var devices = mutableSetOf<BluetoothDevice>()

    private val calendar by lazy {
        Calendar.getInstance()
    }

    private val bluetoothRequestEnableLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        Log.e("TAG", "result: ${result.data}")

    }

    private val gattServerCallback = object : BluetoothGattServerCallback() {

        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)

            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> {
                    Log.e("TAG", "GATT SERVER onConnectionStateChange: STATE_CONNECTED")
                    Log.e(
                        "TAG",
                        "onConnectionStateChange: DEVICE ${device?.address} ${device?.name}",
                    )
                }

                BluetoothGatt.STATE_DISCONNECTED -> {
                    Log.e("TAG", "GATT SERVER onConnectionStateChange: STATE_DISCONNECTED")
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onCharacteristicReadRequest(
            device: BluetoothDevice?,
            requestId: Int,
            offset: Int,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic)
            Log.e("TAG", "onCharacteristicReadRequest: start", )
            bluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, "ok".toByteArray())
        }

        override fun onServiceAdded(status: Int, service: BluetoothGattService?) {
            // Handle service added
            Log.e("TAG", "onServiceAdded: ")
        }
    }

    private val bluetoothGattCallback = object : BluetoothGattCallback() {

        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)

            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> {
                    Log.e("TAG", "onConnectionStateChange: connect success")
                    gatt?.discoverServices()
                }

                BluetoothGatt.STATE_DISCONNECTED -> {
                    Log.e("TAG", "onConnectionStateChange: connect fail $status")
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            Log.e("TAG", "onServicesDiscovered: ", )
            val service = gatt?.getService(SERVICE_UUID.uuid)
            val characteristic = service?.getCharacteristic(CHAR_UUID.uuid)
            gatt?.readCharacteristic(characteristic)
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            Log.e("TAG", "onCharacteristicRead: $status", )
            super.onCharacteristicRead(gatt, characteristic, value, status)
            val data = characteristic.value
            Log.e("TAG", "onCharacteristicRead: ${String(data)} ${String(value)}", )
        }
    }

    private val advertiseCallback = object : AdvertiseCallback() {

        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            super.onStartSuccess(settingsInEffect)

            Log.e("TAG", "onStartSuccess: ", )
        }

        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)

            Log.e("TAG", "onStartFailure: $errorCode", )
        }
    }

    private val leScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.e("TAG", "onScanResult: ${result?.device?.name} ${result?.device?.address} ${result?.scanRecord?.serviceData}", )
            val serviceData = result?.scanRecord?.serviceData?.get(SERVICE_DATA_UUID) ?: return
            val time = String(serviceData).toLongOrNull() ?: return
            Log.e("TAG", "onScanResult: $time", )
            setTime(time)
//            devices.add(result?.device ?: return)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initViews() {
        initPermission()
        (viewDataBinding as? FragmentHomeBluetoothBinding)?.run {
            buttonFindDevices.setOnClickListener {
                scanDevices()
            }

            buttonAdvertisingDevice.setOnClickListener {
                Log.e("TAG", "initViews: ${bluetoothAdapter?.isMultipleAdvertisementSupported}", )
                if (bluetoothAdapter?.isMultipleAdvertisementSupported == false) return@setOnClickListener
                startAdvertising()
            }
        }
    }

    @SuppressLint("MissingPermission")

    private fun startAdvertising() {
        Log.e("TAG", "startAdvertising: ", )
//        val bluetoothGattService = createGattService()
//        bluetoothGattServer = bluetoothManager?.openGattServer(context, gattServerCallback)
//        bluetoothGattServer?.addService(bluetoothGattService)
        lifecycleScope.launch(Dispatchers.Main) {
            val setting = initSettingAdvertising()
            while (true) {
                bluetoothAdapter?.bluetoothLeAdvertiser?.let {
                    val currentTime = System.currentTimeMillis()
                    it.startAdvertising(setting, initDataAdvertise(currentTime.toString()), advertiseCallback)
                    setTime(currentTime)
                    delay(1_000)
                    it.stopAdvertising(advertiseCallback)
                }
            }
        }
    }

    private fun initSettingAdvertising() = AdvertiseSettings.Builder()
        .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
        .setConnectable(false)
        .setTimeout(0)
        .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
        .build()

    private fun initDataAdvertise(time: String) = AdvertiseData.Builder()
        .setIncludeDeviceName(false)
        .addServiceUuid(SERVICE_UUID)
        .addServiceData(SERVICE_DATA_UUID, time.toByteArray())
        .build()

    private fun createGattService(): BluetoothGattService {

        val service = BluetoothGattService(SERVICE_UUID.uuid, BluetoothGattService.SERVICE_TYPE_PRIMARY)

        val characteristic = BluetoothGattCharacteristic(
            CHAR_UUID.uuid,
            BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ
        )

        service.addCharacteristic(characteristic)

        return service
    }

    @SuppressLint("MissingPermission")
    private fun connectDevices(device: BluetoothDevice) {
        Log.e("TAG", "connectDevices: ${device.name} ${device.address}")
        bluetoothGatt = device.connectGatt(context, false, bluetoothGattCallback)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initBluetooth() {
        Log.e("TAG", "initBluetooth: All permission granted", )
        bluetoothManager = activity?.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter ?: return
        enableBluetooth()
    }

    private fun initPermission() {
        Log.e("TAG", "initPermission: request permission", )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Dexter
                .withContext(context)
                .withPermissions(
                    BLUETOOTH_SCAN,
                    BLUETOOTH_CONNECT,
                    ACCESS_FINE_LOCATION,
                    BLUETOOTH,
                    BLUETOOTH_ADMIN,
                    BLUETOOTH_ADVERTISE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        if (p0?.areAllPermissionsGranted() == true) {
                            Log.e("TAG", "onPermissionsChecked: all permission granted", )
                            initBluetooth()
                            initGattServer()
                            return
                        }
                        Log.e("TAG", "onPermissionsChecked: ${p0?.deniedPermissionResponses?.map { it.permissionName }}", )
                        Log.e("TAG", "onPermissionsChecked: fail", )
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        Log.e("TAG", "onPermissionRationaleShouldBeShown: ", )
                        p1?.continuePermissionRequest()
                    }
                }).onSameThread()
                .check()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initGattServer() {
        bluetoothGattServer = bluetoothManager?.openGattServer(context, gattServerCallback)
        bluetoothGattServer?.addService(createGattService())
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun scanDevices() {
        val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
        lifecycleScope.launch(Dispatchers.Main) {
            Log.e("TAG", "scanDevices: start")
            bluetoothLeScanner?.startScan(leScanCallback)
//            delay(10_000)
//            Log.e("TAG", "scanDevices: stop")
//            bluetoothLeScanner?.stopScan(leScanCallback)
//            Log.e("TAG", "scanDevices: $devices", )
//            devices.forEach {

//                if (it.name.isNullOrEmpty()) return@forEach
//                connectDevices(it)
//            }
        }
    }

    private fun enableBluetooth() {
        Log.e("TAG", "enableBluetooth: ${bluetoothAdapter?.isEnabled}")
        if (bluetoothAdapter?.isEnabled == true) return
//        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//        bluetoothRequestEnableLauncher.launch(enableBluetoothIntent)
    }

    private fun setTime(time: Long) {
        calendar.timeInMillis = time
        (viewDataBinding as? FragmentHomeBluetoothBinding)?.textViewTime?.text = calendar.time.toString()
    }
}
