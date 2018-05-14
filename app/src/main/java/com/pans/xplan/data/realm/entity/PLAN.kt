package com.pans.xplan.data.realm.entity

import java.util.*

/**
 * @author android01
 * @date 2018/5/14.
 * @time 下午1:32.
 */
class PLAN  {
    var title: String? = null
    var completeTime: Date? = null
    var createTime: Date? = null

    constructor() {}

    constructor(title: String, completeTime: Date, createTime: Date) {
        this.title = title
        this.completeTime = completeTime
        this.createTime = createTime
    }
}
