package com.milkcu.app.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.String;

import com.milkcu.app.adapter.BasePageAdapter;
import com.milkcu.app.biz.BaseDao;
import com.milkcu.app.biz.BlogsDao;
import com.milkcu.app.biz.NewsDao;
import com.milkcu.app.biz.TopDao;
import com.milkcu.app.biz.WikiDao;
import com.milkcu.app.config.Constants;
import com.milkcu.app.db.DBHelper;
import com.milkcu.app.entity.BlogsResponseEntity;
import com.milkcu.app.entity.CategorysEntity;
import com.milkcu.app.entity.NavigationModel;
import com.milkcu.app.entity.NewsResponseEntity;
import com.milkcu.app.entity.WikiResponseEntity;
import com.milkcu.app.https.NetWorkHelper;
import com.milkcu.app.indicator.PageIndicator;
import com.milkcu.app.slidingmenu.SlidingMenu;
import com.milkcu.app.ui.base.BaseSlidingFragmentActivity;
import com.milkcu.app.utils.IntentUtil;
import com.milkcu.app.utils.PopupWindowUtil;
import com.milkcu.app.widget.CustomButton;
import com.umeng.fb.FeedbackAgent;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.milkcu.app.R;

import android.util.Log;

public class MainActivity extends BaseSlidingFragmentActivity implements
        OnClickListener, AnimationListener {
    private final String LIST_TEXT = "text";
    private final String LIST_IMAGEVIEW = "img";

    // [start]变量
    /**
     * 数字代表列表顺序
     */
    private int mTag = 0;

    private CustomButton cbFeedback;
    private CustomButton cbAbove;
    private View title;
    private LinearLayout mlinear_listview;

    // title标题
    private ImageView imgQuery;
    private ImageView imgMore;
    private ImageView imgLeft;
    private ImageView imgRight;
    private FrameLayout mFrameTv;
    private ImageView mImgTv;

    // views
    private ViewPager mViewPager;
    private BasePageAdapter mBasePageAdapter;
    private PageIndicator mIndicator;
    private LinearLayout loadLayout;
    private LinearLayout loadFaillayout;

    // init daos
    private TopDao topDao;
    private BlogsDao blogsDao;
    private NewsDao newsDao;
    private WikiDao wikiDao;
    
    private int sid0 = 0;
    private int sid1 = 9;
    private int sid2 = 10;
    private int sid3 = 11;
    private int sid4 = 12;
    private int sid5 = 13;
    private int sid6 = 14;
    private int sid7 = 15;
    private int sid8 = 16;
    private int sid9 = 17;
    private int sid10 = 18;
    private int sid11 = 19;
    private int sid12 = 20;
    private int sid13 = 21;
    private int sid14 = 22;
    private int sid15 = 23;
    private int sid16 = 26;
    private int sid17 = 27;
    
    private String sname0 = "智慧校园";
    private String sname1 = "齐鲁师轩";
    private String sname2 = "校长办公室";
    private String sname3 = "学生工作部";
    private String sname4 = "团委";
    private String sname5 = "学生会";
    private String sname6 = "国际交流与合作";
    private String sname7 = "招生办公室";
    private String sname8 = "研究生院";
    private String sname9 = "信息学院";
    private String sname10 = "文学院";
    private String sname11 = "外国语学院";
    private String sname12 = "音乐学院";
    private String sname13 = "美术学院";
    private String sname14 = "物理与电子学院";
    private String sname15 = "管理科学学院";
    private String sname16 = "齐鲁文化中心";
    private String sname17 = "山师附中";
    
    private WikiDao s0Dao;
    private WikiDao s1Dao;
    private WikiDao s2Dao;
    private WikiDao s3Dao;
    private WikiDao s4Dao;
    private WikiDao s5Dao;
    private WikiDao s6Dao;
    private WikiDao s7Dao;
    private WikiDao s8Dao;
    private WikiDao s9Dao;
    private WikiDao s10Dao;
    private WikiDao s11Dao;
    private WikiDao s12Dao;
    private WikiDao s13Dao;
    private WikiDao s14Dao;
    private WikiDao s15Dao;
    private WikiDao s16Dao;
    private WikiDao s17Dao;

    private List<Object> categoryList;

    private List<NavigationModel> navs;

    private ListView lvTitle;
    private SimpleAdapter lvAdapter;
    private LinearLayout llGoHome;
    private ImageButton imgLogin;
    private Button bn_refresh;

    private TextView mAboveTitle;
    private SlidingMenu sm;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;

    // load responseData
    private BlogsResponseEntity responseData;
    private NewsResponseEntity newsResponseData;
    private WikiResponseEntity wikiResponseData;

    private String current_page;

    private InputMethodManager imm;

    private boolean isShowPopupWindows = false;

    // [end]

    // [start]生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        // 一个滑动页面，使用的是Jake Wharton开发的Android-ViewPagerIndicator组件
        setContentView(R.layout.above_slidingmenu);
        initClass();  // 设置布局文件
        initControl();
        // 将页面用到的控件都初始化并赋值，这个方法里面调用的都是findViewById和监听器方法
        initViewPager();
        initListView();
        initgoHome();
        initNav();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            DBHelper db = DBHelper.getInstance(this);
            db.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [end]

    // [start]初始化函数
    private void initSlidingMenu() {
        setBehindContentView(R.layout.behind_slidingmenu);
        // customize the SlidingMenu
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
        //sm.setShadowWidth(20);
        sm.setBehindScrollScale(0);
    }

    private void initControl() {

        imm = (InputMethodManager) getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        loadLayout = (LinearLayout) findViewById(R.id.view_loading);
        loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
        mAboveTitle = (TextView) findViewById(R.id.tv_above_title);
        mAboveTitle.setText("智慧校园");
        imgQuery = (ImageView) findViewById(R.id.imageview_above_query);
        //imgQuery.setOnClickListener(this);
        imgQuery.setVisibility(View.GONE);
        imgMore = (ImageView) findViewById(R.id.imageview_above_more);
        imgMore.setOnClickListener(this);
        imgLeft = (ImageView) findViewById(R.id.imageview_above_left);
        imgRight = (ImageView) findViewById(R.id.imageview_above_right);
        // editSearch.setOnKeyListener(onkey);
        mViewPager = (ViewPager) findViewById(R.id.above_pager);
        mIndicator = (PageIndicator) findViewById(R.id.above_indicator);
        lvTitle = (ListView) findViewById(R.id.behind_list_show);
        llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
        imgLogin = (ImageButton) findViewById(R.id.login_login);

        //imgLogin.setOnClickListener(this);
        cbFeedback = (CustomButton) findViewById(R.id.cbFeedback);
        //cbFeedback.setOnClickListener(this);
        cbAbove = (CustomButton) findViewById(R.id.cbAbove);
        cbAbove.setOnClickListener(this);
        title = findViewById(R.id.main_title);
        mlinear_listview = (LinearLayout) findViewById(R.id.main_linear_listview);
        mFrameTv = (FrameLayout) findViewById(R.id.fl_off);
        mImgTv = (ImageView) findViewById(R.id.iv_off);

        bn_refresh = (Button) findViewById(R.id.bn_refresh);
        bn_refresh.setOnClickListener(this);
    }

    private void initClass() {
        blogsDao = new BlogsDao(this);
        newsDao = new NewsDao(this);
        wikiDao = new WikiDao(this);
        topDao = new TopDao(this);
        
        s0Dao = new WikiDao(this);
        s1Dao = new WikiDao(this);
        s2Dao = new WikiDao(this);
        s3Dao = new WikiDao(this);
        s4Dao = new WikiDao(this);
        s5Dao = new WikiDao(this);
        s6Dao = new WikiDao(this);
        s7Dao = new WikiDao(this);
        s8Dao = new WikiDao(this);
        s9Dao = new WikiDao(this);
        s10Dao = new WikiDao(this);
        s11Dao = new WikiDao(this);
        s12Dao = new WikiDao(this);
        s13Dao = new WikiDao(this);
        s14Dao = new WikiDao(this);
        s15Dao = new WikiDao(this);
        s16Dao = new WikiDao(this);
        s17Dao = new WikiDao(this);
    }

    private void initViewPager() {
        mBasePageAdapter = new BasePageAdapter(MainActivity.this);
        mViewPager.setOffscreenPageLimit(0);
        // 设置mViewPager预先加载页面的数量
        mViewPager.setAdapter(mBasePageAdapter);
        // 将mBasePageAdapter设置为mViewPager适配器的内容
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnPageChangeListener(new MyPageChangeListener());
        new MyTask().execute(s0Dao);
        // 实例化一个匿名对象，并调用异步任务类的execute方法
    }

    private void initListView() {
        lvAdapter = new SimpleAdapter(this, getData(),
                R.layout.behind_list_show, new String[]{LIST_TEXT,
                LIST_IMAGEVIEW},
                new int[]{R.id.textview_behind_title,
                        R.id.imageview_behind_icon}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub.
                View view = super.getView(position, convertView, parent);
                if (position == mTag) {
                    view.setBackgroundResource(R.drawable.back_behind_list);
                    lvTitle.setTag(view);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };
        lvTitle.setAdapter(lvAdapter);
        lvTitle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NavigationModel navModel = navs.get(position);
                mAboveTitle.setText(navModel.getName());
                current_page = navModel.getTags();
                if (lvTitle.getTag() != null) {
                    if (lvTitle.getTag() == view) {
                        MainActivity.this.showContent();
                        return;
                    }
                    ((View) lvTitle.getTag())
                            .setBackgroundColor(Color.TRANSPARENT);
                }
                lvTitle.setTag(view);
                view.setBackgroundResource(R.drawable.back_behind_list);
                imgQuery.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        imgQuery.setVisibility(View.GONE);
                        new MyTask().execute(s0Dao);
                        break;
                    case 1:
                    	new MyTask(sid1, 1).execute(s1Dao);
                        break;
                    case 2:
                    	new MyTask(sid2, 2).execute(s2Dao);
                        break;
                    case 3:
                    	new MyTask(sid3, 3).execute(s3Dao);
                        break;
                    case 4:
                    	new MyTask(sid4, 4).execute(s4Dao);
                        break;
                    case 5:
                    	new MyTask(sid5, 5).execute(s5Dao);
                        break;
                    case 6:
                    	new MyTask(sid6, 6).execute(s6Dao);
                        break;
                    case 7:
                    	new MyTask(sid7, 7).execute(s7Dao);
                        break;
                    case 8:
                    	new MyTask(sid8, 8).execute(s8Dao);
                        break;
                    case 9:
                    	new MyTask(sid9, 9).execute(s9Dao);
                        break;
                    case 10:
                    	new MyTask(sid10, 10).execute(s10Dao);
                        break;
                    case 11:
                    	new MyTask(sid11, 11).execute(s11Dao);
                        break;
                    case 12:
                    	new MyTask(sid12, 12).execute(s12Dao);
                        break;
                    case 13:
                    	new MyTask(sid13, 13).execute(s13Dao);
                        break;
                    case 14:
                    	new MyTask(sid14, 14).execute(s14Dao);
                        break;
                    case 15:
                    	new MyTask(sid15, 15).execute(s15Dao);
                        break;
                    case 16:
                    	new MyTask(sid16, 16).execute(s16Dao);
                        break;
                    case 17:
                    	new MyTask(sid17, 17).execute(s17Dao);
                        break;
                    default :
                    	break;
                }
            }
        });

    }

    private void initNav() {
        navs = new ArrayList<NavigationModel>();
        NavigationModel nav0 = new NavigationModel(sname0, Constants.TAGS.WIKI_TAG);
        NavigationModel nav1 = new NavigationModel(sname1, Constants.TAGS.WIKI_TAG);
        NavigationModel nav2 = new NavigationModel(sname2, Constants.TAGS.WIKI_TAG);
        NavigationModel nav3 = new NavigationModel(sname3, Constants.TAGS.WIKI_TAG);
        NavigationModel nav4 = new NavigationModel(sname4, Constants.TAGS.WIKI_TAG);
        NavigationModel nav5 = new NavigationModel(sname5, Constants.TAGS.WIKI_TAG);
        NavigationModel nav6 = new NavigationModel(sname6, Constants.TAGS.WIKI_TAG);
        NavigationModel nav7 = new NavigationModel(sname7, Constants.TAGS.WIKI_TAG);
        NavigationModel nav8 = new NavigationModel(sname8, Constants.TAGS.WIKI_TAG);
        NavigationModel nav9 = new NavigationModel(sname9, Constants.TAGS.WIKI_TAG);
        NavigationModel nav10 = new NavigationModel(sname10, Constants.TAGS.WIKI_TAG);
        NavigationModel nav11 = new NavigationModel(sname11, Constants.TAGS.WIKI_TAG);
        NavigationModel nav12 = new NavigationModel(sname12, Constants.TAGS.WIKI_TAG);
        NavigationModel nav13 = new NavigationModel(sname13, Constants.TAGS.WIKI_TAG);
        NavigationModel nav14 = new NavigationModel(sname14, Constants.TAGS.WIKI_TAG);
        NavigationModel nav15 = new NavigationModel(sname15, Constants.TAGS.WIKI_TAG);
        NavigationModel nav16 = new NavigationModel(sname16, Constants.TAGS.WIKI_TAG);
        NavigationModel nav17 = new NavigationModel(sname17, Constants.TAGS.WIKI_TAG);
        Collections.addAll(navs, nav0, nav1, nav2, nav3, nav4, nav5, nav6, nav7, nav8,
        		nav9, nav10, nav11, nav12, nav13, nav14, nav15, nav16, nav17);
    }

    private void initgoHome() {
        llGoHome.setOnClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname0);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname1);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname2);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname3);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname4);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname5);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname6);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname7);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname8);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname9);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname10);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname11);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname12);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname13);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname14);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname15);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname16);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, sname17);
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        return list;
    }

    // [end]

    // [start]继承方法
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.Linear_above_toHome:
                showMenu();
                break;
            case R.id.login_login:
                SharedPreferences share = this.getSharedPreferences(
                        UserLoginUidActivity.SharedName, Context.MODE_PRIVATE);
                // [start] 修复上一个bug
                String Key = share.getString(UserLoginUidActivity.KEY, "");
                if (Key != "" && !Key.contains(":")) {
                    Editor edit = share.edit();
                    edit.putString(UserLoginUidActivity.KEY, "");
                    edit.commit();
                }
                // [end] 下一版本删除掉
                if (share.contains(UserLoginUidActivity.KEY)
                        && !share.getString(UserLoginUidActivity.KEY, "")
                        .equals("")) {
                    IntentUtil.start_activity(this, UserCenterActivity.class);
                } else {
                    IntentUtil.start_activity(this, UserLoginActivity.class);
                }
                break;
            case R.id.imageview_above_more:
                if (isShowPopupWindows) {
                    new PopupWindowUtil(mViewPager).showActionWindow(v, this,
                            mBasePageAdapter.tabs);
                }
                break;
            case R.id.imageview_above_query:

                if (NetWorkHelper.isNetworkAvailable(MainActivity.this)) {
                    IntentUtil.start_activity(this, SearchActivity.class,
                            new BasicNameValuePair("tag", current_page));
                } else {
                    Toast.makeText(getApplicationContext(), "网络连接失败,请检查网络",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cbFeedback:
                FeedbackAgent agent = new FeedbackAgent(this);
                agent.startFeedbackActivity();
                break;
            case R.id.cbAbove:
                IntentUtil.start_activity(this, AboutActivity.class);
                break;
            case R.id.bn_refresh:
                switch (mTag) {
                    case 0:
                        imgQuery.setVisibility(View.GONE);
                        new MyTask().execute(s0Dao);
                        break;
                    case 1:
                    	new MyTask(sid1, 1).execute(s1Dao);
                        break;
                    case 2:
                    	new MyTask(sid2, 2).execute(s2Dao);
                        break;
                    case 3:
                    	new MyTask(sid3, 3).execute(s3Dao);
                        break;
                    case 4:
                    	new MyTask(sid4, 4).execute(s4Dao);
                        break;
                    case 5:
                    	new MyTask(sid5, 5).execute(s5Dao);
                        break;
                    case 6:
                    	new MyTask(sid6, 6).execute(s6Dao);
                        break;
                    case 7:
                    	new MyTask(sid7, 7).execute(s7Dao);
                        break;
                    case 8:
                    	new MyTask(sid8, 8).execute(s8Dao);
                        break;
                    case 9:
                    	new MyTask(sid9, 9).execute(s9Dao);
                        break;
                    case 10:
                    	new MyTask(sid10, 10).execute(s10Dao);
                        break;
                    case 11:
                    	new MyTask(sid11, 11).execute(s11Dao);
                        break;
                    case 12:
                    	new MyTask(sid12, 12).execute(s12Dao);
                        break;
                    case 13:
                    	new MyTask(sid13, 13).execute(s13Dao);
                        break;
                    case 14:
                    	new MyTask(sid14, 14).execute(s14Dao);
                        break;
                    case 15:
                    	new MyTask(sid15, 15).execute(s15Dao);
                        break;
                    case 16:
                    	new MyTask(sid16, 16).execute(s16Dao);
                        break;
                    case 17:
                    	new MyTask(sid17, 17).execute(s17Dao);
                        break;
                    default :
                    	break;
                }
                break;
        }

    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        keyBackClickCount = 0;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    Toast.makeText(this,
                            getResources().getString(R.string.press_again_exit),
                            Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                	/*
                    mFrameTv.setVisibility(View.VISIBLE);
                    mImgTv.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.tv_off);
                    anim.setAnimationListener(new tvOffAnimListener());
                    mImgTv.startAnimation(anim);
                    */
                	finish();
                	System.exit(0);
                    break;
                default:
                    break;
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (sm.isMenuShowing()) {
                toggle();
            } else {
                showMenu();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.dispatchTouchEvent(event);
        if (mIsAnim || mViewPager.getChildCount() <= 1) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                if (dX < 8 && dY > 8 && !mIsTitleHide && !down) {
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.push_top_in);
//				anim.setFillAfter(true);
                    anim.setAnimationListener(MainActivity.this);
                    title.startAnimation(anim);
                } else if (dX < 8 && dY > 8 && mIsTitleHide && down) {
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.push_top_out);
//				anim.setFillAfter(true);
                    anim.setAnimationListener(MainActivity.this);
                    title.startAnimation(anim);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // [end]

    /**
     * 加载分类list的task
     *
     * @author wangxin
     */
    public class MyTask extends AsyncTask<BaseDao, String, Map<String, Object>> {

        private boolean mUseCache;
        int mSid;

        public MyTask() {
            mUseCache = true;
            mSid = 0;
            mTag = 0;
        }
        
        public MyTask(int sid, int tag) {
        	mUseCache = true;
        	mSid = sid;
        	mTag = tag;
        }

        public MyTask(boolean useCache) {
            mUseCache = useCache;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            imgLeft.setVisibility(View.GONE);
            imgRight.setVisibility(View.GONE);
            loadLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            mViewPager.removeAllViews();
            mBasePageAdapter.Clear();
            MainActivity.this.showContent();
            super.onPreExecute();
            isShowPopupWindows = false;
        }

        @Override
        protected Map<String, Object> doInBackground(BaseDao... params) {
            BaseDao dao = params[0];
            List<CategorysEntity> categorys = new ArrayList<CategorysEntity>();
            Map<String, Object> map = new HashMap<String, Object>();
            if (dao instanceof TopDao) {
                mTag = 0;
                if ((categoryList = topDao.mapperJson(mUseCache)) != null) {

                    categorys = topDao.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof BlogsDao) {
                mTag = 3;
                if ((responseData = blogsDao.mapperJson(mUseCache)) != null) {
                    categoryList = (List) responseData.getList();
                    categorys = responseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof NewsDao) {
                mTag = 1;
                if ((newsResponseData = newsDao.mapperJson(mUseCache)) != null) {
                    categoryList = (List) newsResponseData.getList();
                    categorys = newsResponseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof WikiDao) {
                //mTag = 2;
                if ((wikiResponseData = wikiDao.mapperJson(mUseCache, mSid)) != null) {
                    categoryList = (List) wikiResponseData.getList();
                    categorys = wikiResponseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else {
                return null;
            }
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            isShowPopupWindows = true;
            mBasePageAdapter.Clear();
            mViewPager.removeAllViews();
            if (!result.isEmpty()) {
                mBasePageAdapter.addFragment((List) result.get("tabs"),
                        (List) result.get("list"));
                imgRight.setVisibility(View.VISIBLE);
                loadLayout.setVisibility(View.GONE);
                loadFaillayout.setVisibility(View.GONE);
            } else {
                mBasePageAdapter.addNullFragment();
                loadLayout.setVisibility(View.GONE);
                loadFaillayout.setVisibility(View.VISIBLE);
            }
            mViewPager.setVisibility(View.VISIBLE);
            mBasePageAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(0);
            mIndicator.notifyDataSetChanged();

        }
    }

    /**
     * viewPager切换页面
     *
     * @author mingxv
     */
    class MyPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            if (arg0 == 0) {
                getSlidingMenu().setTouchModeAbove(
                        SlidingMenu.TOUCHMODE_FULLSCREEN);
                imgLeft.setVisibility(View.GONE);
            } else if (arg0 == mBasePageAdapter.mFragments.size() - 1) {
                imgRight.setVisibility(View.GONE);
                getSlidingMenu()
                        .setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            } else {
                imgRight.setVisibility(View.VISIBLE);
                imgLeft.setVisibility(View.VISIBLE);
                getSlidingMenu()
                        .setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            }
        }
    }

    class tvOffAnimListener implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            defaultFinish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        if (mIsTitleHide) {
            title.setVisibility(View.GONE);
        } else {

        }
        mIsAnim = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
        title.setVisibility(View.VISIBLE);
        if (mIsTitleHide) {
            FrameLayout.LayoutParams lp = (LayoutParams) mlinear_listview
                    .getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            mlinear_listview.setLayoutParams(lp);
        } else {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) title
                    .getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            title.setLayoutParams(lp);
            FrameLayout.LayoutParams lp1 = (LayoutParams) mlinear_listview
                    .getLayoutParams();
            lp1.setMargins(0,
                    getResources().getDimensionPixelSize(R.dimen.title_height),
                    0, 0);
            mlinear_listview.setLayoutParams(lp1);
        }
    }

    private float lastX = 0;
    private float lastY = 0;

}
