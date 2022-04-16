package com.zarisa.answeringapp_cleanprojectlivedata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val number: Int,
    val questionText:String,
    val questionAnswer:String,
    val isAnswered:Boolean
)

@Dao
interface QuestionDao {


    @Query("SELECT * FROM Question WHERE number IN (:n)")
    fun getQuestionLiveData(n : Int?) : Question

    @Query("SELECT count(*) FROM QUESTION")
    fun getTotalNumberOfQuestionsLiveData():LiveData<Int>
    @Query("SELECT count(*) FROM QUESTION")
    fun getTotalNumberOfQuestionsInt():Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg questions: Question)

    @Delete
    fun delete(question : Question)

}

@Database(entities = [Question::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "myDB")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}