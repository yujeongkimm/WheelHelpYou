package org.techtown.wheelhelpyou

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.techtown.wheelhelpyou.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
GoogleMap.OnMyLocationClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var MY_LOCATION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fun onRequestPermissionResult(requestCode : Int, permissions : Array<String>, grantResult : Array<Int>) {
            if(requestCode == MY_LOCATION_REQUEST_CODE) {
                if(permissions.size == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    if(checkCallingPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        checkCallingPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1)
                    }
                    mMap.isMyLocationEnabled = true
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //권한 확인
        if (checkCallingPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1
            )
        }
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

        // 위치 담을 list 리스트 설정
        val list = arrayListOf<Data>()
        // list에 Data 클래스 (이름, 위도/경도) 넣기
        list.add(Data("덕성여대",LatLng( 37.65137716381777, 127.01612821102516)))
        list.add(Data("효문고등학교",LatLng(37.653123393883156, 127.01836458404172)))

        // 반복문 통해 list의 좌표값 뽑아서 마커 설정하기
        for(i in 0..list.size-1) {
            val markerOption = MarkerOptions()
            markerOption.position(list[i].latLng).title(list[i].name)
            mMap.addMarker(markerOption)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(list[0].latLng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15F))



   }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this,"MyLocation clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this,"Current location:\n" + p0, Toast.LENGTH_LONG).show();
    }
}