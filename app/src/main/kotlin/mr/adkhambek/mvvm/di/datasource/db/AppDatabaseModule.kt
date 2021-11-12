package mr.adkhambek.mvvm.di.datasource.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mr.adkhambek.mvvm.BuildConfig
import mr.adkhambek.mvvm.datasource.db.AppDatabase
import mr.adkhambek.mvvm.datasource.db.entity.item.ItemDao
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
open class AppDatabaseModule {

    @[Provides Singleton]
    open fun getRoomDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        val builder = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "AppDatabase"
        )

        if (BuildConfig.DEBUG) builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @[Provides Singleton]
    open fun getItemDao(db: AppDatabase): ItemDao = db.itemDao
}