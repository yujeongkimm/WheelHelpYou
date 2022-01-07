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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.techtown.wheelhelpyou.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
GoogleMap.OnMyLocationClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var MY_LOCATION_REQUEST_CODE = 1

    private var info_list = arrayListOf<String>()

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

        // 정보 list
        info_list.add("영업시간")
        info_list.add("dlfkjalkdfjlaksdjflkdsajflk")
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
        list.add(Data("도봉구보건소",LatLng(37.6579418, 127.0388316)))
        list.add(Data("도봉구청",LatLng(37.66871613, 127.0471314)))
        list.add(Data("지체장애인협회도봉구지",LatLng(37.6564046, 127.0388316)))
        list.add(Data("녹천역",LatLng(37.64569591, 127.0509199921)))
        list.add(Data("방학역",LatLng(37.66634661, 127.0444231)))
        list.add(Data("도봉산역",LatLng(37.6897507, 127.0449195)))
        list.add(Data("쌍문역",LatLng(37.64884986, 127.0348807)))
        list.add(Data("창동",LatLng(37.65321748, 127.047715)))
        list.add(Data("도봉장애인복지관",LatLng(37.66943029, 127.0477018)))
        list.add(Data("도봉노적성해장애인자립센터",LatLng(37.67648534, 127.0471941)))
        list.add(Data("도봉장애인자립생활센터",LatLng(37.64491726, 127.0309699)))
        list.add(Data("도봉1동주민센터",LatLng(37.67869086, 127.0434133)))
        list.add(Data("도봉구민회관",LatLng(37.65416727, 127.0386849)))
        list.add(Data("도봉구청",LatLng(37.66871613, 127.0471314)))
        list.add(Data("도봉동노인복지센터",LatLng(37.68212785, 127.0435807)))
        list.add(Data("도봉병원",LatLng(37.66876084, 127.043802)))
        list.add(Data("도봉산역",LatLng(37.68912056, 127.0465288)))
        list.add(Data("방아골종합사회복지관",LatLng(37.66931252,127.0326388 )))
        list.add(Data("방학동노인복지센터",LatLng(37.67057374, 127.0322023 )))
        list.add(Data("쌍문1동주민센터",LatLng(37.64803784,127.0260513 )))
        list.add(Data("한일병원",LatLng(37.64668485,127.0290583 )))

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