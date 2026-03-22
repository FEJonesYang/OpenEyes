# 用户登录功能设计文档

**日期:** 2026-03-22
**模块:** module-user
**API:** WanAndroid 开放接口

---

## 需求概述

在「我的」Tab（UserFragment）检测登录状态，未登录时跳转登录页，用户输入账号密码完成登录，登录信息通过 MMKV 持久化（失败时降级 SharedPreferences），登录成功后返回 UserFragment 展示用户信息。

---

## 架构

```
LoginActivity
  └── LoginFragment (BaseFragment<LoginViewModel>)
        └── LoginViewModel (BaseViewModel)
              └── LoginRequest (BaseRequest<LoginViewModel>)
                    └── WanAndroidService (Retrofit)
              └── UserStore
                    ├── MMKVStore : KVStore  (主)
                    └── SPStore : KVStore    (降级)
```

---

## 组件说明

### KVStore 接口

```kotlin
interface KVStore {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun remove(key: String)
}
```

### UserStore

- 单例，懒加载时通过 `ApplicationProvider.getContext()` 获取 Context
- 尝试 `MMKV.initialize(context)` → 成功使用 `MMKVStore`
- 初始化失败（catch Exception）→ 降级使用 `SPStore`，用户无感知
- 对外暴露：`saveUser(username: String)`、`getUsername(): String?`、`clear()`

### WanAndroid 登录接口

```
POST https://www.wanandroid.com/user/login
Form: username, password
Response: { errorCode: Int, errorMsg: String, data: UserInfo }
```

WanAndroid 返回字段为 `errorCode`/`errorMsg`，与项目 `BaseResponse`（`code`/`message`）不同。
`WanAndroidResponse<T>` 单独定义，使用 `@SerializedName` 映射：

```kotlin
class WanAndroidResponse<T> {
    @SerializedName("errorCode") val code: Int = -1
    @SerializedName("errorMsg") val message: String = ""
    val data: T? = null
}
```

### LoginRequest

遵循项目 `BaseRequest` 模式：

```kotlin
class LoginRequest(vm: LoginViewModel) : BaseRequest<LoginViewModel>(vm) {
    override var url = "https://www.wanandroid.com/"
    suspend fun login(username: String, password: String) =
        request<WanAndroidService>().login(username, password)
}
```

### LoginViewModel

- 继承 `BaseViewModel`，`loadData()` 为空实现（登录无需预加载数据）
- `loginSuccess: MutableLiveData<UserInfo>` — 登录成功时发送
- `fun login(username: String, password: String)` — 调用 `LoginRequest`，通过 `beforeRequest()` / `afterRequest()` 管理 loading/error 状态
- 登录成功后调用 `UserStore.saveUser(username)`

### LoginFragment

- 账号输入框、密码输入框、登录按钮
- 输入为空时按钮禁用
- 观察 `loginSuccess`：成功 → `requireActivity().finish()`
- 观察 `vm.error`：失败 → Toast 错误信息（BaseFragment 已处理 loading/error）

### UserFragment（改造）

- `onResume` 中检测 `UserStore.getUsername()`
- 未登录 → DRouter 跳转 `LoginActivity`
- 已登录 → 展示用户名

---

## 数据模型

```kotlin
data class UserInfo(
    val id: Int,
    val username: String,
    val nickname: String
)
```

---

## 错误处理

| 场景 | 处理方式 |
|------|----------|
| MMKV 初始化失败 | 降级 SharedPreferences，用户无感知 |
| 网络请求失败 | `vm.error` 触发，BaseFragment 展示错误视图 |
| 账号密码错误 | `errorCode != 0`，Toast `errorMsg` |
| 输入为空 | 前端校验，按钮禁用 |

---

## 文件清单

| 文件路径 | 职责 |
|----------|------|
| `module-user/.../login/LoginActivity.kt` | 承载 Fragment |
| `module-user/.../login/LoginFragment.kt` | 登录表单 UI |
| `module-user/.../login/LoginViewModel.kt` | 登录业务逻辑 |
| `module-user/.../login/LoginRequest.kt` | 网络请求（继承 BaseRequest） |
| `module-user/.../api/WanAndroidService.kt` | Retrofit 接口定义 |
| `module-user/.../api/WanAndroidResponse.kt` | 响应包装类 |
| `module-user/.../store/KVStore.kt` | 存储接口 |
| `module-user/.../store/MMKVStore.kt` | MMKV 实现 |
| `module-user/.../store/SPStore.kt` | SP 降级实现 |
| `module-user/.../store/UserStore.kt` | 单例，管理存储策略 |
| `module-user/.../UserFragment.kt` | 改造：登录态检测 + 用户信息展示 |

---

## 依赖

- `module-user/build.gradle` 新增：`com.tencent:mmkv`
- WanAndroid base URL: `https://www.wanandroid.com/`
