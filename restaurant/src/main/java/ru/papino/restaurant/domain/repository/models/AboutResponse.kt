package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.core.net.repeater.RequestRepeatError

internal sealed class AboutResponse {

    data class Success(val data: AboutModel) : AboutResponse()

    data class Error(val error: Exception) : AboutResponse(), RequestRepeatError
}