# User Login Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 module-user 模块实现账号密码登录，MMKV 持久化（SP 降级），登录入口在 UserFragment。

**Architecture:** LoginFragment/LoginViewModel/LoginRequest 遵循项目 MVVM 模式（BaseFragment/BaseViewModel/BaseRequest）。KVStore 接口抽象存储层，UserStore 单例在运行时选择 MMKV 或 SP 实现。UserFragment 在 onResume 检测登录态，未登录时通过 DRouter 跳转 LoginActivity。

**Tech Stack:** Kotlin, MVVM, Retrofit + Coroutines, MMKV, DRouter, ViewBinding

---

## File Map

| 操作 | 文件 |
|------|------|
| 修改 | `module-user/build.gradle` |
| 修改 | `module-user/src/main/AndroidManifest.xml` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/store/KVStore.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/store/MMKVStore.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/store/SPStore.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/store/UserStore.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/api/WanAndroidResponse.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/api/UserInfo.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/api/WanAndroidService.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/login/LoginRequest.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/login/LoginViewModel.kt` |
| 新建 | `module-user/src/main/res/layout/activity_login.xml` |
| 新建 | `module-user/src/main/res/layout/fragment_login.xml` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/login/LoginFragment.kt` |
| 新建 | `module-user/src/main/java/com/jonesyong/module_user/login/LoginActivity.kt` |
| 修改 | `module-user/src/main/java/com/jonesyong/module_user/UserFragment.kt` |
| 修改 | `module-user/src/main/java/com/jonesyong/module_user/UserViewModel.kt` |
| 修改 | `module-user/src/main/res/layout/fragment_user.xml` |

---

## Task 1: 添加 MMKV 依赖

**Files:**
- Modify: `module-user/build.gradle`

- [ ] **Step 1: 在 build.gradle 中添加 MMKV 依赖**

```groovy
// module-user/build.gradle
apply from:'../module_config.gradle'

dependencies {
    api project(':library-openeyes')
    api "com.tencent:mmkv:1.3.9"
}
```

- [ ] **Step 2: Sync Gradle**

在 Android Studio 中点击 "Sync Now"，或运行：
```bash
./gradlew :module-user:dependencies
```
Expected: BUILD SUCCESSFUL，无依赖冲突

- [ ] **Step 3: Commit**

```bash
git add module-user/build.gradle
git commit -m "build: add MMKV dependency to module-user"
```

---

## Task 2: KVStore 存储抽象层

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/store/KVStore.kt`
- Create: `module-user/src/main/java/com/jonesyong/module_user/store/MMKVStore.kt`
- Create: `module-user/src/main/java/com/jonesyong/module_user/store/SPStore.kt`

- [ ] **Step 1: 创建 KVStore 接口**

```kotlin
// store/KVStore.kt
package com.jonesyong.module_user.store

interface KVStore {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun remove(key: String)
}
```

- [ ] **Step 2: 创建 MMKVStore 实现**

```kotlin
// store/MMKVStore.kt
package com.jonesyong.module_user.store

import com.tencent.mmkv.MMKV

class MMKVStore : KVStore {
    private val mmkv = MMKV.defaultMMKV()

    override fun putString(key: String, value: String) {
        mmkv.encode(key, value)
    }

    override fun getString(key: String): String? = mmkv.decodeString(key)

    override fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }
}
```

- [ ] **Step 3: 创建 SPStore 降级实现**

```kotlin
// store/SPStore.kt
package com.jonesyong.module_user.store

import android.content.Context

class SPStore(context: Context) : KVStore {
    private val sp = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun putString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? = sp.getString(key, null)

    override fun remove(key: String) {
        sp.edit().remove(key).apply()
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/store/
git commit -m "feat: add KVStore abstraction with MMKV and SP implementations"
```

---

## Task 3: UserStore 单例

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/store/UserStore.kt`

背景：`ApplicationProvider.getContext()` 由 `library-core` 的 ContentProvider 在 App 启动时自动初始化，无需手动调用。

- [ ] **Step 1: 创建 UserStore**

```kotlin
// store/UserStore.kt
package com.jonesyong.module_user.store

import com.jonesyong.library_base.application.ApplicationProvider
import com.tencent.mmkv.MMKV

object UserStore {

    private const val KEY_USERNAME = "username"

    private val store: KVStore by lazy {
        val context = ApplicationProvider.getContext()
        try {
            MMKV.initialize(context)
            MMKVStore()
        } catch (e: Exception) {
            SPStore(context)
        }
    }

    fun saveUser(username: String) = store.putString(KEY_USERNAME, username)

    fun getUsername(): String? = store.getString(KEY_USERNAME)

    fun clear() = store.remove(KEY_USERNAME)
}
```

- [ ] **Step 2: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/store/UserStore.kt
git commit -m "feat: add UserStore singleton with MMKV/SP fallback"
```

---

## Task 4: 网络层 — 数据模型与 Retrofit 接口

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/api/UserInfo.kt`
- Create: `module-user/src/main/java/com/jonesyong/module_user/api/WanAndroidResponse.kt`
- Create: `module-user/src/main/java/com/jonesyong/module_user/api/WanAndroidService.kt`

背景：WanAndroid 登录接口返回 `errorCode`/`errorMsg`，与项目 `BaseResponse`（`code`/`message`）字段名不同，需单独定义响应包装类。

- [ ] **Step 1: 创建 UserInfo 数据模型**

```kotlin
// api/UserInfo.kt
package com.jonesyong.module_user.api

data class UserInfo(
    val id: Int,
    val username: String,
    val nickname: String
)
```

- [ ] **Step 2: 创建 WanAndroidResponse 包装类**

```kotlin
// api/WanAndroidResponse.kt
package com.jonesyong.module_user.api

import com.google.gson.annotations.SerializedName

class WanAndroidResponse<T> {
    @SerializedName("errorCode") val code: Int = -1
    @SerializedName("errorMsg") val message: String = ""
    val data: T? = null
}
```

- [ ] **Step 3: 创建 WanAndroidService 接口**

WanAndroid 登录接口：`POST /user/login`，参数为 Form 表单。

```kotlin
// api/WanAndroidService.kt
package com.jonesyong.module_user.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WanAndroidService {
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): WanAndroidResponse<UserInfo>
}
```

- [ ] **Step 4: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/api/
git commit -m "feat: add WanAndroid login API models and service interface"
```

---

## Task 5: LoginRequest

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/login/LoginRequest.kt`

背景：`BaseRequest.request<T>()` 是 inline reified 函数，通过 `HttpServiceManager.rpcService(url, T::class.java)` 创建 Retrofit 服务实例。参考 `RecommendRequest` 的用法。

- [ ] **Step 1: 创建 LoginRequest**

```kotlin
// login/LoginRequest.kt
package com.jonesyong.module_user.login

import com.jonesyong.library_base.model.BaseRequest
import com.jonesyong.module_user.api.WanAndroidService

class LoginRequest(vm: LoginViewModel) : BaseRequest<LoginViewModel>(vm) {
    override var url = "https://www.wanandroid.com/"

    suspend fun login(username: String, password: String) =
        request<WanAndroidService>().login(username, password)
}
```

- [ ] **Step 2: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/login/LoginRequest.kt
git commit -m "feat: add LoginRequest following BaseRequest pattern"
```

---

## Task 6: LoginViewModel

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/login/LoginViewModel.kt`

背景：
- `BaseViewModel` 有 `abstract fun loadData()`，登录页无需预加载，提供空实现即可。
- `beforeRequest()` 设置 loading=true，`afterRequest(success) { }` 设置 loading=false，失败时 error=true。
- `viewModelScope.launch` + `safeLaunch` 是项目标准协程写法（参考 `RecommendVm`）。
- `errorCode != 0` 表示业务错误（账号密码错误等），需单独 Toast 提示，不走 `vm.error` 流程。

- [ ] **Step 1: 创建 LoginViewModel**

```kotlin
// login/LoginViewModel.kt
package com.jonesyong.module_user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_user.api.UserInfo
import com.jonesyong.module_user.store.UserStore
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val loginSuccess = MutableLiveData<UserInfo>()
    val loginError = MutableLiveData<String>()

    private val request = LoginRequest(this)

    override fun loadData() {}

    fun login(username: String, password: String) {
        viewModelScope.launch {
            beforeRequest()
            val response = safeLaunch { request.login(username, password) }
            afterRequest(response != null) {
                if (response!!.code == 0) {
                    UserStore.saveUser(username)
                    loginSuccess.value = response.data
                } else {
                    loginError.value = response.message
                }
            }
        }
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/login/LoginViewModel.kt
git commit -m "feat: add LoginViewModel with login logic and error handling"
```

---

## Task 7: 登录页面布局

**Files:**
- Create: `module-user/src/main/res/layout/activity_login.xml`
- Create: `module-user/src/main/res/layout/fragment_login.xml`

- [ ] **Step 1: 创建 activity_login.xml（Fragment 容器）**

```xml
<!-- res/layout/activity_login.xml -->
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

- [ ] **Step 2: 创建 fragment_login.xml**

```xml
<!-- res/layout/fragment_login.xml -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="32dp">

    <EditText
        android:id="@+id/et_username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="账号"
        android:inputType="text" />

    <EditText
        android:id="@+id/et_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="密码"
        android:inputType="textPassword" />

    <Button
        android:id="@+id/btn_login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:enabled="false"
        android:text="登录" />

</LinearLayout>
```

- [ ] **Step 3: Commit**

```bash
git add module-user/src/main/res/layout/
git commit -m "feat: add login activity and fragment layouts"
```

---

## Task 8: LoginFragment 与 LoginActivity

**Files:**
- Create: `module-user/src/main/java/com/jonesyong/module_user/login/LoginFragment.kt`
- Create: `module-user/src/main/java/com/jonesyong/module_user/login/LoginActivity.kt`
- Modify: `module-user/src/main/AndroidManifest.xml`

背景：
- `BaseFragment` 要求实现 `getInflateId()`、`initViewModel()`、`initView()`。
- `onSubscribeUi()` 在父类中已处理 `vm.loading` 和 `vm.error`，子类调用 `super.onSubscribeUi(view)` 后追加自己的观察。
- DRouter 路由注解 `@Router(path = "/user/login")` 用于跨模块跳转。

- [ ] **Step 1: 创建 LoginFragment**

```kotlin
// login/LoginFragment.kt
package com.jonesyong.module_user.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_user.R
import com.jonesyong.module_user.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel>() {

    private lateinit var binding: FragmentLoginBinding

    override fun getInflateId() = R.layout.fragment_login

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(LoginViewModel::class.java)
    }

    override fun initView(root: View) {
        binding = FragmentLoginBinding.bind(root)

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnLogin.isEnabled =
                    binding.etUsername.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etUsername.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)

        binding.btnLogin.setOnClickListener {
            vm.login(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.loginSuccess.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }
        vm.loginError.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
```

- [ ] **Step 2: 创建 LoginActivity**

```kotlin
// login/LoginActivity.kt
package com.jonesyong.module_user.login

import com.didi.drouter.annotation.Router
import com.jonesyong.library_base.BaseActivity
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_user.R

@Router(path = "/user/login")
class LoginActivity : BaseActivity<BaseViewModel>() {

    override fun getInflateId() = R.layout.activity_login

    override fun initViewModel() {
        // LoginActivity 自身不需要 ViewModel，vm 由 Fragment 持有
        // BaseActivity 要求 vm 初始化，使用匿名实现
        vm = object : BaseViewModel() {
            override fun loadData() {}
        }
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commit()
    }
}
```

- [ ] **Step 3: 在 AndroidManifest.xml 中注册 LoginActivity**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.jonesyong.module_user">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.OpenEyes">

        <activity android:name=".login.LoginActivity" />

    </application>

</manifest>
```

- [ ] **Step 4: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/login/
git add module-user/src/main/AndroidManifest.xml
git commit -m "feat: add LoginFragment and LoginActivity with DRouter annotation"
```

---

## Task 9: 改造 UserFragment 和 UserViewModel

**Files:**
- Modify: `module-user/src/main/java/com/jonesyong/module_user/UserViewModel.kt`
- Modify: `module-user/src/main/java/com/jonesyong/module_user/UserFragment.kt`
- Modify: `module-user/src/main/res/layout/fragment_user.xml`

背景：
- `UserFragment` 目前未继承 `BaseFragment`，需要改造。
- `onResume` 是检测登录态的正确时机：从 LoginActivity 返回后会触发。
- DRouter 跳转：`DRouter.build("/user/login").start(context)`。

- [ ] **Step 1: 改造 UserViewModel 继承 BaseViewModel**

```kotlin
// UserViewModel.kt
package com.jonesyong.module_user

import androidx.lifecycle.MutableLiveData
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_user.store.UserStore

class UserViewModel : BaseViewModel() {
    val username = MutableLiveData<String?>()

    override fun loadData() {
        username.value = UserStore.getUsername()
    }
}
```

- [ ] **Step 2: 更新 fragment_user.xml 展示用户名**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/tv_username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAlignment="center"
        android:textSize="20sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- [ ] **Step 3: 改造 UserFragment 继承 BaseFragment**

```kotlin
// UserFragment.kt
package com.jonesyong.module_user

import android.view.View
import com.didi.drouter.api.DRouter
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_user.databinding.FragmentUserBinding
import com.jonesyong.module_user.store.UserStore

class UserFragment : BaseFragment<UserViewModel>() {

    private lateinit var binding: FragmentUserBinding

    override fun getInflateId() = R.layout.fragment_user

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(UserViewModel::class.java)
    }

    override fun initView(root: View) {
        binding = FragmentUserBinding.bind(root)
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.username.observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username ?: ""
        }
    }

    override fun onResume() {
        super.onResume()
        val username = UserStore.getUsername()
        if (username == null) {
            DRouter.build("/user/login").start(requireContext())
        } else {
            vm.loadData()
        }
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add module-user/src/main/java/com/jonesyong/module_user/UserFragment.kt
git add module-user/src/main/java/com/jonesyong/module_user/UserViewModel.kt
git add module-user/src/main/res/layout/fragment_user.xml
git commit -m "feat: refactor UserFragment to check login state and show username"
```

---

## Task 10: 编译验证

- [ ] **Step 1: 编译整个项目**

```bash
./gradlew assembleDebug
```
Expected: BUILD SUCCESSFUL，无编译错误

- [ ] **Step 2: 如有编译错误，根据错误信息修复后重新编译**

常见问题：
- DRouter 注解处理器未配置 → 检查 `module_config.gradle` 中是否有 `kapt "io.github.didi:drouter-compiler:x.x.x"`
- ViewBinding 生成类找不到 → 确认 `buildFeatures { viewBinding true }` 已在 `module_config.gradle` 中配置（已有）

- [ ] **Step 3: 安装到设备/模拟器，手动验证登录流程**

验证步骤：
1. 启动 App，点击底部「我的」Tab
2. 应跳转到登录页
3. 输入账号密码，点击登录
4. 登录成功后返回「我的」Tab，显示用户名
5. 重启 App，再次点击「我的」Tab，应直接显示用户名（持久化验证）

- [ ] **Step 4: 最终 Commit**

```bash
git add .
git commit -m "feat: complete user login feature"
```
