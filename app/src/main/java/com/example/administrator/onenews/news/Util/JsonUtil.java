package com.example.administrator.onenews.news.Util;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.onenews.news.Bean.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/21.
 */
public class JsonUtil {
    //存储解析出来的数据
    private List<Data>[] msg_list = null;
    private CallBackListener listener;

    public JsonUtil() {

    }

    public JsonUtil(CallBackListener listener) {
        this.listener = listener;
    }

    /**
     * [[{},{}],[{},{}],[]...]
     * 通过OkHttp获取网络数据，get请求获取返回数据，没有封装的回调，
     * 添加okHttp依赖
     */
    public void getResult() {
        //因为是从网络上获取数据，因此要开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 1.创建OKHttpClient对象
                 */

                OkHttpClient okHttpClient = new OkHttpClient();
                /**
                 * 2.创建静态的Request请求
                 */
                Request request = new Request.Builder().url("http://news.ifeng.com/").build();
                /**
                 * 3.返回一个Call对象
                 */
                Call call = okHttpClient.newCall(request);
                /**
                 * 加入调度，执行回调处理
                 */
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //请求成功则加载回所有的网页信息（head+body）
                       if(response.isSuccessful()) {
                           String str = response.body().string();
                           Message message = new Message();
                           message.obj = str;
                           message.arg1 = 0x11;
                           handler.sendMessage(message);//发送携带0x11的消息
                       }
                    }
                });
            }
        }).start();
    }

    /**
     * 创建Handler对象，在里面处理返回的数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0x11) {
                //处理返回的数据
                getJson(msg.obj.toString());
            }
        }

        /**
         * 截取返回数据中json数据
         */
        public void getJson(String msg) {
            String json = null;
            if (msg != null) {
                //如果json数据不为空，则解析json数据，否则加载数据失败
                //indexOf只包含前面的数据，所以截取后面的字符要+3.
                json = msg.substring(msg.indexOf("[[{"), msg.indexOf("}]]") + 3);

            }
            initMessageList(json);
        }
    };

    //解析json数据
    public void initMessageList(String json) {
        try {
            JSONArray array = new JSONArray(json);
            msg_list = new ArrayList[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONArray arr = array.getJSONArray(i);
                msg_list[i] = new ArrayList<>();
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj =arr.getJSONObject(j);
                    //设置数据
                    Data data = new Data();
                    data.setThumbnail(obj.getString("thumbnail"));
                    data.setTitle(obj.getString("title"));
                    data.setUrl(obj.getString("url"));
                    msg_list[i].add(data);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * 通知注册
         */
        listener.upDate(msg_list);
    }

    //定义关注着加载数据类的接口
    public interface CallBackListener {
        void upDate(List<Data>[] msg_list);
    }
}
