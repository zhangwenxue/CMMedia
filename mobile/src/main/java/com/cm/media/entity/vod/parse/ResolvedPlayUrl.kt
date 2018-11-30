package com.cm.media.entity.vod.parse

data class ResolvedPlayUrl(
    var image: String?,
    val default: Int,
    var type: String?,
    var resolution: String?,
    var url: String?
)