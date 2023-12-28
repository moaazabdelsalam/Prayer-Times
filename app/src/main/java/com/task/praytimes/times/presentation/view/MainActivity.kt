package com.task.praytimes.times.presentation.view

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.task.praytimes.R
import com.task.praytimes.databinding.ActivityMainBinding
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.presentation.alarm.AlarmItem
import com.task.praytimes.times.presentation.alarm.AlarmSchedulerImp
import com.task.praytimes.times.presentation.models.DayDate
import com.task.praytimes.times.presentation.models.Prayer
import com.task.praytimes.times.presentation.models.PrayerTimes
import com.task.praytimes.times.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "TAG MainActivity"
    lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var datesAdapter: DatesAdapter
    private lateinit var timesAdapter: TimesAdapter
    private lateinit var prayerTimes: List<PrayerTimes>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val listener: (String) -> Unit = {
        showViews(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getLocation()
        lifecycleScope.launch(Dispatchers.IO) {
            homeViewModel.prayerTimesState.collectLatest {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "failure: ${it.error}")
                        Snackbar.make(
                            binding.root,
                            "something went wrong check connection and try again",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    is ApiState.Loading -> Log.i(TAG, "loading")
                    is ApiState.Success -> {
                        prayerTimes = it.data
                        Log.i(TAG, "success: $prayerTimes")
                        withContext(Dispatchers.Main) { showViews(getCurrentDate()) }
                        scheduleAlarmToPrayerTimes()
                    }
                }
            }
        }
    }

    private fun init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        datesAdapter = DatesAdapter(listener)
        timesAdapter = TimesAdapter()
        binding.apply {
            datesRV.adapter = datesAdapter
            timesRV.adapter = timesAdapter
        }
    }

    private fun showViews(selectedDate: String) {
        datesAdapter.submitList(getDatesList(prayerTimes))
        timesAdapter.submitList(getPrayerList(prayerTimes, selectedDate))
        val today = prayerTimes.find {
            it.date == selectedDate
        } ?: prayerTimes[0]
        binding.apply {
            dayOfWeekTxt.text = today.weekDay
            dateHijriTxt.text = today.hijriDate
            dateTxt.text = today.date
        }
    }

    private fun getDatesList(prayerTimes: List<PrayerTimes>): List<DayDate> {
        return prayerTimes.map {
            DayDate(
                dayOfWeek = it.weekDay,
                fullDate = it.date
            )
        }
    }

    private fun getPrayerList(prayerTimes: List<PrayerTimes>, selectedDate: String): List<Prayer> {
        val targetPrayerTimes = prayerTimes.find {
            it.date == selectedDate
        } ?: prayerTimes[0]

        return listOf(
            Prayer(name = "صلاه الفجر", time = targetPrayerTimes.fajr),
            Prayer(name = "شروق الشمس", time = targetPrayerTimes.sunrise),
            Prayer(name = "صلاه الظهر", time = targetPrayerTimes.dhuhr),
            Prayer(name = "صلاه العصر", time = targetPrayerTimes.asr),
            Prayer(name = "غروب الشمس", time = targetPrayerTimes.sunset),
            Prayer(name = "صلاه المغرب", time = targetPrayerTimes.maghrib),
            Prayer(name = "صلاه العشاء", time = targetPrayerTimes.isha),
            Prayer(name = "ثلث الليل الاول", time = targetPrayerTimes.firstThird),
            Prayer(name = "ثلث الليل الاخير", time = targetPrayerTimes.lastThird)
        )
    }

    private fun scheduleAlarmToPrayerTimes() {
        lifecycleScope.launch(Dispatchers.IO) {
            val scheduler = AlarmSchedulerImp(this@MainActivity)
            prayerTimes.forEach {
                val date = it.date.split("-")
                val fajrTime = it.fajr.split(":")
                val dhuhrTime = it.dhuhr.split(":")
                val asrTime = it.asr.split(":")
                val maghribTime = it.maghrib.split(":")
                val ishaTime = it.isha.split(":")
                scheduler.schedule(createAlarmItem(date, fajrTime, "Fajr"))
                scheduler.schedule(createAlarmItem(date, dhuhrTime, "Dhuhr"))
                scheduler.schedule(createAlarmItem(date, asrTime, "Asr"))
                scheduler.schedule(createAlarmItem(date, maghribTime, "Maghrib"))
                scheduler.schedule(createAlarmItem(date, ishaTime, "Isha"))
            }
        }
    }

    private fun createAlarmItem(
        date: List<String>,
        time: List<String>,
        timing: String
    ): AlarmItem {
        return AlarmItem(
            LocalDateTime.of(
                date[2].toInt(),
                date[1].toInt(),
                date[0].toInt(),
                time[0].toInt(),
                time[1].split(" ")[0].toInt()
            ),
            timing
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(p0: LocationResult?) {
            p0?.let {
                val currentDate = getCurrentDate().split("-")
                val lastLocation: Location = it.lastLocation
                homeViewModel.getPrayerTimes(
                    year = currentDate[2].toInt(),
                    month = currentDate[1].toInt(),
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            }
        }
    }

    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                showLocationDisabledDialog()
            }
        } else requestPermission()
    }

    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 60 * 60 * 1000

        if (checkPermission()) fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionMap: Map<String, Boolean> ->
                if (permissionMap.any { isGranted -> isGranted.value }) {
                    if (isLocationEnabled()) {
                        requestNewLocationData()
                    } else
                        showLocationDisabledDialog()
                } else {
                    showLocationPermissionNeededDialog()
                }
            }
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        )
    }

    private fun showLocationPermissionNeededDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.permission_needed))
            .setMessage(getString(R.string.location_permission_needed_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.open_setting)) { dialog, _ ->
                openAppDetailsSettings()
                dialog.dismiss()
            }
            .show()
    }

    private fun openAppDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showLocationDisabledDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.location_disabled))
            .setMessage(getString(R.string.location_disabled_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.open_setting)) { dialog, _ ->
                openLocationSettings()
                dialog.dismiss()
            }
            .show()
    }

    private fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return sdf.format(calendar.time)
    }
}