package com.example.administrator.zhihu.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class StoryBean {
    /**
     * date : 20170110
     * stories : [{"images":["http://pic4.zhimg.com/a466316169692418db704d463dd99a43.jpg"],"type":0,"id":9136123,"ga_prefix":"011011","title":"为什么我们需要仪式感？"},{"images":["http://pic1.zhimg.com/e8ab305ecd01d2a140eb0f6e564dcb0c.jpg"],"type":0,"id":9136094,"ga_prefix":"011010","title":"一组 ↓ ↑ A 摁出去，游戏里的动作的怎么连起来的？"},{"images":["http://pic2.zhimg.com/ede267d45cf177c5fe8d345060c6d6d5.jpg"],"type":0,"id":9134168,"ga_prefix":"011009","title":"「为增重给生猪灌食泥浆」？那不是泥浆，但猪也挺惨的"},{"images":["http://pic1.zhimg.com/3f7f378c03475d718bb389cd9a54d6d4.jpg"],"type":0,"id":9063433,"ga_prefix":"011008","title":"在市区开车，每个细节都来自多年的经验"},{"images":["http://pic4.zhimg.com/d03196268c40bc59f617ae3749418ee7.jpg"],"type":0,"id":9136968,"ga_prefix":"011007","title":"别盯着代码有多少行，这才是考核程序员绩效的方式"},{"images":["http://pic4.zhimg.com/9c04b35430005068a55c84085d847893.jpg"],"type":0,"id":9136189,"ga_prefix":"011007","title":"看了一圈「兵马俑」，出来的那一刻，我意识到被骗了"},{"images":["http://pic4.zhimg.com/818019602b5c69f211a0b2898dc9e0e3.jpg"],"type":0,"id":9136657,"ga_prefix":"011007","title":"微信小程序正式上线，体验如何？"},{"images":["http://pic4.zhimg.com/3e3559cb2760bb528c9a6bf36564bdab.jpg"],"type":0,"id":9136212,"ga_prefix":"011006","title":"瞎扯 · 如何正确地吐槽"},{"images":["http://pic1.zhimg.com/5fcb3e059bf59b43e99635373a9797a8.jpg"],"type":0,"id":9135740,"ga_prefix":"011006","title":"这里是广告 · 「望、闻、尝、切」\u2014\u2014全方位感知雾霾的新方法"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/21cec5faafa9ad9119dde5a24eb633ff.jpg","type":0,"id":9134168,"ga_prefix":"011009","title":"「为增重给生猪灌食泥浆」？那不是泥浆，但猪也挺惨的"},{"image":"http://pic2.zhimg.com/6bca5bab92833e122ebe26f5a4eac9bd.jpg","type":0,"id":9136189,"ga_prefix":"011007","title":"看了一圈「兵马俑」，出来的那一刻，我意识到被骗了"},{"image":"http://pic4.zhimg.com/f280154a7f2a1409ed9eb391f915aceb.jpg","type":0,"id":9136657,"ga_prefix":"011007","title":"微信小程序正式上线，体验如何？"},{"image":"http://pic2.zhimg.com/da210e4120f7aa236562f87c80a60985.jpg","type":0,"id":9135856,"ga_prefix":"010917","title":"知乎好问题 · 国外的幼儿教育有哪些优秀之处？"},{"image":"http://pic1.zhimg.com/70e583d43f456726143bde0df75d9908.jpg","type":0,"id":9135662,"ga_prefix":"010918","title":"诺基亚发布新手机，真正值得注意的是背后的富士康"}]
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

    public static class StoriesBean {
        /**
         * images : ["http://pic4.zhimg.com/a466316169692418db704d463dd99a43.jpg"]
         * type : 0
         * id : 9136123
         * ga_prefix : 011011
         * title : 为什么我们需要仪式感？
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

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

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : http://pic4.zhimg.com/21cec5faafa9ad9119dde5a24eb633ff.jpg
         * type : 0
         * id : 9134168
         * ga_prefix : 011009
         * title : 「为增重给生猪灌食泥浆」？那不是泥浆，但猪也挺惨的
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
