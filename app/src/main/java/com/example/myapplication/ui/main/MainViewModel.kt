package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.CitiesAndFoods
import com.example.myapplication.data.model.CustomObject
import com.example.myapplication.data.repository.CitiesAndFoodsRepository
import com.example.myapplication.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mCitiesAndFoodsRepository: CitiesAndFoodsRepository,
    private val mErrorAnalyzer: ErrorAnalyzer
) : ViewModel() {

    private val _mGetCitiesAndFoods: MutableLiveData<Event<Result<List<CustomObject>>>> =
        MutableLiveData()
    val mGetCitiesAndFoods: LiveData<Event<Result<List<CustomObject>>>> = _mGetCitiesAndFoods

    val isSwipeRefresh: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))

    fun getCitiesAndFoods() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _mGetCitiesAndFoods.postValue(
                Event(
                    Result.error(mErrorAnalyzer.getAnalyzedErrorMessage(throwable))
                )
            )
        }) {
            _mGetCitiesAndFoods.postValue(Event(Result.loading()))
            try {
                mCitiesAndFoodsRepository.getCitiesAndFoods().let { response ->
                    if (response.isSuccessful)
                        response.body()?.let {
                            makeList(it)
                        }
                    else
                        response.errorBody()?.let {

                            _mGetCitiesAndFoods.postValue(
                                Event(
                                    Result.error(
                                        it.string()
                                    )
                                )
                            )
                        }
                }
            } catch (exp: Exception) {
                Timber.e(exp)
                _mGetCitiesAndFoods.postValue(
                    Event(
                        Result.error(exp.localizedMessage)
                    )
                )
            }
        }
    }

    private fun makeList(citiesAndFoods: CitiesAndFoods) {
        val finalList = mutableListOf<CustomObject>()

        finalList.add(CustomObject("Main Cities in Japan", Constants.VIEW_TYPE_TITLE))

        citiesAndFoods.mCities?.let { cities ->
            for (city in cities) {
                val customObject = CustomObject(city, Constants.VIEW_TYPE_CITY)
                finalList.add(customObject)
            }
        }

        finalList.add(CustomObject("Most Popular Japanese Food", Constants.VIEW_TYPE_TITLE))

        citiesAndFoods.mFoods?.let { foods ->
            for (food in foods) {
                val customObject = CustomObject(food, Constants.VIEW_TYPE_FOOD)
                finalList.add(customObject)
            }
        }

        _mGetCitiesAndFoods.postValue(Event(Result.success(finalList)))
    }
}