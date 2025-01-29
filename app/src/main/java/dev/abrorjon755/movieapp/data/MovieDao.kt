package dev.abrorjon755.movieapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.abrorjon755.movieapp.model.StoredMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<StoredMovie>>

    @Insert
    suspend fun insertAll(movies: StoredMovie)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovie(id: Int)
}