package com.yao.amusement38demo.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YaoFengjuan on 2016/12/2.
 */

public class SisterContentList {


    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"pagebean":{"allNum":3793,"allPages":190,"contentlist":[{"create_time":"2015-07-02 13:00:02","hate":"111","height":"800","id":"14710774","image0":"http://ww2.sinaimg.cn/mw240/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image1":"http://ww2.sinaimg.cn/bmiddle/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image2":"http://ww2.sinaimg.cn/large/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image3":"http://wimg.spriteapp.cn/ugc/2015/07/01/5592ca3b28f6b.jpg","is_gif":"0","love":"303","text":"哎尼玛！抢我五杀","type":"10","videotime":"0","video_uri":"","voicelength":"0","voicetime":"0","voiceuri":"","weixin_url":"http://14710774.f.budejie.com/budejie/land.php?pid=14710774&wx.qq.com&appname=","width":"600"}],"currentPage":1,"maxResult":20},"ret_code":0}
     */

    public int showapi_res_code;
    public String showapi_res_error;
    public ShowapiResBodyEntity showapi_res_body;

    public static SisterContentList objectFromData(String str) {

        return new Gson().fromJson(str, SisterContentList.class);
    }

    public static SisterContentList objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), SisterContentList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SisterContentList> arraySisterItemContentFromData(String str) {

        Type listType = new TypeToken<ArrayList<SisterContentList>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SisterContentList> arraySisterItemContentFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SisterContentList>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public static class ShowapiResBodyEntity {
        /**
         * pagebean : {"allNum":3793,"allPages":190,"contentlist":[{"create_time":"2015-07-02 13:00:02","hate":"111","height":"800","id":"14710774","image0":"http://ww2.sinaimg.cn/mw240/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image1":"http://ww2.sinaimg.cn/bmiddle/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image2":"http://ww2.sinaimg.cn/large/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image3":"http://wimg.spriteapp.cn/ugc/2015/07/01/5592ca3b28f6b.jpg","is_gif":"0","love":"303","text":"哎尼玛！抢我五杀","type":"10","videotime":"0","video_uri":"","voicelength":"0","voicetime":"0","voiceuri":"","weixin_url":"http://14710774.f.budejie.com/budejie/land.php?pid=14710774&wx.qq.com&appname=","width":"600"}],"currentPage":1,"maxResult":20}
         * ret_code : 0
         */

        public PagebeanEntity pagebean;
        public int ret_code;

        public static ShowapiResBodyEntity objectFromData(String str) {

            return new Gson().fromJson(str, ShowapiResBodyEntity.class);
        }

        public static ShowapiResBodyEntity objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ShowapiResBodyEntity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ShowapiResBodyEntity> arrayShowapiResBodyEntityFromData(String str) {

            Type listType = new TypeToken<ArrayList<ShowapiResBodyEntity>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<ShowapiResBodyEntity> arrayShowapiResBodyEntityFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ShowapiResBodyEntity>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public static class PagebeanEntity {
            /**
             * allNum : 3793
             * allPages : 190
             * contentlist : [{"create_time":"2015-07-02 13:00:02","hate":"111","height":"800","id":"14710774","image0":"http://ww2.sinaimg.cn/mw240/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image1":"http://ww2.sinaimg.cn/bmiddle/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image2":"http://ww2.sinaimg.cn/large/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg","image3":"http://wimg.spriteapp.cn/ugc/2015/07/01/5592ca3b28f6b.jpg","is_gif":"0","love":"303","text":"哎尼玛！抢我五杀","type":"10","videotime":"0","video_uri":"","voicelength":"0","voicetime":"0","voiceuri":"","weixin_url":"http://14710774.f.budejie.com/budejie/land.php?pid=14710774&wx.qq.com&appname=","width":"600"}]
             * currentPage : 1
             * maxResult : 20
             */

            public int allNum;
            public int allPages;
            public int currentPage;
            public int maxResult;
            public List<ContentlistEntity> contentlist;

            public static PagebeanEntity objectFromData(String str) {

                return new Gson().fromJson(str, PagebeanEntity.class);
            }

            public static PagebeanEntity objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), PagebeanEntity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<PagebeanEntity> arrayPagebeanEntityFromData(String str) {

                Type listType = new TypeToken<ArrayList<PagebeanEntity>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<PagebeanEntity> arrayPagebeanEntityFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<PagebeanEntity>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public static class ContentlistEntity {
                /**
                 * create_time : 2015-07-02 13:00:02
                 * hate : 111
                 * height : 800
                 * id : 14710774
                 * image0 : http://ww2.sinaimg.cn/mw240/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg
                 * image1 : http://ww2.sinaimg.cn/bmiddle/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg
                 * image2 : http://ww2.sinaimg.cn/large/005OPYkojw1etn14eqlj8j30go0m8ab3.jpg
                 * image3 : http://wimg.spriteapp.cn/ugc/2015/07/01/5592ca3b28f6b.jpg
                 * is_gif : 0
                 * love : 303
                 * text : 哎尼玛！抢我五杀
                 * type : 10
                 * <p>
                 * videotime : 0
                 * video_uri :
                 * voicelength : 0
                 * voicetime : 0
                 * voiceuri :
                 * weixin_url : http://14710774.f.budejie.com/budejie/land.php?pid=14710774&wx.qq.com&appname=
                 * width : 600
                 */

                public String create_time;
                public String hate;
                public String height;
                public String id;
                public String image0;
                public String image1;
                public String image2;
                public String image3;
                public String is_gif;
                public String love;
                public String text;
                /**
                 * * ( 查询的类型，默认全部返回。
                 * type=10 图片
                 * type=29 段子
                 * type=31 声音
                 * type=41 视频)
                 */
                public String type;
                public String videotime;
                public String video_uri;
                public String voicelength;
                public String voicetime;
                public String voiceuri;
                public String voice_uri;
                public String weixin_url;
                public String width;
                public String profile_image;
                public String name;

                public static ContentlistEntity objectFromData(String str) {

                    return new Gson().fromJson(str, ContentlistEntity.class);
                }

                @Override
                public String toString() {
                    return "ContentlistEntity{" +
                            "id:'" + id + '\'' +
                            ", video_uri:'" + video_uri + '\'' +
                            ", name:'" + name + '\'' +
                            ", text:'" + text + '\'' +
                            ", text:'" + text + '\'' +
                            '}';
                }

                public static ContentlistEntity objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), ContentlistEntity.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<ContentlistEntity> arrayContentlistEntityFromData(String str) {

                    Type listType = new TypeToken<ArrayList<ContentlistEntity>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<ContentlistEntity> arrayContentlistEntityFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<ContentlistEntity>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }
            }
        }
    }
}
