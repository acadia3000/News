# Tech Crunch News (Example)

### Clean Architecture

data, domain, presentation layer 분리

UiController(Activity, Fragment) <--> ViewModel <--> UseCase <--> Repository <--> RemoteDataSource

### Coroutine
LiveData lifeCycle 서브루틴 동작과 코드 간결화
```kt
class ActionStateLiveData<T>(
    initialLastUpdatedAt: Long = 0,
    fetch: (suspend ActionStateLiveData<T>.() -> T)
) : MutableLiveData<StateResult<T>>() {

    private val action = MutableLiveData<ActionState>(ActionState.None)

    private lateinit var liveDataScope: CoroutineScope
    private var job: Job? = null

    private val observer = Observer<ActionState> {
        when (it) {
            is ActionState.Load -> {
                if (it.withLoadingProgress) {
                    emit(StateResult.Loading)
                }

                job = liveDataScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            fetch()
                        }
                        emit(StateResult.Success(response))
                    } catch (ignored: CancellationException) {
                    } catch (e: Throwable) {
                        emit(StateResult.Failure(e))
                    }
                }
            }
        }
    }

    private var lastUpdatedAt: Long = initialLastUpdatedAt

    fun emit(StateResult: StateResult<T>) {
        value = StateResult

        if (StateResult !is StateResult.Loading) {
            action.value = ActionState.None
        }

        if (StateResult is StateResult.Success<T>) {
            lastUpdatedAt = Calendar.getInstance().timeInMillis
        }
    }

    override fun onActive() {
        super.onActive()

        liveDataScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        action.observeForever(observer)
    }

    override fun onInactive() {
        super.onInactive()

        action.removeObserver(observer)
        liveDataScope.cancel()
    }

    fun isLoading() = (action.value is ActionState.Load)

    fun load(withLoadingProgress: Boolean = true) {
        if (isLoading()) {
            return
        }

        action.value = ActionState.Load(withLoadingProgress)
    }

    fun refresh(intervalInSeconds: Long) {
        if (intervalInSeconds <= 1) {
            return
        }

        val diff = Calendar.getInstance().timeInMillis - lastUpdatedAt
        val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff)

        if (diffInSeconds >= intervalInSeconds) {
            load(false)
        }
    }

    fun cancel() {
        job?.cancel()
        action.value = ActionState.None
    }
}
```

간단하게 사용 가능
```kt
val headlines = ActionStateLiveData<List<Article>> {
    useCase.headlines()
}
```

### DataBinding
view value 셋팅을 layout xml로 위임


### StateResult
remote api result 의 상태 전달

```kt
sealed class StateResult<out R> {
    object Loading : StateResult<Nothing>()
    data class Success<out T>(val item: T) : StateResult<T>()
    data class Failure(val cause: Throwable) : StateResult<Nothing>()
}
```

StateResult 사용
```kt
viewModel.articles.observe(this, Observer { data ->
        when (data) {
            is StateResult.Success<List<Article>> -> {
                listAdapter.submitList(data.item)
            }
            is StateResult.Failure -> {
                Toast.makeText(this, data.cause.message, Toast.LENGTH_LONG).show()
            }
        }
    })
}
```

### Koin (Dependency Injection)
modules injection 에 사용
