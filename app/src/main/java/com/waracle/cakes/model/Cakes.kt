package com.waracle.cakes.model

import com.google.gson.annotations.SerializedName

data class Cakes(

    @SerializedName("title") var title: String? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("image") var image: String? = null

)