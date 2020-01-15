package com.jiangyt.tabdemo.ui.post

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jiangyt.tabdemo.R
import com.jiangyt.tabdemo.base.BaseViewModel
import com.jiangyt.tabdemo.model.Post
import com.jiangyt.tabdemo.model.PostDao
import com.jiangyt.tabdemo.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Desc: com.jiangyt.tabdemo.ui.post
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi

    val postListAdapter: PostListAdapter2 = PostListAdapter2()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts() {
        subscription = Observable.fromCallable { postDao.all }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty())
                    postApi.getPosts().concatMap { apiPostList ->
                        postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { error -> onRetrievePostListError(error) }
            )

    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: MutableList<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        Log.e("JYT", error.message)
        errorMessage.value = R.string.app_name
    }
}