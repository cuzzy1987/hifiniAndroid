

##### 登录流程（hifini.com） 

1. 请求时在header添加cookie值(例bbs_sid=l7skv915ouqg55a0fa421hbm49) 需重写UA(例PostmanRuntime/7.26.8)

2. 登录成功后返回bbs_token

3. 携带bbs_token请求(根据返回值判断失效时间 失效时重新登录)