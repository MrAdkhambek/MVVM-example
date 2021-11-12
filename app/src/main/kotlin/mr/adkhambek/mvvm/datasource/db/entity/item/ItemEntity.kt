package mr.adkhambek.mvvm.datasource.db.entity.item


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "item_table"
)
data class ItemEntity(

    @ColumnInfo(name = "end_date")
    val endDate: String,

    @ColumnInfo(name = "icon")
    val icon: String,

    @ColumnInfo(name = "login_required")
    val loginRequired: Boolean,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "obj_type")
    val objType: String,

    @ColumnInfo(name = "start_date")
    val startDate: String,

    @ColumnInfo(name = "url")
    val url: String,
) {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Long? = null
}