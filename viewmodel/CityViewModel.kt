package com.example.nearbyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbyapp.data.model.LocationResponse
import com.example.nearbyapp.data.model.Venue
import com.example.nearbyapp.repo.CityRepository
import com.example.nearbyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {

    init {
        fetchVenuesFromDb()
    }

    private val _searchCity: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()
    val searchCity: LiveData<Resource<LocationResponse>> get() = _searchCity
    var cachedLocation: MutableLiveData<Resource<List<Venue>>> = MutableLiveData()
        private set

    var pageNum = 1;

    private fun fetchVenuesFromDb() = viewModelScope.launch(Dispatchers.IO) {
        _searchCity.postValue(Resource.Loading())

        val venues = cityRepository.getLastStoredVenuesFromDB()
        if (venues?.isNotEmpty() == true)
            Resource.Success(venues).let { cachedLocation.postValue(it) }
    }


    fun searchCity(lat: Double, long: Double) = viewModelScope.launch(Dispatchers.IO) {
        // check for network connection before making network Request
        val data: MutableMap<String, String> = HashMap()
        data["lat"] = lat.toString()
        data["lon"] = long.toString()
        data["page"] = (pageNum++).toString()
        val response = cityRepository.searchCity(data)
        handleSearchCityResponse(response)
        _searchCity.postValue(handleSearchCityResponse(response))
    }

    private suspend fun handleSearchCityResponse(response: Response<LocationResponse>): Resource<LocationResponse> {
        if (response.isSuccessful) {
            response.body()?.let { res ->
                res.venues?.let {
                    cityRepository.addVenues(it)
                }

                return Resource.Success(res)
            }
        }
        return Resource.Error(response.message())
    }
}