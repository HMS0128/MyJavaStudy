# MyJavaStudy
## 禁用键盘

``` cmd title="Windows 批处理脚本"
sc config i8042prt start=disabled
```

## 启用键盘
``` cmd title="Windows 批处理脚本"
sc config i8042prt start=auto
```
