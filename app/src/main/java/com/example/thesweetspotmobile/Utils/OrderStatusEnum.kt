package com.example.sweetspot.Utils

enum class OrderStatusEnum(val status: String) {
    NotPaid("Не сплачено!"),
    Paid("Сплачено"),
    Processing("В процесі"),
    Sent("Відправлено"),
    Delivered("Доставлено"),
    Received("Отримано"),
    Unknown("Невідомий статус");

    companion object {
        fun fromInt(status: Int) = entries.toTypedArray().getOrElse(status) { Unknown }
    }
}