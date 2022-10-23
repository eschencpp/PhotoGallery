package com.example.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()
    private val preferencesRepository = PreferencesRepository.get()
    private val _uiState: MutableStateFlow<PhotoGalleryUIState> =
        MutableStateFlow(PhotoGalleryUIState())
    val uiState: StateFlow<PhotoGalleryUIState>
        get() = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest { storedQuery ->
                try {
                    val items = fetchGalleryItems(storedQuery)
                    _uiState.update { oldState ->
                        oldState.copy(
                            images = items,
                            query = storedQuery
                        )
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to fetch gallery items", ex)
                }
            }
        }
    }

    fun setQuery(query: String){
        viewModelScope.launch { preferencesRepository.setStoredQuery(query) }
    }

    private suspend fun fetchGalleryItems(query: String) : List<GalleryItem> {
        return if (query.isNotEmpty()){
            photoRepository.searchPhotos(query)
        } else{
            photoRepository.fetchPhotos()
        }
    }

}

data class PhotoGalleryUIState(
    val images: List<GalleryItem> = listOf(),
    val query: String = "",
)