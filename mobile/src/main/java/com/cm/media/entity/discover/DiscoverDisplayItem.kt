package com.cm.media.entity.discover

data class DiscoverDisplayItem(
    val id: Int,
    val videoTopicId: Int,
    val type: Int,
    val source: Int,
    var name: String,
    var info: String,
    var img: String,
    var area: String,
    var year: String,
    var sourceScore: String,
    val sourceIsVip: Int,
    val sourcePlaySum: Int,
    val sourcePlaySumText: String,
    val isVip: Int,
    var updateText: String,
    var playSum: Int,
    val isTrailer: Int,
    val sourceOrderHot: Int,
    var sourceUrl: String,
    val isHas: Int,
    var season: String,
    val isOver: Int,
    val orders: Int,
    val sourceSort: Int
)