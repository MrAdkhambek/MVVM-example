package mr.adkhambek.mvvm.datasource.db.mapper

import mr.adkhambek.mvvm.datasource.db.EntityMapper
import mr.adkhambek.mvvm.datasource.db.entity.item.ItemEntity
import mr.adkhambek.mvvm.model.ItemDTO
import javax.inject.Inject


class ItemMapper @Inject constructor() : EntityMapper<ItemDTO, ItemEntity> {

    override fun mapTR(from: ItemDTO): ItemEntity = ItemEntity(
        loginRequired = from.loginRequired,
        startDate = from.startDate,
        endDate = from.endDate,
        objType = from.objType,
        icon = from.icon,
        name = from.name,
        url = from.url,
    )

    override fun mapRT(from: ItemEntity): ItemDTO = ItemDTO(
        loginRequired = from.loginRequired,
        startDate = from.startDate,
        endDate = from.endDate,
        objType = from.objType,
        icon = from.icon,
        name = from.name,
        url = from.url,
    )
}