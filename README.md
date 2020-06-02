# Tech Crunch News (Example)

### Clean Architecture

data, domain, presentation layer 분리


### Coroutine
LiveData lifeCycle 서브루틴 동작과 코드 간결화


### DataBinding
view value 셋팅을 layout xml로 위임


### StateResult
remote api result 의 상태 전달

```kotlin
sealed class StateResult<out R> {
    object Loading : StateResult<Nothing>()
    data class Success<out T>(val item: T) : StateResult<T>()
    data class Failure(val cause: Throwable) : StateResult<Nothing>()
}
```

### Koin (Dependency Injection)
modules injection 에 사용