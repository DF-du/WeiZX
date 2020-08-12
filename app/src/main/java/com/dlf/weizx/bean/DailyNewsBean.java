package com.dlf.weizx.bean;

import java.io.Serializable;
import java.util.List;

public class DailyNewsBean implements Serializable {

    /**
     * date : 20200302
     * stories : [{"image_hue":"0xad9573","title":"你是否支持立法禁吃野生动物？","url":"https://daily.zhihu.com/story/9721039","hint":"小钱 · 6 分钟阅读","ga_prefix":"030209","images":["https://pic2.zhimg.com/v2-9efb291633b6aac85a32e0b620ec6cc9.jpg"],"type":0,"id":9721039},{"image_hue":"0xb3817d","title":"人类语言各不相通，历史上第一个翻译是如何做到的？","url":"https://daily.zhihu.com/story/9721029","hint":"zeno · 6 分钟阅读","ga_prefix":"030207","images":["https://pic4.zhimg.com/v2-1d68ee1efb3d25abb0656f4b3554f4cf.jpg"],"type":0,"id":9721029},{"image_hue":"0x7d85b3","title":"瞎扯 · 如何正确地吐槽","url":"https://daily.zhihu.com/story/9721067","hint":"VOL.2342","ga_prefix":"030206","images":["https://pic1.zhimg.com/v2-976db631376764d86ac673dc81f2eb50.jpg"],"type":0,"id":9721067}]
     * top_stories : [{"image_hue":"0xb3947d","hint":"作者 / 我是一只小萌刀","url":"https://daily.zhihu.com/story/9720746","image":"https://pic3.zhimg.com/v2-a2e68715865ee4717110c684ff34072e.jpg","title":"历史上有哪些英雄人物在晚年对自己一生的评价？","ga_prefix":"022411","type":0,"id":9720746},{"image_hue":"0x051e27","hint":"作者 / 羽则","url":"https://daily.zhihu.com/story/9720668","image":"https://pic2.zhimg.com/v2-162fbbfbf55aba80dc8a50c7f989d67d.jpg","title":"你见过最野的黑客什么样？","ga_prefix":"022209","type":0,"id":9720668},{"image_hue":"0x736650","hint":"作者 / Miss liz","url":"https://daily.zhihu.com/story/9720198","image":"https://pic1.zhimg.com/v2-f6a1c61c36fdf2968d3342b985bf1ac0.jpg","title":"小事 · 串好的糖葫芦，堆在垃圾箱","ga_prefix":"020922","type":0,"id":9720198},{"image_hue":"0x7da2b3","hint":"作者 / ChemX","url":"https://daily.zhihu.com/story/9720115","image":"https://pic4.zhimg.com/v2-37175280c534cb0a43ab235b9f8fa6fb.jpg","title":"你在生活中用过最高端的化学知识是什么？","ga_prefix":"020707","type":0,"id":9720115},{"image_hue":"0xb3947d","hint":"作者 / 张艾菲","url":"https://daily.zhihu.com/story/9719989","image":"https://pic3.zhimg.com/v2-ff2dfa8f090102b31543cab51adf344a.jpg","title":"日本八十年代有哪些好动画？","ga_prefix":"020511","type":0,"id":9719989}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean implements Serializable {
        /**
         * image_hue : 0xad9573
         * title : 你是否支持立法禁吃野生动物？
         * url : https://daily.zhihu.com/story/9721039
         * hint : 小钱 · 6 分钟阅读
         * ga_prefix : 030209
         * images : ["https://pic2.zhimg.com/v2-9efb291633b6aac85a32e0b620ec6cc9.jpg"]
         * type : 0
         * id : 9721039
         */

        private String image_hue;
        private String title;
        private String url;
        private String hint;
        private String ga_prefix;
        private int type;
        private int id;
        private List<String> images;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean implements Serializable {
        /**
         * image_hue : 0xb3947d
         * hint : 作者 / 我是一只小萌刀
         * url : https://daily.zhihu.com/story/9720746
         * image : https://pic3.zhimg.com/v2-a2e68715865ee4717110c684ff34072e.jpg
         * title : 历史上有哪些英雄人物在晚年对自己一生的评价？
         * ga_prefix : 022411
         * type : 0
         * id : 9720746
         */

        private String image_hue;
        private String hint;
        private String url;
        private String image;
        private String title;
        private String ga_prefix;
        private int type;
        private int id;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
