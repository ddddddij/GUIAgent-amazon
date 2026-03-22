# amazon_sim

Jetpack Compose 实现的 Amazon 本地静态演示项目，当前已完成底部导航、HomeTab、ProfileTab、CartTab、MenuTab 页面以及商品详情页面。

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
- ProductDetailActivity（商品详情页）：
  - 从 HomeTab 点击商品卡片进入，通过 Intent 传入 productId
  - 顶部返回箭头 + 搜索栏（橙黄色背景）
  - 品牌信息行（品牌 Logo 占位 + 品牌名 + Visit Store + 评分星星 + 评价数）
  - 商品名称、销量标签（"500+ bought in past month"）
  - 主图轮播区（HorizontalPager + 指示点 + 收藏/分享图标）
  - 功能按钮行（Hear the highlights + VIEW IN 3D，保留接口）
  - 规格选择区采用通用 SpecGroup/SpecOption 架构，支持任意维度组合（颜色变体带图片卡片、存储容量纯文字格子等）
  - 价格区由当前选中规格组合动态计算（折扣百分比 + 当前价格含上标 $ + 划线原价 + 分期付款标签）
  - 配送信息区（免费配送日期 + 最快配送 + 倒计时 + 配送地址）
  - 库存状态 + 数量下拉选择（1~10）
  - Add to Cart 按钮（黄色）+ Buy Now 按钮（橙色）
  - Customer reviews 区（总评分 + 评价摘要 + AI 生成提示 + 两列评价标签）
  - Customer photos and videos 横向滚动占位图
  - 首个完整实现商品：Marshall Acton III（Black/Cream/Midnight Blue 三色变体）
  - 通用模板设计，后续新增商品只需在 Repository 中添加数据
- SearchActivity（搜索推荐页）：
  - 点击任意页面的搜索栏进入
  - 顶部搜索栏（返回箭头 + 搜索输入框自动聚焦 + 相机/语音图标）
  - 搜索推荐列表：未输入时显示全部16个商品名，输入后实时模糊过滤
  - 匹配逻辑：商品名、品牌名、商品类型三字段模糊匹配（不区分大小写）
  - 点击推荐项或键盘确认键跳转搜索结果页
- SearchResultActivity（搜索结果页）：
  - 结果数量提示（"X results for \"keyword\""）
  - 商品卡片列表：商品图片、Best Seller 角标、商品名、规格标签、评分、销量、价格（上标$）、划线原价、配送日期、颜色信息及色块
  - 点击商品卡片或 "See options" 跳转商品详情页
  - 空状态展示（无匹配结果时）
  - 4个主页面的搜索栏均已连接搜索功能

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
- `app/src/main/java/com/example/amazon_sim/ui/screen/productdetail`：商品详情页面、组件与 ViewModel
- `app/src/main/java/com/example/amazon_sim/ui/screen/search`：搜索推荐页、搜索结果页、组件与 ViewModel
- `app/src/main/assets/data`：预置数据
- `docs`：开发要求与架构文档
