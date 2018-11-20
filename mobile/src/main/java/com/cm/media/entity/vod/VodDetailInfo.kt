package com.cm.media.entity.vod

data class VodDetailInfo(
    var alias: String?,
    var director: String?,
    var actor: String?,
    var host: String?,
    var guest: String?,
    val episodeNum: Int,
    var episodeText: String,
    var summary: String,
    val superId: Int,
    var tagText: String?,
    var actorText: String?
)