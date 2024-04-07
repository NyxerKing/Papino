package ru.papino.maps

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraListener
import ru.papino.maps.core.MapPermissions
import ru.papino.maps.core.contracts.AddressActivityContract
import ru.papino.maps.databinding.ActivityMapBinding
import ru.papino.maps.listeners.MapCameraListener
import ru.papino.maps.listeners.MapInputListener
import ru.papino.maps.listeners.MapLocationListener
import ru.papino.uikit.extensions.showAlert

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapKit: MapKit
    private lateinit var mapInputListener: MapInputListener
    private lateinit var mapLocationListener: MapLocationListener
    private lateinit var mapCameraListener: CameraListener

    private val viewModel by lazy { MapViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapKitFactory.initialize(this)
        initReference()
        initPermissions()
        initActions()
    }

    override fun onStart() {
        super.onStart()
        mapKit.onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        mapKit.onStop()
        super.onStop()
    }

    private fun initReference() {
        mapKit = MapKitFactory.getInstance()

        mapInputListener = MapInputListener(
            context = this,
            onMapTapAddress = { address ->
                address?.let {
                    val intentResult = Intent().apply {
                        putExtra(AddressActivityContract.KEY, it)
                    }
                    setResult(Activity.RESULT_OK, intentResult)
                    finish()
                } ?: run {
                    this.showAlert("Ошибка", "Адрес не определился", {})
                }
            })

        mapLocationListener = MapLocationListener(
            onLocation = { viewModel.openMyLocation(getMap(), it) }
        )

        mapCameraListener = MapCameraListener { updateNearMeTint(isClick = false) }
    }

    private fun initPermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d(TAG, "isGranted = true")
                } else {
                    Log.d(TAG, "isGranted = false")
                }
            }
        val mapPermissions = MapPermissions(requestPermissionLauncher)
        mapPermissions.request(this, ::initMapLocation)
    }

    private fun initMapLocation() {
        mapKit.createLocationManager().requestSingleUpdate(mapLocationListener)
    }

    private fun initActions() {
        initMapActions()

        with(binding) {
            imageButtonNearMe.setOnClickListener {
                updateNearMeTint(true)
                initMapLocation()
            }
        }
    }

    private fun initMapActions() {
        getMap().addInputListener(mapInputListener)
        getMap().addCameraListener(mapCameraListener)
    }

    private fun updateNearMeTint(isClick: Boolean) {
        val color = resources.getColor(
            if (isClick) ru.papino.uikit.R.color.backgroundAccentColor else ru.papino.uikit.R.color.elementPrimaryColor,
            theme
        )
        binding.imageButtonNearMe.imageTintList = ColorStateList.valueOf(color)
    }

    private fun getMap() = binding.mapview.mapWindow.map

    companion object {

        private const val TAG = "MapActivity"
        fun getInstance(context: Context) = Intent(context, MapActivity::class.java)
    }
}