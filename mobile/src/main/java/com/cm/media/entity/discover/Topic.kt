package com.cm.media.entity.discover

data class Topic(
    var appCheckStatus: String,
    var attention: String,
    val beginTime: Long,
    val createUserId: Int,
    val endTime: Long,
    val id: Int,
    var img: String,
    var info: String,
    var isDelete: String,
    val isDiscover: Int,
    val isHome: Int,
    val ordersDiscover: Int,
    val ordersHome: Int,
    val showType: Int,
    val summary: String,
    var title: String,
    val topicId: Int,
    var videoList: List<TopicVod>
)