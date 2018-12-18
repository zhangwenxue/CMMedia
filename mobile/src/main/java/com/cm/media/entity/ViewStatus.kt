package com.cm.media.entity


data class ViewStatus(val state: Int = 0, var message: String?) {
    companion object {
        const val STATE_ERROR: Int = -1
        const val STATE_LOADING: Int = 0
        const val STATE_EMPTY: Int = -2
        const val STATE_SUCCESS: Int = 1
        fun generateErrorStatus(msg: String?): ViewStatus {
            return ViewStatus(STATE_ERROR, msg)
        }

        fun generateSuccessStatus(msg: String?): ViewStatus {
            return ViewStatus(STATE_SUCCESS, msg)
        }

        fun generateEmptyStatus(msg: String?): ViewStatus {
            return ViewStatus(STATE_EMPTY, msg)
        }

        fun generateLoadingStatus(msg: String?): ViewStatus {
            return ViewStatus(STATE_LOADING, msg)
        }

    }
}