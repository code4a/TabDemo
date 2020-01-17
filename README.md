## RecyclerView Adapter 基类

> androidx databinding

### 依赖

```
implementation 'com.code4a:baservadapter:1.0.0'
```

### 使用

* 实现BaseHolder

```
class PostListHolder : BaseHolder<Post> {
    private val viewModel = PostViewModel()

    constructor(parent: ViewGroup) : super(parent, R.layout.item_post)

    override fun setData(data: Post, position: Int) {
        viewModel.bind(data)
        getBinding<ItemPostBinding>()!!.viewModel = viewModel
    }
}
```

* 实现BaseAdapter

```
class PostListAdapter2 : BaseAdapter<Post>(arrayListOf()) {

    override fun getBaseHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<Post> {
        return if (viewType == 0) PostListHolder(parent) else PostList2Holder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }

    fun updatePostList(postList: MutableList<Post>) {
        this.dataList.clear()
        this.dataList.addAll(postList)
        notifyDataSetChanged()
    }

```

### 参考

* [JessYanCoding的框架MVPArms中的DefaultAdapter](https://github.com/JessYanCoding/MVPArms/blob/master/arms/src/main/java/com/jess/arms/base/DefaultAdapter.java)