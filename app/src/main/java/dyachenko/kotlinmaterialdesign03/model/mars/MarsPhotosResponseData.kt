package dyachenko.kotlinmaterialdesign03.model.mars

import com.google.gson.annotations.SerializedName

data class MarsPhotosResponseData(
    @field:SerializedName("photos") val photos: Array<MarsPhotoResponseData>,
)
