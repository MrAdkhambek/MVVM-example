package mr.adkhambek.mvvm.datasource.db.entity.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Query("SELECT * FROM item_table WHERE _id ORDER BY _id")
    fun getAsFlow(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item_table WHERE _id ORDER BY _id")
    suspend fun getItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userInfoEntity: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(messageEntity: List<ItemEntity>): List<Long>

    @Query("DELETE FROM item_table")
    suspend fun clearAll()
}