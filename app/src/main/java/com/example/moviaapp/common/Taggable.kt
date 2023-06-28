package com.example.moviaapp.common



interface Taggable {

    val tag: String
        get() = this.javaClass.simpleName
}
