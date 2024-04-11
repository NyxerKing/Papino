package ru.papino.restaurant.data.mappers

import ru.papino.restaurant.data.datasource.net.models.AboutJsonModel
import ru.papino.restaurant.domain.models.AboutModel

internal class AboutMapper {

    fun toDomain(data: AboutJsonModel) = AboutModel(
        id = data.id,
        name = data.name,
        aboutOrganization = data.aboutOrganization,
        latitude = data.latitude,
        longitude = data.longitude,
        isClose = data.isClose
    )
}