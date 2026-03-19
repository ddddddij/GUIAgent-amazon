# amazon_sim

Jetpack Compose 实现的 Amazon 本地静态演示项目，当前已完成底部导航、HomeTab、ProfileTab、CartTab 和 MenuTab 页面。

## 当前功能
- 底部导航：Home / Profile / Cart / Menu
- HomeTab：
  - 顶部搜索栏
  - 地址与快捷标签横向列表
  - Banner 横向轮播
  - Recommended deals 横向商品卡片
  - Save on Amazon Devices 两列商品区
  - Trending near you 两列分类区
  - Keep shopping 横向商品区
  - Fresh finds just landed 横向分类区
- ProfileTab：
  - 固定顶部搜索栏
  - 用户信息栏（头像占位、用户名、设置/通知/语言标识）
  - 快捷功能标签（Orders / Interests / Account / Lists）
  - Your Orders 区块与横向促销 Banner
  - Buy Again 区块与单操作按钮
  - Your Interests 区块与单操作按钮
  - Keep shopping for 横向商品卡片区
  - Lists and Registries 区块与单操作按钮
  - Your Amazon highlights 横向卡片区
  - Your Account 横向功能入口卡片区
  - Gift Card Balance 双按钮区
  - Need help? Customer Service 入口
- CartTab：
  - 顶部搜索栏（与 HomeTab 一致）
  - 小计金额栏（动态显示 "No items selected" 或 "Subtotal $XXX.XX"）
  - 黄色结算按钮（"Proceed to checkout (N items)"）
  - "Select all items" 全选/取消全选
  - 商品卡片列表（复选框、商品图占位、名称、价格、库存状态）
  - 数量调节栏（🗑️ | 数量 | +，黄色圆角边框）
  - 操作按钮（Delete / Save for later / Compare / Share / View in your room）
  - "Saved for Later" 蓝色提示文字
  - 所有交互由 CartViewModel 通过 StateFlow 管理
- MenuTab：
  - 顶部搜索栏（橙黄色背景，含搜索/相机/语音图标）
  - "Your shortcuts" 快捷入口横向按钮（Lists / Orders / Buy Again / Account）
  - "Shop by category" 分类区，含可展开/折叠的 "Shop by Department" 分组
  - 23 个分类条目（纯文字列表，AnimatedVisibility 动画）
  - 底部功能入口卡片（Switch Accounts / Sign Out / Customer Service，各自独立圆角边框）
  - 底部提示文字 "Looking for app settings? They've moved to 👤"
  - 所有交互由 MenuViewModel 通过 StateFlow 管理

## 当前说明
- 当前 HomeTab 与 ProfileTab 均为本地静态演示，不接入登录、联网或真实跳转
- 页面内所有点击入口均已预留回调接口，便于后续接入空页面或真实页面
- ProfileTab 通过现有 `profile` 路由进入，未改动底部导航入口结构

## 修复记录
- 修正 HomeTab 相关文件中错误的 Compose 导入（`weight`、`item` 不可作为顶层导入，需由作用域解析）
- 补齐 `material-icons-extended` 依赖，解决 `CameraAlt`、`Mic` 图标 unresolved reference

## 技术栈
- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- MVVM
- StateFlow

## 关键目录
- `app/src/main/java/com/example/amazon_sim/ui/navigation`：导航
- `app/src/main/java/com/example/amazon_sim/ui/components`：通用组件
- `app/src/main/java/com/example/amazon_sim/ui/screen/home`：HomeTab 页面与 ViewModel
- `app/src/main/java/com/example/amazon_sim/ui/screen/profile`：ProfileTab 页面、组件与 ViewModel
- `app/src/main/java/com/example/amazon_sim/ui/screen/cart`：CartTab 页面、组件与 ViewModel
- `app/src/main/java/com/example/amazon_sim/ui/screen/menu`：MenuTab 页面、组件与 ViewModel
- `app/src/main/assets/data`：预置数据
- `docs`：开发要求与架构文档
