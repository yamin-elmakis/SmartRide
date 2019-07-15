package com.example.smartride.utils

inline fun <reified T, R> T.changed(old: Any?, member: T.() -> R, action: (newValue: R) -> Unit) {
    if (old !is T || this.member() != old.member()) action.invoke(this.member())
}

inline fun <reified T> T.changedAny(old: Any?, vararg members: T.() -> Any?, action: () -> Unit) {
    if (old !is T || members.any { it.invoke(this) != it.invoke(old) }) action.invoke()
}