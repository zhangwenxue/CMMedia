package com.cm.media.entity.discover

import com.cm.media.entity.discover.banner.Banner

data class Discovery(var banner: Banner, var names: List<DiscoverItem>, val topics: List<Topic>)