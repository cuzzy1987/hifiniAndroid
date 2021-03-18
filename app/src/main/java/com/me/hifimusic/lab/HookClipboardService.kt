package com.me.hifimusic.lab

//import android.content.ClipData
//import android.content.ClipboardManager
//import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
//import android.text.ClipboardManager
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class HookClipboardService{

//    声明构造修饰符 constructor 可指定(private||public 默认public)
//    若主构造函数没有任何参数 可以省略构造修饰符

    val TAG: String = HookClipboardService::class.java.simpleName

    companion object obj {

        fun isOverP(){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
                return
            }
            try {
                var forName = Class::class.java.getDeclaredMethod("forName",String::class.java)
                var declaredMethod = Class::class.java.getDeclaredMethod("getDeclaredMethod",String::class.java,
                    arrayOf(Class::class.java)::class.java)
                var vmRuntimeClass = forName.invoke(null,"dalvik.system.VMRuntime")
                var getRuntime = declaredMethod.invoke(vmRuntimeClass,"getRuntime", null) as Method
                var setHiddenApi = declaredMethod.invoke(vmRuntimeClass,"setHiddenApiExemptions", arrayOf(String::class.java)) as Method

                var sVmRuntime = getRuntime.invoke(null)
//                new Object[]{new String[]{"L"}}
                setHiddenApi.invoke(sVmRuntime, arrayOf(arrayOf({"L"})))
            }catch (e: Throwable){
                Log.e("has error on reflect=> ",e.message.toString())
            }
        }

        fun hookClipboardService(context: Context) {

//            isOverP()
            try {


                var clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var mServiceField =
                    ClipboardManager::class.java.getDeclaredField("mService")

                Log.d("Hook Field", "mServiceField=> $mServiceField")

                mServiceField.isAccessible = true

                // 获取系统mService ||mService存在是空的可能
                val mService: Any? = mServiceField.get(clipboardManager)
                // 初始化动态代理对象
                var clasz = Class.forName("android.content.IClipboard")
                var proxyInstance =
                    Proxy.newProxyInstance(context.javaClass.classLoader, arrayOf(clasz),
                        InvocationHandler { proxy, method, args ->
                            Log.d(HookClipboardService::class.java.simpleName, "method=>${method}")
                            var methodName = method.name
                            if (args != null && args.isNotEmpty()) {
                                for (item in args) {
                                    Log.d("item => ", item.toString())
                                }
                            }

                            if ("setPrimaryClip" == methodName) {
                                var arg = args[0]
                                if (arg is ClipData) {
                                    var clipData = arg as ClipData
                                    var count = clipData.itemCount
                                    loop@ for (i in 0 until count) {
                                        var item = clipData.getItemAt(i)
                                        Log.d("loop=> ", "invoke item => $item")
                                    }
                                }
                                Toast.makeText(context, "检测到设置剪切板内容", Toast.LENGTH_LONG).show()
                            } else if ("getPrimaryClip" == methodName) {
                                Toast.makeText(context, "检测到获取剪切板内容", Toast.LENGTH_LONG).show()
                            }

                            method.invoke(mService, args)
                        }

                    )

                // 替换系统mService
                val serviceField: Field = ClipboardManager::class.java.getDeclaredField("mService")
                serviceField.isAccessible = true
                serviceField.set(clipboardManager, proxyInstance)
            }catch (e: Exception){
                Log.e("has error hook ",e.toString())
            }

        }
    }


    /*

    10.0 以上系统提示: Accessing hidden method********* 诸如此类的问题主要是Google 限制了隐藏方法通过反射调用,使用以下代码在,
业务代码调用前先执行可以解决以上问题。




if (SDK_INT < Build.VERSION_CODES.P) {
    return;
}
try {
    Method forName = Class.class.getDeclaredMethod("forName", String.class);
    Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
    Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
    Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
    Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
    Object sVmRuntime = getRuntime.invoke(null);
    setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
} catch (Throwable e) {
    Log.e("[error]", "reflect bootstrap failed:", e);
}
    * */

}