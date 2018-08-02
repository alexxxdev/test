package com.github.alexxxdev.test

import io.realm.RealmList
import io.realm.annotations.PrimaryKey
import io.realm.RealmObject


class Person : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String? = null
    var dogs: RealmList<Dog>? = null
}
