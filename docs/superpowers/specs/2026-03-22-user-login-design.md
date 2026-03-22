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
              └── UserRepository
                    ├── WanAndroidService (Retrofit)
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

- 单例，懒加载时尝试 `MMKV.initialize(context)`
- 初始化成功 → 使用 `MMKVStore`
- 初始化失败（catch Exception）→ 降级使用 `SPStore`
- 对外暴露：`saveUser(username, cookie)`、`getUser(): UserInfo?`、`clear()`

### WanAndroidService

```
POST https://www.wanandroid.com/user/login
Form: username, password
Response: { errorCode: Int, errorMsg: String, data: UserInfo }
```

Cookie 由 OkHttp 自动处理，同时将 `username` 存入 UserStore。

### LoginViewModel

- `loginResult: MutableLiveData<Result<UserInfo>>`
- `fun login(username: String, password: String)` — 调用 `UserRepository`，通过 `beforeRequest()` / `afterRequest()` 管理 loading/error 状态

### LoginFragment

- 账号输入框、密码输入框、登录按钮
- 观察 `loginResult`：成功 → `requireActivity().finish()`；失败 → Toast 错误信息
- 输入为空时按钮禁用

### UserFragment（改造）

- `onResume` 中检测 `UserStore.getUser()`
- 未登录 → DRouter 跳转 `LoginActivity`
- 已登录 → 展示用户名等信息

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
| 网络请求失败 | BaseViewModel.error 触发，Toast 提示 |
| 账号密码错误 | WanAndroid 返回 errorCode != 0，Toast errorMsg |
| 输入为空 | 前端校验，按钮禁用 |

---

## 文件清单

| 文件路径 | 职责 |
|----------|------|
| `module-user/.../login/LoginActivity.kt` | 承载 Fragment |
| `module-user/.../login/LoginFragment.kt` | 登录表单 UI |
| `module-user/.../login/LoginViewModel.kt` | 登录业务逻辑 |
| `module-user/.../UserRepository.kt` | 网络 + 存储 |
| `module-user/.../api/WanAndroidService.kt` | Retrofit 接口 |
| `module-user/.../store/KVStore.kt` | 存储接口 |
| `module-user/.../store/MMKVStore.kt` | MMKV 实现 |
| `module-user/.../store/SPStore.kt` | SP 降级实现 |
| `module-user/.../store/UserStore.kt` | 单例，管理存储策略 |
| `module-user/.../UserFragment.kt` | 改造：登录态检测 + 用户信息展示 |

---

## 依赖

- `module-user/build.gradle` 新增：`com.tencent:mmkv`
- WanAndroid base URL: `https://www.wanandroid.com/`
