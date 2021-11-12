package mr.adkhambek.mvvm.datasource.db


import androidx.room.Database
import androidx.room.RoomDatabase
import mr.adkhambek.mvvm.datasource.db.entity.item.ItemDao
import mr.adkhambek.mvvm.datasource.db.entity.item.ItemEntity


@Database(
    entities = [
        ItemEntity::class,
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
}