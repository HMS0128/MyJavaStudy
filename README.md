## 杂项

### 切换工作目录

- 切换到盘符根目录
```
cd /
```
												 
- 显示当前目录
```
cd
```
　　　										 
- 进入上级目录
```
cd ..
```
　　　　　　　　									 
- 进入E盘根目录　
```
E:
```
　　　　 　								
- 进入E盘temp目录下的node文件夹
```
cd /d E:\temp\node
```
　									
- 进入当前目录下的Python文件夹
```
cd Python
cd .\Python
```

### 服务操作

- 启动服务
```
net start [服务名称]
```										 

- 停止服务
```
net stop [服务名称]										 
```

- 卸载服务
```
sc delete [服务名称]										 
```

### 未分类

- 修改用户名密码
```
net user [用户名] [密码]									 
```

- 关闭指定的应用程序
```
taskkill /f /t /im [应用程序名]								 
```

- 启动文件资源管理器
```
start %windir%\explorer.exe							 
```



　　　 									
## 文件相关	

### 查看文件

- 查看所有文件和文件夹
```
tree /F
```												

- 只查看文件夹, 忽略文件	
```
tree /A
```														

### 创建文件

- 创建文件夹
```
md [文件夹]
```													

- 创建 1.txt 空文件
```
type nul>1.txt
```											 

- 创建 1.txt 写入内容 "1" 
```
echo 1 > 1.txt
```											 

### 删除文件
- 强行删除文件夹，包括其内嵌套文件夹
```
rd /s/q [文件夹]` `rmdir /s [文件夹]
```			

- 强行删除指定文件
```
del /F [文件名]
```											 

- 删除嵌套几千层的文件夹
```
Robocopy /MIR [新建的文件夹] [嵌套几千层的文件夹]
```

### 强行删除DLL

#### 方案一：找到调用DLL的程序并关闭程序
- 列出了目前运行的各个程序正在调用的dll文件，并保存到文件 listdll.txt
```
tasklist /m>F:\HMS\Desktop\listdll.txt
```				 
- 什么程序调用 XXX.dll
```
tasklist /m XXX.dll	
```									

#### 方案二：自动清除内存中没用的dll文件

1. 打开注册表： win+R 运行 regedit

2. 打开路径：计算机\HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer

3. 新建 项 AlwaysUnloadDLL ，修改右侧默认 值设为1

4. 重启电脑，再去删除dll




## 网络相关

- 刷新DNS缓存
```
ipconfig /flushdns
```	

- 查看本机公网IP（无公网IP返回：NAT转换的IP）
``` 
curl cip.cc									              
```

- 请求指定网站，返回响应数据
```
curl https://www.baidu.com								 
```

## 端口

### 查看端口信息
- 显示出电脑中所有被打开的端口列表
```
netstat -an　　											 
```

- 显示出所有占用端口的列表
```
netstat -ano　　										 
```

- 显示出80端口占用的详细情况
```
netstat -aon | findstr :80　　							 
```

- 查询端口具体哪个应用占用（参数为PID）
```
tasklist | findstr "6168"　　　　						 
```

- 查看本机所有应用的网络连接端口
```
netstat -nb												 
```

- 查看tcp动态端口的范围
```
netsh int ipv4 show dynamicport tcp						 
```

- 看当前所有已被征用的端口
```
netsh int ipv4 show excludedportrange protocol=tcp		 
```

### 设置动态端口
- ipv4设置动态端口范围：19152-65535
```
netsh int ipv4 set dynamic tcp start=19152 num=46384	 
```

- ipv6设置动态端口范围：19152-65535
```
netsh int ipv6 set dynamic tcp start=19152 num=46384	 
```
