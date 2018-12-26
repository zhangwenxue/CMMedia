package com.cm.media.entity.discover

data class TopicVod(
    var area: String,
    val id: Int,
    var img: String,
    var info: String,
    val isHas: Int,
    val isOver: Int,
    val isTrailer: Int,
    val isVip: Int,
    var name: String,
    val orders: Int,
    val playSum: Int,
    val season: Int,
    val source: Int,
    val sourceIsVip: Int,
    val sourceOrderHot: Int,
    val sourcePlaySum: Int,
    var sourcePlaySumText: String,
    var sourceScore: String,
    var sourceSort: String,
    var sourceUrl: String,
    val type: Int,
    var updateText: String,
    val videoTopicId: Int,
    val year: Int
)