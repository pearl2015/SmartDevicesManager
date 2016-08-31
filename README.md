本项目开发一个用来管理基于低耗蓝牙(BLE)开发的智能设备的云管家。以下是每个模块的开发过程
# 智能设备(低耗蓝牙BLE)的云管家的实现过程
## 登录与注册
### 登录 
<img src="https://github.com/pearl2015/SmartDevicesManager/blob/master/pic/login.png?raw=true" width="40%" height="50%">
 
1. 采用Material Design风格，在http://sourcey.com/beautiful-android-login-and-signup-screens-with-material-design/中的例子里面，加入了两个checkbox，用来保存用户和自动登录；
2. 使用Bmob云服务为服务器，用户等信息存储在Bmob数据库中；
3. 使用ButterKnife view注入框架；

### 注册
<img src="https://github.com/pearl2015/SmartDevicesManager/blob/master/pic/signup.png?raw=ture" width="40%" height="50%">

1. 注册也是使用Bmob云服务提供的接口；
2. 邮箱认证：Bmob提供的接口。

## 主界面
<img src="https://github.com/pearl2015/SmartDevicesManager/blob/master/pic/main2.png?raw=ture" width="40%" height="50%">

1. 通过intent将用户信息从登录界面传递到主界面中；
2. 使用 DrawerLayout 实现 Material Design风格的侧滑



