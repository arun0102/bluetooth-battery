package arun.pkg.bluetoothbattery

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = BluetoothAdapter.getDefaultAdapter()
        val iterator = adapter.bondedDevices.iterator()
        var status = ""
        while (iterator.hasNext()) {
            val device = iterator.next()
            val battery = getBatteryLevelReflection(device)
            if(-1 != battery) {
                status += "\n${device.name} -\t $battery"
            }
        }
        findViewById<TextView>(R.id.txt_battery).text = status
    }

    private fun getBatteryLevelReflection(pairedDevice: BluetoothDevice?): Int {
        return pairedDevice?.let { bluetoothDevice ->
            (bluetoothDevice.javaClass.getMethod("getBatteryLevel")).invoke(pairedDevice) as Int
        } ?: -1
    }
}