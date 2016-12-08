package com.lxt.handlol.ui.fragment.herodata;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.lxt.handlol.R;
import com.lxt.handlol.adapter.filter.AllHeroAdapter;
import com.lxt.handlol.adapter.filter.WeekFreeAdapter;
import com.lxt.handlol.base.BaseFragment;
import com.lxt.handlol.module.filter.WeekFree;
import com.lxt.handlol.module.hero.AllHero;
import com.lxt.handlol.net.RetrofitHelper;
import com.lxt.handlol.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ${reallyCommon} on 2016/11/4 0004.
 * e-mail:871281347@qq.com
 */

public class WeekFreeFragment extends BaseFragment {
    @Bind(R.id.week_free0)
    GridView mWeekFree0;
    private WeekFreeAdapter adapter;
    private List<WeekFree.ListBean> list=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_week_free;
    }

    @Override
    public void initView() {
        RetrofitHelper.builder().getFreeInfo().getWeekFree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeekFree>() {
                    @Override
                    public void call(WeekFree allHero) {
                        if (allHero.getList() == null) {
                            LogUtil.e("加载英雄信息失败1");
                        } else {
                            LogUtil.e("这是英雄的数量" + allHero.getList().size());
                            list = allHero.getList();
                            adapter = new WeekFreeAdapter(getContext(), list);
                            mWeekFree0.setAdapter(adapter);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("加载英雄信息失败2" + throwable.getMessage());
                    }
                });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
