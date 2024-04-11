package ru.papino.restaurant.domain.response

import ru.papino.restaurant.core.net.repeater.RequestRepeatError
import ru.papino.restaurant.domain.models.AboutModel

internal sealed class AboutResponse {

    data class Success(val data: AboutModel) : AboutResponse()

    data class Error(val error: Exception) : AboutResponse(), RequestRepeatError
}