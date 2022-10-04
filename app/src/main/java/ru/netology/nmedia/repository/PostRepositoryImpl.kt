package ru.netology.nmedia.repository

import androidx.paging.*
import kotlinx.coroutines.flow.*
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.error.UnknownError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
) : PostRepository {

    override val data: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = false, maxSize = 15),
        pagingSourceFactory = { postDao.getAll() }
    ).flow
        .map { it.map(PostEntity::toDto) }

    override suspend fun getAll() {
        try {
            postDao.getAll()
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun save(post: Post) {
        try {
            postDao.insert(PostEntity.fromDto(post))
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            postDao.removeById(id)
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}
