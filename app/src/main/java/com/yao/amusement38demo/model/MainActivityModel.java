package com.yao.amusement38demo.model;

import android.util.Log;

import com.show.api.ShowApiRequest;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.content.Constant;
import com.yao.amusement38demo.inter.ILoadModel;
import com.yao.amusement38demo.inter.ILoadPageModel;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by YaoFengjuan on 2016/9/22.
 */
public class MainActivityModel implements ILoadPageModel<SisterContentList> {

    /**
     * 查询的类型，默认全部返回。
     * type=10 图片
     * type=29 段子
     * type=31 声音
     * type=41 视频
     */
    private String type;//
    /**
     * 文本中包括的内容，模糊查询
     */
    private String title;
    /**
     * 第几页。每页最多返回20条记录
     */
    private int page;

    @Override
    public SisterContentList loadInfo(int page) {
        Log.e(this.getClass().getName(), "load info");
        try {
            String result = new ShowApiRequest("http://route.showapi.com/255-1", Constant.appid, Constant.secret)
                    .addTextPara("type", type)
                    .addTextPara("title", title)
                    .addTextPara("page", String.valueOf(page)).post();
            Log.d(this.getClass().getName(), "result:->->->->->" + result);
            SisterContentList data = SisterContentList.objectFromData(result);
            if (data.showapi_res_code < 0) {
                return null;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SisterContentList loadInfo(String type, String title, int page) {
        this.type = type;
        this.title = title;
        this.page = page;
        return loadInfo(page);
    }

}
