package com.lozasolutions.fredompop.data.model

import com.google.gson.annotations.SerializedName

data class InfoResponse(val id: String, val name: String, val sprites: Sprites, val stats: List<Statistic>)

